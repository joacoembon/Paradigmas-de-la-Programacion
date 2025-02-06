
import static org.junit.jupiter.api.Assertions.*;

import Modelo.BloqueMovil;
import Modelo.BloqueOpacoMovil;
import Modelo.Celda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovimientoBloqueTest {

    private Celda celdaInicial;
    private Celda celdaDestino;
    private BloqueMovil bloqueMovil;

    @BeforeEach
    void setUp() throws Exception {
        celdaInicial = new Celda(2, 2, true, null);
        celdaDestino = new Celda(3, 3, true, null);
        bloqueMovil = new BloqueOpacoMovil(celdaInicial);
    }

    @Test
    void testMoverBloqueExitosamente() throws Exception {
        bloqueMovil.Mover(celdaDestino);
        assertEquals(celdaDestino, bloqueMovil.getCelda(), "El bloque debería moverse a la nueva celda");
    }

    @Test
    void testMoverBloqueACeldaSinPiso() {
        Celda celdaSinPiso = new Celda(4, 4, false, null);
        Exception exception = assertThrows(Exception.class, () -> {
            bloqueMovil.Mover(celdaSinPiso);
        });
        assertEquals("La celda de destino es inválida o no tiene piso.", exception.getMessage());
    }
}

