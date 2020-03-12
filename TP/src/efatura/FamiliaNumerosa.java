package efatura;

import java.util.List;

public class FamiliaNumerosa extends Individual {

    private static final long serialVersionUID = 5831811534064624124L;
    private float bonificacao;

    //Construtores
    public FamiliaNumerosa(){
        super();
        this.bonificacao = 0;
    }

    public FamiliaNumerosa(Individual a){
        super(a);
        this.bonificacao = a.getnDependentes() * 0.05f;
    }

    public FamiliaNumerosa(FamiliaNumerosa a){
        super(a);
        this.bonificacao = a.getBonificacao();
    }

    public FamiliaNumerosa(int nif, String email, String nome, String morada, String password, int nDependentes,
                           List<Integer> numFiscais, float cofFiscal, List<ActividadeEconomica> codigos,
                           List<Fatura> listaFact, float bonificacao){
        super(nif, email, nome, morada, password, nDependentes,
              numFiscais, cofFiscal, codigos,
              listaFact);
        this.bonificacao = bonificacao;

    }

    //Getters
    public float getBonificacao(){
        return this.bonificacao;
    }

    //Setters
    public void setBonificacao(float bonificacao){
        this.bonificacao = bonificacao;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        EmpresaInterior emp = (EmpresaInterior) o;
        return this.bonificacao == emp.getBonificacao();
    }

    @Override
    public FamiliaNumerosa clone(){
        return new FamiliaNumerosa(this);
    }

    @Override
    public String toString(){
        return "FamiliaNumerosa{ bonificacao: " + this.bonificacao + "}" + super.toString();
    }


    /**
     * Metodo que permite aumentar as deducoes, por ser uma Familia numerosa
     *
     * @return Total a ser deduzido com o desconto de familia numerosa
     */
    public double reducaoImposto(){
        return (1 + this.bonificacao);
    }

}
