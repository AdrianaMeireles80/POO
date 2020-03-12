package efatura;

public class UserAlreadyPresentException extends Exception {

    private static final long serialVersionUID = -5602651162846615561L;

    //Construtores
    public UserAlreadyPresentException(){
        super("Utilizador já existe");
    }

    public UserAlreadyPresentException(String s){
        super(s);
    }

}
