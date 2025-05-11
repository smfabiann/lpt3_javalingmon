public class Planta extends Javaling {
    private int fotosintesis;

    public Planta() {
        super(65, 1, Tipo.PLANTA, new Movimiento[]{
            new Movimiento("Latigazo", Tipo.PLANTA, false),
            new Movimiento("Hoja afilada", Tipo.PLANTA, false),
            new Movimiento("Esporas", Tipo.PLANTA, true),
            Movimiento.movimientoRandomChance()
        });
        
        this.hpBase += desfase(5);
        this.fotosintesis = 0;
    }

    @Override
    public int atacar(Javaling objetivo, int indiceMov) {
        Movimiento mov = getMovimientos()[indiceMov];
        double STAB = (mov.getTipo() == getTipo()) ? 1.5 : 1.0;
        double eficacia = 1.0;
        
        // Eficacia según tabla
        if (objetivo.getTipo() == Tipo.AGUA) eficacia *= 2;
        if (objetivo.getTipo() == Tipo.FUEGO || objetivo.getTipo() == Tipo.DRAGON) eficacia *= 0.5;
        
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