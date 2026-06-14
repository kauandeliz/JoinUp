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
import model.Organizador;

import java.util.List;

public class OrganizadorView extends GridPane {

    private Label labelIdSelecionado;
    private TextField campoNome;
    private TextField campoEmail;
    private TextField campoTelefone;
    private TextField campoEmpresa;
    private Button botaoSalvar;
    private Button botaoEditar;
    private Button botaoExcluir;
    private Button botaoLimpar;

    private TableView<Organizador> tabelaOrganizadores;
    private ObservableList<Organizador> tabelaData;

    private Button botaoVoltar;

    public OrganizadorView() {
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

        Label titulo = new Label("Gestao de organizadores");
        labelIdSelecionado = new Label("ID: nenhum selecionado");

        campoNome = new TextField();
        campoNome.setPromptText("Digite o nome");
        campoEmail = new TextField();
        campoEmail.setPromptText("Digite o email");
        campoTelefone = new TextField();
        campoTelefone.setPromptText("Digite o telefone");
        campoEmpresa = new TextField();
        campoEmpresa.setPromptText("Digite a empresa");

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
                new Label("Email:"), campoEmail,
                new Label("Telefone:"), campoTelefone,
                new Label("Empresa:"), campoEmpresa,
                painelBotoes);

        this.add(colunaFormulario, 0, 0);
    }

    private void construirTabelaConsulta() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Organizadores cadastrados");
        tabelaOrganizadores = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<Organizador, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Organizador, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(150);

        TableColumn<Organizador, String> colunaEmail = new TableColumn<>("Email");
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaEmail.setPrefWidth(180);

        TableColumn<Organizador, String> colunaTelefone = new TableColumn<>("Telefone");
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaTelefone.setPrefWidth(120);

        TableColumn<Organizador, String> colunaEmpresa = new TableColumn<>("Empresa");
        colunaEmpresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        colunaEmpresa.setPrefWidth(150);

        tabelaOrganizadores.getColumns().addAll(colunaId, colunaNome, colunaEmail, colunaTelefone, colunaEmpresa);
        tabelaOrganizadores.setItems(tabelaData);
        tabelaOrganizadores.setPrefSize(620, 260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaOrganizadores);
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

    public String getEmpresa() {
        return campoEmpresa.getText();
    }

    public void atualizarTabela(List<Organizador> organizadores) {
        try {
            if (organizadores == null) {
                System.err.println("Erro: Lista de organizadores e nula.");
                tabelaData.clear();
                return;
            }
            tabelaData.setAll(organizadores);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TableView<Organizador> getTabelaOrganizadores() {
        return tabelaOrganizadores;
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
        campoEmpresa.clear();
    }

    public void preencherCampos(Organizador organizador) {
        try {
            if (organizador == null) {
                labelIdSelecionado.setText("ID: nenhum selecionado");
                limparCampos();
                botaoEditar.setDisable(true);
                botaoExcluir.setDisable(true);
                return;
            }

            if (organizador.getNome() == null || organizador.getId() < 1) {
                System.err.println("Aviso: Organizador invalido.");
                limparCampos();
                return;
            }

            labelIdSelecionado.setText("ID: " + organizador.getId());
            campoNome.setText(organizador.getNome() != null ? organizador.getNome() : "");
            campoEmail.setText(organizador.getEmail() != null ? organizador.getEmail() : "");
            campoTelefone.setText(organizador.getTelefone() != null ? organizador.getTelefone() : "");
            campoEmpresa.setText(organizador.getEmpresa() != null ? organizador.getEmpresa() : "");
            botaoEditar.setDisable(false);
            botaoExcluir.setDisable(false);

        } catch (Exception e) {
            System.err.println("Erro ao preencher campos: " + e.getMessage());
            e.printStackTrace();
            limparCampos();
        }
    }

    public Organizador getOrganizadorSelecionado() {
        return tabelaOrganizadores.getSelectionModel().getSelectedItem();
    }

    public void limparSelecao() {
        tabelaOrganizadores.getSelectionModel().clearSelection();
        preencherCampos(null);
    }
}
