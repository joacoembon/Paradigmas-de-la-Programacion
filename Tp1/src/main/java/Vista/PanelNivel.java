package Vista;

import Modelo.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class PanelNivel {
    private Pane panelTablero;
    private Juego juego;
    private EventosBloque eventosBloque;
    private Pane panelLasers;

    public PanelNivel(Pane panelTablero, Juego juego, Pane panelLasers) {
        this.panelTablero = panelTablero;
        this.juego = juego;
        this.panelLasers = panelLasers;
        this.eventosBloque = new EventosBloque(this);
    }

    public VBox inicializarPanelNiveles() {
        VBox panelNiveles = new VBox(10);
        panelNiveles.setAlignment(Pos.CENTER);
        panelNiveles.setPrefWidth(150);

        // Creamos botones para cada nivel (1-6).
        for (int i = 0; i < 6; i++) {
            Button botonNivel = new Button("Nivel " + (i + 1));
            final int numeroNivel = i;
            botonNivel.setOnAction(e -> {
                try {
                    cargarNivel("level" + (numeroNivel + 1) + ".dat");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            botonNivel.setMaxWidth(Double.MAX_VALUE);
            panelNiveles.getChildren().add(botonNivel);
        }
        return panelNiveles;
    }

    // Metodo para cargar un nivel y mostrar el tablero correspondiente.
    private void cargarNivel(String nivelArchivo) throws Exception {
        // Establecemos el nivel en el juego.
        juego.SetNivel(nivelArchivo);

        // Obtenemos el tablero del nivel actual.
        Tablero tablero = juego.getNivel().getTablero();

        // Mostramos el tablero en el panel.
        mostrarTablero(tablero);
    }

    // Metodo para mostrar el tablero en el panel derecho.
    public void mostrarTablero(Tablero tablero) throws Exception {
        PropagarLaser laseres = new PropagarLaser();
        MostrarObjetivo objetivos = new MostrarObjetivo();

        panelTablero.getChildren().clear();  //Limpiar el contenido del tablero anterior.
        panelTablero.setDisable(false);
        tablero.getObjetivos().forEach(objetivo -> {objetivo.setEstaAlcanzado(false);});
        panelTablero.setStyle("");

        // Usamos un GridPane para organizar las celdas en la interfaz.
        GridPane gridPane = new GridPane();

        // Recorremos todas las celdas del tablero y las añadimos al GridPane.
        for (int y = 0; y < tablero.getAlto(); y++) {
            for (int x = 0; x < tablero.getAncho(); x++) {
                Celda celda = tablero.getCelda(x, y);

                if (celda != null) {
                    // Creamos un botón que representa cada celda (esto es solo para visualizar).
                    Rectangle celdaRect = new Rectangle(60, 60); // Tamaño de la celda.

                    // Establecer el color de fondo dependiendo del tipo de celda.
                    if (celda.tienePiso() && celda.getBloque() == null) {
                        celdaRect.setFill(Color.LIGHTGRAY); // Color para celdas de piso.
                    } else if (celda.getBloque() instanceof BloqueOpacoFijo) {
                        celdaRect.setFill(Color.DARKSLATEGRAY); // Color para bloques opacos fijos.
                    } else if (celda.getBloque() instanceof BloqueOpacoMovil) {
                        celdaRect.setFill(Color.DARKSLATEGRAY); // Color para bloques opacos móviles.
                    } else if (celda.getBloque() instanceof BloqueEspejo) {
                        celdaRect.setFill(Color.DARKSEAGREEN); // Color para bloques espejo.
                    } else if (celda.getBloque() instanceof BloqueCristal) {
                        celdaRect.setFill(Color.MEDIUMAQUAMARINE); // Color para bloques de cristal.
                    } else if (celda.getBloque() instanceof BloqueVidrio) {
                        celdaRect.setFill(Color.SKYBLUE); // Color para bloques de vidrio.
                    } else {
                        continue; // Celda vacía.
                    }

                    // Configurar el borde.
                    celdaRect.setStroke(Color.BLACK); // Color del borde.
                    celdaRect.setStrokeWidth(2); // Ancho del borde.

                    // Crear líneas para la cruz (solo para el bloque opaco fijo).
                    Line cruz1 = new Line(2, 2, 58, 58); // Línea de la esquina superior izquierda a la esquina inferior derecha.
                    Line cruz2 = new Line(58, 2, 2, 58); // Línea de la esquina superior derecha a la esquina inferior izquierda.

                    // Establecer el color de la cruz.
                    cruz1.setStroke(Color.BLACK);
                    cruz2.setStroke(Color.BLACK);

                    // Aumentar el grosor de las líneas de la cruz.
                    cruz1.setStrokeWidth(3);
                    cruz2.setStrokeWidth(3);

                    // Agregar evento de clic.
                    int finalX = x;
                    int finalY = y;
                    celdaRect.setOnMouseClicked(e -> {
                        // Lógica que se ejecuta al hacer clic en la celda.
                        System.out.println("Celda clickeada en: " + finalX + ", " + finalY);
                    });

                    // Agregar la celda al grid.
                    if (celda.getBloque() instanceof BloqueOpacoFijo) {
                        // Agregar las líneas de la cruz al grupo que contendrá la celda.
                        Group celdaGroup = new Group(celdaRect, cruz1, cruz2);
                        gridPane.add(celdaGroup, x, y);
                    } else if (celda.getBloque() instanceof BloqueMovil) {
                        // Efecto de mover (opcional).
                        celdaRect.setOnMouseEntered(e -> {
                            celdaRect.setOpacity(0.7); // Cambiar la opacidad al pasar el mouse.
                        });
                        celdaRect.setOnMouseExited(e -> {
                            celdaRect.setOpacity(1); // Restaurar opacidad al salir.
                        });
                        eventosBloque.configurarEventosDrag(celdaRect, finalX, finalY);
                        gridPane.add(celdaRect, x, y);
                    } else {
                        eventosBloque.configurarEventosDrag(celdaRect, finalX, finalY);
                        gridPane.add(celdaRect, x, y);
                    }
                }
            }
        }
        // Agregamos el grid con el tablero al panel derecho.
        panelTablero.getChildren().add(gridPane);
        laseres.dibujarLasers(panelTablero, panelLasers, tablero);
        objetivos.dibujarObjetivos(panelTablero, tablero);
    }

    public void estaCompletado() {
        if (juego.getNivel().estaCompletado()) {
            panelTablero.setStyle("-fx-background-color: lightgreen;");
            panelTablero.setDisable(true);
        }
    }

    public Juego getJuego() {
        return juego;
    }
}
