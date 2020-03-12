package efatura;

import java.io.Serializable;
import java.util.Comparator;

public class EmpresaFaturaCountComparator implements Comparator<Empresa>,
                                                     Serializable {

    private static final long serialVersionUID = -8866627969433419787L;

    @Override
    /**
     * Comparador entre duas empresas em relacao ao numero de faturas emitidas
     */
    public int compare(Empresa o1, Empresa o2){

        return Integer.compare(o1.getListaFact().size(), o2.getListaFact().size());
    }
}


