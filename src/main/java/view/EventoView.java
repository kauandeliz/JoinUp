package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Evento;

import java.util.List;

public class EventoView extends GridPane {

    private TextField campoNome;
    private TextField campoData;
    private TextField campoEndereco;
    private Button botaoSalvar;

    private TableView<Evento> tabelaEvento;
    private ObservableList<Evento> tabelaData;

    private Label labelIdSelecionada;
    private TextField campoNomeSelecionado;
    private TextField campoDataSelecionada;
    private TextField campoEnderecoSelecionado;
    private Button botaoAtualizar;
    private Button botaoExcluir;
    private Button botaoVoltar;

    public EventoView() {
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

        Label tituloCadastro = new Label("Cadastro de evento");
        campoNome = new TextField();
        campoData = new TextField();
        campoEndereco = new TextField();
        botaoSalvar = new Button("Salvar");
        botaoVoltar = new Button("Voltar");

        colunaCadastro.getChildren().addAll(
                tituloCadastro,
                new Label("Nome:"), campoNome,
                new Label("Data:"), campoData,
                new Label("Endereço:"), campoEndereco,
                botaoSalvar,
                botaoVoltar);

        this.add(colunaCadastro, 0, 0);
    }

    private void construirTabelaConsulta() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Consulta de eventos");
        tabelaEvento = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<Evento, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Evento, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(150);

        TableColumn<Evento, String> colunaData = new TableColumn<>("Data");
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaData.setPrefWidth(200);

        TableColumn<Evento, String> colunaEndereco = new TableColumn<>("Endereço");
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colunaEndereco.setPrefWidth(150);

        tabelaEvento.getColumns().addAll(colunaId, colunaNome, colunaData, colunaEndereco);
        tabelaEvento.setItems(tabelaData);
        tabelaEvento.setPrefSize(560, 260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaEvento);
        this.add(colunaTabela, 1, 0);
    }

    private void construirPainelEdicao() {
        VBox colunaEdicao = new VBox(10);
        colunaEdicao.setAlignment(Pos.TOP_LEFT);

        Label tituloEdicao = new Label("Seleção para atualizar / excluir");
        labelIdSelecionada = new Label("ID selecionado: nenhum");
        campoNomeSelecionado = new TextField();
        campoDataSelecionada = new TextField();
        campoEnderecoSelecionado = new TextField();
        botaoAtualizar = new Button("Atualizar");
        botaoExcluir = new Button("Excluir");

        colunaEdicao.getChildren().addAll(
                tituloEdicao,
                labelIdSelecionada,
                new Label("Nome:"), campoNomeSelecionado,
                new Label("Data:"), campoDataSelecionada,
                new Label("Endereço:"), campoEnderecoSelecionado,
                botaoAtualizar,
                botaoExcluir);

        this.add(colunaEdicao, 2, 0);
    }

    public String getNomeDigitado() {
        return campoNome.getText();
    }

    public String getDataDigitada() {
        return campoData.getText();
    }

    public String getEnderecoDigitado() {
        return campoEndereco.getText();
    }

    public String getNomeSelecionado() {
        return campoNomeSelecionado.getText();
    }

    public String getDataSelecionada() {
        return campoDataSelecionada.getText();
    }

    public String getEnderecoSelecionado() {
        return campoEnderecoSelecionado.getText();
    }

    public void atualizarTabela(List<Evento> eventos) {
        tabelaData.setAll(eventos);
    }

    public TableView<Evento> getTabelaEventos() {
        return tabelaEvento;
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
        campoData.clear();
        campoEndereco.clear();
    }

    public void limparCamposSelecao() {
        labelIdSelecionada.setText("ID selecionado: nenhum");
        campoNomeSelecionado.clear();
        campoDataSelecionada.clear();
        campoEnderecoSelecionado.clear();
        tabelaEvento.getSelectionModel().clearSelection();
    }

    public void preencherCamposSelecao(Evento evento) {
        if (evento == null) {
            limparCamposSelecao();
            return;
        }

        labelIdSelecionada.setText("ID selecionado: " + evento.getId());
        campoNomeSelecionado.setText(evento.getNome());
        campoDataSelecionada.setText(evento.getData());
        campoEnderecoSelecionado.setText(evento.getEndereco());
    }
}