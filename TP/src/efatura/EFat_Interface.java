package efatura;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EFat_Interface implements Serializable {

    private static final long serialVersionUID = -7859455168681412308L;

    private EFatApp app;


    private EFat_Interface(){
        super();
        this.app = new EFatApp();
    }

    /**
     * Metodo para
     */
    private void run(){

        try{
            this.app = EFatApp.load("save.obj");
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
            this.app.dummy_generator();
        }


        int controlo = 0;
        while(controlo != 2){
            controlo = login();

            if(controlo == 2)
                return;

            if(app.getLoggedInC() == null) continue;
            if(app.getLoggedInInstance() == Individual.class || app.getLoggedInInstance() == FamiliaNumerosa.class){
                controlo = Contribuinte(this.app);
            }else if(app.getLoggedInInstance() == Empresa.class || app.getLoggedInInstance() == EmpresaInterior.class){
                controlo = Empresa(this.app);
            }else if(app.getLoggedInInstance() == Admin.class)
                controlo = Admin(this.app);
        }

    }

    /**
     * Metodo para executar login do utilizador e permitir o acesso as diferentes funcoes da aplicacao
     *
     * @return Estado do utilizador no sistema. A aplicacao termina devolvendo false
     */
    private int login(){
        Boolean controlo = false;
        int op;
        List<String> dataL;
        List<String> dataR;

        MenuLogin loginMenu = new MenuLogin();
        loginMenu.exec("login");
        op = loginMenu.getOp();

        switch(op){
            case 0:
                try{
                    this.app.save("save.obj");
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
                this.app.setLoggedIn_NULL();
                return 2;
            case 1:
                do{
                    try{
                        loginMenu.exec("register");
                        dataR = loginMenu.showMenuRegister();
                        if(dataR.get(0).equals("error")) break;
                        List<ActividadeEconomica> lmao = new ArrayList<>(ActividadeEconomica.getAllSectors());
                        ArrayList<Concelho> concelhos = new ArrayList<Concelho>() {
                            private static final long serialVersionUID = -8486619620955521097L;

                            {
                                this.addAll(Arrays.asList(Concelho.values()));
                            }
                        };

                        if(dataR.get(0).equals("Contribuinte")){
                            try{
                                List<Integer> numF = new ArrayList<>();
                                int j;
                                for(j = 7; j < dataR.size(); j++){
                                    if(dataR.get(j).equals("DONE N")){
                                        break;
                                    }else{
                                        numF.add(Integer.parseInt(dataR.get(j)));
                                    }
                                }
                                List<ActividadeEconomica> pass = new ArrayList<>();
                                for(int x = j + 1; x < dataR.size() - 1; x++){
                                    pass.add(lmao.get(Integer.parseInt(dataR.get(x))));
                                }

                                Individual i = new Individual(Integer.parseInt(dataR.get(1)), dataR.get(2),
                                                              dataR.get(3),
                                                              dataR.get(4), dataR.get(5),
                                                              Integer.parseInt(dataR.get(6)),
                                                              numF,
                                                              Float.parseFloat(dataR.get(j + 1)), pass,
                                                              new LinkedList<>());

                                app.register(i);
                                System.out.println("Registo efetuado com sucesso!");
                                controlo = true;
                            }catch(Exception e){
                                System.out.println(e.getMessage());
                                controlo = false;
                            }

                        }

                        if(dataR.get(0).equals("Empresa")){
                            List<ActividadeEconomica> pass = new ArrayList<>();
                            try{
                                for(int x = 7; x < dataR.size() - 2; x++){
                                    pass.add(lmao.get(Integer.parseInt(dataR.get(x))));
                                }
                                Empresa e = new Empresa(Integer.parseInt(dataR.get(1)), dataR.get(2), dataR.get(3),
                                                        dataR.get(4), dataR.get(5), pass,
                                                        Float.parseFloat(dataR.get(dataR.size() - 2)),
                                                        concelhos.get(Integer.parseInt(dataR.get(dataR.size() - 1))));


                                app.register(e);
                                controlo = true;
                            }catch(Exception e){
                                System.out.println(e.getMessage());
                                controlo = false;
                            }

                        }

                        if(dataR.get(0).equals("Admin")){
                            Admin a = new Admin(Integer.parseInt(dataR.get(1)), dataR.get(2), dataR.get(3),
                                                dataR.get(4));

                            app.register(a);
                            controlo = true;
                        }


                    }catch(UserAlreadyPresentException e){
                        //e.printStackTrace();
                        System.out.println(e.getMessage());
                        return 1;
                    }
                }
                while(!controlo);
                break;

            case 2:
                do{
                    try{
                        dataL = loginMenu.showMenuLogin();
                        this.app.login(Integer.parseInt(dataL.get(0)), dataL.get(1));
                        System.out.println("Login with sucess\n");
                        controlo = true;
                    }catch(UserNotFoundException | UserPasswordIncorrectException e){
                        System.out.println(e.getMessage());
                        return 1;
                    }
                }
                while(!controlo);
                break;
            default:
                this.app.setLoggedIn_NULL();
                return 0;
        }

        return 1;

    }

    private int Contribuinte(EFatApp efat){
        int op;
        MenuContribuinte contriMenu = new MenuContribuinte();

        while(contriMenu.getOp() != 0){
            contriMenu.exec();
            op = contriMenu.getOp();
            switch(op){
                case 0:
                    try{
                        this.app.save("save.obj");
                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                    return 2;
                case 1:
                    contriMenu.listFaturas(efat);
                    break;
                case 2:
                    contriMenu.listFaturasPend(efat);
                    break;
                case 3:
                    contriMenu.listaFaturasSetores(efat);
                    break;
                case 4:
                    contriMenu.changeFatura(efat);
                    break;
                case 5:
                    contriMenu.getPerfil(efat);
                    break;
                default:
                    this.app.setLoggedIn_NULL();
                    return 0;
            }
        }
        return 1;
    }

    public int Empresa(EFatApp efat){
        int op;
        MenuEmpresa empMenu = new MenuEmpresa();

        while(empMenu.getOp() != 0){
            empMenu.exec();
            op = empMenu.getOp();
            switch(op){
                case 0:
                    try{
                        this.app.save("save.obj");
                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                    return 2;
                case 1:
                    empMenu.faturasCrono(efat);
                    break;
                case 2:
                    empMenu.faturasValor(efat);
                    break;
                case 3:
                    empMenu.faturasBetweenDates(efat);
                    break;
                case 4:
                    empMenu.faturasIndividual(efat);
                    break;
                case 5:
                    empMenu.faturado(efat);
                    break;
                case 6:
                    empMenu.emitFatura(efat);
                    break;
                case 7:
                    empMenu.getPerfil(efat);
                    break;
                default:
                    this.app.setLoggedIn_NULL();
                    return 0;
            }
        }
        return 1;
    }

    private int Admin(EFatApp efat){
        int op;
        MenuAdmin adminMenu = new MenuAdmin();

        while(adminMenu.getOp() != 0){
            adminMenu.exec();
            op = adminMenu.getOp();
            switch(op){
                case 0:
                    try{
                        this.app.save("save.obj");
                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                    return 2;
                case 1:
                    System.out.println("Foram emitidas " + Fatura.count + "faturas\n");
                    break;
                case 2:
                    adminMenu.top10(efat);
                    break;
                case 3:
                    adminMenu.topXDeduct(efat);
                    break;
                default:
                    this.app.setLoggedIn_NULL();
                    return 0;
            }
        }
        return 1;
    }

    /**
     * @param args
     */
    public static void main(String[] args){
        new EFat_Interface().run();
    }
}
