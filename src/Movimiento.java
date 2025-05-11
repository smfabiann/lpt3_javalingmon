import java.util.Random;

public class Movimiento {
    private String nombre;
    private int potencia;
    private int precision;
    private Tipo tipo;
    private boolean esEstado;
    // agregados
    // private TipoMovimiento tipoMovimiento;


    // inicialisamos random
    private static Random random = new Random();

    // constructor
    public Movimiento(String nombre, Tipo tipo, boolean esEstado) {
        this.nombre = nombre;
        this.potencia = random.nextInt(120+1);  // potencia [0-120]
        this.precision = random.nextInt(100+1); // precision [0-100]
        this.tipo = tipo;
        this.esEstado = esEstado;
        // if (esEstado) {
        //     tipoMovimiento
        // }
    }

    /* Funciones */
    // retorna un movimiento random cualquiera
    public static Movimiento movimientoRandom() {
        // TODO
        Tipo tipo = Javaling.tipoRandom();
        Movimiento movimiento = new Movimiento("Caquita //CAMBIAR DESPUES", tipo, random.nextBoolean()); 

        return movimiento;
    }
    // retorna un movimiento random cualquiera pero con chance
    public static Movimiento movimientoRandomChance() {
        // TODO
        Tipo tipo = Javaling.tipoRandom();
        Movimiento movimiento = new Movimiento("Caquita //CAMBIAR DESPUES", tipo, random.nextBoolean()); 
        if (random.nextBoolean()) {
            return movimiento;
        } else {
            return null;
        }
    }

    // setters
    // getters
    public String getNombre() {return nombre;}
    public int getPotencia() {return potencia;}
    public int getPrecision() {return precision;}
    public Tipo getTipo() {return tipo;}
    public boolean getEsEstado() {return esEstado;}
}
