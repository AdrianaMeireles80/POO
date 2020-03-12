package efatura;

public class InvalidAcessException extends Exception {

    private static final long serialVersionUID = -3055663157101954051L;

    //Construtores
    public InvalidAcessException(){
        super("Não tem permissão para essa ação!");
    }

    public InvalidAcessException(String s){
        super(s);
    }
}
