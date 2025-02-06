package Vista;

import Modelo.Laser;
import Modelo.Tablero;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;

import java.util.List;

public class PropagarLaser {

    public void dibujarLasers(Pane panelTablero, Pane panelLasers, Tablero tablero) {
        panelLasers.getChildren().clear();

        for (Laser laser : tablero.getLasers()) {
            laser.propagarCamino(tablero);
            List<String> camino = laser.getCamino();
            System.out.println(camino.size());
            propagarUnicoLaser(camino, panelLasers, false);

            List<String> caminoExtra = laser.getCaminoExtra();
            if (!caminoExtra.isEmpty()) {
                propagarUnicoLaser(caminoExtra, panelLasers, true);
                }
            }
        panelTablero.getChildren().add(panelLasers);
    }

    public void propagarUnicoLaser(List<String> camino, Pane panelLasers, boolean caminoExtra) {
        for (int i = 0; i < camino.size()-1; i++) {
            String[] laser1 = camino.get(i).split(",");
            String[] laser2 = camino.get(i + 1).split(",");

            double x1 = Double.parseDouble(laser1[0]);
            double y1 = Double.parseDouble(laser1[1]);
            double startX = (x1 * 31);
            double startY = (y1 * 31);

            if (i == 0 && !caminoExtra) {
                Circle startCircle = new Circle(x1*31,y1*31 , 5);
                startCircle.setFill(Color.RED);
                panelLasers.getChildren().add(startCircle);
            }

            double x2 = Double.parseDouble(laser2[0]);
            double y2 = Double.parseDouble(laser2[1]);
            double endX = (x2 * 31);
            double endY = (y2 * 31);

            Line linea1 = new Line(startX, startY,  endX, endY);
            linea1.setStroke(Color.RED);
            linea1.setStrokeWidth(3);
            panelLasers.getChildren().add(linea1);
        }
    }
}
