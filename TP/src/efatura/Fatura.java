package efatura;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Fatura implements Serializable {

    private static final long serialVersionUID = -3388572640491975844L;
    private int nif_emitente;
    private int nif_cliente;

    private LocalDateTime data;
    private String nome_emitente;
    private String des_despesa;

    private float valor;
    private float valorPagar;
    private List<Fatura> historico;
    private ActividadeEconomica act;


    private static final float iva = 0.23f;

    static int count = 0;

    //Construtores
    public Fatura(){
        super();

        this.nif_cliente = 0;
        this.nif_emitente = 0;

        this.data = LocalDateTime.now();
        this.nome_emitente = "";
        this.des_despesa = "";

        this.valor = 0.0f;
        this.valorPagar = 0.0f;
        this.historico = new LinkedList<>();
        this.act = null;


        ++(Fatura.count);
    }

    public Fatura(int nif_cliente, int nif_emitente, String nome_emitente, String des_despesa, float valor,
                  ActividadeEconomica act){
        super();

        this.nif_cliente = nif_cliente;
        this.nif_emitente = nif_emitente;


        this.data = LocalDateTime.now();
        this.nome_emitente = nome_emitente;
        this.des_despesa = des_despesa;

        this.valor = valor;
        this.valorPagar = valor * (1 + Fatura.iva);
        this.historico = new LinkedList<>();
        this.act = act;


        ++(Fatura.count);
    }


    public Fatura(Fatura f){
        super();
        this.nif_emitente = f.getNif_emitente();
        this.nif_cliente = f.getNif_cliente();
        this.data = f.getData();
        this.nome_emitente = f.getNome_emitente();
        this.des_despesa = f.getDes_despesa();
        this.valor = f.getValor();
        this.valorPagar = f.getValorPagar();
        this.historico = f.getHistorico();
        this.act = f.getAct();
    }

    //Getters
    public ActividadeEconomica getAct(){
        return act;
    }

    public int getNif_emitente(){
        return nif_emitente;
    }

    public int getNif_cliente(){
        return nif_cliente;
    }

    public LocalDateTime getData(){
        return data;
    }

    public String getNome_emitente(){
        return nome_emitente;
    }

    public String getDes_despesa(){
        return des_despesa;
    }

    public float getValor(){
        return valor;
    }

    public float getValorPagar(){
        return valorPagar;
    }

    public List<Fatura> getHistorico(){
        return historico.stream().map(Fatura::clone).collect(Collectors.toList());
    }


    //Setters
    public void setNif_emitente(int nif_emitente){
        this.nif_emitente = nif_emitente;
    }

    public void setNif_cliente(int nif_cliente){
        this.nif_cliente = nif_cliente;
    }

    public void setData(LocalDateTime data){
        this.data = data;
    }

    public void setNome_emitente(String nome_emitente){
        this.nome_emitente = nome_emitente;
    }

    public void setDes_despesa(String des_despesa){
        this.des_despesa = des_despesa;
    }

    public void setValor(float valor){
        this.valor = valor;
    }

    public void setValorPagar(float valorPagar){
        this.valorPagar = valorPagar;
    }

    public void setHistorico(List<Fatura> historico){
        this.historico = historico.stream().map(Fatura::clone).collect(Collectors.toList());

    }

    public void setCount(int x){
        Fatura.count = x;
    }


    public void setAct(ActividadeEconomica act){
        this.act = act;
    }

    public Fatura clone(){
        return new Fatura(this);
    }

    @Override
    public String toString(){
        return "Fatura{" +
               "nif_emitente=" + nif_emitente +
               ", nif_cliente=" + nif_cliente +
               ", data=" + data +
               ", nome_emitente='" + nome_emitente + '\'' +
               ", des_despesa='" + des_despesa + '\'' +
               ", valor=" + valor +
               ", valorPagar=" + valorPagar +
               ", historico=" + historico +
               ", act=" + act +
               '}';
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Fatura f = (Fatura) o;
        return this.act.equals(f.getAct()) &&
               this.historico.equals(f.getHistorico()) &&
               this.valorPagar == f.getValorPagar() &&
               this.valor == f.getValor() &&
               this.des_despesa.equals(f.getDes_despesa()) &&
               this.nome_emitente.equals(f.getNome_emitente()) &&
               this.nif_cliente == f.getNif_cliente() &&
               this.nif_emitente == f.getNif_emitente() &&
               this.data.equals(f.getData());

    }

    /**
     * Metodo para mudar a actividade economica de uma fatura
     *
     * @param act Atividade economica a ser atribuida a fatura
     */
    public void changeFatura(ActividadeEconomica act){
        Fatura nv;
        nv = new Fatura(this);

        nv.setHistorico(new LinkedList<>());

        this.historico.add(nv);

        this.setAct(act);
    }

}