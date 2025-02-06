
import static org.junit.jupiter.api.Assertions.*;

import Modelo.BloqueMovil;
import Modelo.Laser;
import Modelo.Nivel;
import Modelo.Tablero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NivelCompletoTest {

    private Nivel nivel;
    private Tablero tablero;

    @BeforeEach
    void setUp() throws Exception {
        String archivo = "level6.dat";
        nivel = new Nivel(archivo);
        tablero = nivel.getTablero();
    }

    @Test
    void testNivelCompletado() {
        // Simular el juego para alcanzar los objetivos
        /* level1
        tablero.moverBloque((BloqueMovil) tablero.getCelda(3,5).getBloque(),tablero.getCelda(5,5));

        tablero.moverBloque((BloqueMovil) tablero.getCelda(5,1).getBloque(),tablero.getCelda(1,7));

        tablero.moverBloque((BloqueMovil) tablero.getCelda(3,11).getBloque(),tablero.getCelda(5,11));

        tablero.moverBloque((BloqueMovil) tablero.getCelda(7,5).getBloque(),tablero.getCelda(7,7));
        for (Laser laser : tablero.getLasers()) {
            laser.propagarCamino(tablero);
        }
        tablero.estaCompletado();

        assertTrue(nivel.getTablero().estaCompletado(), "El nivel debería completarse cuando los objetivos se alcanzan");
       */
        /* level 2
        tablero.moverBloque((BloqueMovil) tablero.getCelda(1,9).getBloque(),tablero.getCelda(3,9));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(7,9).getBloque(),tablero.getCelda(7,5));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(1,1).getBloque(),tablero.getCelda(3,1));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(7,1).getBloque(),tablero.getCelda(1,3));
        for (Laser laser : tablero.getLasers()) {
            laser.propagarCamino(tablero);
        }
        tablero.estaCompletado();

        assertTrue(nivel.getTablero().estaCompletado(), "El nivel debería completarse cuando los objetivos se alcanzan");
        */
        /* level 3
        tablero.moverBloque((BloqueMovil) tablero.getCelda(3,3).getBloque(),tablero.getCelda(3,5));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(1,3).getBloque(),tablero.getCelda(3,3));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(3,1).getBloque(),tablero.getCelda(1,3));
        for (Laser laser : tablero.getLasers()) {
            laser.propagarCamino(tablero);
        }
        tablero.estaCompletado();

        assertTrue(nivel.getTablero().estaCompletado(), "El nivel debería completarse cuando los objetivos se alcanzan");
        */
        /* level 4
        tablero.moverBloque((BloqueMovil) tablero.getCelda(5,1).getBloque(),tablero.getCelda(3,1));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(7,3).getBloque(),tablero.getCelda(1,3));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(7,1).getBloque(),tablero.getCelda(7,5));

        for (Laser laser : tablero.getLasers()) {
            laser.propagarCamino(tablero);
        }
        tablero.estaCompletado();

        assertTrue(nivel.getTablero().estaCompletado(), "El nivel debería completarse cuando los objetivos se alcanzan");
         */
        /* level 5
        tablero.moverBloque((BloqueMovil) tablero.getCelda(7,5).getBloque(),tablero.getCelda(1,5));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(3,3).getBloque(),tablero.getCelda(5,7));

        for (Laser laser : tablero.getLasers()) {
            laser.propagarCamino(tablero);
        }
        tablero.estaCompletado();

        assertTrue(nivel.getTablero().estaCompletado(), "El nivel debería completarse cuando los objetivos se alcanzan");
         */
        /* level 6
        tablero.moverBloque((BloqueMovil) tablero.getCelda(1,1).getBloque(),tablero.getCelda(3,3));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(7,1).getBloque(),tablero.getCelda(5,1));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(1,7).getBloque(),tablero.getCelda(3,7));
        tablero.moverBloque((BloqueMovil) tablero.getCelda(7,7).getBloque(),tablero.getCelda(5,5));

        for (Laser laser : tablero.getLasers()) {
            laser.propagarCamino(tablero);
        }
        tablero.estaCompletado();

        assertTrue(nivel.getTablero().estaCompletado(), "El nivel debería completarse cuando los objetivos se alcanzan");
         */
    }
}

