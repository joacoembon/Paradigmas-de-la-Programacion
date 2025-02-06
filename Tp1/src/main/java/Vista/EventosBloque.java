package Vista;

import Modelo.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Rectangle;

public class EventosBloque {
    private PanelNivel nivel;

    public EventosBloque(PanelNivel nivel) {this.nivel = nivel;}

    public void configurarEventosDrag(Rectangle celdaRect, int finalX, int finalY) {
        celdaRect.setOnDragDetected(e -> {
            System.out.println("Arrastre detectado en la celda (" + finalX + ", " + finalY + ")");
            Dragboard db = celdaRect.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(finalX + "," + finalY); // Guardamos las coordenadas de origen.
            db.setContent(content);
            celdaRect.setOpacity(0.5);

            e.consume();

        });
        // Evento para manejar cuando el bloque es soltado en una nueva celda.
        celdaRect.setOnDragOver(e -> {
            if (e.getGestureSource() != celdaRect && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });
        // Evento para manejar cuando el bloque es soltado en una nueva celda.
        celdaRect.setOnDragDropped(e -> {
            System.out.println("Arrastre soltado en la celda (" + finalX + ", " + finalY + ")");

            Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                // Obtener las coordenadas de la celda de origen.
                String[] coords = db.getString().split(",");
                int origenX = Integer.parseInt(coords[0]);
                int origenY = Integer.parseInt(coords[1]);

                System.out.println("Intentando mover bloque de (" + origenX + ", " + origenY + ") a (" + finalX + ", " + finalY + ")");

                // Lógica para mover el bloque.
                try {
                    success = moverBloque(origenX, origenY, finalX, finalY);
                } catch (Exception ex) {
                    System.out.println("Error al mover el bloque: " + ex.getMessage());
                }
            }
            // Aceptar el evento de arrastre.
            e.setDropCompleted(success);
            celdaRect.setOpacity(1);
            e.consume();
        });
        celdaRect.setOnDragDone(e -> {
            celdaRect.setOpacity(1); // Restablecer opacidad.
        });
    }

    private boolean moverBloque(int origenX, int origenY, int destinoX, int destinoY) throws Exception {
        Tablero tablero = nivel.getJuego().getNivel().getTablero();
        Celda origen = tablero.getCelda(origenX, origenY);
        Celda destino = tablero.getCelda(destinoX, destinoY);

        System.out.println("Intentando mover bloque de (" + origenX + ", " + origenY + ") a (" + destinoX + ", " + destinoY + ")");

        // Verificar si el origen y el destino son válidos.
        if (origen != null && destino != null) {
            if (destino.getBloque() instanceof BloqueMovil) {
                return false;
            }
            Bloque bloqueOrigen = origen.getBloque();
            System.out.println("Bloque en origen: " + bloqueOrigen);

            // Verificar si la celda de origen tiene un bloque móvil.
            if (bloqueOrigen instanceof BloqueMovil) {
                BloqueMovil bloqueMovil = (BloqueMovil) bloqueOrigen;

                // Intentamos mover el bloque móvil.
                try {
                    bloqueMovil.Mover(destino);
                    System.out.println("Bloque movido exitosamente.");

                    // Volver a renderizar el tablero para reflejar los cambios.
                    nivel.mostrarTablero(tablero);
                    nivel.estaCompletado();
                    return true;
                } catch (Exception ex) {
                    System.out.println("Error al mover el bloque: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                System.out.println("El bloque no es movible.");
            }
        } else {
            System.out.println("Celda origen o destino inválida.");
        }
        return false;
    }
}
