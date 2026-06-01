package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Categoria;
import view.CategoriaView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        this.view.getBotaoSalvar().setOnAction(event -> salvarCategoria());
        this.view.getBotaoAtualizar().setOnAction(event -> atualizarCategoria());
        this.view.getBotaoExcluir().setOnAction(event -> excluirCategoria());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        // atualiza campos do painel de edição quando o usuário seleciona uma linha
        this.view.getTabelaCategorias().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCamposSelecao(selecionado));
    }

    private void salvarCategoria() {
        String nome = view.getNomeDigitado();
        String descricao = view.getDescricaoDigitada();
        String classificacao = view.getClassificacaoDigitada();

        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome da categoria é obrigatório.");
            return;
        }

        Categoria categoria = new Categoria(nome, descricao, classificacao);
        categorias.add(categoria);
        persistirCategorias();

        view.atualizarTabela(categorias);
        view.limparCamposCadastro();
        System.out.println("Categoria salva: " + categoria);
    }

    private void atualizarCategoria() {
        Categoria selecionada = view.getTabelaCategorias().getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            System.out.println("Selecione uma categoria para atualizar.");
            return;
        }

        String nome = view.getNomeSelecionado();
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome da categoria é obrigatório para atualização.");
            return;
        }

        selecionada.setNome(nome);
        selecionada.setDescricao(view.getDescricaoSelecionada());
        selecionada.setClassificacao(view.getClassificacaoSelecionada());

        persistirCategorias();
        view.atualizarTabela(categorias);
        System.out.println("Categoria atualizada: " + selecionada);
    }

    private void excluirCategoria() {
        Categoria selecionada = view.getTabelaCategorias().getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            System.out.println("Selecione uma categoria para excluir.");
            return;
        }

        categorias.remove(selecionada);
        persistirCategorias();
        view.atualizarTabela(categorias);
        view.limparCamposSelecao();
        System.out.println("Categoria excluída: " + selecionada);
    }

    private void persistirCategorias() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(categorias);
        } catch (IOException e) {
            System.out.println("Erro ao salvar categorias: " + e.getMessage());
        }
    }

    private List<Categoria> carregarCategorias() {
        try (FileInputStream fis = new FileInputStream(STORAGE_FILE);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object objeto = ois.readObject();
            if (objeto instanceof List) {
                @SuppressWarnings("unchecked")
                List<Categoria> lista = (List<Categoria>) objeto;
                ajustarNextId(lista);
                System.out.println("Categorias carregadas: " + lista.size());
                return lista;
            }

            System.out.println("Arquivo de categorias não contém lista válida. Iniciando vazio.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Arquivo de persistência não encontrado ou inválido. Iniciando lista vazia.");
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
}
