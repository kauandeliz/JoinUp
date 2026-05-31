package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class UsuarioView extends GridPane {

    private TextField campoNome;
    private TextField campoEmail;
    private Button botaoSalvar;

    public UsuarioView() {
        // Configurações do layout (equivalentes aos atributos do GridPane no FXML)
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        // Inicialização dos componentes gráficos
        Label labelNome = new Label("Nome:");
        campoNome = new TextField();

        Label labelEmail = new Label("Email:");
        campoEmail = new TextField();

        botaoSalvar = new Button("Salvar");

        // Posicionamento no Grid (coluna, linha)
        this.add(labelNome, 0, 0);
        this.add(campoNome, 1, 0);

        this.add(labelEmail, 0, 1);
        this.add(campoEmail, 1, 1);

        this.add(botaoSalvar, 1, 2);
    }

    // Métodos getters para expor os dados e ações ao Controller
    public String getNomeDigitado() {
        return campoNome.getText();
    }

    public String getEmailDigitado() {
        return campoEmail.getText();
    }

    public Button getBotaoSalvar() {
        return botaoSalvar;
    }
}
