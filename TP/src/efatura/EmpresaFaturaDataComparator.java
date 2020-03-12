package efatura;

import java.io.Serializable;
import java.util.Comparator;

public class EmpresaFaturaDataComparator implements Comparator<Fatura>,
                                                    Serializable {

    private static final long serialVersionUID = -8151525980970157866L;

    @Override
    /**
     * Comparador entre duas faturas em relacao a data de criacao
     */
    public int compare(Fatura f1, Fatura f2){
        int t = f2.getData().compareTo(f1.getData());
        if(t == 0) return -1;
        return t;
    }
}
