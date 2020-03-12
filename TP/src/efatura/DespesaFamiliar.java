package efatura;

public class DespesaFamiliar extends ActividadeEconomica implements Deductible {

    private static final DespesaFamiliar instance = new DespesaFamiliar();
    private static final long serialVersionUID = -351286370679383650L;
    private static final float limite = 3500;
    private static final float coef = 0.189f;

    public static DespesaFamiliar getInstance(){
        return instance;
    }

    private DespesaFamiliar(){
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
