package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Categoria;
import model.Evento;
import view.EventoView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventoController {

    private static final String STORAGE_FILE = "Eventos.ser";
    private static final String STORAGE_FILE_CATEGORIAS = "categorias.ser";

    private EventoView view;
    private List<Evento> eventos;
    private List<Categoria> categorias;

    public EventoController(EventoView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.eventos = carregarEventos();
        this.categorias = carregarCategorias();

        // atualiza a combobox com as categorias disponíveis
        this.view.atualizarCategorias(categorias);

        // atualiza a tabela com os dados persistidos
        this.view.atualizarTabela(eventos);

        // configura os botões
        this.view.getBotaoSalvar().setOnAction(event -> salvarOuAtualizarEvento());
        this.view.getBotaoExcluir().setOnAction(event -> excluirEvento());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        // atualiza campos do formulário quando o usuário seleciona uma linha na tabela
        this.view.getTabelaEventos().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    private void salvarOuAtualizarEvento() {
        try {
            String nome = view.getNome();
            String data = view.getData();
            String endereco = view.getEndereco();
            Categoria categoriaSelecionada = view.getCategoriaSelecionada();

            // Validações
            if (nome == null || nome.trim().isEmpty()) {
                mostrarErro("Nome do evento é obrigatório.");
                return;
            }

            if (nome.length() > 100) {
                mostrarErro("Nome não pode exceder 100 caracteres.");
                return;
            }

            if (categoriaSelecionada == null) {
                mostrarErro("Selecione uma categoria para o evento.");
                return;
            }

            Evento selecionado = view.getEventoSelecionado();

            if (selecionado == null) {
                // Criar novo evento
                Evento novoEvento = new Evento(nome, data, endereco, categoriaSelecionada.getId());
                eventos.add(novoEvento);
                System.out.println("Evento criado: " + novoEvento);
            } else {
                // Atualizar evento existente
                selecionado.setNome(nome);
                selecionado.setData(data);
                selecionado.setEndereco(endereco);
                selecionado.setIdCategoria(categoriaSelecionada.getId());
                System.out.println("Evento atualizado: " + selecionado);
            }

            persistirEventos();
            view.atualizarTabela(eventos);
            view.limparSelecao();
            mostrarSucesso("Evento salvo com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao salvar evento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirEvento() {
        Evento selecionado = view.getEventoSelecionado();
        if (selecionado == null) {
            System.out.println("Selecione um evento para excluir.");
            return;
        }

        eventos.remove(selecionado);
        persistirEventos();
        view.atualizarTabela(eventos);
        view.limparSelecao();
        System.out.println("Evento excluído: " + selecionado);
    }

    private void persistirEventos() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            if (eventos == null) {
                System.err.println("Erro: Lista de eventos é nula.");
                return;
            }

            oos.writeObject(eventos);
            System.out.println("Eventos salvos com sucesso.");

        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de eventos não encontrado - " + STORAGE_FILE);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao salvar eventos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar eventos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Evento> carregarEventos() {
        try (FileInputStream fis = new FileInputStream(STORAGE_FILE);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object objeto = ois.readObject();

            if (objeto == null) {
                System.out.println("Arquivo existe mas está vazio. Iniciando lista vazia.");
                return new ArrayList<>();
            }

            if (!(objeto instanceof List)) {
                System.err.println("Erro: Arquivo não contém uma lista válida.");
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            List<Evento> lista = (List<Evento>) objeto;

            if (lista.isEmpty()) {
                System.out.println("Nenhum evento carregado.");
                return lista;
            }

            ajustarNextId(lista);
            System.out.println("Eventos carregados: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de eventos não existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de eventos está corrompido (vazio). Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe de evento não encontrada - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de eventos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar eventos: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<Evento> lista) {
        int maiorId = 0;
        for (Evento evento : lista) {
            if (evento.getId() > maiorId) {
                maiorId = evento.getId();
            }
        }
        Evento.setNextId(maiorId + 1);
    }

    private List<Categoria> carregarCategorias() {
        try (FileInputStream fis = new FileInputStream(STORAGE_FILE_CATEGORIAS);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object objeto = ois.readObject();

            if (objeto == null) {
                System.out.println("Arquivo de categorias existe mas está vazio.");
                return new ArrayList<>();
            }

            if (!(objeto instanceof List)) {
                System.err.println("Erro: Arquivo de categorias não contém uma lista válida.");
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            List<Categoria> lista = (List<Categoria>) objeto;

            System.out.println("Categorias carregadas: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de categorias não encontrado. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de categorias está corrompido. Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe de categoria não encontrada - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de categorias: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar categorias: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // Métodos auxiliares para exibir mensagens
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

    public void recarregarCategorias() {
        this.categorias = carregarCategorias();
        this.view.atualizarCategorias(categorias);
    }
}
