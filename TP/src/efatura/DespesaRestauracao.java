package efatura;


public class DespesaRestauracao extends ActividadeEconomica {

    private static final DespesaRestauracao instance = new DespesaRestauracao();
    private static final long serialVersionUID = 9150978115385656367L;

    public static DespesaRestauracao getInstance(){
        return instance;
    }

    private DespesaRestauracao(){
        super();
    }

    protected Object readResolve(){
        return getInstance();
    }


}