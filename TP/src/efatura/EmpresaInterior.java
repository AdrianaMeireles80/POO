package efatura;

public class EmpresaInterior extends Empresa {

    private static final long serialVersionUID = 6187196780197351074L;
    private float bonificacao;

    //Construtores
    public EmpresaInterior(){
        super();
        this.bonificacao = 0;
    }

    public EmpresaInterior(Empresa a){
        super(a);
        this.bonificacao = a.getConcelho().getBonificacao();
    }

    public EmpresaInterior(EmpresaInterior a){
        super(a);
        this.bonificacao = a.getBonificacao();
    }


    //Getters
    public float getBonificacao(){
        return this.bonificacao;
    }

    //Setters
    public void setBonificacao(float b){
        this.bonificacao = b;
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
    public EmpresaInterior clone(){
        return new EmpresaInterior(this);
    }

    @Override
    public String toString(){
        return "EmpresaInterior{ bonificacao: " + this.bonificacao + "}" + super.toString();
    }


    /**
     * Metodo que permite aumentar as deducoes, por ser uma fatura de uma empresa do interior
     *
     * @return Total a ser deduzido com o desconto de uma emmpresa do interior
     */
    public double reducaoImposto(){
        return 1 + this.bonificacao;
    }
}
