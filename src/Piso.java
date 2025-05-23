import java.util.Random;
import java.util.Scanner;

public class Piso {
    private boolean centroSansanito;
    private int decision;
    public int numPiso;

    private static Random random = new Random();

    public Piso() {
        this.numPiso = 0;
        this.centroSansanito = false;
    }

    // funcion da de forma random, segun el pdf, un tipo de objeto
    private TipoObjeto tipoObjetoRandom() {
        double prob = Math.random() * 100;  // double del 0 al 100

        if (prob < 20) {        // 20%
            return TipoObjeto.POCION;
        } else if (prob < 50) { // 30%
            return TipoObjeto.SUPERPOCION;
        } else if (prob < 60) { // 10%
            return TipoObjeto.POCION_MAXIMA;
        } else if (prob < 70) { // 10%
            return TipoObjeto.REVIVIR_MAXIMO;
        } else if (prob < 90) { // 20%
            return TipoObjeto.CARAMELO_PEQUEÑO;
        } else {                // 10%
            return TipoObjeto.CARAMELO_GRANDE;
        }
    }

    // funcion da un Tipo de Javaling random, segun el pdf
    private Tipo tipoRandomCaptura() {
        Random random = new Random();

        int prob = random.nextInt(100);   // 0-99

        if (prob < 3) { // chance que aparesca tipo dragon 3%
            return Tipo.DRAGON;
        }

        // se asume que es la misma chance para todos Javaling para la captura
        prob = random.nextInt(3);   // 0-2
        // un poqco
        if (prob == 0) {return Tipo.FUEGO;} else
        if (prob == 1) {return Tipo.AGUA;} else
        {return Tipo.PLANTA;}
    }

    /*
     * return boolean
     * Funcion encargada de la logica de intentar capturar un Javaling.
     * Retorna false cuando hay que parar con el loop, retorna true cuando hay que seguir el loop
     */
    private boolean intento_de_captura(Jugador jugador, Tipo randomJavaling) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // chance para capturar un Javaling 40%
        int prob = random.nextInt(5);   // 0-4

