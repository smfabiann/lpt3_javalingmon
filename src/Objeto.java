public class Objeto {
    private boolean esCurativo;
    private int cantidad;   // entiendo que habla de la cantidad de ese objeto
    private TipoObjeto tipoObjeto;

    // contructor
    public Objeto(TipoObjeto tipo) {
        // como hay mas objetos curativos que caramelos, primero asumimos que 
        // es un objeto curativo
        this.esCurativo = true;
        this.tipoObjeto = tipo;
        
        // luego lo cambiamos si es que no es curativo
        if (tipo == TipoObjeto.CARAMELO_PEQUEÑO || tipo == TipoObjeto.CARAMELO_GRANDE) {
            esCurativo = false;
        }
        // se le asigna la cantidad de 1
        this.cantidad = 1;
    }

    // add one
    public void addOneCantidad() {cantidad++;}

    // getters
    public boolean getEsCurativo() {return esCurativo;}
    public int getCantidad() {return cantidad;}
    public TipoObjeto getTipoObjeto() {return tipoObjeto;}
    // setters
    public void setCantidad(int n) {cantidad = n;}
    public void setTipoObjeto(TipoObjeto tipo) {tipoObjeto = tipo;}
}
