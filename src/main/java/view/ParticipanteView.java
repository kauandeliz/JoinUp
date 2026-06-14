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
import model.Participante;

import java.util.List;

public class ParticipanteView extends GridPane {

    private Label labelIdSelecionado;
    private TextField campoNome;
    private TextField campoEmail;
    private TextField campoCpf;
    private TextField campoTelefone;
    private Button botaoSalvar;
    private Button botaoEditar;
    private Button botaoExcluir;
    private Button botaoLimpar;

    private TableView<Participante> tabelaParticipantes;
    private ObservableList<Participante> tabelaData;

    private Button botaoVoltar;

    public ParticipanteView() {
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

        Label titulo = new Label("Gestao de participantes");
        labelIdSelecionado = new Label("ID: nenhum selecionado");

        campoNome = new TextField();
        campoNome.setPromptText("Digite o nome");
        campoEmail = new TextField();
        campoEmail.setPromptText("Digite o email");
        campoCpf = new TextField();
        campoCpf.setPromptText("Digite o CPF");
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
                new Label("Email:"), campoEmail,
                new Label("CPF:"), campoCpf,
                new Label("Telefone:"), campoTelefone,
                painelBotoes);

        this.add(colunaFormulario, 0, 0);
    }

    private void construirTabelaConsulta() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Participantes cadastrados");
        tabelaParticipantes = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<Participante, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Participante, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(150);

        TableColumn<Participante, String> colunaEmail = new TableColumn<>("Email");
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaEmail.setPrefWidth(180);

        TableColumn<Participante, String> colunaCpf = new TableColumn<>("CPF");
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaCpf.setPrefWidth(120);

        TableColumn<Participante, String> colunaTelefone = new TableColumn<>("Telefone");
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaTelefone.setPrefWidth(120);

        tabelaParticipantes.getColumns().addAll(colunaId, colunaNome, colunaEmail, colunaCpf, colunaTelefone);
        tabelaParticipantes.setItems(tabelaData);
        tabelaParticipantes.setPrefSize(620, 260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaParticipantes);
        this.add(colunaTabela, 1, 0);
    }

    public String getNome() {
        return campoNome.getText();
    }

    public String getEmail() {
        return campoEmail.getText();
    }

    public String getCpf() {
        return campoCpf.getText();
    }

    public String getTelefone() {
        return campoTelefone.getText();
    }

    public void atualizarTabela(List<Participante> participantes) {
        try {
            if (participantes == null) {
                System.err.println("Erro: Lista de participantes e nula.");
                tabelaData.clear();
                return;
            }
            tabelaData.setAll(participantes);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TableView<Participante> getTabelaParticipantes() {
        return tabelaParticipantes;
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
        campoCpf.clear();
        campoTelefone.clear();
    }

    public void preencherCampos(Participante participante) {
        try {
            if (participante == null) {
                labelIdSelecionado.setText("ID: nenhum selecionado");
                limparCampos();
                botaoEditar.setDisable(true);
                botaoExcluir.setDisable(true);
                return;
            }

            if (participante.getNome() == null || participante.getId() < 1) {
                System.err.println("Aviso: Participante invalido.");
                limparCampos();
                return;
            }

            labelIdSelecionado.setText("ID: " + participante.getId());
            campoNome.setText(participante.getNome() != null ? participante.getNome() : "");
            campoEmail.setText(participante.getEmail() != null ? participante.getEmail() : "");
            campoCpf.setText(participante.getCpf() != null ? participante.getCpf() : "");
            campoTelefone.setText(participante.getTelefone() != null ? participante.getTelefone() : "");
            botaoEditar.setDisable(false);
            botaoExcluir.setDisable(false);

        } catch (Exception e) {
            System.err.println("Erro ao preencher campos: " + e.getMessage());
            e.printStackTrace();
            limparCampos();
        }
    }

    public Participante getParticipanteSelecionado() {
        return tabelaParticipantes.getSelectionModel().getSelectedItem();
    }

    public void limparSelecao() {
        tabelaParticipantes.getSelectionModel().clearSelection();
        preencherCampos(null);
    }
}
