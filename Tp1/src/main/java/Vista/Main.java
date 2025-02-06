package Vista;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private Vista vista; // Instancia de la clase Vista que manejará la interfaz.

    @Override
    public void start(Stage primaryStage) {
        vista = new Vista();
        vista.inicializarVista(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}