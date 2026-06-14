package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MenuView extends GridPane {

    private Button botaoCategoria;
    private Button botaoEvento;
    private Button botaoParticipante;
    private Button botaoOrganizador;

    public MenuView(){
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        botaoCategoria = new Button("Gestão de categorias");
        botaoEvento = new Button("Gestão de eventos");
        botaoParticipante = new Button("Participantes");
        botaoOrganizador = new Button("Organizadores");

        this.add(botaoCategoria, 0, 0);
        this.add(botaoEvento, 0, 1);
        this.add(botaoParticipante, 0, 2);
        this.add(botaoOrganizador, 0, 3);
    }

    public Button getBotaoCategoria() {
        return botaoCategoria;
    }

    public Button getBotaoEvento() {
        return botaoEvento;
    }

    public Button getBotaoParticipante() {
        return botaoParticipante;
    }

    public Button getBotaoOrganizador() {
        return botaoOrganizador;
    }
}
