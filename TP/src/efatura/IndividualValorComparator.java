package efatura;

import java.io.Serializable;
import java.util.Comparator;

public class IndividualValorComparator implements Comparator<Individual>,
                                                  Serializable {

    private static final long serialVersionUID = -7861107149865743696L;

    @Override
    /**
     * Comparador entre dois individuais em relacao ao valor das despesas totais
     */
    public int compare(Individual o1, Individual o2){
        Double d1 = o1.getListaFact().stream().mapToDouble(Fatura::getValor).sum();
        Double d2 = o2.getListaFact().stream().mapToDouble(Fatura::getValor).sum();

        return Double.compare(d1, d2);
    }

}
