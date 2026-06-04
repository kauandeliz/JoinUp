package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MenuView;

public class MenuController {

    private MenuView view;
    private Stage primaryStage;
    private Scene sceneCategoria;
    private Scene sceneEvento;

    public MenuController(MenuView view, Stage primaryStage, Scene sceneCategoria, Scene sceneEvento){
        this.view = view;
        this.primaryStage = primaryStage;
        this.sceneCategoria = sceneCategoria;
        this.sceneEvento = sceneEvento;

        this.view.getBotaoCategoria().setOnAction(event -> irParaCategoria());
        this.view.getBotaoEvento().setOnAction(event -> irParaEvento());
    }

    private void irParaCategoria(){
        primaryStage.setScene(sceneCategoria);
    }

    private void irParaEvento(){
        primaryStage.setScene(sceneEvento);
    }
}
