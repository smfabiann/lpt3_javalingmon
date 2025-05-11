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
    // sobrecargado para el entrenador que me da lata hacerlo de otra forma
    public Planta(boolean b) {
        super(65, 1, Tipo.PLANTA, new Movimiento[]{
            new Movimiento("Latigazo", Tipo.PLANTA, false),
            new Movimiento("Hoja afilada", Tipo.PLANTA, false),
            new Movimiento("Esporas", Tipo.PLANTA, true),
            Math.random() < 0.25 ? Movimiento.movimientoRandomChance() : null // 25% de probabilidad
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
        
        // Si el objetivo es un dragon con multiescamas
        if (objetivo instanceof Dragon) {
            Dragon dragonObjetivo = (Dragon) objetivo;
            if (dragonObjetivo.getMultiescamas() && objetivo.getHpActual() == objetivo.getHpTotal()) {
                daño *= 0.1; // Reducir el daño al 10% si la habilidad está activa
                dragonObjetivo.setMultiescamas(false); // desactivamos la habilidad
            }
        }

        // Verificar precisión del movimiento al final
        if (Math.random() * 100 >= mov.getPrecision()) {
            Utilidades.slowPrint("¡El ataque falló!\n", "rojo");
            return 0; // El ataque falla
        }

        return (int) daño;
    }

    // Método para activar la habilidad "Fotosíntesis"
    public int activarFotosintesis() {
        // realmente no se porque fotosintesis es un int pero en algo se debe usar, no?
        fotosintesis = (int) (getHpTotal() * 0.05); // 5% de la vida total
        
        // retorna la vida al maxima o la vida actual curada
        setHpActual(Math.min(getHpActual() + fotosintesis, getHpTotal())); // Curar sin exceder la vida máxima
        
        // retorna para avisarle al jugador
        return fotosintesis;
    }

    @Override
    public void subirNivel(int nivel) {
        return;
    }
}