        // 40% probabilidad de captura del Javaling
        if (prob < 2) {
            Utilidades.slowPrint("Javaling capturado con exito!!!\n", "amarillo");
            
            // le damos la opcion al jugador de nombrar al su Javaling
            Utilidades.slowPrint("¿Como deseas nombrarlo?\n");
            String nombreJavaling = scanner.nextLine();

            // creamos el Javaling
            Javaling javalinito;
            
            // contructor para cada javaling dependiendo del tipo
            if (randomJavaling == Tipo.FUEGO) {
                javalinito = new Fuego();
            } else if (randomJavaling == Tipo.AGUA) {
                javalinito = new Agua();
            } else if (randomJavaling == Tipo.PLANTA) {
                javalinito = new Planta();
            } else {    // sino, tiene que ser dragon
                javalinito = new Dragon();
            }

            // colocamos nombre al javaling
            javalinito.setNombre(nombreJavaling);
            // se añade Javaling al jugador
            jugador.añadirJavalingEquipo(javalinito);
            // damos data del Javaling capturado
            Javaling.printJavalingData(javalinito);

            // el loop se detiene
            return false;
        } else {
            // avisamos al jugador que no se pudo capturar el Javaling
            Utilidades.slowPrint("Ups! El Javaling no se pudo capturar!!\n", "naranjo");

            // ahora la probabilidad del 30% de que se escape
            prob = random.nextInt(100); // 0-99
            if (prob < 30) {
                Utilidades.slowPrint("El Javaling se a escapado!!\n", "rojo");
                // loop se detiene
                return false;
            }

            // el loop continua
            return true;
        }
    }

    // logica de las batallas. esta logica se repetira en todas las battallas
    private void doBattle(Jugador jugador) {
        Scanner scanner = new Scanner(System.in);
        Batalla jugadorB = jugador;
        Javaling javalingJugador = jugadorB.elgirJavalingActivo();

        // se genera un entrenador
        Entrenador entr = new Entrenador(false);
        entr.generarEquipoAleatorio(numPiso);   // genera el equipo del entrenador
        // loop principal
        while (true) {
            // mensajes iniciales. se le da las opciones al jugador
            System.out.println();
            Utilidades.slowPrint("1. Luchar\n");
            Utilidades.slowPrint("2. Cambiar Javaling\n");
            Utilidades.slowPrint("3. Escapar!!\n");

            
            Batalla entrenadorB = entr;
            // asignamos el javaling del enrenador. siempre es el mas cerca de [0] que no sea null
            Javaling javalingEntrenador = entrenadorB.elgirJavalingActivo();
            
            // vemos la opcion del jugador
            int opcion = scanner.nextInt();

            // habilidad de javaling tipo planta
            if (javalingJugador.getTipo() == Tipo.PLANTA) {
                // si el javaling es de tipo planta, se le da la habilidad de curarse
                if (javalingJugador.getHpActual() < javalingJugador.getHpTotal()) {
                    Utilidades.slowPrint("El javaling se cura un poco por su habilidad!!\n", "verde");
                    Planta planta = (Planta) javalingJugador;
                    Utilidades.slowPrint("Tu Javaling se cura " + planta.activarFotosintesis() + "de HP!!!\n", "verde");
                }
            }

            // loop principal de la batalla
            if (opcion == 1) {
                // Mostrar movimientos del jugador
                javalingJugador.printMovimientos();

                // Ejecutar el movimiento
                opcion = scanner.nextInt();
                int daño = javalingJugador.atacar(javalingEntrenador, opcion - 1);

                // Notificar al jugador sobre el movimiento y el daño
                Utilidades.mensajeMovimiento(javalingJugador.getNombre(), javalingJugador.getMovimientos()[opcion - 1].getNombre());
                Utilidades.mensajeDaño(daño);

                // Reducir la vida del Javaling enemigo
                javalingEntrenador.setHpActual(javalingEntrenador.getHpActual() - daño);

                // Si el Javaling enemigo muere
                if (javalingEntrenador.getHpActual() <= 0) {
                    Utilidades.mensajeEnemigoCaido();

                    // Cambiar el Javaling del entrenador
                    entr.nullifyJavaling(javalingEntrenador);
                    javalingEntrenador = entrenadorB.elgirJavalingActivo();

                    // Si no quedan más Javalings
                    if (javalingEntrenador == null) {
                        Utilidades.slowPrint("Entrenador derrotado!!!\n", "verde");
                        Utilidades.slowPrint("+2 Lv\n", "amarillo");
                        jugador.subirNivelesTodos();
                        jugador.subirNivelesTodos();
                        return;
                    }

                    // El entrenador saca otro Javaling
                    Utilidades.mensajeEntrenadorSacaOtroJavaling();
                }

                // Mostrar el estado del Javaling enemigo
                Utilidades.mensajeEstadoJavalingEnemigo(javalingEntrenador);
            } else if (opcion == 2) {
                javalingJugador = jugadorB.elgirJavalingActivo();
            } else if (opcion == 3) {
                Utilidades.slowPrint("CORRE CTM!\n", "rojo");
                Utilidades.sleep(700);
                for (int i = 0; i < 50; i++) System.out.println();
                Utilidades.slowPrint("Escapaste...\n", "naranja");
                return;
            }

            /*
             * Turno del entrenador
             */
            while (true) {
                int indice = random.nextInt(4);
                if (javalingEntrenador.getMovimientos()[indice] != null) {
                    int daño = javalingEntrenador.atacar(javalingJugador, indice);
                    Utilidades.slowPrint("El enemigo ataca!!!\n", "rojo");
                    Utilidades.mensajeDaño(daño);

                    // Reducir la vida del Javaling del jugador
                    javalingJugador.setHpActual(javalingJugador.getHpActual() - daño);

                    // Activar habilidad de oleaje si es tipo agua
                    if (javalingEntrenador.getTipo() == Tipo.AGUA) {
                        Agua agua = (Agua) javalingEntrenador;
                        agua.activarOleaje();
                    }

                    // Si el Javaling del jugador muere
                    if (javalingJugador.getHpActual() <= 0) {
                        Utilidades.slowPrint("Tu Javaling ha caído!!!!\n", "rojo");
                        javalingJugador.setHpActual(0);
                        javalingJugador = jugadorB.elgirJavalingActivo();

                        // Si no quedan más Javalings
                        if (javalingJugador == null) {
                            Utilidades.mensajeGameOver();
                            System.exit(0);
                        }
                    }

                    // Mostrar el estado del Javaling del jugador
                    Utilidades.slowPrint("Lv. " + javalingJugador.getNivel() + " '" +
                        javalingJugador.getNombre() + "' HP: " + javalingJugador.getHpActual() +
                        "/" + javalingJugador.getHpTotal() + "\n", "naranja");
                    break;
                }
            }
        }
    }

    // funcion que cura al los Javaling del jugador
    public void curar(Jugador jugador) {
        for (int i = 0; i < jugador.getEquipo().length; i++) {
            // si es que no hay javaling en ese slot
            if (jugador.getEquipo()[i] == null) {continue;}
            jugador.getEquipo()[i].setHpActual(jugador.getEquipo()[i].getHpTotal());
        }
        return;
    }
    // ejecuta la decision correspondiente
    public void ejecutarDecision(Jugador jugador) {
        switch (decision) {
            case 1: {   // Pelear contra otro entrenador
                System.out.println();
                // funcion encargada de la logica del juego
                doBattle(jugador);
                // se aumenta el numero del piso
                addOneNumPiso();
                break;
            }
            case 2: {   // Capturar un Javaling
                // tipo random de javaling
                Tipo randomJavaling = tipoRandomCaptura();
                Utilidades.slowPrint("Un Javaling salvage tipo '" + randomJavaling
                + "' ha aparecido!!\n", "naranja");

                while (true) {
                    // se le da la opcion de intentar capturar al Javaling
                    Utilidades.slowPrint("¿Quieres intentar capturarlo? (y/n)\n", "naranja");
                    Scanner scanner = new Scanner(System.in);
                    String eleccion = scanner.nextLine();
                    eleccion = eleccion.toLowerCase();

                    // procesamos la decicion del jugador
                    if (eleccion.equals("y")) {
                        // se intentara capturar un Javaling 40% chance de capturarlo, si falla 30% chance
                        // de que se escape
                        if (!intento_de_captura(jugador, randomJavaling)) {
                            break;
                        }
                    } else if (eleccion.equals("n")) {
                        break;
                    }
                }
                // finalmente añadimos 1 piso
                addOneNumPiso();
                break;
            }
            case 3: {   // Elegir un objeto aleatorio
                /*
                    * Pocion               20%
                    * Superpocion          30%
                    * Pocion Maxima        10%
                    * Revivir Maximo       10%
                    * Caramelo pequeño     20%
                    * Caramelo grandre     10%
                    */
                TipoObjeto tipoObjetoRandom = tipoObjetoRandom();
                jugador.añadirEnBolsa(tipoObjetoRandom);
                addOneNumPiso();
                break;
            }
            case 4: {   // ve el contenido de la bolsa
                if (jugador.getBolsa().isEmpty()) {
                    Utilidades.slowPrint("No hay nada todavia en la bolsa.\n", "naranja");
                    break;
                }
                for (Objeto objeto : jugador.getBolsa()) {
                    Utilidades.slowPrint(objeto.getTipoObjeto().toString(), "naranja");
                    Utilidades.slowPrint(": " + objeto.getCantidad() + "\n", "naranja");
                }
                break;
            }
            case 5: {   // ve los javalings del jugador
                jugador.verJavalingsEquipo();
                break;
            }
            case 6: {
                if (centroSansanito) {
                    curar(jugador);
                    Utilidades.slowPrint("\nTus Javalines han sido curados!!\n\n", "verde");
                    addOneNumPiso();
                } else {
                    break;
                }
            }
        }
    }

    // añade 1 a numPiso
    private void addOneNumPiso() {numPiso++;}

    // getters
    public int getDecision() {return decision;}
    public int getNumPiso() {return numPiso;}
    public boolean getCentroSansanito() {return centroSansanito;}
    // setters
    public void setDecision(int opcion) {decision = opcion;}
    public void setCentroSansanito(boolean bool) {centroSansanito = bool;}
}
