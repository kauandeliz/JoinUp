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
import model.Suporte;

import java.util.List;

public class SuporteView extends GridPane {

    private Label labelIdSelecionado;
    private TextField campoNome;
    private TextField campoEmail;
    private TextField campoTelefone;
    private Button botaoSalvar;
    private Button botaoEditar;
    private Button botaoExcluir;
    private Button botaoLimpar;

    private TableView<Suporte> tabelaSuportes;
    private ObservableList<Suporte> tabelaData;

    private Button botaoVoltar;

    public SuporteView() {
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

        Label titulo = new Label("Gestão de suportes");
        labelIdSelecionado = new Label("ID: nenhum selecionado");

        campoNome = new TextField();
        campoNome.setPromptText("Digite o nome");
        campoEmail = new TextField();
        campoEmail.setPromptText("Digite o e-mail");
        campoTelefone = new TextField();
        campoTelefone.setPromptText("Digite o telefone");

        botaoSalvar = new Button("Salvar");
        botaoEditar = new Button("Editar");
        botaoEditar.setDisable(true);
        botaoExcluir = new Button("Excluir");
        botaoExcluir.setStyle("-fx-text-fill: white; -fx-background-color: #d32f2f;");
        botaoExcluir.setDisable(true);

        botaoLimpar = new Button("Limpar");
        botaoVoltar = new Button("Voltar");

        HBox painelBotoes = new HBox(10);
        painelBotoes.setAlignment(Pos.CENTER_LEFT);
        painelBotoes.getChildren().addAll(botaoSalvar, botaoEditar, botaoExcluir, botaoLimpar, botaoVoltar);

        colunaFormulario.getChildren().addAll(
                titulo,
                labelIdSelecionado,
                new Label("Nome:"), campoNome,
                new Label("E-mail:"), campoEmail,
                new Label("Telefone:"), campoTelefone,
                painelBotoes);

        this.add(colunaFormulario, 0, 0);
    }

    private void construirTabelaConsulta() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Suportes cadastrados");
        tabelaSuportes = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<Suporte, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Suporte, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(150);

        TableColumn<Suporte, String> colunaContato = new TableColumn<>("E-mail");
        colunaContato.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaContato.setPrefWidth(180);

        TableColumn<Suporte, String> colunaTelefone = new TableColumn<>("Telefone");
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaTelefone.setPrefWidth(120);

        tabelaSuportes.getColumns().addAll(colunaId, colunaNome, colunaContato, colunaTelefone);
        tabelaSuportes.setItems(tabelaData);
        tabelaSuportes.setPrefSize(620, 260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaSuportes);
        this.add(colunaTabela, 1, 0);
    }

    public String getNome() {
        return campoNome.getText();
    }

    public String getEmail() {
        return campoEmail.getText();
    }

    public String getTelefone() {
        return campoTelefone.getText();
    }

    public void atualizarTabela(List<Suporte> suportes) {
        try {
            if (suportes == null) {
                System.err.println("Erro: Lista de suportes e nula.");
                tabelaData.clear();
                return;
            }
            tabelaData.setAll(suportes);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TableView<Suporte> getTabelaSuportes() {
        return tabelaSuportes;
    }

    public Button getBotaoSalvar() {
        return botaoSalvar;
    }

    public Button getBotaoEditar() {
        return botaoEditar;
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
        campoEmail.clear();
        campoTelefone.clear();
    }

    public void preencherCampos(Suporte suporte) {
        try {
            if (suporte == null) {
                labelIdSelecionado.setText("ID: nenhum selecionado");
                limparCampos();
                botaoEditar.setDisable(true);
                botaoExcluir.setDisable(true);
                return;
            }

            if (suporte.getNome() == null || suporte.getId() < 1) {
                System.err.println("Aviso: Suporte invalido.");
                limparCampos();
                return;
            }

            labelIdSelecionado.setText("ID: " + suporte.getId());
            campoNome.setText(suporte.getNome() != null ? suporte.getNome() : "");
            campoEmail.setText(suporte.getEmail() != null ? suporte.getEmail() : "");
            campoTelefone.setText(suporte.getTelefone() != null ? suporte.getTelefone() : "");
            botaoEditar.setDisable(false);
            botaoExcluir.setDisable(false);

        } catch (Exception e) {
            System.err.println("Erro ao preencher campos: " + e.getMessage());
            e.printStackTrace();
            limparCampos();
        }
    }

    public Suporte getSuporteSelecionado() {
        return tabelaSuportes.getSelectionModel().getSelectedItem();
    }

    public void limparSelecao() {
        tabelaSuportes.getSelectionModel().clearSelection();
        preencherCampos(null);
    }
}
