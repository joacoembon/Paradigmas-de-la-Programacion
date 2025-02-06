

import static org.junit.jupiter.api.Assertions.*;

import Modelo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

class LaserInteractionTest {

    private Laser laser;
    private Celda celdaInicial;
    private Tablero tablero;

    @BeforeEach
    void setUp() throws Exception {
        // Inicialización de una celda en el tablero
        celdaInicial = new Celda(1, 1, true, null);  // Celda vacía con piso.
        tablero = new Tablero(10, 10, new HashMap<>(), new ArrayList<>(), new ArrayList<>());
        laser = new Laser(1, 1, Direccion.NE); // Crear un láser apuntando al noreste
    }

    @Test
    void testBloqueCristalInteraction() throws Exception {
        BloqueCristal bloqueCristal = new BloqueCristal(celdaInicial);
        bloqueCristal.interactuarConLaser(laser, tablero);
        assertEquals(Direccion.NE, laser.getDireccion(), "El láser debería refractarse hacia el Este y seguir en NE");
    }

    @Test
    void testBloqueEspejoInteraction() throws Exception {
        BloqueEspejo bloqueEspejo = new BloqueEspejo(celdaInicial);
        bloqueEspejo.interactuarConLaser(laser, tablero);
        assertEquals(Direccion.SE, laser.getDireccion(), "El láser debería rebotar en el espejo");
    }

    @Test
    void testBloqueOpacoMovilInteraction() throws Exception {
        BloqueOpacoMovil bloqueOpacoMovil = new BloqueOpacoMovil(celdaInicial);
        bloqueOpacoMovil.interactuarConLaser(laser, tablero);
        assertTrue(laser.estaDetenido(), "El láser debería detenerse en el bloque opaco móvil");
    }

    @Test
    void testBloqueVidrioInteraction() throws Exception {
        BloqueVidrio bloqueVidrio = new BloqueVidrio(celdaInicial);
        bloqueVidrio.interactuarConLaser(laser, tablero);
        assertEquals(Direccion.SE, laser.getDireccion(), "El láser debería rebotar y duplicarse al pasar por vidrio");
    }
}
