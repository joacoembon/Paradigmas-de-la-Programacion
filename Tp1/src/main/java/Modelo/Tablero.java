package Modelo;

import java.util.HashMap;
import java.util.List;

public class Tablero {
    private final int ancho;
    private final int alto;
    private final HashMap<String, Celda> celdas;
    private final List<Laser> lasers;
    private final List<Objetivo> objetivos;

    public Tablero(int ancho, int alto, HashMap<String, Celda> celdas, List<Laser> lasers, List<Objetivo> objetivos) {
        this.ancho = ancho;
        this.alto = alto;
        this.celdas = celdas;
        this.lasers = lasers;
        this.objetivos = objetivos;
    }
    
    public int getAncho() {
        return ancho;
    }
    
    public int getAlto() {
        return alto;
    }

    public HashMap<String, Celda> getCeldas() {
        return celdas;
    }

    public Celda getCelda(int x, int y) {
        String fila = String.valueOf(x);
        String columna = String.valueOf(y);
        return this.celdas.get(fila + ", " + columna);
    }

    public void agregarLaser(Laser nuevoLaser) {
        this.lasers.add(nuevoLaser);
    }

    public void sacarLaser(Laser viejoLaser) {
        this.lasers.remove(viejoLaser);
    }

    public boolean estaCompletado() {
        for(Objetivo obj : this.objetivos) {
            if (!obj.estaAlcanzado) {
                return false;
            }
        }
        return true;
    }

    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    public void moverBloque(BloqueMovil bloque, Celda celda){
        try {
            bloque.Mover(celda);
        } catch (Exception e) {
            return;
        }
    }

    public List<Laser> getLasers() {
        return lasers;
    }
}
