package efatura;


public class DespesaPendente extends ActividadeEconomica {

    private static final DespesaPendente instance = new DespesaPendente();
    private static final long serialVersionUID = 908965577841347300L;

    public static DespesaPendente getInstance(){
        return instance;
    }

    private DespesaPendente(){
        super();
    }

    protected Object readResolve(){
        return getInstance();
    }


}