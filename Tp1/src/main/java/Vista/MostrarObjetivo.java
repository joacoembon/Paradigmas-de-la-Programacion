package Vista;

import Modelo.Objetivo;
import Modelo.Tablero;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MostrarObjetivo {

    public void dibujarObjetivos(Pane panelTablero, Tablero tablero) {
        for (Objetivo objetivo : tablero.getObjetivos()) {
            int x = objetivo.getX();
            int y = objetivo.getY();

            // Dibujar un círculo vacío que representa el objetivo.
            Circle objetivoCircle = new Circle(x*31,y*31 , 5); // Tamaño del círculo.
            if (objetivo.estaAlcanzado()) {
                objetivoCircle.setFill(Color.RED);
            } else {
                objetivoCircle.setFill(Color.WHITE); // Centro vacío.
            }
            objetivoCircle.setStroke(Color.RED); // Color del borde.
            objetivoCircle.setStrokeWidth(2); // Grosor del borde.
            panelTablero.getChildren().add(objetivoCircle);
        }
    }
}
