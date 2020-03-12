package efatura;

import java.io.Serializable;

public abstract class Menu implements Serializable {

    private static final long serialVersionUID = -1521945042875556072L;

    public abstract int getOp();

    public abstract void showMenu();

    public abstract void exec();

    public abstract int lerOpcao();

}