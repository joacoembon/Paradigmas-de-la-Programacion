package Modelo;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Nivel {
    private final Tablero tablero;
    private boolean estaCompletado = false;

    public Nivel(String archivo) throws Exception {
        this.tablero = cargarNivel(archivo);
    }

    private Tablero cargarNivel(String archivo) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        System.out.println("Intentando cargar el archivo: " + archivo);
        InputStream nivel = classLoader.getResourceAsStream(archivo);

        if (nivel == null) {
            throw new RuntimeException("No se pudo encontrar el archivo: " + archivo);
        }

        List<Laser> lasers = new ArrayList<Laser>();
        List<Objetivo> objetivos = new ArrayList<Objetivo>();
        HashMap<String, Celda> celdas = new HashMap<String, Celda>();

        final char emisor = 'E';
        final char celda = ' ';
        final char celdaPiso = '.';
        final char bloqueOpacoFijo = 'F';
        final char bloqueOpacoMovil = 'B';
        final char bloqueEspejo = 'R';
        final char bloqueVidrio = 'G';
        final char bloqueCristal = 'C';

        Scanner scanner = new Scanner(nivel);

        String line;
        int ancho = 0;
        int alto = 0;

        boolean esTablero = true; // Para saber si estamos leyendo la parte del tablero.
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            

            // Ignorar líneas vacías.
            if (line.isEmpty()) {
                if (esTablero) {
                    // Cambiamos a la sección de entidades después de encontrar una línea vacía.
                    esTablero = false;
                    continue; // Pasamos a la siguiente línea.
                } else {
                    // Si ya estamos en la sección de entidades y encontramos otra línea vacía, simplemente la ignoramos.
                    continue;
                }
            }

            if (esTablero) {
                // Procesar el tablero.
                if (ancho == 0) {
                    ancho = line.length() * 2; // Consideramos que todas las celdas son de 2*2.
                }
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    int x = (i * 2) + 1; // Multiplicamos por 2 porque cada celda ocupa 2 unidades.
                    int y = (alto * 2) + 1; // Y hacemos lo mismo para el alto.

                    switch (c) {
                        case celda:
                            celdas.put(x + ", " + y, new Celda(x, y, false, null));
                            break;
                        case celdaPiso:
                            celdas.put(x + ", " + y, new Celda(x, y, true, null));
                            break;
                        case bloqueOpacoFijo:
                            celdas.put(x + ", " + y, new Celda(x, y, true, new BloqueOpacoFijo(new Celda(x, y, true, null))));
                            break;
                        case bloqueOpacoMovil:
                            celdas.put(x + ", " + y, new Celda(x, y, true, new BloqueOpacoMovil(new Celda(x, y, true, null))));
                            break;
                        case bloqueVidrio:
                            celdas.put(x + ", " + y, new Celda(x, y, true, new BloqueVidrio(new Celda(x, y, true, null))));
                            break;
                        case bloqueCristal:
                            celdas.put(x + ", " + y, new Celda(x, y, true, new BloqueCristal(new Celda(x, y, true, null))));
                            break;
                        case bloqueEspejo:
                            celdas.put(x + ", " + y, new Celda(x, y, true, new BloqueEspejo(new Celda(x, y, true, null))));
                            break;
                        default:
                            System.err.println("Caracter inesperado en el tablero: " + c);
                            break;
                    }
                }
                alto++; // Incrementar la fila después de procesar cada linea del tablero.
            } else {
                // Procesar entidades (lasers y objetivos).
                String[] linea = line.split(" ");
                System.out.println("Procesando línea de entidad: " + line); // Para depuración
                if (linea.length < 3) {
                    System.err.println("Formato incorrecto en la línea: \"" + line + "\" (Se esperaban al menos 3 elementos)");
                    continue; // Pasar a la siguiente linea en caso de error.
                }

                if (linea[0].charAt(0) == emisor) {
                    // Procesar laser.
                    try {
                        lasers.add(new Laser(Integer.parseInt(linea[1]), Integer.parseInt(linea[2]), Direccion.valueOf(linea[3])));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error al parsear la dirección en la línea: " + line);
                    }
                } else {
                    // Procesar objetivo.
                    try {
                        objetivos.add(new Objetivo(Integer.parseInt(linea[1]), Integer.parseInt(linea[2])));
                    } catch (NumberFormatException e) {
                        System.err.println("Error al parsear las coordenadas del objetivo en la línea: " + line);
                    }
                }
            }
        }
        scanner.close();
        alto = alto*2;

        return new Tablero(ancho, alto, celdas, lasers, objetivos);
    }

    public Tablero getTablero() {return tablero;}

    public boolean estaCompletado() {
        return tablero.estaCompletado();
    }
}