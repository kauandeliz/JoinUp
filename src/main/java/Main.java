import controller.UsuarioController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.UsuarioView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Instancia a View gráfica
        UsuarioView view = new UsuarioView();

        // Instancia o Controller injetando a View nele
        UsuarioController controller = new UsuarioController(view);

        // Passa a própria View (que herda de GridPane) como nó raiz da cena
        Scene scene = new Scene(view, 300, 250);

        primaryStage.setTitle("Exemplo MVC JavaFX POO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
