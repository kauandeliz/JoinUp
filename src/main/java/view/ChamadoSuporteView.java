package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ChamadoSuporte;

import java.util.List;

public class ChamadoSuporteView extends GridPane {

    private Label labelIdSelecionado;
    private TextField campoTitulo;
    private TextField campoDescricao;
    private ComboBox<String> campoPrioridade;
    private Button botaoSalvar;
    private Button botaoEditar;
    private Button botaoExcluir;
    private Button botaoLimpar;

    private TableView<ChamadoSuporte> tabelaChamados;
    private ObservableList<ChamadoSuporte> tabelaData;

    private Button botaoVoltar;

    public ChamadoSuporteView() {
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

        Label titulo = new Label("Gestão de chamados de suporte");
        labelIdSelecionado = new Label("ID: nenhum selecionado");

        campoTitulo = new TextField();
        campoTitulo.setPromptText("Digite o título");
        campoDescricao = new TextField();
        campoDescricao.setPromptText("Digite a descrição");
        campoPrioridade = new ComboBox<>(FXCollections.observableArrayList("Baixa", "Média", "Alta"));
        campoPrioridade.setPromptText("Selecione a prioridade");

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
                new Label("Título:"), campoTitulo,
                new Label("Descrição:"), campoDescricao,
                new Label("Prioridade:"), campoPrioridade,
                painelBotoes);

        this.add(colunaFormulario, 0, 0);
    }

    private void construirTabelaConsulta() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Chamados cadastrados");
        tabelaChamados = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<ChamadoSuporte, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<ChamadoSuporte, String> colunaTitulo = new TableColumn<>("Título");
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaTitulo.setPrefWidth(200);

        TableColumn<ChamadoSuporte, String> colunaDescricao = new TableColumn<>("Descrição");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaDescricao.setPrefWidth(300);

        TableColumn<ChamadoSuporte, String> colunaPrioridade = new TableColumn<>("Prioridade");
        colunaPrioridade.setCellValueFactory(new PropertyValueFactory<>("prioridade"));
        colunaPrioridade.setPrefWidth(100);

        tabelaChamados.getColumns().addAll(colunaId, colunaTitulo, colunaDescricao, colunaPrioridade);
        tabelaChamados.setItems(tabelaData);
        tabelaChamados.setPrefSize(760, 260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaChamados);
        this.add(colunaTabela, 1, 0);
    }

    public String getTitulo() {
        return campoTitulo.getText();
    }

    public String getDescricao() {
        return campoDescricao.getText();
    }

    public String getPrioridade() {
        return campoPrioridade.getValue();
    }

    public void atualizarTabela(List<ChamadoSuporte> chamados) {
        try {
            if (chamados == null) {
                System.err.println("Erro: Lista de chamados e nula.");
                tabelaData.clear();
                return;
            }
            tabelaData.setAll(chamados);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TableView<ChamadoSuporte> getTabelaChamados() {
        return tabelaChamados;
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
        campoTitulo.clear();
        campoDescricao.clear();
        campoPrioridade.getSelectionModel().clearSelection();
    }

    public void preencherCampos(ChamadoSuporte chamado) {
        try {
            if (chamado == null) {
                labelIdSelecionado.setText("ID: nenhum selecionado");
                limparCampos();
                botaoEditar.setDisable(true);
                botaoExcluir.setDisable(true);
                return;
            }

            if (chamado.getTitulo() == null || chamado.getId() < 1) {
                System.err.println("Aviso: Chamado invalido.");
                limparCampos();
                return;
            }

            labelIdSelecionado.setText("ID: " + chamado.getId());
            campoTitulo.setText(chamado.getTitulo() != null ? chamado.getTitulo() : "");
            campoDescricao.setText(chamado.getDescricao() != null ? chamado.getDescricao() : "");
            campoPrioridade.setValue(chamado.getPrioridade() != null ? chamado.getPrioridade() : null);
            botaoEditar.setDisable(false);
            botaoExcluir.setDisable(false);

        } catch (Exception e) {
            System.err.println("Erro ao preencher campos: " + e.getMessage());
            e.printStackTrace();
            limparCampos();
        }
    }

    public ChamadoSuporte getChamadoSelecionado() {
        return tabelaChamados.getSelectionModel().getSelectedItem();
    }

    public void limparSelecao() {
        tabelaChamados.getSelectionModel().clearSelection();
        preencherCampos(null);
    }
}
