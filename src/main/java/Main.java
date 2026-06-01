import controller.CategoriaController;
import controller.MenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.CategoriaView;
import view.MenuView;

public class Main extends Application {

    Scene sceneMenu, sceneCategoria;

    @Override
    public void start(Stage primaryStage) {

        MenuView menuView = new MenuView();
        sceneMenu = new Scene(menuView, 300, 250);

        CategoriaView view = new CategoriaView();
        sceneCategoria = new Scene(view, 980, 360);

        MenuController menuController = new MenuController(menuView, primaryStage, sceneCategoria);
        CategoriaController controller = new CategoriaController(view, primaryStage, sceneMenu);

        primaryStage.setTitle("Exemplo MVC JavaFX POO");
        primaryStage.setScene(sceneMenu);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
