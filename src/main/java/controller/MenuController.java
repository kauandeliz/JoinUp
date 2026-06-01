package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.CategoriaView;
import view.MenuView;

public class MenuController {

    private MenuView view;
    private Stage primaryStage;
    private Scene sceneCategoria;

    public MenuController(MenuView view, Stage primaryStage, Scene sceneCategoria){
        this.view = view;
        this.primaryStage = primaryStage;
        this.sceneCategoria = sceneCategoria;

        this.view.getBotaoCategoria().setOnAction(event -> irParaCategoria());
    }

    private void irParaCategoria(){
        primaryStage.setScene(sceneCategoria);
    }
}
