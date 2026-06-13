package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Categoria;
import java.util.List;

public class CategoriaView extends GridPane {

    private Label labelIdSelecionada;
    private TextField campoNome;
    private TextField campoDescricao;
    private TextField campoClassificacao;
    private Button botaoSalvar;
    private Button botaoExcluir;
    private Button botaoLimpar;

    private TableView<Categoria> tabelaCategorias;
    private ObservableList<Categoria> tabelaData;

    private Button botaoVoltar;

    public CategoriaView() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(20);
        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));

        construirFormularioUnificado();
        construirTabelaConsulta();
    }

    private void construirFormularioUnificado() {
        VBox colunaFormulario = new VBox(10);
        colunaFormulario.setAlignment(Pos.TOP_LEFT);

        Label titulo = new Label("Gestão de categorias");
        labelIdSelecionada = new Label("ID: nenhum selecionado");

        campoNome = new TextField();
        campoNome.setPromptText("Digite o nome");
        campoDescricao = new TextField();
        campoDescricao.setPromptText("Digite a descrição");
        campoClassificacao = new TextField();
        campoClassificacao.setPromptText("Digite a classificação");

        botaoSalvar = new Button("Salvar");
        botaoExcluir = new Button("Excluir");
        botaoExcluir.setStyle("-fx-text-fill: white; -fx-background-color: #d32f2f;");
        botaoExcluir.setDisable(true);

        botaoLimpar = new Button("Limpar");
        botaoVoltar = new Button("Voltar");

        HBox painelBotoes = new HBox(10);
        painelBotoes.setAlignment(Pos.CENTER_LEFT);
        painelBotoes.getChildren().addAll(botaoSalvar, botaoExcluir, botaoLimpar, botaoVoltar);

        colunaFormulario.getChildren().addAll(
                titulo,
                labelIdSelecionada,
                new Label("Nome:"), campoNome,
                new Label("Descrição:"), campoDescricao,
                new Label("Classificação:"), campoClassificacao,
                painelBotoes);

        this.add(colunaFormulario, 0, 0);
    }

    private void construirTabelaConsulta() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Categorias cadastradas");
        tabelaCategorias = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<Categoria, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Categoria, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(150);

        TableColumn<Categoria, String> colunaDescricao = new TableColumn<>("Descrição");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaDescricao.setPrefWidth(200);

        TableColumn<Categoria, String> colunaClassificacao = new TableColumn<>("Classificação");
        colunaClassificacao.setCellValueFactory(new PropertyValueFactory<>("classificacao"));
        colunaClassificacao.setPrefWidth(150);

        tabelaCategorias.getColumns().addAll(colunaId, colunaNome, colunaDescricao, colunaClassificacao);
        tabelaCategorias.setItems(tabelaData);
        tabelaCategorias.setPrefSize(560, 260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaCategorias);
        this.add(colunaTabela, 1, 0);
    }

    public String getNome() {
        return campoNome.getText();
    }

    public String getDescricao() {
        return campoDescricao.getText();
    }

    public String getClassificacao() {
        return campoClassificacao.getText();
    }

    public void atualizarTabela(List<Categoria> categorias) {
        try {
            if (categorias == null) {
                System.err.println("Erro: Lista de categorias é nula.");
                tabelaData.clear();
                return;
            }
            tabelaData.setAll(categorias);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TableView<Categoria> getTabelaCategorias() {
        return tabelaCategorias;
    }

    public Button getBotaoSalvar() {
        return botaoSalvar;
    }

    public Button getBotaoExcluir() {
        return botaoExcluir;
    }

    public Button getBotaoLimpar() {
        return botaoLimpar;
    }

    public Button getBotaoVoltar() {
        return botaoVoltar;
    }

    public void limparCampos() {
        campoNome.clear();
        campoDescricao.clear();
        campoClassificacao.clear();
    }

    public void preencherCampos(Categoria categoria) {
        try {
            if (categoria == null) {
                labelIdSelecionada.setText("ID: nenhum selecionado");
                limparCampos();
                botaoExcluir.setDisable(true);
                return;
            }

            // Validação de categoria inválida
            if (categoria.getNome() == null || categoria.getId() < 1) {
                System.err.println("Aviso: Categoria inválida.");
                limparCampos();
                return;
            }

            labelIdSelecionada.setText("ID: " + categoria.getId());
            campoNome.setText(categoria.getNome() != null ? categoria.getNome() : "");
            campoDescricao.setText(categoria.getDescricao() != null ? categoria.getDescricao() : "");
            campoClassificacao.setText(categoria.getClassificacao() != null ? categoria.getClassificacao() : "");
            botaoExcluir.setDisable(false);

        } catch (Exception e) {
            System.err.println("Erro ao preencher campos: " + e.getMessage());
            e.printStackTrace();
            limparCampos();
        }
    }

    public Categoria getCategoriaSelecionada() {
        return tabelaCategorias.getSelectionModel().getSelectedItem();
    }

    public void limparSelecao() {
        tabelaCategorias.getSelectionModel().clearSelection();
        preencherCampos(null);
    }
}
