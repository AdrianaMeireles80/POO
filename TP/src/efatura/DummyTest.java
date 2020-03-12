package efatura;

import java.util.*;

class DummyTest {

    private static final long serialVersionUID = 3074493954458264468L;

    DummyTest(){
        super();

    }

    /**
     * Metodo que permite popular a aplicacao com dados gerados no momento. Apenas gera Utilizadores
     *
     * @param app Estado da aplicacao
     */
    void generateContribuintes(EFatApp app){
        String[] names = new String[]{
                "Zero", "Um", "Dois", "Três", "Quatro", "Cinco", "Seis", "Sete", "Oito", "Nove",
                };
        ArrayList<Concelho> concelhos = new ArrayList<Concelho>() {


            {
                this.addAll(Arrays.asList(Concelho.values()));
            }
        };

        Random r = new Random();
        Random cc = new Random();
        Set<ActividadeEconomica> allSectors = ActividadeEconomica.getAllSectors();
        Iterator<ActividadeEconomica> it = allSectors.iterator();
        for(int i = 0; i < 40; i++){ //Change this to change the amount of people
            if(!it.hasNext()) it = allSectors.iterator();
            int j = i;
            StringBuilder nameBuilder = new StringBuilder();
            do{
                nameBuilder.insert(0, names[j % 10] + " ");
                j /= 10;
            }
            while(j > 0);
            String name = nameBuilder.toString();
            //noinspection SpellCheckingInspection
            String email = String.format("%s%s%s", i % 2 == 1 ? "indiv." : "empr.",
                                         name.toLowerCase().replace(" ", ""), "@email.com");
            String address = "Rua " + name;
            String pass = "pass";
            float fiscalCoefficient = r.nextFloat() % 0.3f;
            // Escolhe setores aleatoriamente
            List<ActividadeEconomica> econSectors = new ArrayList<>();
            j = i;
            for(ActividadeEconomica allSector : allSectors)
                if(r.nextInt(10) > ((i % 2 == 1) ? 5 : j++)) econSectors.add(allSector);
            if(econSectors.isEmpty()) econSectors.add(it.next());
            if(i % 2 == 1){
                try{
                    List<Integer> aggregate = new ArrayList<>();
                    //Adiciona todos os individuais anteriores ao agregado
                    for(j = i - 2; j > 0; j -= 2) aggregate.add(j);
                    int numDependants = !aggregate.isEmpty() ? r.nextInt(aggregate.size()) : 0;
                    Individual a = new Individual(i, email, name, address, pass, numDependants,
                                                  aggregate, fiscalCoefficient, econSectors, new LinkedList<>());
                    app.register(a);
                }catch(UserAlreadyPresentException e){
                    e.printStackTrace();
                }
            }else{
                try{
                    Empresa a = new Empresa(i, email, name, address, pass, econSectors, fiscalCoefficient,
                                            concelhos.get(cc.nextInt(concelhos.size() - 1)));
                    app.register(a);
                }catch(UserAlreadyPresentException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
