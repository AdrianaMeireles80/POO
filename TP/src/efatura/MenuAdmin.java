package efatura;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuAdmin extends Menu {

    private static final long serialVersionUID = -6609841778095092235L;
    private List<String> opcoes;
    private int op;

    public MenuAdmin(){
        super();
        opcoes = new ArrayList<>();
        opcoes.add("Total de faturas emitidas");
        opcoes.add("Top 10 contribuintes");
        opcoes.add("Top X empresas");
        opcoes.add("Terminar sessao");
        op = -1;
    }

    public void showMenu(){
        System.out.println("   _____       .___      .__        ");
        System.out.println("  /  _  \\    __| _/_____ |__| ____  ");
        System.out.println(" /  /_\\  \\  / __ |/     \\|  |/    \\ ");
        System.out.println("/    |    \\/ /_/ |  Y Y  \\  |   |  \\");
        System.out.println("\\____|__  /\\____ |__|_|  /__|___|  /");
        System.out.println("        \\/      \\/     \\/        \\/ ");

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


    public void top10(EFatApp app){
        System.out.println("\nTop 10 individual: ");
        try{
            List<Individual> top10Gastam = app.getTop10Gastam();
            for(Individual i : top10Gastam){
                System.out.println(i);
            }
        }catch(InvalidAcessException e1){
            System.out.println();
        }

    }

    public void topXDeduct(EFatApp app){
        try{
            Scanner input = new Scanner(System.in);
            System.out.println("\nTop x empresas que mais deduzem:");
            System.out.println("Filtrar top: ");
            int x = input.nextInt();

            List<Empresa> f = app.getTopXEmpresas(x);
            for(Empresa e : f){
                System.out.println(e);
            }
            System.out.println("Total dedutivel representado: " + app.getTopXDeduct(f));
        }catch(InvalidAcessException e){
            System.out.println(e.getMessage());
        }


    }
}