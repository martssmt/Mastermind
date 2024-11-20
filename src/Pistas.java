public class Pistas {

// Ya implementada, NO TOCAR!!!!!!

    // Atributos

    private static final char PUNTO_BLANCO = '\u25CB';
    private static final char PUNTO_NEGRO = '\u25CF';

    private int aciertos;
    private int descolocados;

    // Constructor (dado ya)

    public Pistas(int aciertos, int descolocados) {
        this.aciertos = aciertos;
        this.descolocados = descolocados;
    }

    // Visualizar (dada ya)

    public void visualizar() {
        if (aciertos == 0 && descolocados == 0) {
            System.out.print("Fallo completo");
        } else {
            for (int i = 0; i < aciertos; i++) {
                System.out.print(PUNTO_NEGRO + " ");
            }
            for (int i = 0; i < descolocados; i++) {
                System.out.print(PUNTO_BLANCO + " ");
            }
        }
        System.out.print("\u001B[0m");
    }

    // Getter aciertos (dada ya)

    public int getAciertos() {
        return aciertos;
    }

    // toString (dado ya)

    @Override
    public String toString() {
        return aciertos + " " + descolocados;
    }
}
