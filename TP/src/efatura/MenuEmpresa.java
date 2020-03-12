package efatura;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuEmpresa extends Menu {

    private static final long serialVersionUID = -6609841778095092235L;
    private List<String> opcoes;
    private int op;

    public MenuEmpresa(){
        super();
        opcoes = new ArrayList<>();
        opcoes.add("Lista de faturas emitidas por ordem cronologica");
        opcoes.add("Lista de faturas emitidas por valor");
        opcoes.add("Lista de faturas entre datas");
        opcoes.add(
                "Lista de faturas de um dado contribuinte");
        opcoes.add("Total faturado ");
        opcoes.add("Emitir fatura");
        opcoes.add("Ver Perfil");
        opcoes.add("Terminar sessao");
        op = -1;
    }

    public void showMenu(){
        System.out.println("\n___________                                            ");
        System.out.println("\\_   _____/ _____ _____________   ____   ___________   ");
        System.out.println(" |    __)_ /     \\\\____ \\_  __ \\_/ __ \\ /  ___/\\__  \\  ");
        System.out.println(" |    __)_ /     \\\\____ \\_  __ \\_/ __ \\ /  ___/\\__  \\  ");
        System.out.println("/_______  /__|_|  /   __/|__|    \\___  >____  >(____  /");
        System.out.println("        \\/      \\/|__|               \\/     \\/      \\/ \n");
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
        System.out.print("Opção: ");
        try{
            op = is.nextInt();
        }catch(InputMismatchException e){ // No int
            op = -1;
        }
        if(op < 0 || op > opcoes.size()){
            System.out.println("\nOpção invalida");
            op = -1;
        }
        return op;
    }

    public int getOp(){
        return op;
    }

    public void faturasCrono(EFatApp app){
        try{

            List<Fatura> l = app.getFaturasEmpresa_ordered(new EmpresaFaturaDataComparator());
            System.out.println("\nLista de faturas por ordem cronologica: ");
            if(l.isEmpty()){
                System.out.println("Nao possui nenhuma fatura.");
                return;
            }
            for(Fatura f : l){
                System.out.println(f);
            }
        }catch(InvalidAcessException e){
            e.printStackTrace();
        }

    }

    public void faturasValor(EFatApp app){
        try{

            List<Fatura> l = app.getFaturasEmpresa_ordered(new EmpresaFaturaValorComparator());
            System.out.println("\nLista de faturas por valor: ");
            for(Fatura f : l){
                System.out.println(f);
            }
        }catch(InvalidAcessException e){
            System.out.println(e.getMessage());
        }

    }

    public void faturasBetweenDates(EFatApp app){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Scanner input = new Scanner(System.in);

            System.out.println("\nData de começo (ano-mes-dia horas:minutos): ");
            String beginS = input.nextLine();
            LocalDateTime begin = LocalDateTime.parse(beginS, formatter);


            System.out.println("Data de fim (ano-mes-dia horas:minutos): ");
            String endS = input.nextLine();
            LocalDateTime end = LocalDateTime.parse(endS, formatter);

            List l = app.geFactBetweenEmpresa(begin, end);
            if(l.isEmpty()){
                System.out.println("Nao possui faturas entre essas data!");
                return;
            }
            System.out.println("Faturas entre essas datas: ");
            for(Object f : l){
                Fatura fa = (Fatura) f;
                System.out.println(fa);
            }
        }catch(InvalidAcessException e){
            e.printStackTrace();

        }


    }

    public void faturasIndividual(EFatApp app){
        try{
            Scanner input = new Scanner(System.in);

            System.out.println("\nNIF: ");
            int escolha = input.nextInt();
            List<Fatura> l = app.getListFaturaContr(escolha);
            if(l.isEmpty()){
                System.out.println("Nao possui nenhuma fatura com esse cliente.");
                return;
            }
            for(Fatura f : l){
                System.out.println(f);
            }
        }catch(InvalidAcessException e){
            System.out.println(e.getMessage());
        }
    }

    public void faturado(EFatApp app){
        try{
            double fat = app.getTotalFaturado();
            System.out.println("\nTotal faturado: " + fat);
        }catch(InvalidAcessException e){
            System.out.println(e.getMessage());
        }
    }

    public void emitFatura(EFatApp efat){
        try{
            Scanner input = new Scanner(System.in);
            System.out.println("\n");
            System.out.println("Nif");
            int nif = input.nextInt();
            System.out.println("Valor");
            float valor = input.nextFloat();
            System.out.println("Descrição");
            String des = input.next();
            efat.emiteFatura(nif, valor, des);
        }catch(InvalidAcessException | UserNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void getPerfil(EFatApp efat){
        Empresa a = (Empresa) efat.getLoggedInC();
        System.out.println("\nNif: " + a.getNif());
        System.out.println("Nome: " + a.getNome());
        System.out.println("Concelho: " + a.getConcelho());
        System.out.println("Atividades Economicas: " + a.getCodActivades().toString());
    }
}
