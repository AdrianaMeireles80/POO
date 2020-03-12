package efatura;

import java.io.Serializable;
import java.util.*;

public class MenuLogin implements Serializable {


    private static final long serialVersionUID = 8763763746188227335L;
    private List<String> opcoesLogin;
    private List<String> opcoesRegister;
    private int op;

    /**
     * Constructor for objects of class Menu
     */
    public MenuLogin(){
        super();
        opcoesLogin = new ArrayList<>();
        opcoesRegister = new ArrayList<>();

        opcoesLogin.add("Sign up");
        opcoesLogin.add("Sign in");
        //opcoesLogin.add("Log out");

        opcoesRegister.add("Como contribuinte");
        opcoesRegister.add("Como empresa");
        opcoesRegister.add("Como admin");

        op = 0;
    }


    private void showMenu(String str){
        if(str.equals("login")){
            System.out.println("\n      ___________       __                       ");
            System.out.println("  ____\\_   _____/____ _/  |_ __ ______________   ");
            System.out.println("_/ __ \\|    __) \\__  \\\\   __\\  |  \\_  __ \\__  \\  ");
            System.out.println(("\\  ___/|     \\   / __ \\|  | |  |  /|  | \\// __ \\_"));
            System.out.println((" \\___  >___  /  (____  /__| |____/ |__|  (____  /"));
            System.out.println("     \\/    \\/        \\/                       \\/ \n");

            int i = 1;
            for(String f : opcoesLogin){
                System.out.print("[" + i++ + "] - " + f + "\n");

            }
            System.out.println("\n[0] - Exit\n");

        }else if(str.equals("register")){
            System.out.println("\nSignUp\n");
            int i = 1;
            for(String f : opcoesRegister){
                System.out.print("[" + i++ + "] - " + f + "\n");

            }
            System.out.println("\n[0] - Exit\n");
        }
    }


    public void exec(String str){
        do{
            showMenu(str);
            op = readOp(str);
        }
        while(op == -1);
    }

    private int readOp(String str){
        int op;
        Scanner is = new Scanner(System.in);

        if(str.equals("login")){
            System.out.print("Opção: ");

            try{
                op = is.nextInt();
            }catch(InputMismatchException e){ //NotInt
                op = -1;
            }

            if(op < 0 || op > opcoesLogin.size()){
                System.out.println("Opção inválida");
                op = -1;
            }

            return op;

        }else{
            System.out.print("Opção: ");

            try{
                op = is.nextInt();
            }catch(InputMismatchException e){ //NoInt
                op = -1;
            }

            if(op < 0 || op > opcoesRegister.size()){
                System.out.println("Opção inválida");
                op = -1;
            }
            return op;
        }
    }


    /** get opcao **/
    public int getOp(){
        return this.op;
    }

    /** menu Login **/
    public ArrayList<String> showMenuLogin(){
        Scanner input = new Scanner(System.in);
        ArrayList<String> res = new ArrayList<>();

        System.out.println("\n  _________.__                .___        ");
        System.out.println(" /   _____/|__| ____   ____   |   | ____  ");
        System.out.println(" \\_____  \\ |  |/ ___\\ /    \\  |   |/    \\ ");
        System.out.println(" /        \\|  / /_/  >   |  \\ |   |   |  \\");
        System.out.println("/_______  /|__\\___  /|___|  / |___|___|  /");
        System.out.println("        \\/   /_____/      \\/           \\/ \n");

        System.out.println("\nNIF ");
        res.add(input.next());
        System.out.println("\nPassword: ");
        res.add(input.next());

        op = -1;
        return res;

    }

    /** menu Register **/
    public ArrayList<String> showMenuRegister(){
        Scanner input = new Scanner(System.in);
        ArrayList<String> res = new ArrayList<>();
        boolean flag = true;
        List<ActividadeEconomica> lmao = new ArrayList<>(ActividadeEconomica.getAllSectors());
        ArrayList<Concelho> concelhos = new ArrayList<Concelho>() {
            private static final long serialVersionUID = 2958751059180601106L;

            {
                this.addAll(Arrays.asList(Concelho.values()));
            }
        };

        if(op == 1){
            res.add("Contribuinte");
            System.out.println("\nSignUp Contribuinte\n");

            System.out.println("\nNIF: ");
            res.add(input.next());
            System.out.println("\nEmail: ");
            res.add(input.next());
            System.out.println("\nNome: ");
            res.add(input.next());
            System.out.println("\nMorada: ");
            res.add(input.next());
            System.out.println("\nPassword: ");
            res.add(input.next());
            System.out.println("\nNúmero de dependentes ");
            res.add(input.next());

            while(flag){
                System.out.println("\nNIFs do agregado familiar (0 para seguir em frente): ");
                String s = input.next();
                if(!s.equals("0"))
                    res.add(s);
                else
                    flag = false;
            }
            res.add("DONE N");

            System.out.println("Setores economicos");
            int i = 0;
            flag = true;
            for(ActividadeEconomica s : lmao){
                System.out.println("\n[" + i++ + "]" + " - " + s);
            }

            while(flag){
                System.out.println("Setor (Exit para seguir em frente): ");
                String s = input.next();
                if(!s.equalsIgnoreCase("exit"))
                    res.add(s);
                else
                    flag = false;
            }
            System.out.println("Coeficiente fiscal: ");
            res.add(input.next());

        }
        if(op == 2){
            res.add("Empresa");
            System.out.println("\nSignUp Empresa\n");
            System.out.println("NIF: ");
            res.add(input.next());
            System.out.println("Email: ");
            res.add(input.next());
            System.out.println("\nNome: ");
            res.add(input.next());
            System.out.println("\nMorada: ");
            res.add(input.next());
            System.out.println("\nPassword: ");
            res.add(input.next());

            System.out.println("Coeficiente fiscal da empresa: ");
            res.add(input.next());

            System.out.println("\nConcelho: ");
            int i = 0;
            for(Concelho c : concelhos){
                System.out.println("\n[" + i++ + "]" + " - " + c);
            }
            res.add(input.next());


            i = 0;
            flag = true;
            for(ActividadeEconomica s : lmao){
                System.out.println("\n[" + i++ + "]" + " - " + s);
            }

            while(flag){
                System.out.println("Setor (Exit para seguir em frente): ");
                String s = input.next();
                if(!s.equalsIgnoreCase("exit"))
                    res.add(s);
                else
                    flag = false;
            }
        }
        if(op == 3){
            res.add("Admin");
            System.out.println("\nSignUp Admin\n");
            System.out.println("\nNIF: ");
            res.add(input.next());
            System.out.println("\nEmail: ");
            res.add(input.next());
            System.out.println("\nNome: ");
            res.add(input.next());
            System.out.println("\nPassword: ");
            res.add(input.next());
        }
        if(op != 1 && op != 2 && op != 3)
            res.add("error");

        op = -1;

        return res;

    }

}



