package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Artista;
import view.ArtistaView;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller do CRUD de Artista.
 *
 * Esta classe liga a tela ArtistaView ao model Artista e controla:
 * inclusão, consulta, atualização, exclusão e persistência em arquivo.
 */
public class ArtistaController {

    private static final String STORAGE_FILE = "artistas.ser";

    private ArtistaView view;
    private List<Artista> artistas;

    public ArtistaController(ArtistaView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.artistas = carregarArtistas();

        // Mostra na tabela os artistas já salvos no arquivo.
        this.view.atualizarTabela(artistas);

        // Eventos dos botões da tela.
        this.view.getBotaoSalvar().setOnAction(event -> salvarOuAtualizarArtista());
        this.view.getBotaoExcluir().setOnAction(event -> excluirArtista());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        // Ao clicar em uma linha da tabela, os campos são preenchidos para consulta/edição.
        this.view.getTabelaArtistas().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    /**
     * Decide se a ação será cadastro ou atualização.
     * Se não houver item selecionado, cria um novo artista.
     * Se houver item selecionado, atualiza o registro existente.
     */
    private void salvarOuAtualizarArtista() {
        try {
            String nome = view.getNome();
            String generoMusical = view.getGeneroMusical();
            String cidadeOrigem = view.getCidadeOrigem();

            if (nome == null || nome.trim().isEmpty()) {
                mostrarErro("Nome do artista é obrigatório.");
                return;
            }

            if (generoMusical == null || generoMusical.trim().isEmpty()) {
                mostrarErro("Gênero musical é obrigatório.");
                return;
            }

            if (cidadeOrigem == null || cidadeOrigem.trim().isEmpty()) {
                mostrarErro("Cidade de origem é obrigatória.");
                return;
            }

            Artista selecionado = view.getArtistaSelecionado();

            if (selecionado == null) {
                Artista novoArtista = new Artista(nome.trim(), generoMusical.trim(), cidadeOrigem.trim());
                artistas.add(novoArtista);
                System.out.println("Artista criado: " + novoArtista);
            } else {
                selecionado.setNome(nome.trim());
                selecionado.setGeneroMusical(generoMusical.trim());
                selecionado.setCidadeOrigem(cidadeOrigem.trim());
                System.out.println("Artista atualizado: " + selecionado);
            }

            persistirArtistas();
            view.atualizarTabela(artistas);
            view.limparSelecao();
            mostrarSucesso("Artista salvo com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao salvar artista: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Remove o artista selecionado na tabela.
     */
    private void excluirArtista() {
        try {
            Artista selecionado = view.getArtistaSelecionado();

            if (selecionado == null) {
                mostrarErro("Selecione um artista para excluir.");
                return;
            }

            artistas.remove(selecionado);
            persistirArtistas();
            view.atualizarTabela(artistas);
            view.limparSelecao();
            mostrarSucesso("Artista excluído com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao excluir artista: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Salva a lista de artistas em arquivo usando serialização.
     */
    private void persistirArtistas() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(artistas);
            System.out.println("Artistas salvos com sucesso.");

        } catch (IOException e) {
            mostrarErro("Erro ao salvar o arquivo de artistas: " + e.getMessage());
        }
    }

    /**
     * Carrega os artistas salvos no arquivo .ser.
     * Caso o arquivo ainda não exista, retorna uma lista vazia.
     */
    private List<Artista> carregarArtistas() {
        try (FileInputStream fis = new FileInputStream(STORAGE_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object objeto = ois.readObject();

            if (!(objeto instanceof List)) {
                System.err.println("Arquivo de artistas não contém uma lista válida.");
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            List<Artista> lista = (List<Artista>) objeto;
            ajustarNextId(lista);
            System.out.println("Artistas carregados: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de artistas não existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de artistas vazio. Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Classe Artista não encontrada: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler artistas: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar artistas: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    /**
     * Ajusta o próximo ID com base no maior ID já salvo.
     */
    private void ajustarNextId(List<Artista> lista) {
        int maiorId = 0;
        for (Artista artista : lista) {
            if (artista.getId() > maiorId) {
                maiorId = artista.getId();
            }
        }
        Artista.setNextId(maiorId + 1);
    }

    private void mostrarErro(String mensagem) {
        System.err.println("[ERRO] " + mensagem);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Operação não pode ser concluída");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarSucesso(String mensagem) {
        System.out.println("[SUCESSO] " + mensagem);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
