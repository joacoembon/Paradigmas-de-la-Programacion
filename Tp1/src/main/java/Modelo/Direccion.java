package Modelo;

public enum Direccion {
    N(0, -1), //Norte
    E(1, 0), //Este
    W(-1, 0), //Oeste
    S(0, 1), //Sur
    NE(1, -1), // Norte-Este
    NW(-1, -1), // Norte-Oeste
    SE(1, 1), // Sur-Este
    SW(-1, 1); // Sur-Oeste

    private final int dx; // Desplazamiento en el eje X
    private final int dy; // Desplazamiento en el eje Y

    Direccion(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Direccion getDireccionOpuesta() {
        return switch (this) {
            case N -> S;
            case E -> W;
            case W -> E;
            case S -> N;
            case NE -> SW;
            case NW -> SE;
            case SE -> NW;
            case SW -> NE;
        };
    }
}
