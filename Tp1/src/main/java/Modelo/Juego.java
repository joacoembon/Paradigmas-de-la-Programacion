package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Juego {
    private Nivel nivel = null;
    private final List<String> niveles = new ArrayList<String>();

    public Juego() {
        for (int i = 0; i < 6; i++) {
            String actual = String.valueOf(i);
            niveles.add("level" + i + ".dat");
        }
    }

    public void SetNivel(String nivel) throws Exception {
        this.nivel = new Nivel(nivel);
    }

    public Nivel getNivel() {
        return nivel;
    }

}
