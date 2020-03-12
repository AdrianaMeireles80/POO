package efatura;

public class FaturaNotFoundException extends Exception {

    private static final long serialVersionUID = 2292848204028277199L;

    //Construtores
    public FaturaNotFoundException(){
        super("Fatura nao existe nos seus registos");
    }

    public FaturaNotFoundException(String s){
        super(s);
    }
}
