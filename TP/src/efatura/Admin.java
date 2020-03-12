package efatura;

public class Admin extends Entidade {

    private static final long serialVersionUID = 7341770710231721423L;

    public Admin(int nif, String email, String nome, String passwd){
        super(nif, email, nome, "NSA", passwd);

    }

    private Admin(Admin a){
        super(a);
    }

    @Override
    public Entidade clone(){
        return new Admin(this);
    }
}
