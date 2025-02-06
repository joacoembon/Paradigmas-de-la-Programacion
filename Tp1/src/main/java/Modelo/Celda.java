package Modelo;

public class Celda {
    private int[] centro = new int[2];
    private final boolean tienePiso;
    private Bloque bloque; // Cuando es una celda libre, apunta a null.

    public Celda(int x, int y, boolean tienepiso, Bloque bloque) {
        this.centro[0] = x;
        this.centro[1] = y;
        this.tienePiso = tienepiso;
        this.bloque = bloque;
        if (bloque instanceof BloqueMovil) {
            ((BloqueMovil) bloque).celda = this; // Asigna la celda al bloque
        }
    }

    public int getX() {
        return centro[0];
    }

    public int getY() {
        return centro[1];
    }

    public boolean tienePiso() {
        return tienePiso;
    }

    public Bloque getBloque() {
        return this.bloque;
    }

    public void setBloque(BloqueMovil bloque) throws Exception {
        if (!tienePiso || this.bloque != null){
            throw new Exception("No se puede ocupar esta celda");
        }
        this.bloque = (Bloque) bloque;
        if (bloque != null) {
            bloque.celda = this; // Actualiza la celda del bloque
        }
    }

    public void Vaciar() {
        this.bloque = null;
    }

}