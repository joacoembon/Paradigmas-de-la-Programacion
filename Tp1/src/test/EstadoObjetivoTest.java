
import Modelo.Direccion;
import Modelo.Laser;
import Modelo.Objetivo;
import Modelo.Tablero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EstadoObjetivoTest {

    private Tablero tablero;
    private Laser laser;
    private Objetivo objetivo;

    @BeforeEach
    void setUp() throws Exception {
        objetivo = new Objetivo(2, 2);
        List<Objetivo> objetivos = List.of(objetivo);
        tablero = new Tablero(10, 10, new HashMap<>(), List.of(laser), objetivos);
        laser = new Laser(2, 2, Direccion.N);
    }

    @Test
    void testObjetivoAlcanzadoPorLaser() {
        objetivo.Actualizar(List.of(laser));
        assertTrue(objetivo.estaAlcanzado(), "El objetivo debería estar alcanzado por el láser");
    }
}

