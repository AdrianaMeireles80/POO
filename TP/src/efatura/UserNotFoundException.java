package efatura;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = -528194777356064567L;

    //Construtores
    public UserNotFoundException(){
        super("Utilizador não encontrado.");
    }

    public UserNotFoundException(String s){
        super(s);
    }
}
