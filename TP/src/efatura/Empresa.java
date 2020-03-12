package efatura;
//

import java.util.*;
import java.util.stream.Collectors;

public class Empresa extends Entidade {

    private static final long serialVersionUID = 5207834366333453518L;
    private List<ActividadeEconomica> codActivades;
    private float cofFiscal;
    private LinkedList<Fatura> listaFact;
    private Concelho concelho;


    /**
     * Construtor vazio
     */
    public Empresa(){
        super();

        this.codActivades = new ArrayList<>();
        this.cofFiscal = 0.0f;
        this.listaFact = new LinkedList<>();
        this.concelho = Concelho.OUTRO;
    }

    /**
     * Construtor parametrico
     *
     * @param nif          NIF da empresa
     * @param email        Email da empresa
     * @param nome         Nome da empresa
     * @param morada       Morada da empresa
     * @param password     Password da conta da empresa
     * @param codActivades Atividades Economicas da empresa
     * @param cofFiscal    Coeficiente fiscar da empresa
     */
    public Empresa(int nif, String email, String nome, String morada, String password,
                   List<ActividadeEconomica> codActivades, float cofFiscal, Concelho c){
        super(nif, email, nome, morada, password);

        this.codActivades = new ArrayList<>(codActivades);
        this.listaFact = new LinkedList<>();

        this.cofFiscal = cofFiscal;
        this.concelho = c;
    }

    public Empresa(Empresa other){
        super(other);

        this.codActivades = other.getCodActivades();
        this.cofFiscal = other.getCofFiscal();
        this.listaFact = other.getListaFact();
        this.concelho = other.getConcelho();
    }

    // Getters

    public List<ActividadeEconomica> getCodActivades(){
        return new ArrayList<>(this.codActivades);

    }

    public LinkedList<Fatura> getListaFact(){
        return this.listaFact.stream().map(Fatura::clone).collect(Collectors.toCollection(LinkedList::new));
    }


    public float getCofFiscal(){
        return this.cofFiscal;
    }

    public Concelho getConcelho(){
        return this.concelho;
    }


    // Setters

    public void setConcelho(Concelho a){
        this.concelho = a;
    }


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        Empresa empresa = (Empresa) o;
        return this.getCofFiscal() == empresa.getCofFiscal() &&
               this.getCodActivades().equals(empresa.getCodActivades()) &&
               this.getListaFact().equals(empresa.getListaFact()) &&
               this.concelho.equals(empresa.concelho);
    }

    @Override
    public String toString(){
        return "Empresa{" +
               "Concelho=" + concelho +
               ", codActivades=" + codActivades +
               ", cofFiscal=" + cofFiscal +
               ", listaFact=" + listaFact +
               '}' + super.toString();
    }

    @Override
    public Empresa clone(){
        return new Empresa(this);
    }

    /**
     * Ordena a lista de faturas desta impresa dependendo do comparador usado
     *
     * @param c Comparador usado para ordenacao
     * @return TreeSet ordenado das faturas
     */
    public TreeSet<Fatura> getFaturas_ordered(Comparator<Fatura> c){
        TreeSet<Fatura> res = new TreeSet<>(c);
        if(this.listaFact != null)
            this.listaFact.forEach(f -> res.add(f.clone()));
        return res;
    }

    /**
     * Metodo que gera uma fatura associada a um cliente
     *
     * @param user     Utilizador a quem pertencera a fatura
     * @param valor    Valor da despesa
     * @param des_desp Descricao da despesa
     */
    public void emiteFatura(Individual user, float valor, String des_desp){
        ActividadeEconomica act;

        if(codActivades.size() > 1)
            act = DespesaPendente.getInstance();
        else
            act = this.codActivades.get(0);

        Fatura fatura = new Fatura(user.getNif(), this.getNif(), this.getNome(), des_desp, valor, act);


        this.listaFact.addFirst(fatura);
        user.addFatura(fatura);
    }


    /**
     * @return Total faturado
     */
    public double getTotal(){
        return this.getListaFact().stream().mapToDouble(Fatura::getValor).sum();
    }
}
