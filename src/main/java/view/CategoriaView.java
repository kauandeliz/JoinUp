package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CategoriaView extends GridPane {

    private TextField campoNome;
    private TextField campoDescricao;
    private TextField campoClassificacao;
    private Button botaoSalvar;
    private Button botaoVoltar;

    public CategoriaView() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        Label labelNome = new Label("Nome:");
        campoNome = new TextField();

        Label labelDescricao = new Label("Descrição:");
        campoDescricao = new TextField();

        Label labelClassificacao = new Label("Classificação:");
        campoClassificacao = new TextField();

        botaoSalvar = new Button("Salvar");
        botaoVoltar = new Button("Voltar");

        this.add(labelNome, 0, 0);
        this.add(campoNome, 1, 0);

        this.add(labelDescricao, 0, 1);
        this.add(campoDescricao, 1, 1);

        this.add(labelClassificacao, 0, 2);
        this.add(campoClassificacao, 1, 2);

        this.add(botaoSalvar, 1, 3);
        this.add(botaoVoltar, 0, 3);
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

    public Button getBotaoSalvar() {
        return botaoSalvar;
    }

    public Button getBotaoVoltar() {
        return botaoVoltar;
    }

}
