public class Agua extends Javaling {
    private boolean oleaje;

    public Agua() {
        super(55, 1, Tipo.AGUA, new Movimiento[]{
            new Movimiento("Tsunami", Tipo.AGUA, false),
            new Movimiento("Burbujas", Tipo.AGUA, false),
            new Movimiento("Congelar", Tipo.AGUA, true),
            Movimiento.movimientoRandomChance()
        });
        
        this.hpBase += desfase(5);
        this.oleaje = false;
    }
    // Constructor sobrecargado para el entrenador
    public Agua(boolean b) {
        super(55, 1, Tipo.AGUA, new Movimiento[]{
            new Movimiento("Tsunami", Tipo.AGUA, false),
            new Movimiento("Burbujas", Tipo.AGUA, false),
            new Movimiento("Congelar", Tipo.AGUA, true),
            Math.random() < 0.25 ? Movimiento.movimientoRandomChance() : null // 25% de probabilidad
        });
        
        this.hpBase += desfase(5);
        this.oleaje = false;
    }


    @Override
    public int atacar(Javaling objetivo, int indiceMov) {
        Movimiento mov = getMovimientos()[indiceMov];
        double STAB = (mov.getTipo() == getTipo()) ? 1.5 : 1.0;
        double eficacia = 1.0;
        
        // Eficacia según tabla
        if (objetivo.getTipo() == Tipo.FUEGO) eficacia *= 2;
        if (objetivo.getTipo() == Tipo.PLANTA || objetivo.getTipo() == Tipo.DRAGON) eficacia *= 0.5;
        
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

        // oelaje
        if (oleaje) {
            daño *= 1.15; // Incrementar daño en un 15%
            oleaje = false; // La habilidad se desactiva después de usarse
        }

        return (int) daño;
    }

    // activa la habilidad oleaje
    public void activarOleaje() {
        this.oleaje = true;
    }

    @Override
    public void subirNivel(int nivel) {
        return;
    }
}