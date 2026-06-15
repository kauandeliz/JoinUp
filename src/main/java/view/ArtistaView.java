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
import model.Artista;

import java.util.List;

/**
 * Tela JavaFX responsável pelo CRUD de Artista.
 *
 * Importante para a atividade: todos os componentes são criados por código.
 * Não há uso de FXML, FXMLLoader ou SceneBuilder.
 */
public class ArtistaView extends GridPane {

    private Label labelIdSelecionado;
    private TextField campoNome;
    private TextField campoGeneroMusical;
    private TextField campoCidadeOrigem;

    private Button botaoSalvar;
    private Button botaoExcluir;
    private Button botaoLimpar;
    private Button botaoVoltar;

    private TableView<Artista> tabelaArtistas;
    private ObservableList<Artista> tabelaData;

    public ArtistaView() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(20);
        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));

        construirFormulario();
        construirTabela();
    }

    /**
     * Monta os campos de cadastro/edição e os botões da tela.
     */
    private void construirFormulario() {
        VBox colunaFormulario = new VBox(10);
        colunaFormulario.setAlignment(Pos.TOP_LEFT);

        Label titulo = new Label("Gestão de artistas");
        labelIdSelecionado = new Label("ID: nenhum selecionado");

        campoNome = new TextField();
        campoNome.setPromptText("Digite o nome do artista");

        campoGeneroMusical = new TextField();
        campoGeneroMusical.setPromptText("Ex.: Sertanejo, Rock, Funk, Pop");

        campoCidadeOrigem = new TextField();
        campoCidadeOrigem.setPromptText("Digite a cidade de origem");

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
                labelIdSelecionado,
                new Label("Nome:"), campoNome,
                new Label("Gênero musical:"), campoGeneroMusical,
                new Label("Cidade de origem:"), campoCidadeOrigem,
                painelBotoes
        );

        this.add(colunaFormulario, 0, 0);
    }

    /**
     * Monta a tabela de consulta dos artistas cadastrados.
     */
    private void construirTabela() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Artistas cadastrados");
        tabelaArtistas = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<Artista, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Artista, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(170);

        TableColumn<Artista, String> colunaGenero = new TableColumn<>("Gênero musical");
        colunaGenero.setCellValueFactory(new PropertyValueFactory<>("generoMusical"));
        colunaGenero.setPrefWidth(150);

        TableColumn<Artista, String> colunaCidade = new TableColumn<>("Cidade origem");
        colunaCidade.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));
        colunaCidade.setPrefWidth(150);

        tabelaArtistas.getColumns().addAll(colunaId, colunaNome, colunaGenero, colunaCidade);
        tabelaArtistas.setItems(tabelaData);
        tabelaArtistas.setPrefWidth(540);
        tabelaArtistas.setPrefHeight(260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaArtistas);
        this.add(colunaTabela, 1, 0);
    }

    /**
     * Atualiza a tabela sempre que o controller salva, altera ou exclui dados.
     */
    public void atualizarTabela(List<Artista> artistas) {
        tabelaData.clear();
        tabelaData.addAll(artistas);
    }

    /**
     * Preenche o formulário com o item selecionado na tabela.
     */
    public void preencherCampos(Artista artista) {
        if (artista == null) {
            limparSelecao();
            return;
        }

        labelIdSelecionado.setText("ID: " + artista.getId());
        campoNome.setText(artista.getNome());
        campoGeneroMusical.setText(artista.getGeneroMusical());
        campoCidadeOrigem.setText(artista.getCidadeOrigem());
        botaoExcluir.setDisable(false);
    }

    /**
     * Limpa o formulário e remove a seleção da tabela.
     */
    public void limparSelecao() {
        tabelaArtistas.getSelectionModel().clearSelection();
        labelIdSelecionado.setText("ID: nenhum selecionado");
        campoNome.clear();
        campoGeneroMusical.clear();
        campoCidadeOrigem.clear();
        botaoExcluir.setDisable(true);
    }

    public String getNome() {
        return campoNome.getText();
    }

    public String getGeneroMusical() {
        return campoGeneroMusical.getText();
    }

    public String getCidadeOrigem() {
        return campoCidadeOrigem.getText();
    }

    public Artista getArtistaSelecionado() {
        return tabelaArtistas.getSelectionModel().getSelectedItem();
    }

    public TableView<Artista> getTabelaArtistas() {
        return tabelaArtistas;
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
}
