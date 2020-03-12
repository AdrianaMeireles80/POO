package efatura;

import java.io.Serializable;
import java.util.Comparator;

public class EmpresaFaturaValorComparator implements Comparator<Fatura>,
                                                     Serializable {

    private static final long serialVersionUID = 8809504377515898839L;

    @Override
    /**
     * Comparador entre duas Faturas em relacao ao valor da despesa
     */
    public int compare(Fatura o1, Fatura o2){
        return Float.compare(o2.getValor(), o1.getValor());
    }
}
