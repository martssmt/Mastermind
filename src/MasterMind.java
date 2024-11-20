import java.io.*;

public class MasterMind {

    // Atributos

    private Tablero tablero;
    private Jugada jugadaOculta;
    private int numFichas;

    // Getter tablero (dado ya)

    public Tablero getTablero() {
        return tablero;
    }

        // Constructor (hecho)

    public MasterMind(int numFichas) {
        this.numFichas=numFichas;
        jugadaOculta=new Jugada(numFichas);
        tablero=new Tablero();
    }

        // Constructor de partidas ya empezadas (rev)

    public MasterMind(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            this.tablero = new Tablero();
            String linea = reader.readLine();
            this.jugadaOculta = new Jugada(linea);
            this.numFichas = linea.length();
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length == 3) {                   // Validar formato de la línea
                    Jugada jugada = new Jugada(partes[0]);
                    int aciertos = Integer.parseInt(partes[1]);
                    int descolocados = Integer.parseInt(partes[2]);
                    Pistas pistas = new Pistas(aciertos, descolocados);
                    tablero.insertar(jugada, pistas);
                } else {
                    System.out.println("Línea mal formateada: " + linea);
                }
            }
        } catch (IOException ex) {
            System.out.println("ERROR AL RECUPERAR LA PARTIDA");
        }
    }


    // Guardar partida (rev)

    public void guardarPartida(String nombreArchivo) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(nombreArchivo));
            writer.write(jugadaOculta.toString());
            writer.newLine();
            for (int i = 0; i < tablero.getNumJugadas(); i++) {
                writer.write(tablero.getJugadas()[i].toString() + " ");
                writer.write(tablero.getResultados()[i].toString());
                writer.newLine();
            }
            System.out.println("Partida guardada correctamente en " + nombreArchivo);
        } catch (IOException ex) {
            System.out.println("ERROR AL GUARDAR LA PARTIDA");
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo");
            }
        }
    }

    // Jugar (hecho)

    public void jugar() {
        boolean ganado=false;
        boolean ordenDeGuardar=false;
        while (!tablero.completo()&&!ganado&&!ordenDeGuardar) {
            String cadenaJugada=Teclado.leerJugadaGuardar(numFichas,"Introduce jugada o G (guardar la partida)\nR (Rojo), V (Verde), A (Amarillo), P (Purpura) ");
            if (cadenaJugada.equals("G")) {
                guardarPartida(Teclado.leerString("Nombre del archivo: "));
                ordenDeGuardar=true;
            } else {
                Jugada jugada = new Jugada(cadenaJugada);
                Pistas pistas = jugada.comprobar(jugadaOculta);
                tablero.insertar(jugada, pistas);
                tablero.visualizar();
                if (pistas.getAciertos() == numFichas) {
                    System.out.println("ACERTASTE LA JUGADA");
                    ganado = true;
                }
            }
        }
        if (tablero.completo()&&!ganado&&!ordenDeGuardar) {
            System.out.println("FIN DE LOS INTENTOS, NO CONSEGUISTE ACERTAR");
            System.out.print("La jugada oculta era\t");
            jugadaOculta.visualizar();
        }
    }

    // Main (dada ya)

    public static void main(String[] args) {
        MasterMind masterMind;
        if (Teclado.leerSiNo("¿Quieres recuperar una partida? (S/N): ") == 'S') {
            String nombreArchivo = Teclado.leerString("Nombre del archivo: ");
            masterMind = new MasterMind(nombreArchivo);
            masterMind.getTablero().visualizar();
        } else {
            int fichas = Teclado.leerEntero(4, 6, "Número de fichas de las jugadas (4 - 6): ");
            masterMind = new MasterMind(fichas);
        }
        masterMind.jugar();
    }
}
