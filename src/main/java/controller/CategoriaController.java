package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Categoria;
import view.CategoriaView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    private static final String STORAGE_FILE = "categorias.ser";

    private CategoriaView view;
    private List<Categoria> categorias;

    public CategoriaController(CategoriaView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.categorias = carregarCategorias();

        // atualiza a tabela com os dados persistidos
        this.view.atualizarTabela(categorias);

        // configura os botões
        this.view.getBotaoSalvar().setOnAction(event -> salvarOuAtualizarCategoria());
        this.view.getBotaoExcluir().setOnAction(event -> excluirCategoria());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        // atualiza campos do formulário quando o usuário seleciona uma linha na tabela
        this.view.getTabelaCategorias().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    private void salvarOuAtualizarCategoria() {
        try {
            String nome = view.getNome();
            String descricao = view.getDescricao();
            String classificacao = view.getClassificacao();

            // Validações
            if (nome == null || nome.trim().isEmpty()) {
                mostrarErro("Nome da categoria é obrigatório.");
                return;
            }

            if (nome.length() > 100) {
                mostrarErro("Nome não pode exceder 100 caracteres.");
                return;
            }

            Categoria selecionada = view.getCategoriaSelecionada();

            if (selecionada == null) {
                // Criar nova categoria
                Categoria novaCategoria = new Categoria(nome, descricao, classificacao);
                categorias.add(novaCategoria);
                System.out.println("Categoria criada: " + novaCategoria);
            } else {
                // Atualizar categoria existente
                selecionada.setNome(nome);
                selecionada.setDescricao(descricao);
                selecionada.setClassificacao(classificacao);
                System.out.println("Categoria atualizada: " + selecionada);
            }

            persistirCategorias();
            view.atualizarTabela(categorias);
            view.limparSelecao();
            mostrarSucesso("Categoria salva com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao salvar categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirCategoria() {
        Categoria selecionada = view.getCategoriaSelecionada();
        if (selecionada == null) {
            System.out.println("Selecione uma categoria para excluir.");
            return;
        }

        categorias.remove(selecionada);
        persistirCategorias();
        view.atualizarTabela(categorias);
        view.limparSelecao();
        System.out.println("Categoria excluída: " + selecionada);
    }

    private void persistirCategorias() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            if (categorias == null) {
                System.err.println("Erro: Lista de categorias é nula.");
                return;
            }

            oos.writeObject(categorias);
            System.out.println("Categorias salvas com sucesso.");

        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de categorias não encontrado - " + STORAGE_FILE);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao salvar categorias: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar categorias: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Categoria> carregarCategorias() {
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
            List<Categoria> lista = (List<Categoria>) objeto;

            if (lista.isEmpty()) {
                System.out.println("Nenhuma categoria carregada.");
                return lista;
            }

            ajustarNextId(lista);
            System.out.println("Categorias carregadas: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de categorias não existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de categorias está corrompido (vazio). Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe de categoria não encontrada - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de categorias: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar categorias: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<Categoria> lista) {
        int maiorId = 0;
        for (Categoria categoria : lista) {
            if (categoria.getId() > maiorId) {
                maiorId = categoria.getId();
            }
        }
        Categoria.setNextId(maiorId + 1);
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
}
