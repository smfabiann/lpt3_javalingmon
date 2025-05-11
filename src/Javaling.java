import java.util.Random;

public abstract class Javaling {
    private String nombre;
    protected int hpBase;
    protected int velocidad;
    private int hpTotal;
    private int hpActual;
    private int nivel;
    private Tipo tipo;
    // [][][][]
    private Movimiento[] movimientos = new Movimiento[4];

    /* Contructor */
    public Javaling(int hpBase, int nivel_inicial, Tipo tipo, Movimiento movIniciales[]) {
        this.hpBase = hpBase;
        this.nivel = nivel_inicial;
        this.velocidad = 200;
        // se le añade un desfase de +-[0-199] a la velocidad
        this.velocidad += desfase(199);
        this.hpTotal = ((2* hpBase * nivel) / 100) + nivel + 10;
        this.hpActual = hpTotal;
        this.tipo = tipo;

        // iteramos por la lista de movimientos iniciales
        // 2 de Tipo, 1 de Estado y uno al azar (50%)
        // itera en 4 si el movimiento alazar 50% se cumple, sino se coloca 3
        for (int i = 0; i < 4 && i < movIniciales.length; i++) {
            // si encuentra un null, rompe el for
            if (movIniciales[i] == null) {break;}
            // se le asigna el movimiento
            movimientos[i] = movIniciales[i];
        }
    }


    /* Funciones */
    // retorna int, siendo un desfase de un rango entre  [0-numero]
    protected static int desfase(int numero) {
        Random rand = new Random();
        int desfase = rand.nextInt(numero + 1);     // [0, numero]
        boolean suma = rand.nextBoolean();

        // retorna un desfase opsitivo o negativo
        if (suma) {return desfase;} else {return -desfase;}
    }
    // imprime los datos del javaling
    public static void printJavalingData(Javaling javaling) {
        // Cambiar el color dependiendo del tipo del Javaling
        switch (javaling.getTipo()) {
            case Tipo.FUEGO:
                Utilidades.setPrintColor("naranjo");
                break;
            case Tipo.AGUA:
                Utilidades.setPrintColor("cyan");
                break;
            case Tipo.PLANTA:
                Utilidades.setPrintColor("verde");
                break;
            case Tipo.DRAGON:
                Utilidades.setPrintColor("magenta");
                break;
        }

        // Encabezado
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.printf("║ %-42s ║\n", "DATOS DEL JAVALING");
        System.out.println("╚════════════════════════════════════════════╝");

        // Información del Javaling
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.printf("║ Nombre: %-33s ║\n", "'" + javaling.getNombre() + "'");
        System.out.printf("║ Tipo: %-35s ║\n", "[" + javaling.getTipo() + "]");
        System.out.printf("║ HP Total: %-31d ║\n", javaling.getHpTotal());
        System.out.printf("║ Velocidad: %-30d ║\n", javaling.getVelocidad());
        System.out.println("╚════════════════════════════════════════════╝");

        // Movimientos
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.printf("║ %-42s ║\n", "MOVIMIENTOS");
        System.out.println("╚════════════════════════════════════════════╝");

        for (int i = 0; i < javaling.getMovimientos().length; i++) {
            Movimiento mov = javaling.getMovimientos()[i];
            if (mov != null) {
                System.out.printf(" %d) %-10s [%s] Potencia: %-3d Precisión: %-3d%%\n",
                    i + 1, mov.getNombre(), mov.getTipo(), mov.getPotencia(), mov.getPrecision());
            }
        }

        // Resetear el color al final
        Utilidades.setPrintColor("");
    }

    // retorna un Tipo de Javaling random
    public static Tipo tipoRandom() {
        Tipo[] tipos = Tipo.values();

        int randint = (int) (Math.random() * tipos.length);
        return tipos[randint];
    }

    // public int atacar(int Javaling objetivo, int indiceMov) {};
    public abstract int atacar(Javaling d, int indiceMov);
    public abstract void subirNivel(int nivel);
    // funcionm que sube de nivel
    public void levelUp() {
        nivel += 1;
    }

    // ajusta la vida actual. esta funcion TIENE que ejecutarce antes que updateHpTotal
    public void updateHp() {
        // Guardamos el porcentaje de vida actual
        double porcentajeVida = (hpActual > 0) ? (double) hpActual / hpTotal : 0.0;

        // Actualizamos hpTotal
        hpTotal = ((2 * hpBase * nivel) / 100) + nivel + 10;

        // Calculamos la nueva hpActual solo si el Javaling tiene vida
        if (hpActual > 0) {
            hpActual = (int) Math.round(hpTotal * porcentajeVida);
        }
    }

    // imprime los movimentos del Javaling
    public void printMovimientos() {
        int i = 1;
        for (Movimiento mov : movimientos) {
            if (mov != null) {
                // printeamos el nombre del movimiento, su tipo, potencia y presicion
                Utilidades.slowPrint(i + ") '" + mov.getNombre() + "' [" + mov.getTipo() + "] " +
                "Potencia: " + mov.getPotencia() + " Precision: " + mov.getPrecision() + "%\n");
                i++;
            }
        }
    }

    // setters
    public void setNombre(String name) {nombre = name;}
    public void setNivel(int n) {nivel = n;}
    public void setHpTotal(int hp) {hpTotal = hp;}
    public void setHpActual(int hp) {hpActual = hp;}
    // getters
    public String getNombre() {return nombre;}
    // public int getHpBase() {return hpBase;}
    public int getVelocidad() {return velocidad;}
    public int getHpTotal() {return hpTotal;}
    public int getHpActual() {return hpActual;}
    public int getHpBase() {return hpBase;}
    public int getNivel() {return nivel;}
    public Tipo getTipo() {return tipo;}
    public Movimiento[] getMovimientos() {return movimientos;}
}
