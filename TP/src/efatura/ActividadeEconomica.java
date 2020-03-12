package efatura;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class ActividadeEconomica implements Serializable {

    private static final long serialVersionUID = -5009892463782091683L;


    @Override
    public boolean equals(Object obj){
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }


    private static final class SectorWrapper {

        private static final Set<ActividadeEconomica> actividades = new HashSet<>();

        static{
            actividades.add(DespesaSaude.getInstance());
            actividades.add(DespesaRestauracao.getInstance());
            actividades.add(DespesaCabeleireiro.getInstance());
            actividades.add(DespesaEducacao.getInstance());
            actividades.add(DespesaFamiliar.getInstance());
            actividades.add(DespesaOutros.getInstance());
        }

        private static Set<ActividadeEconomica> get(){
            return new HashSet<>(actividades);
        }
    }


    /**
     * @return Todos os setores possiveis de Actividades economicas
     */
    public static Set<ActividadeEconomica> getAllSectors(){
        return SectorWrapper.get();
    }
}