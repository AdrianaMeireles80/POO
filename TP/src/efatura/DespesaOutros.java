package efatura;


public class DespesaOutros extends ActividadeEconomica {

    private static final DespesaOutros instance = new DespesaOutros();
    private static final long serialVersionUID = 5623734689479284924L;

    public static DespesaOutros getInstance(){
        return instance;
    }

    private DespesaOutros(){
        super();
    }

    protected Object readResolve(){
        return getInstance();
    }

}