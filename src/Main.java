import java.util.Scanner;

public class Main {
    // inicializamos un scanner que se reutilizara en todo el codigo
    // tengo ententido que si se hace de esta forma se puede reutilizar en todo el codigo
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // inicializamos al jugador
        Jugador jugador = new Jugador();
        /*
         * Inicio del juego
         * Introduccion
        */
        {
            System.out.println("Escoge tu Javaling!!");
            System.out.println("1. [FUEGO]");
            System.out.println("2. [AGUA]");
            System.out.println("3. [PLANTA]");
            
            // escaneamos por la opcion del jugador
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                {
                    // tipo Fuego
                    jugador.setEquipoSlot(new Fuego(), 0);
                    Utilidades.slowPrint("Escogiste un Javaling tipo " + Tipo.FUEGO + "!!!!\n",
                    "naranjo");
                    break;
                }
                case 2:
                {
                    // tipo Agua
                    jugador.setEquipoSlot(new Agua(), 0);
                    Utilidades.slowPrint("Escogiste un Javaling tipo " + Tipo.AGUA + "!!!!\n",
                    "naranjo");
                    break;
                }
                case 3:
                {
                    // tipo Planta
                    jugador.setEquipoSlot(new Planta(), 0);
                    Utilidades.slowPrint("Escogiste un Javaling tipo " + Tipo.PLANTA + "!!!!\n",
                    "naranjo");
                    break;
                }
            }

            // nombrar al javaling
            System.out.println("¿Como lo quieres nombrar?");
            String nombre = scanner.nextLine();

            
            jugador.getEquipo()[0].setNombre(nombre);
            
            /*
             * Tras la elección de inicial se imprimen en consola: nombre, tipo, hpTotal,
             * velocidad y los nombres de los 4 movimientos del Javaling activo.
             */
            Javaling.printJavalingData(jugador.getEquipo()[0]);
            
            Utilidades.slowPrint("Tu y " + jugador.getEquipo()[0].getNombre() +
            " enfrentaran muchos desafios escalando la Torre de los Javalings!!!\n");
            Utilidades.slowPrint("Les deseo mucha suerte en su aventura, 'Jugador'.\n", "rojo");
        }
        
        /*
         * Loop Pricipal del juego
         */
        // inicialisamos los pisos
        Piso piso = new Piso();
        jugador.setPisoActual(piso);
        {
            for (int i = 0; i < 1; i++) {
                System.out.println();
            }
            // "Tutorial"
            System.out.println("Bienvenido a la Torre de Javalings, donde solo el mejor"
            + " entrenador podra escalar hasta la sima!!!");
            Utilidades.slowPrint("Actualmente estas en el Piso [0]\n", "naranja");
            System.out.println("Cada vez que traverzas un nuevo piso te daremos 3 opciones:\n" +
            "Pelear contra otro entrenador, capturar un Javaling para tu equipo o elegir" +
            " un objeto aleatorio.");
            Utilidades.slowPrint("Escoge sabiamente.\n", "rojo");
            System.out.println("Cada vez que escojas subiras 1 Piso.");
            System.out.println("Preparate para el enfrentamiento con el Campeon, ¡Es muy fuerte!");
            
            // Entramos al bucle principal del juego
            while (true) {
                System.out.println();
                Utilidades.slowPrint("Piso [" + piso.getNumPiso() + "]\n", "naranja");
                Utilidades.slowPrint("¿Que quieres hacer?\n");
                Utilidades.slowPrint("1. Pelear contra otro entrenador.\n");
                Utilidades.slowPrint("2. Capturar un Javaling.\n");
                Utilidades.slowPrint("3. Elegir un objeto aleatorio.\n");
                Utilidades.slowPrint("4. Ver bolsa.\n");
                Utilidades.slowPrint("5. Ver mis Javalings.\n");
                if (jugador.getPisoActual().getNumPiso() % 10 == 0 && jugador.getPisoActual().getNumPiso()!= 0) {
                    jugador.getPisoActual().setCentroSansanito(true);
                    Utilidades.slowPrint("6. Sansanito (sala de curacion).\n");
                } else {
                    jugador.getPisoActual().setCentroSansanito(false);
                }

                int opcion = -1;    // para inputs invalidos
                try {
                    opcion = scanner.nextInt();
                } catch (java.util.InputMismatchException e) {
                    Utilidades.slowPrint("Opcion invalida, por favor escoge una opcion valida.\n", "rojo");
                    scanner.nextLine(); // se limpa el scanner
                    continue; // devuelta al bucle
                }

                // se asigna la decision del jugador y se realiza
                jugador.getPisoActual().setDecision(opcion);
                jugador.getPisoActual().ejecutarDecision(jugador);
            }
            
        }
    }
}