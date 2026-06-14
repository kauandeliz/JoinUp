import controller.CategoriaController;
import controller.EventoController;
import controller.MenuController;
import controller.OrganizadorController;
import controller.ParticipanteController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.CategoriaView;
import view.EventoView;
import view.MenuView;
import view.OrganizadorView;
import view.ParticipanteView;

public class Main extends Application {

    Scene sceneMenu, sceneCategoria, sceneEvento, sceneParticipante, sceneOrganizador;

    @Override
    public void start(Stage primaryStage) {

        MenuView menuView = new MenuView();
        sceneMenu = new Scene(menuView, 300, 250);

        CategoriaView viewCategoria = new CategoriaView();
        sceneCategoria = new Scene(viewCategoria, 980, 360);

        EventoView viewEvento = new EventoView();
        sceneEvento = new Scene(viewEvento, 980, 360);

        ParticipanteView viewParticipante = new ParticipanteView();
        sceneParticipante = new Scene(viewParticipante, 1040, 360);

        OrganizadorView viewOrganizador = new OrganizadorView();
        sceneOrganizador = new Scene(viewOrganizador, 1040, 360);

        MenuController menuController = new MenuController(menuView, primaryStage, sceneCategoria, sceneEvento,
                sceneParticipante, sceneOrganizador);
        CategoriaController controllerCategoria = new CategoriaController(viewCategoria, primaryStage, sceneMenu);
        EventoController controllerEvento = new EventoController(viewEvento, primaryStage, sceneMenu);
        ParticipanteController controllerParticipante = new ParticipanteController(viewParticipante, primaryStage,
                sceneMenu);
        OrganizadorController controllerOrganizador = new OrganizadorController(viewOrganizador, primaryStage,
                sceneMenu);

        // Configura o MenuController com o EventoController para recarregar categorias
        menuController.setEventoController(controllerEvento);

        primaryStage.setTitle("Exemplo MVC JavaFX POO");
        primaryStage.setScene(sceneMenu);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
