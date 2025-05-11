import java.util.Random;

public class Entrenador implements Batalla{
    private boolean esCampeon;
    private Javaling equipo[] = new Javaling[6];

    private static Random random = new Random();

    public Entrenador(boolean campeon) {
        this.esCampeon = campeon;
    }

    /* Funciones agregadas */
    // retorn nivel random segun el piso
    private int getRandNivel(int piso) {
        // si el piso es menos a 5
        if (piso < 5) return Utilidades.randInt(4, 6);
        // en el caso que de que de un nivel menor a 1
        if (Utilidades.randInt(1, 3*piso) + Utilidades.randInt(-3, 3) < 1) return 1;
                // Lbase                         +          randit(-3,3)
        return Utilidades.randInt(1, 3*piso) + Utilidades.randInt(-3, 3);
    }
    // funcion que genera un tipo random de Javaling segun la probabilidad del entrenador
    private Tipo getTipoRandomEntrenador() {
        int prob = random.nextInt(100);   // 0-99

        if (prob < 5) { // chance que aparesca tipo dragon 5%
            return Tipo.DRAGON;
        }

        // se asume que es la misma chance para todos Javaling para la captura
        prob = random.nextInt(3);   // 0-2
        // un poqco
        if (prob == 0) {return Tipo.FUEGO;} else
        if (prob == 1) {return Tipo.AGUA;} else
        {return Tipo.PLANTA;}
    }

    // genera un equipo aleatorio segun el piso
    public void generarEquipoAleatorio(int piso) {
        // tamaño maximo de equipo
        int k = 1;
        if (piso >= 1 && piso <= 20) {
        k = 1;      
        } else if (piso >= 21 && piso <= 30) {
            k = 2;
        } else if (piso >= 31 && piso <= 40) {
            k = 3;
        } else if (piso >= 41 && piso <= 50) {
            k = 4;
        }
        // este sera el tamaño del equipo del entrenador
        k += (random.nextBoolean() ? 1:0);
        if (k > 6) k = 6;

        // creamos el equipo del entrenador
        for (int i = 0; i < k; i++) {
            // selecionamos de forma alzar el tipo de Javaling del entrenador (no tenia idea que se podia hacer asi)
            Javaling javalinito = switch (getTipoRandomEntrenador()) {
                case Tipo.FUEGO -> new Fuego();
                case Tipo.AGUA -> new Agua();
                case Tipo.PLANTA -> new Planta();
                case Tipo.DRAGON -> new Dragon();
            };
            // actualizamos el status del javaling
            javalinito.setNivel(getRandNivel(piso));
            javalinito.updateHp();
            // asignamos el javaling al equipo
            equipo[i] = javalinito;
        }
    }

    // coloca null al javaling equivalente
    public void nullifyJavaling(Javaling javaling) {
        for (int i = 0; i < equipo.length; i++) {
            if (equipo[i] == javaling) {
                equipo[i] = null;
            }
        }
    }

    // gettter
    public Javaling[] getEquipo() { return equipo;}

    public Javaling elgirJavalingActivo() {
        // parte desde indice 0 buscando un Javaling
        for (int i = 0; i < equipo.length; i++) {
            if (equipo[i] != null) return equipo[i];
        }
        // si no encuentra ninguno, significa que murieron, entonces devolver null
        return null;
    }
        

}
