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
import javafx.scene.layout.VBox;
import model.Categoria;
import java.util.List;

public class CategoriaView extends GridPane {

    private TextField campoNome;
    private TextField campoDescricao;
    private TextField campoClassificacao;
    private Button botaoSalvar;

    private TableView<Categoria> tabelaCategorias;
    private ObservableList<Categoria> tabelaData;

    private Label labelIdSelecionada;
    private TextField campoNomeSelecionado;
    private TextField campoDescricaoSelecionada;
    private TextField campoClassificacaoSelecionada;
    private Button botaoAtualizar;
    private Button botaoExcluir;
    private Button botaoVoltar;

    public CategoriaView() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(20);
        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));

        construirFormularioCadastro();
        construirTabelaConsulta();
        construirPainelEdicao();
    }

    private void construirFormularioCadastro() {
        VBox colunaCadastro = new VBox(10);
        colunaCadastro.setAlignment(Pos.TOP_LEFT);

        Label tituloCadastro = new Label("Cadastro de categoria");
        campoNome = new TextField();
        campoDescricao = new TextField();
        campoClassificacao = new TextField();
        botaoSalvar = new Button("Salvar");
        botaoVoltar = new Button("Voltar");

        colunaCadastro.getChildren().addAll(
                tituloCadastro,
                new Label("Nome:"), campoNome,
                new Label("Descrição:"), campoDescricao,
                new Label("Classificação:"), campoClassificacao,
                botaoSalvar,
                botaoVoltar);

        this.add(colunaCadastro, 0, 0);
    }

    private void construirTabelaConsulta() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Consulta de categorias");
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

    private void construirPainelEdicao() {
        VBox colunaEdicao = new VBox(10);
        colunaEdicao.setAlignment(Pos.TOP_LEFT);

        Label tituloEdicao = new Label("Seleção para atualizar / excluir");
        labelIdSelecionada = new Label("ID selecionado: nenhum");
        campoNomeSelecionado = new TextField();
        campoDescricaoSelecionada = new TextField();
        campoClassificacaoSelecionada = new TextField();
        botaoAtualizar = new Button("Atualizar");
        botaoExcluir = new Button("Excluir");

        colunaEdicao.getChildren().addAll(
                tituloEdicao,
                labelIdSelecionada,
                new Label("Nome:"), campoNomeSelecionado,
                new Label("Descrição:"), campoDescricaoSelecionada,
                new Label("Classificação:"), campoClassificacaoSelecionada,
                botaoAtualizar,
                botaoExcluir);

        this.add(colunaEdicao, 2, 0);
    }

    public String getNomeDigitado() {
        return campoNome.getText();
    }

    public String getDescricaoDigitada() {
        return campoDescricao.getText();
    }

    public String getClassificacaoDigitada() {
        return campoClassificacao.getText();
    }

    public String getNomeSelecionado() {
        return campoNomeSelecionado.getText();
    }

    public String getDescricaoSelecionada() {
        return campoDescricaoSelecionada.getText();
    }

    public String getClassificacaoSelecionada() {
        return campoClassificacaoSelecionada.getText();
    }

    public void atualizarTabela(List<Categoria> categorias) {
        tabelaData.setAll(categorias);
    }

    public TableView<Categoria> getTabelaCategorias() {
        return tabelaCategorias;
    }

    public Button getBotaoSalvar() {
        return botaoSalvar;
    }

    public Button getBotaoAtualizar() {
        return botaoAtualizar;
    }

    public Button getBotaoExcluir() {
        return botaoExcluir;
    }

    public Button getBotaoVoltar() {
        return botaoVoltar;
    }

    public void limparCamposCadastro() {
        campoNome.clear();
        campoDescricao.clear();
        campoClassificacao.clear();
    }

    public void limparCamposSelecao() {
        labelIdSelecionada.setText("ID selecionado: nenhum");
        campoNomeSelecionado.clear();
        campoDescricaoSelecionada.clear();
        campoClassificacaoSelecionada.clear();
        tabelaCategorias.getSelectionModel().clearSelection();
    }

    public void preencherCamposSelecao(Categoria categoria) {
        if (categoria == null) {
            limparCamposSelecao();
            return;
        }

        labelIdSelecionada.setText("ID selecionado: " + categoria.getId());
        campoNomeSelecionado.setText(categoria.getNome());
        campoDescricaoSelecionada.setText(categoria.getDescricao());
        campoClassificacaoSelecionada.setText(categoria.getClassificacao());
    }
}
