package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MenuView extends GridPane {

    private Button botaoCategoria;
    private Button botaoEvento;

    public MenuView(){
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        botaoCategoria = new Button("Gestão de categorias");
        botaoEvento = new Button("Gestão de eventos");

        this.add(botaoCategoria, 0, 0);
        this.add(botaoEvento, 0, 1);
    }

    public Button getBotaoCategoria() {
        return botaoCategoria;
    }

    public Button getBotaoEvento() {
        return botaoEvento;
    }
}
