package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MenuView;

public class MenuController {

    private MenuView view;
    private Stage primaryStage;
    private Scene sceneCategoria;
    private Scene sceneEvento;
    private Scene sceneParticipante;
    private Scene sceneOrganizador;
    private EventoController eventoController;
    private Scene sceneArtista;
    private Scene sceneIngresso;

    public MenuController(MenuView view, Stage primaryStage, Scene sceneCategoria, Scene sceneEvento,
            Scene sceneParticipante, Scene sceneOrganizador, Scene sceneArtista, Scene sceneIngresso) {
        this.view = view;
        this.primaryStage = primaryStage;
        this.sceneCategoria = sceneCategoria;
        this.sceneEvento = sceneEvento;
        this.sceneParticipante = sceneParticipante;
        this.sceneOrganizador = sceneOrganizador;
        this.sceneArtista = sceneArtista;
        this.sceneIngresso = sceneIngresso;

        this.view.getBotaoCategoria().setOnAction(event -> irParaCategoria());
        this.view.getBotaoEvento().setOnAction(event -> irParaEvento());
        this.view.getBotaoParticipante().setOnAction(event -> irParaParticipante());
        this.view.getBotaoOrganizador().setOnAction(event -> irParaOrganizador());
        this.view.getBotaoArtista().setOnAction(event -> irParaArtista());
        this.view.getBotaoIngresso().setOnAction(event -> irParaIngresso());
    }

    public void setEventoController(EventoController eventoController) {
        this.eventoController = eventoController;
    }

    private void irParaCategoria() {
        primaryStage.setScene(sceneCategoria);
    }

    private void irParaEvento() {
        // Recarrega as categorias antes de ir para a tela de eventos
        if (eventoController != null) {
            eventoController.recarregarCategorias();
        }
        primaryStage.setScene(sceneEvento);
    }

    private void irParaParticipante() {
        primaryStage.setScene(sceneParticipante);
    }

    private void irParaOrganizador() {
        primaryStage.setScene(sceneOrganizador);
    }

    private void irParaArtista() {
        primaryStage.setScene(sceneArtista);
    }

    private void irParaIngresso() {
        primaryStage.setScene(sceneIngresso);
    }
}
