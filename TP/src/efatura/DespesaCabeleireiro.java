package efatura;


public class DespesaCabeleireiro extends ActividadeEconomica implements Deductible {

    private static final DespesaCabeleireiro instance = new DespesaCabeleireiro();
    private static final long serialVersionUID = 2212840178574910555L;
    private static final float limite = 500; // Valor máximo para deduçao
    private static final float coef = 0.211f;

    public static DespesaCabeleireiro getInstance(){
        return instance;
    }

    private DespesaCabeleireiro(){
        super();
    }

    @Override
    public float deduct_fatura(Fatura f, Entidade user, Empresa empresa){
        float tmp = f.getValorPagar() * (coef + empresa.getCofFiscal());
        if(user instanceof Individual){
            tmp *= 1 + ((Individual) user).getCofFiscal();
        }else{
            tmp *= 1 + ((Empresa) user).getCofFiscal();
        }

        if(empresa instanceof EmpresaInterior){
            tmp *= ((EmpresaInterior) empresa).reducaoImposto();
        }

        return tmp;
    }

    @Override
    public double deduct_total(Double total, Entidade user){
        if(user instanceof FamiliaNumerosa){
            total *= ((FamiliaNumerosa) user).reducaoImposto();
        }
        if(user instanceof EmpresaInterior){
            total *= ((EmpresaInterior) user).reducaoImposto();
        }

        if(total >= limite){
            return limite;
        }else{
            return total;
        }
    }

    /**
     * @return
     */
    protected Object readResolve(){
        return getInstance();
    }
}