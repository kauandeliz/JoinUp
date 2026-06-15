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
import model.Ingresso;

import java.util.List;

/**
 * Tela JavaFX responsável pelo CRUD de Ingresso.
 *
 * Toda a interface é montada diretamente no código, conforme solicitado
 * na atividade de POO.
 */
public class IngressoView extends GridPane {

    private Label labelIdSelecionado;
    private TextField campoNomeEvento;
    private TextField campoTipo;
    private TextField campoPreco;
    private TextField campoQuantidadeDisponivel;

    private Button botaoSalvar;
    private Button botaoExcluir;
    private Button botaoLimpar;
    private Button botaoVoltar;

    private TableView<Ingresso> tabelaIngressos;
    private ObservableList<Ingresso> tabelaData;

    public IngressoView() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(20);
        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));

        construirFormulario();
        construirTabela();
    }

    /**
     * Cria os campos do formulário e os botões de ação.
     */
    private void construirFormulario() {
        VBox colunaFormulario = new VBox(10);
        colunaFormulario.setAlignment(Pos.TOP_LEFT);

        Label titulo = new Label("Gestão de ingressos");
        labelIdSelecionado = new Label("ID: nenhum selecionado");

        campoNomeEvento = new TextField();
        campoNomeEvento.setPromptText("Digite o nome do evento");

        campoTipo = new TextField();
        campoTipo.setPromptText("Ex.: Pista, VIP, Camarote");

        campoPreco = new TextField();
        campoPreco.setPromptText("Digite o preço. Ex.: 120.50");

        campoQuantidadeDisponivel = new TextField();
        campoQuantidadeDisponivel.setPromptText("Digite a quantidade disponível");

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
                new Label("Evento:"), campoNomeEvento,
                new Label("Tipo:"), campoTipo,
                new Label("Preço:"), campoPreco,
                new Label("Quantidade disponível:"), campoQuantidadeDisponivel,
                painelBotoes
        );

        this.add(colunaFormulario, 0, 0);
    }

    /**
     * Cria a tabela usada para consultar e selecionar ingressos.
     */
    private void construirTabela() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Ingressos cadastrados");
        tabelaIngressos = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<Ingresso, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Ingresso, String> colunaEvento = new TableColumn<>("Evento");
        colunaEvento.setCellValueFactory(new PropertyValueFactory<>("nomeEvento"));
        colunaEvento.setPrefWidth(170);

        TableColumn<Ingresso, String> colunaTipo = new TableColumn<>("Tipo");
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colunaTipo.setPrefWidth(120);

        TableColumn<Ingresso, Double> colunaPreco = new TableColumn<>("Preço");
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaPreco.setPrefWidth(90);

        TableColumn<Ingresso, Integer> colunaQuantidade = new TableColumn<>("Quantidade");
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeDisponivel"));
        colunaQuantidade.setPrefWidth(100);

        tabelaIngressos.getColumns().addAll(colunaId, colunaEvento, colunaTipo, colunaPreco, colunaQuantidade);
        tabelaIngressos.setItems(tabelaData);
        tabelaIngressos.setPrefWidth(560);
        tabelaIngressos.setPrefHeight(260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaIngressos);
        this.add(colunaTabela, 1, 0);
    }

    /**
     * Recarrega os dados da tabela depois das operações de CRUD.
     */
    public void atualizarTabela(List<Ingresso> ingressos) {
        tabelaData.clear();
        tabelaData.addAll(ingressos);
    }

    /**
     * Coloca os dados do ingresso selecionado nos campos do formulário.
     */
    public void preencherCampos(Ingresso ingresso) {
        if (ingresso == null) {
            limparSelecao();
            return;
        }

        labelIdSelecionado.setText("ID: " + ingresso.getId());
        campoNomeEvento.setText(ingresso.getNomeEvento());
        campoTipo.setText(ingresso.getTipo());
        campoPreco.setText(String.valueOf(ingresso.getPreco()));
        campoQuantidadeDisponivel.setText(String.valueOf(ingresso.getQuantidadeDisponivel()));
        botaoExcluir.setDisable(false);
    }

    /**
     * Limpa o formulário e desabilita o botão de exclusão.
     */
    public void limparSelecao() {
        tabelaIngressos.getSelectionModel().clearSelection();
        labelIdSelecionado.setText("ID: nenhum selecionado");
        campoNomeEvento.clear();
        campoTipo.clear();
        campoPreco.clear();
        campoQuantidadeDisponivel.clear();
        botaoExcluir.setDisable(true);
    }

    public String getNomeEvento() {
        return campoNomeEvento.getText();
    }

    public String getTipo() {
        return campoTipo.getText();
    }

    public String getPreco() {
        return campoPreco.getText();
    }

    public String getQuantidadeDisponivel() {
        return campoQuantidadeDisponivel.getText();
    }

    public Ingresso getIngressoSelecionado() {
        return tabelaIngressos.getSelectionModel().getSelectedItem();
    }

    public TableView<Ingresso> getTabelaIngressos() {
        return tabelaIngressos;
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
