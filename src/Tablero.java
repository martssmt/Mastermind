public class Tablero {

    // Atributos

    private final static int MAX_JUGADAS = 10;

    private Jugada[] jugadas;
    private Pistas[] pistas;
    private int numJugadas;

        // Constructor (hecho)

    public Tablero() {
       this.jugadas=new Jugada[MAX_JUGADAS];
       this.pistas=new Pistas[MAX_JUGADAS];
       this.numJugadas=0;
    }

        // Getters (hecho)

    public int getNumJugadas() {
        return numJugadas;
    }

    public Jugada[] getJugadas() {
        return jugadas;
    }

    public Pistas[] getResultados() {
        return pistas;
    }

        // Insertar jugada con sus pistas en el tablero (hecho)

    public void insertar(Jugada jugada, Pistas pistas) {
        this.jugadas[numJugadas] = jugada;
        this.pistas[numJugadas] = pistas;
        numJugadas++;
    }

        // Determina si tablero contiene max de jugadas (hecho)

    public boolean completo() {
        return numJugadas == MAX_JUGADAS;
    }

        // Visualiza tablero hasta el momento (hecho)

    public void visualizar() {
        System.out.print("\u001B[37m");
        for (int i=0; i<numJugadas; i++) {
            System.out.print("Jugada " + (i+1) + "\t");
            jugadas[i].visualizar();
            pistas[i].visualizar();
            System.out.println();
        }
        System.out.print("\u001B[0m");
    }


}
