package efatura;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Individual extends Entidade {

    private static final long serialVersionUID = -2952930655382522953L;
    private int nDependentes; // nDependentes != nElementos da Familia
    private List<Integer> numFiscais; // Familia
    private float cofFiscal;
    private List<ActividadeEconomica> codigos; // so pode deduzir para estas
    private LinkedList<Fatura> listaFact;

    //Construtores
    public Individual(){
        super();
        nDependentes = 0;
        numFiscais = new LinkedList<>();
        cofFiscal = 0.0f;
        codigos = new ArrayList<>();
        listaFact = new LinkedList<>();
    }

    public Individual(int nif, String email, String nome, String morada, String password, int nDependentes,
                      List<Integer> numFiscais, float cofFiscal, List<ActividadeEconomica> codigos,
                      List<Fatura> listaFact){

        super(nif, email, nome, morada, password);
        this.nDependentes = nDependentes;
        this.numFiscais = new LinkedList<>(numFiscais);
        this.cofFiscal = cofFiscal;
        this.codigos = new ArrayList<>(codigos);
        this.listaFact = listaFact.stream().map(Fatura::clone).collect(Collectors.toCollection(LinkedList::new));
    }

    public Individual(Individual other){
        super(other);
        this.nDependentes = other.getnDependentes();
        this.numFiscais = other.getNumFiscais();
        this.cofFiscal = getCofFiscal();
        this.codigos = other.getCodigos();
        this.listaFact = other.getListaFact();
    }

    // Getters
    public int getnDependentes(){
        return this.nDependentes;
    }

    public List<Integer> getNumFiscais(){
        // List of integer
        // imutavel o conteudo
        return new LinkedList<>(this.numFiscais);
    }

    public float getCofFiscal(){
        return this.cofFiscal;
    }

    public List<ActividadeEconomica> getCodigos(){
        return codigos = new ArrayList<>(this.codigos);
    }

    public LinkedList<Fatura> getListaFact(){
        return this.listaFact.stream().map(Fatura::clone).collect(Collectors.toCollection(LinkedList::new));
    }

    // Setter


    public void setnDependentes(int nDependentes){
        this.nDependentes = nDependentes;
    }

    public void setNumFiscais(List<Integer> numFiscais){
        // Outra forma de acrescentar todos os elementos se forem imutaveis
        this.numFiscais = new LinkedList<>();
        this.numFiscais.addAll(numFiscais);
    }

    public void setCofFiscal(float cofFiscal){
        this.cofFiscal = cofFiscal;
    }

    public void setCodigos(List<ActividadeEconomica> codigos){
        // Sao singletons
        this.codigos = new ArrayList<>();
        this.codigos.addAll((codigos));
    }

    public void setListaFact(LinkedList<Fatura> listaFact){
        this.listaFact = listaFact.stream().map(Fatura::clone).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Adiciona uma fatura as faturas desta entidade
     *
     * @param f Fatura a ser inserida
     */
    public void addFatura(Fatura f){
        //  yes we share pointers here, TAM TAM TAM
        this.listaFact.addFirst(f);
    }


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Individual)) return false;
        if(!super.equals(o)) return false;
        Individual that = (Individual) o;
        return this.getnDependentes() == that.getnDependentes() &&
               this.getCofFiscal() == getCofFiscal() &&
               this.getNumFiscais().equals(that.getNumFiscais()) &&
               this.getCodigos().equals(that.getCodigos()) &&
               this.getListaFact().equals(that.getListaFact());
    }

    @Override
    public String toString(){
        return "efatura.Individual{" + "nDependentes=" + nDependentes +
               ", numFiscais=" + numFiscais +
               ", cofFiscal=" + cofFiscal +
               ", codigos=" + codigos +
               ", listaFact=" + listaFact +
               '}' + super.toString();
    }

    @Override
    public Individual clone(){
        return new Individual(this);
    }


    /**
     * Muda a atividade economica de uma fatura
     *
     * @param f   Fatura ser a alterada
     * @param act Atividade economica a ser atribuida na fatura
     * @throws FaturaNotFoundException Caso a fatura nao exista
     */
    public void changeFatura(Fatura f, ActividadeEconomica act) throws
                                                                FaturaNotFoundException{
        int index = this.listaFact.indexOf(f);
        if(index == -1){
            throw new FaturaNotFoundException();
        }
        this.listaFact.get(index).changeFatura(act);
    }

}
