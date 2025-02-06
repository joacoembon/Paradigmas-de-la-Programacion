package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Laser extends Entidad{
    private Direccion direccion;
    private boolean detenido;
    private List<String> camino;
    private final int inicialX;
    private final int inicialY;
    private final Direccion inicialDireccion;
    private List<String> caminoExtra;

    public Laser(int fila, int columna, Direccion direccion) {
        super(fila, columna);
        this.inicialX = fila;
        this.inicialY = columna;
        this.direccion = direccion;
        this.inicialDireccion = direccion;
        this.detenido = false;
        this.camino = new ArrayList<>();
        this.caminoExtra = new ArrayList<>();
    }

    public List<String> getCamino() {
        return camino;
    }

    public List<String> getCaminoExtra() {
        return caminoExtra;
    }

    public void propagarCamino(Tablero tablero) {
        caminoExtra.clear();
        camino.clear();
        detenido = false;
        int posActualX = inicialX;
        int posActualY = inicialY;
        camino.add(posActualX + "," + posActualY);
        direccion = inicialDireccion;
        setX(inicialX);
        setY(inicialY);

        System.out.println("Láser iniciado en posición: (" + posActualX + "," + posActualY + "), dirección: " + direccion);

        while (!detenido) {
            int nuevoX = getX() + direccion.getDx();
            int nuevoY = getY() + direccion.getDy();
            setX(nuevoX);
            setY(nuevoY);

            for (Objetivo objetivo : tablero.getObjetivos()) {
                if (objetivo.getX() == nuevoX && objetivo.getY() == nuevoY) {
                    objetivo.setEstaAlcanzado(true);
                }
            }

            camino.add(nuevoX + "," + nuevoY);
            System.out.println("Láser avanzó a: (" + nuevoX + "," + nuevoY + "), dirección: " + direccion);

            Celda celda;
            if (nuevoX % 2 == 0 && nuevoY % 2 != 0) {
                if (direccion == Direccion.SE || direccion == Direccion.NE) {
                    celda = tablero.getCelda(nuevoX+1, nuevoY);
                    System.out.println("Choca con la celda: (" + (nuevoX+1) + "," + nuevoY + "), dirección: " + direccion);
                } else {
                    celda = tablero.getCelda(nuevoX-1, nuevoY);
                    System.out.println("Choca con la celda: (" + (nuevoX-1) + "," + nuevoY + "), dirección: " + direccion);
                }
            } else {
                if (direccion == Direccion.SE || direccion == Direccion.SW) {
                    celda = tablero.getCelda(nuevoX, nuevoY+1);
                    System.out.println("Choca con la celda: (" + nuevoX + "," + (nuevoY+1) + "), dirección: " + direccion);
                } else {
                    celda = tablero.getCelda(nuevoX, nuevoY-1);
                    System.out.println("Choca con la celda: (" + nuevoX + "," + (nuevoY-1) + "), dirección: " + direccion);
                }
            }

            if (celda != null) {
                Bloque bloque = celda.getBloque();
                if (bloque != null) {
                    bloque.interactuarConLaser(this, tablero);
                }
            } else {
                detener();
                return;
            }
        }
    }

    public void avanzarManual(int pasos) {
        for (int i = 0; i < pasos; i++) {
            int nuevoX = this.getX() + direccion.getDx();
            int nuevoY = this.getY() + direccion.getDy();
            camino.add(nuevoX + "," + nuevoY);
            System.out.println("Láser avanzó a: (" + nuevoX + "," + nuevoY + "), dirección: " + direccion);
            this.setX(nuevoX);
            this.setY(nuevoY);
        }
    }

    public void agregarCamino(List<String> caminoNuevo) {
        this.caminoExtra.addAll(caminoNuevo);
    }

    public int getX() {
        return super.x;
    }

    public int getY() {
        return super.y;
    }

    public void setX(int x) {
        super.x = x;
    }

    public void setY(int y) {
        super.y = y;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void detener() {
        this.detenido = true;
    }

    public void cambiarDireccion(Direccion nuevaDireccion){
        this.direccion = nuevaDireccion;
    }

    public static void reboteLaser(Laser laser, Direccion direccion, int impactoX, int impactoY) {
        switch (direccion) {
            case NE -> {
                if (impactoX % 2 == 0 && impactoY % 2 == 1) { // Impacto en la parte Izquierda.
                    laser.cambiarDireccion(Direccion.NW); // Rebota hacia Noroeste.
                } else { // Impacto en la parte inferior.
                    laser.cambiarDireccion(Direccion.SE); // Rebota hacia Sureste.
                }
            }
            case NW -> {
                if (impactoX % 2 == 1 && impactoY % 2 == 0) { // Impacto en la parte inferior.
                    laser.cambiarDireccion(Direccion.SW); // Rebota hacia Suroeste.
                } else { // Impacto en la parte derecha.
                    laser.cambiarDireccion(Direccion.NE); // Rebota hacia Noreste.
                }
            }
            case SE -> {
                if (impactoX % 2 == 0 && impactoY % 2 == 1) { // Impacto en la parte izquierda.
                    laser.cambiarDireccion(Direccion.SW); // Rebota hacia Suroeste.
                } else { // Impacto en la parte superior.
                    laser.cambiarDireccion(Direccion.NE); // Rebota hacia Noreste.
                }
            }
            case SW -> {
                if (impactoX % 2 == 1 && impactoY % 2 == 0) { // Impacto en la parte superior.
                    laser.cambiarDireccion(Direccion.NW); // Rebota hacia Noroeste.
                } else { // Impacto en la parte derecha.
                    laser.cambiarDireccion(Direccion.SE); // Rebota hacia Sureste.
                }
            }
            default -> throw new IllegalArgumentException("Dirección no válida para el láser.");
        }
    }
}
