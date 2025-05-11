public class Dragon extends Javaling {
    private boolean multiescamas;

    public Dragon() {
        super(70, 1, Tipo.DRAGON, new Movimiento[]{
            new Movimiento("Cola dragón", Tipo.DRAGON, false),
            new Movimiento("Fuego dragón", Tipo.DRAGON, false),
            new Movimiento("Rugido", Tipo.DRAGON, true),
            Movimiento.movimientoRandomChance()
        });
        
        this.hpBase += desfase(5);
        this.multiescamas = false;
    }

    @Override
    public int atacar(Javaling objetivo, int indiceMov) {
        Movimiento mov = getMovimientos()[indiceMov];
        double STAB = (mov.getTipo() == getTipo()) ? 1.5 : 1.0;
        double eficacia = 1.0;
        
        // Eficacia según tabla (Dragon vs Dragon)
        if (objetivo.getTipo() == Tipo.DRAGON) {
            eficacia *= 2;    // Eficaz contra Dragón
            eficacia *= 0.5;  // Débil contra Dragón (resultado final: 1.0)
        }
        
        // Fórmula de daño (misma que Fuego)
        double daño = ((2 * getNivel()) / 5.0 + 2) * mov.getPotencia() * (getHpBase() / 100.0);
        daño = ((daño / 50.0) + 2) * STAB * eficacia;
        
        return (int) daño;
    }

    @Override
    public void subirNivel(int nivel) {
        return;
    }
}