public class Fuego extends Javaling {
    private int enLlamas;

    public Fuego() {
        // contructor del javaling
        super(60, 1, Tipo.FUEGO,
        new Movimiento[] {
            new Movimiento("Lanzallamas", Tipo.FUEGO, false),
            new Movimiento("Ascuas", Tipo.FUEGO, false),
            new Movimiento("Picante", Tipo.FUEGO, true),
            Movimiento.movimientoRandomChance() // 50% de movimiento aleatorio
            }
        );
        
        this.hpBase += desfase(5);
        this.enLlamas = 0;
    }
    
    // sobrecargado para el entrenador que me da lata hacerlo de otra forma
    public Fuego(boolean b) {
    // constructor del javaling
        super(60, 1, Tipo.FUEGO,
        new Movimiento[] {
            new Movimiento("Lanzallamas", Tipo.FUEGO, false),
            new Movimiento("Ascuas", Tipo.FUEGO, false),
            new Movimiento("Picante", Tipo.FUEGO, true),
            Math.random() < 0.25 ? Movimiento.movimientoRandomChance() : null // 25% de probabilidad
            }
        );
        
        this.hpBase += desfase(5);
        this.enLlamas = 0;
    }

    @Override
    public int atacar(Javaling objetivo, int indiceMov) {
        Movimiento mov = getMovimientos()[indiceMov];
        double STAB = (mov.getTipo() == getTipo()) ? 1.5 : 1.0;
        double eficacia = 1.0;

        // Eficacia según tabla
        if (objetivo.getTipo() == Tipo.PLANTA) eficacia *= 2;
        if (objetivo.getTipo() == Tipo.AGUA || objetivo.getTipo() == Tipo.DRAGON) eficacia *= 0.5;

        // Fórmula de daño
        double daño = ((2 * getNivel()) / 5.0 + 2) * mov.getPotencia() * (getHpBase() / 100.0);
        daño = ((daño / 50.0) + 2) * STAB * eficacia;

        // Si el objetivo es un dragon con multiescamas
        if (objetivo instanceof Dragon) {
            Dragon dragonObjetivo = (Dragon) objetivo;
            if (dragonObjetivo.getMultiescamas() && objetivo.getHpActual() == objetivo.getHpTotal()) {
                daño *= 0.1; // Reducir el daño al 10% si la habilidad está activa
                dragonObjetivo.setMultiescamas(false); // desactivamos la habilidad
            }
        }

        // Incrementar el contador "enLlamas" si el movimiento tiene potencia > 0
        if (mov.getPotencia() > 0) {
            enLlamas++;
            if (enLlamas >= 3) {
                daño *= 1.2; // Incrementar daño en un 20%
                enLlamas = 0; // Reiniciar el contador
            }
        }

        return (int) daño;
    }

    @Override
    public void subirNivel(int nivel) {
        return;
    }
}