package Modelo;

public class BloqueMovil {
    protected Celda celda;

    public BloqueMovil(Celda celdaInicial) throws Exception {
        if (celdaInicial == null) {
            throw new IllegalArgumentException("La celda inicial no puede ser nula.");
        }
        this.celda = celdaInicial;
        this.celda.setBloque(this);
    }

    public void Mover(Celda celdaNueva) throws Exception {
        if (celdaNueva == null || !celdaNueva.tienePiso()) {
            throw new Exception("La celda de destino es inválida o no tiene piso.");
        }
        // Vacía la celda anterior
        this.celda.Vaciar(); // Cambia de celda
        celdaNueva.setBloque(this); // Asigna el bloque a la nueva celda
        this.celda = celdaNueva; // Actualiza la referencia de la celda
    }

    public Celda getCelda() {
        return celda;
    }
}