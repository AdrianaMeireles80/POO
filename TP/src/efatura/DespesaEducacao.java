package efatura;


public class DespesaEducacao extends ActividadeEconomica implements Deductible {

    private static final DespesaEducacao instance = new DespesaEducacao();
    private static final long serialVersionUID = 1598674907172052678L;
    private static final float limite = 2250;
    private static final float coef = 0.1147f;

    public static DespesaEducacao getInstance(){
        return instance;
    }

    private DespesaEducacao(){
        super();
    }


    @Override
    public float deduct_fatura(Fatura f, Entidade user, Empresa empresa){
        float tmp = f.getValorPagar() * (coef);
        if(user instanceof Individual){
            tmp *= 1 + ((Individual) user).getCofFiscal() + (((Individual) user).getnDependentes() * 0.002f);
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

    protected Object readResolve(){
        return getInstance();
    }

}