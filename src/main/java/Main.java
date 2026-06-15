import controller.CategoriaController;
import controller.ChamadoSuporteController;
import controller.EventoController;
import controller.MenuController;
import controller.OrganizadorController;
import controller.ParticipanteController;
import controller.SuporteController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.CategoriaView;
import view.ChamadoSuporteView;
import view.EventoView;
import view.MenuView;
import view.OrganizadorView;
import view.ParticipanteView;
import controller.ArtistaController;
import controller.IngressoController;
import view.ArtistaView;
import view.IngressoView;
import view.SuporteView;

public class Main extends Application {

    Scene sceneMenu, sceneCategoria, sceneEvento, sceneParticipante, sceneOrganizador, 
          sceneArtista, sceneIngresso, sceneSuporte, sceneChamadoSuporte;

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

        ArtistaView viewArtista = new ArtistaView();
        sceneArtista = new Scene(viewArtista, 1040, 360);

        IngressoView viewIngresso = new IngressoView();
        sceneIngresso = new Scene(viewIngresso, 1040, 360);  

        SuporteView viewSuporte = new SuporteView();
        sceneSuporte = new Scene(viewSuporte, 1040, 360);

        ChamadoSuporteView viewChamadoSuporte = new ChamadoSuporteView();
        sceneChamadoSuporte = new Scene(viewChamadoSuporte, 1040, 360);

        MenuController menuController = new MenuController(menuView, primaryStage, sceneCategoria, sceneEvento,
                sceneParticipante, sceneOrganizador, sceneArtista, sceneIngresso, sceneSuporte, sceneChamadoSuporte);

        CategoriaController controllerCategoria = new CategoriaController(viewCategoria, primaryStage, sceneMenu);

        EventoController controllerEvento = new EventoController(viewEvento, primaryStage, sceneMenu);

        ParticipanteController controllerParticipante = new ParticipanteController(viewParticipante, primaryStage, sceneMenu);

        OrganizadorController controllerOrganizador = new OrganizadorController(viewOrganizador, primaryStage, sceneMenu);

        ArtistaController controllerArtista = new ArtistaController(viewArtista, primaryStage, sceneMenu);
        
        IngressoController controllerIngresso = new IngressoController(viewIngresso, primaryStage, sceneMenu);

        SuporteController controllerSuporte = new SuporteController(viewSuporte, primaryStage, sceneMenu);

        ChamadoSuporteController controllerChamadoSuporte = new ChamadoSuporteController(viewChamadoSuporte,
                primaryStage, sceneMenu);

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
