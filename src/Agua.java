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
        
        return (int) daño;
    }

    @Override
    public void subirNivel(int nivel) {
        return;
    }
}