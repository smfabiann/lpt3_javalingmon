import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Jugador implements Batalla {
    private String nombre;
    private List<Objeto> bolsa;
    private Javaling equipo[] = new Javaling[6];
    private Piso pisoActual;

    // contructor
    public Jugador() {
        this.bolsa = new ArrayList<>();
    }
    
    public Javaling elgirJavalingActivo() {
        // primero chequeamos si hay por lo menos un javaling vivo
        boolean valido = false;
        // iteramos por el equipo
        for (int i = 0; i < equipo.length; i++) {
            // se me olvido el chequeo de null
            if (equipo[i] == null) continue;
            // si el javaling tiene vida
            if (equipo[i].getHpActual() > 0) {
                valido = true;  // si encuentra uno con vida, pordemos continuar con el while
            }
        }
        
        Scanner scanner = new Scanner(System.in);
        
        while (valido) {
            Utilidades.slowPrint("Seleccion tu Javaling para pelear!!\n");
            verJavalingsEquipo();
            Utilidades.slowPrint("  0) ESCAPAR!!!\n", "rojo");

            int eleccion = scanner.nextInt();
            
            // si es que el jugador quiere escapar
            if (eleccion == 0) {
                return null;
            };

            // chequeamos que el javaling elegido esta vivo
            if (equipo[eleccion-1].getHpActual() <= 0) {
                Utilidades.slowPrint("Opcion invalida.\n", "rojo");
                continue;
            }
            return equipo[eleccion-1];
        }
        return null;
    }

    /* Funciones agregadas */
    // funcion para añadir objetos a la bolsa en un especio vacio
    public void añadirEnBolsa(TipoObjeto tipoObjeto) {
        // si el objeto ya existe en la bolsa, se le añade 1 a cantidad del objeto
        for (Objeto i_objeto: bolsa) {
            if (i_objeto.getTipoObjeto() == tipoObjeto) {
                // se suma 1 a la cantidad del objeto
                i_objeto.addOneCantidad();
                // mensaje al jugador sobre el objeto encontrado
                Utilidades.slowPrint("Encontraste una " + tipoObjeto + "!!!\n", "naranja");
                return;
            }
        }
        // si no, se crea el objeto y se añade a la bolsa
        Objeto objeto = new Objeto(tipoObjeto);
        bolsa.add(objeto);
        // mensaje al jugador sobre el objeto encontrado
        Utilidades.slowPrint("Encontraste una " + tipoObjeto + "!!!\n", "naranja");
        return;
    }
    // en caso que el equipo este lleno
    private void añadirEquipoLLeno(Javaling javaling) {
        // si es que no encontro un especio vacio, entonces se le da la opcion al jugador de
        // remplazar uno de sus javalines

        // primero, mostraremos todos los Javalings del Inventario
        int iterador = 1;
        for (Javaling i : equipo) {
            // si es que no hay un Javaling en esa posicion
            if (i == null) continue;
            // agregamos color constumisado para el tipo de Javaling
            if (i.getTipo() == Tipo.FUEGO) Utilidades.setPrintColor("naranja");
            if (i.getTipo() == Tipo.AGUA) Utilidades.setPrintColor("cyan");
            if (i.getTipo() == Tipo.PLANTA) Utilidades.setPrintColor("verde");
            if (i.getTipo() == Tipo.DRAGON) Utilidades.setPrintColor("magenta");
            // numero del javaling y su nombre con su respectivo tipo
            Utilidades.slowPrint(iterador + ". '" + i.getNombre() + "' ["+ i.getTipo() + "]\n");
            iterador++;
        }
        Utilidades.slowPrint("7. CANCELAR\n");

        // le preguntamos al jugador cual quiere reemplazar. 7 para cancelar
        System.out.println("¿Cual deseas remplazar?");
        Scanner scanner = new Scanner(System.in);
        int eleccion = scanner.nextInt();
        if (eleccion == 7) {
            scanner.close();
            return;
        };
        // se remplaza el javaling
        subirNivelesTodos();
        getEquipo()[eleccion-1] = javaling;
        scanner.close();
        return;
    }
    // añade a un javaling al equipo
    public void añadirJavalingEquipo(Javaling javaling) {
        // iteramos por el equipo
        for (int i = 0; i < equipo.length; i++) {
            if (equipo[i] == null) {
                // se sube de nivel a todo el equipo
                subirNivelesTodos();

                equipo[i] = javaling;
                return;
            }
        }
        // en caso que el equipo este lleno
        añadirEquipoLLeno(javaling);
        return;
    }
    // ve los  Javalings del equipo
    public void verJavalingsEquipo() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║              EQUIPO JAVALINGS              ║");
        System.out.println("╚════════════════════════════════════════════╝");

        int iter = 1;
        for (Javaling i : equipo) {
            // Si no hay un Javaling en esa posición, lo ignoramos
            if (i == null) continue;

            // Cambiar el color según el tipo del Javaling
            if (i.getTipo() == Tipo.FUEGO) Utilidades.setPrintColor("naranja");
            if (i.getTipo() == Tipo.AGUA) Utilidades.setPrintColor("cyan");
            if (i.getTipo() == Tipo.PLANTA) Utilidades.setPrintColor("verde");
            if (i.getTipo() == Tipo.DRAGON) Utilidades.setPrintColor("magenta");

            // Mostrar información del Javaling con bordes
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.printf("║ %d) %-10s [%-6s] Lv. %-2d HP: %-3d/%-3d ║\n",
                iter, "'" + i.getNombre() + "'", i.getTipo(), i.getNivel(), i.getHpActual(), i.getHpTotal());
            System.out.println("╚════════════════════════════════════════════╝");

            iter++;
        }
    }
    // subir nivel a todos los javalings
    public void subirNivelesTodos() {
        for (int i = 0; i < equipo.length; i++) {
            if (equipo[i] == null) break;
            equipo[i].levelUp();
            equipo[i].updateHp();
        }
    }

    // getters
    public Javaling[] getEquipo() {return equipo;}
    public Piso getPisoActual() {return pisoActual;}
    public List<Objeto> getBolsa() {return bolsa;}
    // setters
    public void setEquipoSlot(Javaling javaling, int slot) {equipo[slot] = javaling;}
    public void setPisoActual(Piso piso) {pisoActual = piso;}
}
