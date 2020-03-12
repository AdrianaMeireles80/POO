package efatura;

import java.io.Serializable;

public abstract class Entidade implements Serializable {

    private static final long serialVersionUID = -2858812485414995458L;
    private int nif;
    private String email;
    private String nome;
    private String morada;
    private String password;

    //Construtores
    public Entidade(){
        super();
        this.nif = 0;
        this.email = "";
        this.nome = "";
        this.morada = "";
        this.password = "";
    }

    public Entidade(int nif, String email, String nome, String morada, String password){
        super();
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.password = password;
    }

    public Entidade(Entidade other){
        super();
        this.nif = other.getNif();
        this.email = other.getEmail();
        this.nome = other.getNome();
        this.morada = other.getMorada();
        this.password = other.getPassword();
    }

// Getters

    /**
     * @return
     */
    public int getNif(){
        return nif;
    }

    /**
     * @return
     */
    public String getEmail(){
        return email;
    }

    /**
     * @return
     */
    public String getNome(){
        return nome;
    }

    /**
     * @return
     */
    public String getMorada(){
        return morada;
    }

    /**
     * @return
     */
    public String getPassword(){
        return password;
    }

    // Setters

    /**
     * @param nif
     */
    public void setNif(int nif){
        this.nif = nif;
    }

    /**
     * @param email
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * @param nome
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * @param morada
     */
    public void setMorada(String morada){
        this.morada = morada;
    }

    /**
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Entidade entidade = (Entidade) o;

        return this.getNif() == entidade.getNif() &&
               this.getEmail().equals(entidade.getEmail()) &&
               this.getNome().equals(entidade.getNome()) &&
               this.getMorada().equals(entidade.getMorada()) &&
               this.getPassword().equals(entidade.getPassword());
    }

    /**
     * @return
     */
    @Override
    public String toString(){
        return "efatura.Entidade{" + "nif=" + nif +
               ", email='" + email + '\'' +
               ", nome='" + nome + '\'' +
               ", morada='" + morada + '\'' +
               ", password='" + password + '\'' +
               '}';
    }

    public abstract Entidade clone();
}
