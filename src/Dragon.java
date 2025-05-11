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

    // sobrecargado para entrenador que me da lata hacerlo de otra forma
    public Dragon(boolean b) {
        super(70, 1, Tipo.DRAGON, new Movimiento[]{
            new Movimiento("Cola dragón", Tipo.DRAGON, false),
            new Movimiento("Fuego dragón", Tipo.DRAGON, false),
            new Movimiento("Rugido", Tipo.DRAGON, true),
            Math.random() < 0.25 ? Movimiento.movimientoRandomChance() : null // 25% de probabilidad
        });
        
        this.hpBase += desfase(5);
        this.multiescamas = false;
    }

    @Override
    public int atacar(Javaling objetivo, int indiceMov) {
        Movimiento mov = getMovimientos()[indiceMov];
        double STAB = (mov.getTipo() == getTipo()) ? 1.5 : 1.0;
        double eficacia = 1.0;

        // Eficacia según tabla
        if (mov.getTipo() == Tipo.DRAGON && objetivo.getTipo() == Tipo.DRAGON) {
            eficacia *= 2;    // Eficaz contra Dragón
            eficacia *= 0.5;  // Débil contra Dragón (resultado final: 1.0)
        }

        // Fórmula de daño
        double daño = ((2.0 * getNivel()) / 5.0 + 2) * mov.getPotencia() * (getHpBase() / 100.0);
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

    @Override
    public void subirNivel(int nivel) {
        return;
    }
    // getters
    public boolean getMultiescamas() {return multiescamas;}
    // setters
    public void setMultiescamas(boolean multiescamas) {this.multiescamas = multiescamas;}
}