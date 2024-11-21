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

    // Constructor de partidas ya empezadas (hecho)

    public MasterMind(String nombreArchivo) {
        BufferedReader entrada=null;
        try {
            entrada=new BufferedReader(new FileReader(nombreArchivo));
            String cadena= entrada.readLine();
            this.tablero=new Tablero();
            this.jugadaOculta=new Jugada(cadena);
            this.numFichas=cadena.length();
            while ((cadena=entrada.readLine())!=null) {
                String[] partes=cadena.split(" ");
                if (partes.length==3) {         // Comprueba que el formato está bien
                    Jugada jugada=new Jugada(partes[0]);
                    int aciertos=Integer.parseInt(partes[1]);
                    int descolocados=Integer.parseInt(partes[2]);
                    Pistas pistas=new Pistas(aciertos, descolocados);
                    tablero.insertar(jugada, pistas);
                } else {
                    System.out.println("ERROR. LINEA MAL FORMATEADA");
                }
            }
        } catch (IOException ex) {
            System.out.println("ERROR AL RECUPERAR LA PARTIDA");
        } finally {
            try {
                if (entrada!=null) {
                    entrada.close();
                }
            } catch (IOException ex) {
                System.out.println("ERROR AL CERRAR EL ARCHIVO");
            }
        }
    }


    // Guardar partida (hecho)

    public void guardarPartida(String nombreArchivo) {
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(nombreArchivo);
            salida.println(jugadaOculta.toString());
            for (int i = 0; i < tablero.getNumJugadas(); i++) {
                salida.print(tablero.getJugadas()[i].toString() + " ");
                salida.print(tablero.getResultados()[i].toString());
                salida.println();
            }
            System.out.println("Partida guardada correctamente en " + nombreArchivo);
        } catch (IOException ex) {
            System.out.println("ERROR AL GUARDAR LA PARTIDA");
        } finally {
            if (salida != null) {
                salida.close();
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
