package efatura;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuContribuinte extends Menu {

    private static final long serialVersionUID = -7403562299714149064L;
    private int op;
    private List<String> opcoes;


    public MenuContribuinte(){
        super();
        opcoes = new ArrayList<>();
        opcoes.add("Lista de faturas"); // Total deduzido e deduzido pelo agregado
        opcoes.add("Lista de faturas por validar");
        opcoes.add("Lista de faturas por setor"); // E total de cada setor
        opcoes.add("Editar fatura");
        opcoes.add("Ver Perfil");
        opcoes.add("Terminar sessao");
        op = -1;
    }


    public void showMenu(){
        System.out.println("\n_________                __         ._____.         .__        __          ");
        System.out.println("\\_   ___ \\  ____   _____/  |________|__\\_ |__  __ __|__| _____/  |_  ____  ");
        System.out.println("/    \\  \\/ /  _ \\ /    \\   __\\_  __ \\  || __ \\|  |  \\  |/    \\   __\\/ __ \\ ");
        System.out.println("\\     \\___(  <_> )   |  \\  |  |  | \\/  || \\_\\ \\  |  /  |   |  \\  | \\  ___/ ");
        System.out.println(" \\______  /\\____/|___|  /__|  |__|  |__||___  /____/|__|___|  /__|  \\___  >");
        System.out.println("        \\/            \\/                    \\/              \\/          \\/ \n");
        int i = 1;
        for(String f : opcoes){
            System.out.print("[" + i++ + "] - " + f + "\n");

        }
        System.out.println("\n[0] - Sair\n");

    }

    public void exec(){
        do{
            showMenu();
            op = lerOpcao();
        }
        while(op == -1);
    }

    /** ler opcao **/
    public int lerOpcao(){
        int op;
        Scanner is = new Scanner(System.in);
        System.out.print("Option: ");
        try{
            op = is.nextInt();
        }catch(InputMismatchException e){
            op = -1;
        }
        if(op < 0 || op > opcoes.size()){
            System.out.println("\nInvalid option");
            op = -1;
        }
        return op;
    }


    public int getOp(){
        return op;
    }

    public void listFaturas(EFatApp app){
        try{
            List<Fatura> faturasIndividual = app.getFaturasIndividual();
            int i = 0;

            System.out.println("\nFaturas: ");
            for(Fatura f : faturasIndividual){
                System.out.println("[" + i++ + "]" + " " + f);

            }
            System.out.println("\nO seu total de deduções é: " + app.getDeductIndividual());
            System.out.println("O total de deduções da sua familia é: " + app.getDeductFam());

        }catch(InvalidAcessException e){
            System.out.println(e.getMessage());
        }
    }

    public void listFaturasPend(EFatApp app){
        try{
            List<Fatura> pend = app.getFaturasIndividualSetor(DespesaPendente.getInstance());

            System.out.println("Fatura que tem por validar: ");
            for(Fatura f : pend){
                System.out.println(f);
            }
        }catch(InvalidAcessException e){
            System.out.println(e.getMessage());
        }
    }

    public void listaFaturasSetores(EFatApp app){
        try{
            System.out.println("Lista de faturas por setor: ");
            List<ActividadeEconomica> lmao = new ArrayList<>(ActividadeEconomica.getAllSectors());

            for(ActividadeEconomica act : lmao){
                List<Fatura> fPerSetor = app.getFaturasIndividualSetor(act);
                System.out.println(act);
                for(Fatura f : fPerSetor){
                    System.out.println("\t " + f);
                }
                Individual user = (Individual) app.getLoggedInC();
                if(act instanceof Deductible && user.getCodigos().contains(act)){
                    double total = app.getDeductPerAct(act, user);
                    System.out.println(
                            ("Total deduzido em despesa de " + act.getClass().getSimpleName()) + " é: " + total);
                }
            }

        }catch(InvalidAcessException e){
            e.printStackTrace();
        }
    }

    public void changeFatura(EFatApp app){
        try{
            Scanner input = new Scanner(System.in);
            Entidade log = app.getLoggedInC();
            if(log instanceof Individual){
                Individual inLogged = (Individual) log;
                List<Fatura> list = inLogged.getListaFact();
                int j = 0;
                for(Fatura a : list){
                    System.out.println("[" + j++ + "]" + a);
                }
                System.out.print("Escolha: ");
                Fatura f = list.get(input.nextInt());


                System.out.println("Para que actividade economica quer alterar a fatura?");
                List<ActividadeEconomica> possible = ((Empresa) app.getInstanceEntidade(
                        f.getNif_emitente())).getCodActivades();

                int i = 0;
                for(ActividadeEconomica e : possible){
                    System.out.println("[" + i++ + "] - " + e);
                }
                ActividadeEconomica esc = possible.get(input.nextInt());


                app.changeFatura(esc, f);

            }else
                throw new InvalidAcessException();
        }catch(UserNotFoundException | FaturaNotFoundException | InvalidAcessException e){
            System.out.println(e.getMessage());
        }

    }

    public void getPerfil(EFatApp efat){
        Individual a = (Individual) efat.getLoggedInC();
        System.out.println("\nNif: " + a.getNif());
        System.out.println("Nome: " + a.getNome());
        System.out.println("N_Dependentes: " + a.getnDependentes());
        System.out.println("Atividades Economicas: " + a.getCodigos().toString());
        System.out.println("NºFiscais dos agregados familiar: " + a.getNumFiscais());
    }
}
