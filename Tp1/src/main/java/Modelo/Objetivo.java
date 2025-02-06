package Modelo;

public class Objetivo extends Entidad {
    protected boolean estaAlcanzado;

    public Objetivo(int x, int y) {
        super(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean estaAlcanzado() {
        return estaAlcanzado;
    }

    public void setEstaAlcanzado(boolean estaAlcanzado) {
        this.estaAlcanzado = estaAlcanzado;
    }
}