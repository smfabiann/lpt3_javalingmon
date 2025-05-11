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

    @Override
    public int atacar(Javaling objetivo, int indiceMov) {
        Movimiento mov = getMovimientos()[indiceMov];
        // paramentros del ataque
        double STAB = 1.0;
        double eficacia = 1;
        double potencia = mov.getPotencia();
        int n = getNivel();
        if (mov.getTipo() == getTipo()) STAB = 1.5; // STAB
        // eficacia
        if (objetivo.getTipo() == Tipo.PLANTA) eficacia = eficacia * 2;
        if (objetivo.getTipo() == Tipo.AGUA || objetivo.getTipo() == Tipo.DRAGON) eficacia = eficacia * 0.5;
        /*
         * ATRIBUTI ELEMENTAL TODO
         */
        double daño = ((2*n)/5) + 2;
        daño = daño * potencia * (getHpBase()/100);
        daño = (daño/50) + 2;
        daño = daño * STAB * eficacia;
        return (int) daño;
    }

    @Override
    public void subirNivel(int nivel) {
        return;
    }
}