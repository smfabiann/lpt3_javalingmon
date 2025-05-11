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
        /*
         * nombre, tipo, hpTotal, velocidad y los nombres de los 4 movimientos del Javaling
         * activo.
         */
        // cambiamos de color dependiendo del tipo de Javaling
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
        Utilidades.slowPrint("'" + javaling.getNombre() + "' Tipo: [" + javaling.getTipo() + "]\n" +
        "   hpTotal:" + javaling.getHpTotal() + "\n" +
        "   Velocidad: " + javaling.getVelocidad() + "\n");
        // Utilidades.setPrintColor(""); // volvemos el color al normal
        // nombrar los movimientos
        Utilidades.slowPrint("Movimientos: \n");
        Utilidades.slowPrint("  - " + javaling.movimientos[0].getNombre() + "\n");
        Utilidades.slowPrint("  - " + javaling.movimientos[1].getNombre() + "\n");
        Utilidades.slowPrint("  - " + javaling.movimientos[2].getNombre() + "\n");
        if (javaling.movimientos[3] != null) {  // si es que exsite el cuarto movimiento
            Utilidades.slowPrint("  - " + javaling.movimientos[3].getNombre() + "\n");
        }
        Utilidades.setPrintColor(""); // volvemos el color al normal
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
        // guardamos el porcentaje de vida actual
        double porcentajeVida;
        if (hpActual == 0) {
            porcentajeVida = 1.0;
        } else {
            porcentajeVida = hpActual / hpTotal;
        }

        // actualizamos hpTotal
        hpTotal = ((2* hpBase * nivel) / 100) + nivel + 10;

        // calculamos la nueva hpTotal
        hpActual = (int) Math.round(hpTotal * porcentajeVida);

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
