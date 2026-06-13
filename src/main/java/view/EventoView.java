package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Categoria;
import model.Evento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventoView extends GridPane {

    private Label labelIdSelecionada;
    private TextField campoNome;
    private TextField campoData;
    private TextField campoEndereco;
    private ComboBox<Categoria> comboCategoria;
    private Button botaoSalvar;
    private Button botaoExcluir;
    private Button botaoLimpar;

    private TableView<Evento> tabelaEvento;
    private ObservableList<Evento> tabelaData;

    private Button botaoVoltar;

    private Map<Integer, Categoria> categoriasMap;

    public EventoView() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(20);
        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));

        this.categoriasMap = new HashMap<>();

        construirFormularioUnificado();
        construirTabelaConsulta();
    }

    private void construirFormularioUnificado() {
        VBox colunaFormulario = new VBox(10);
        colunaFormulario.setAlignment(Pos.TOP_LEFT);

        Label titulo = new Label("Gestão de eventos");
        labelIdSelecionada = new Label("ID: nenhum selecionado");

        campoNome = new TextField();
        campoNome.setPromptText("Digite o nome");
        campoData = new TextField();
        campoData.setPromptText("Digite a data");
        campoEndereco = new TextField();
        campoEndereco.setPromptText("Digite o endereço");

        comboCategoria = new ComboBox<>();
        comboCategoria.setPromptText("Selecione uma categoria");

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
                new Label("Data:"), campoData,
                new Label("Endereço:"), campoEndereco,
                new Label("Categoria:"), comboCategoria,
                painelBotoes);

        this.add(colunaFormulario, 0, 0);
    }

    private void construirTabelaConsulta() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Eventos cadastrados");
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

        TableColumn<Evento, Integer> colunaIdCategoria = new TableColumn<>("ID Categoria");
        colunaIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        colunaIdCategoria.setPrefWidth(80);

        TableColumn<Evento, String> colunaNomeCategoria = new TableColumn<>("Categoria");
        colunaNomeCategoria.setCellValueFactory(cellData -> {
            int idCategoria = cellData.getValue().getIdCategoria();
            Categoria categoria = categoriasMap.get(idCategoria);
            String nome = categoria != null ? categoria.getNome() : "Não definida";
            return new javafx.beans.property.SimpleStringProperty(nome);
        });
        colunaNomeCategoria.setPrefWidth(150);

        tabelaEvento.getColumns().addAll(colunaId, colunaNome, colunaData, colunaEndereco, colunaIdCategoria,
                colunaNomeCategoria);
        tabelaEvento.setItems(tabelaData);
        tabelaEvento.setPrefSize(560, 260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaEvento);
        this.add(colunaTabela, 1, 0);
    }

    public String getNome() {
        return campoNome.getText();
    }

    public String getData() {
        return campoData.getText();
    }

    public String getEndereco() {
        return campoEndereco.getText();
    }

    public Categoria getCategoriaSelecionada() {
        return comboCategoria.getSelectionModel().getSelectedItem();
    }

    public void atualizarCategorias(List<Categoria> categorias) {
        try {
            if (categorias == null) {
                System.err.println("Erro: Lista de categorias é nula.");
                return;
            }

            comboCategoria.setItems(FXCollections.observableArrayList(categorias));

            // Atualiza o mapa de categorias para uso na tabela
            categoriasMap.clear();
            for (Categoria cat : categorias) {
                if (cat != null) {
                    categoriasMap.put(cat.getId(), cat);
                }
            }

            // Força atualização da tabela
            tabelaEvento.refresh();

        } catch (Exception e) {
            System.err.println("Erro ao atualizar categorias: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void atualizarTabela(List<Evento> eventos) {
        try {
            if (eventos == null) {
                System.err.println("Erro: Lista de eventos é nula.");
                tabelaData.clear();
                return;
            }
            tabelaData.setAll(eventos);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TableView<Evento> getTabelaEventos() {
        return tabelaEvento;
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
        campoData.clear();
        campoEndereco.clear();
        comboCategoria.getSelectionModel().clearSelection();
    }

    public void preencherCampos(Evento evento) {
        try {
            if (evento == null) {
                labelIdSelecionada.setText("ID: nenhum selecionado");
                limparCampos();
                botaoExcluir.setDisable(true);
                return;
            }

            // Validação de evento inválido
            if (evento.getNome() == null || evento.getId() < 1) {
                System.err.println("Aviso: Evento inválido.");
                limparCampos();
                return;
            }

            labelIdSelecionada.setText("ID: " + evento.getId());
            campoNome.setText(evento.getNome() != null ? evento.getNome() : "");
            campoData.setText(evento.getData() != null ? evento.getData() : "");
            campoEndereco.setText(evento.getEndereco() != null ? evento.getEndereco() : "");

            // Seleciona a categoria correspondente ao evento
            Categoria categoriaEvento = comboCategoria.getItems().stream()
                    .filter(cat -> cat != null && cat.getId() == evento.getIdCategoria())
                    .findFirst()
                    .orElse(null);
            comboCategoria.getSelectionModel().select(categoriaEvento);

            botaoExcluir.setDisable(false);

        } catch (Exception e) {
            System.err.println("Erro ao preencher campos: " + e.getMessage());
            e.printStackTrace();
            limparCampos();
        }
    }

    public Evento getEventoSelecionado() {
        return tabelaEvento.getSelectionModel().getSelectedItem();
    }

    public void limparSelecao() {
        tabelaEvento.getSelectionModel().clearSelection();
        preencherCampos(null);
    }
}