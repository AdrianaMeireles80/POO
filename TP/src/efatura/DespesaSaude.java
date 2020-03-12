package efatura;


public class DespesaSaude extends ActividadeEconomica implements Deductible {

    private static final DespesaSaude instance = new DespesaSaude();
    private static final long serialVersionUID = 1842794825442379375L;
    private static final float limite = 4000;
    private static final float coef = 0.153f;


    public static DespesaSaude getInstance(){
        return instance;
    }

    private DespesaSaude(){
        super();
    }

    @Override
    public float deduct_fatura(Fatura f, Entidade user, Empresa empresa){
        float tmp = f.getValorPagar() * coef;
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

    protected Object readResolve(){
        return getInstance();
    }
}