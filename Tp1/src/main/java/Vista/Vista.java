package Vista;

import Modelo.Juego;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Vista {

    private Juego juego; // Referencia al juego.
    private Pane panelTablero; // Panel para el tablero.
    private Pane panelLasers;  // Panel para los lasers.

    public Vista() {
        this.juego = new Juego();
        // Panel donde se mostrará el tablero del juego.
        this.panelTablero = new Pane();
        // Panel donde se mostrarán los lasers.
        this.panelLasers = new Pane();
        this.panelLasers.setMouseTransparent(true); // Evitamos que el panel de lasers interfiera con la interacción del usuario.
    }

    public void inicializarVista(Stage primaryStage) {
        primaryStage.setTitle("Juego de Laser - Niveles");

        // Creamos el layout principal.
        BorderPane root = new BorderPane();

        // Panel izquierdo para los botones de niveles.
        PanelNivel panelNivel = new PanelNivel(panelTablero, juego, panelLasers);
        VBox panelNiveles = panelNivel.inicializarPanelNiveles(); // Panel de botones de niveles.

        // Agregamos los paneles al layout principal.
        root.setLeft(panelNiveles); // Botones de niveles a la izquierda.
        root.setCenter(panelTablero); // Tablero en el centro.

        // Creamos la escena.
        Scene scene = new Scene(root, 500, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
