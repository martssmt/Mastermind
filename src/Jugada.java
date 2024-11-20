// Enumerado (dado ya)

enum Color {
    ROJO, VERDE, AMARILLO, PURPURA
}

// Clase

public class Jugada {

    // Atributos

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final char CUADRADO = '\u25A0';

    private Color[] fichas;

        // Constructor (hecho)

    public Jugada(String cadena) {
        this.fichas= new Color[cadena.length()];
        for (int i=0; i<cadena.length(); i++) {
            switch (cadena.charAt(i)) {
                case 'R': {
                    fichas[i]=Color.ROJO;
                    break;
                }
                case 'V': {
                    fichas[i]=Color.VERDE;
                    break;
                }
                case 'A': {
                    fichas[i]=Color.AMARILLO;
                    break;
                }
                case 'P': {
                    fichas[i]=Color.PURPURA;
                    break;
                }
            }
        }
    }

        // Constructor 2 (hecho)

    public Jugada(int numFichas) {
        this.fichas=new Color[numFichas];
        for (int i=0; i<numFichas; i++) {
            int numColor=(int) (Math.random()*4);
            switch (numColor) {
                case 0: {
                    fichas[i]=Color.ROJO;
                    break;
                }
                case 1: {
                    fichas[i]=Color.VERDE;
                    break;
                }
                case 2: {
                    fichas[i]=Color.AMARILLO;
                    break;
                }
                case 3: {
                    fichas[i]=Color.PURPURA;
                    break;
                }
            }
        }
    }

    // Comprobación de la jugada (rev)

    public Pistas comprobar(Jugada oculta) {
        boolean[] encontradasActual = new boolean[fichas.length];
        boolean[] encontradasJugada = new boolean[fichas.length];
        int contAciertos=0;
        int contDescolocados=0;
        for (int i=0; i<fichas.length; i++) {
            if (this.fichas[i]==oculta.fichas[i]) {
                encontradasActual[i]=true;
                encontradasJugada[i]=true;
                contAciertos++;
            }
        }
        boolean encontrada = false;
        for (int i=0; i<fichas.length && !encontrada; i++) {
            if (!encontradasActual[i]) {
                for (int j=0; j<fichas.length; j++) {
                    if (!encontradasJugada[j]&&this.fichas[i]==oculta.fichas[j]) {
                        encontradasActual[i]=true;
                        encontradasJugada[j]=true;
                        contDescolocados++;
                        encontrada = true;
                    }
                }
            }
        }
        return new Pistas(contAciertos,contDescolocados);
    }

    // Visualizar (dado ya)

    public void visualizar() {
        for (int i = 0; i < fichas.length; i++) {
            switch (fichas[i]) {
                case ROJO:
                    System.out.print(ANSI_RED + CUADRADO + " ");
                    break;
                case VERDE:
                    System.out.print(ANSI_GREEN + CUADRADO + " ");
                    break;
                case AMARILLO:
                    System.out.print(ANSI_YELLOW + CUADRADO + " ");
                    break;
                case PURPURA:
                    System.out.print(ANSI_PURPLE + CUADRADO + " ");
                    break;
            }
        }
        System.out.print(ANSI_BLACK);
    }

    // toString (dado ya)

    public String toString() {
        String resultado = "";
        for (int i = 0; i < fichas.length; i++) {
            switch (fichas[i]) {
                case ROJO:
                    resultado += "R";
                    break;
                case VERDE:
                    resultado += "V";
                    break;
                case AMARILLO:
                    resultado += "A";
                    break;
                case PURPURA:
                    resultado += "P";
                    break;
            }
        }
        return resultado;
    }

}
