package efatura;

public class UserPasswordIncorrectException extends Exception {

    private static final long serialVersionUID = -254811938763798719L;

    // Construtores
    public UserPasswordIncorrectException(){
        super("Password errada.");
    }

    public UserPasswordIncorrectException(String s){
        super(s);
    }
}
