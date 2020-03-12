package efatura;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EFatApp implements Serializable {

    private static final long serialVersionUID = -1501475220295671807L;
    private Map<Integer,Entidade> entidadesMap;
    private Map<Integer,String> passwordsMap;
    private transient Entidade loggedIn;


    //Construtores
    public EFatApp(){
        super();
        this.entidadesMap = new HashMap<>();
        this.passwordsMap = new HashMap<>();
        this.loggedIn = null;

    }

    /**
     * Guarda o estado da aplicacao
     *
     * @param name Nome do ficheiro onde sera gravado
     * @throws IOException
     */
    public void save(String name) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name));
        oos.writeInt(Fatura.count);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    /**
     * Le o estado da aplicacao de um ficheiro
     *
     * @param name Nome do ficheiro a ser lido
     * @return Estado da aplicacao com os utilizadores, empresas e tudo referente a estes.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static EFatApp load(String name) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name));
        Fatura.count = ois.readInt();
        EFatApp app = (EFatApp) ois.readObject();
        ois.close();
        return app;
    }

    //Getters
    private Entidade getLoggedIn(){
        return this.loggedIn;
    }

    public Entidade getLoggedInC(){
        if(this.loggedIn != null) return this.loggedIn.clone();
        else return null;
    }

    public Class<? extends Entidade> getLoggedInInstance(){
        return this.getLoggedIn().getClass();
    }

    /**
     * @param nif NIF da entidade procurada
     * @return Entidade com o nif passado
     */
    private Entidade getEntidade(Integer nif){
        return this.entidadesMap.get(nif);
    }

    public HashMap<Integer,Entidade> getEntidades(){
        HashMap<Integer,Entidade> ret = new HashMap<>();
        this.entidadesMap.values().forEach(e -> ret.put(e.getNif(), e.clone()));
        return ret;
    }

    public void setLoggedIn_NULL(){
        this.loggedIn = null;
    }

    /**
     * Executa o login de um utilizador no sistema
     *
     * @param nif    NIF da pessoa a fazer login
     * @param passwd Password da pessoa a fazer login
     * @throws UserNotFoundException          Caso nao exista ainda o utilizador
     * @throws UserPasswordIncorrectException Caso a password para o utilizador esteja incorreta
     */
    public void login(int nif, String passwd) throws UserNotFoundException, UserPasswordIncorrectException{
        Entidade gajo = entidadesMap.get(nif);

        if(gajo == null){
            throw new UserNotFoundException("Não existe nenhum utilizador com o nif " + nif);
        }

        String pwd = passwordsMap.get(nif);
        if(!pwd.equals(passwd)){
            throw new UserPasswordIncorrectException(
                    "Password errada, por favor verifique se digitou o nif correctamente");
        }

        this.loggedIn = gajo;
    }

    /**
     * Regista um utilizador no sistema
     *
     * @param e Entidade a ser registada
     * @throws UserAlreadyPresentException Caso já exista este utilizador
     */
    public void register(Entidade e) throws UserAlreadyPresentException{
        if(getEntidade(e.getNif()) != null)
            throw new UserAlreadyPresentException("Ja se encontra registado no sistema");
        if(e instanceof Individual){
            int filhos = ((Individual) e).getnDependentes();
            if(filhos >= 4){
                FamiliaNumerosa a = new FamiliaNumerosa((Individual) e);
                this.entidadesMap.put(e.getNif(), a);
                this.passwordsMap.put(e.getNif(), e.getPassword());
            }else{
                this.entidadesMap.put(e.getNif(), e);
                this.passwordsMap.put(e.getNif(), e.getPassword());
            }
        }else{
            if(e instanceof Empresa && ((Empresa) e).getConcelho() != Concelho.OUTRO){
                EmpresaInterior a = new EmpresaInterior((Empresa) e);
                this.entidadesMap.put(e.getNif(), a);
                this.passwordsMap.put(e.getNif(), e.getPassword());
            }else{
                this.entidadesMap.put(e.getNif(), e);
                this.passwordsMap.put(e.getNif(), e.getPassword());
            }
        }
    }


    /**
     * @return Faturas do utilizador que está online no sistema
     *
     * @throws InvalidAcessException Caso seja uma empresa
     */
    public List<Fatura> getFaturasIndividual() throws InvalidAcessException{
        if(this.getLoggedIn() instanceof Individual){
            return ((Individual) this.getLoggedIn()).getListaFact();
        }else
            throw new InvalidAcessException("Sendo uma empresa nao tem permissão para essa operação");
    }

    /**
     * @param e Actividade economica que o utilizador pretende as sua faturas
     * @return Faturas com a atividade economica passada
     *
     * @throws InvalidAcessException Caos seja uma empresa
     */
    public List<Fatura> getFaturasIndividualSetor(ActividadeEconomica e) throws InvalidAcessException{
        if(this.getLoggedIn() instanceof Individual){
            return ((Individual) this.getLoggedIn()).getListaFact()
                                                    .stream()
                                                    .filter(f -> f.getAct().getClass() == e.getClass())
                                                    .collect(Collectors.toList());
        }else
            throw new InvalidAcessException("Sendo uma empresa nao tem permissão para essa operação");

    }

    /**
     * Metodo auxiliar que permite calcular o total de deducao de um utilizador, numa determinada Actividade Economica
     *
     * @param act  Atividade economica pretendida
     * @param user Utilizador a ser utilizado
     * @return Total deduzido na atividade economica em argumento
     */
    public double getDeductPerAct(ActividadeEconomica act, Entidade user){
        double total = 0;
        if(user instanceof Individual){
            Individual a = (Individual) user;
            List<Fatura> fat = a.getListaFact()
                                .stream()
                                .filter(f -> f.getAct().equals(act))
                                .collect(Collectors.toList());
            total = fat.stream()
                       .mapToDouble(
                               f -> ((Deductible) act).deduct_fatura(f, a,
                                                                     (Empresa) this.getEntidade(f.getNif_emitente())))
                       .sum();
        }
        if(user instanceof Empresa){
            Empresa a = (Empresa) user;
            List<Fatura> fat = a.getListaFact()
                                .stream()
                                .filter(f -> f.getAct().equals(act))
                                .collect(Collectors.toList());
            total = fat.stream()
                       .mapToDouble(
                               f -> ((Deductible) act).deduct_fatura(f, a,
                                                                     (Empresa) this.getEntidade(f.getNif_emitente())))
                       .sum();

        }
        return ((Deductible) act).deduct_total(total, user);
    }

    /**
     * Permite calcular o total de deducao do utilizador passado como argumento
     *
     * @param user Entidade a ser verificada
     * @return Total deduzido por esta entidade
     */
    private double getDeductIndividual(Entidade user){ // throws InvalidAcessException
        if(user instanceof Individual){
            Individual i = (Individual) user;
            List<ActividadeEconomica> acs = i.getCodigos().stream()
                                             .filter(Deductible.class::isInstance)
                                             .collect(Collectors.toList());

            return acs.stream().mapToDouble(act -> getDeductPerAct(act, i)).sum();
        }else{
            Empresa i = (Empresa) user;
            List<ActividadeEconomica> acs = i.getCodActivades().stream()
                                             .filter(Deductible.class::isInstance)
                                             .collect(Collectors.toList());

            return acs.stream().mapToDouble(act -> getDeductPerAct(act, i)).sum();
        }
        // }else
        //    throw new InvalidAcessException("Sendo uma empresa nao tem permissão para essa operação");
    }

    /**
     * Metodo que permite calcular o total de deducao do utilizador online no sistema
     *
     * @return Total deduzido pela entidade online no sistema
     *
     * @throws InvalidAcessException Caso seja uma empresa
     */
    public double getDeductIndividual() throws InvalidAcessException{
        if(this.loggedIn instanceof Individual){
            Individual i = (Individual) this.loggedIn;
            List<ActividadeEconomica> acs = i.getCodigos().stream()
                                             .filter(Deductible.class::isInstance)
                                             .collect(Collectors.toList());

            return acs.stream().mapToDouble(act -> getDeductPerAct(act, i)).sum();
        }else
            throw new InvalidAcessException("Sendo uma empresa nao tem permissão para essa operação");
    }

    /**
     * @return Total deduzido pela familia de um utilizador
     *
     * @throws InvalidAcessException Casos seja uma empresa
     */
    public double getDeductFam(){
        //if(this.loggedIn instanceof Individual){
        Individual i = (Individual) this.loggedIn;
        return i.getNumFiscais()
                .stream()
                .map(this::getEntidade).filter(Individual.class::isInstance).map(Individual.class::cast)
                .mapToDouble(user -> {
                    //try{
                    return getDeductIndividual(user);
                    //}catch(InvalidAcessException e){
                    //   return 0;
                    // }
                })
                .sum();

        //}else
        //  throw new InvalidAcessException("Sendo uma empresa nao tem permissão para essa operação");
    }

    /**
     * @param begin Data de inicio
     * @param end   Data do fim
     * @return Faturas de uma empresa compreendidas entre as datas  pedidas
     *
     * @throws InvalidAcessException Caso seja uma entidade individual
     */
    public List<Fatura> geFactBetweenEmpresa(LocalDateTime begin, LocalDateTime end) throws InvalidAcessException{
        if(this.loggedIn instanceof Empresa){
            Empresa e = (Empresa) this.loggedIn;
            List<Fatura> lmao = e.getListaFact();
            return lmao.stream()
                       .filter(f -> f.getData().isAfter(begin) && f.getData().isBefore(end))
                       .collect(Collectors.toList());
        }else
            throw new InvalidAcessException("Sendo um contribuinte nao tem permissão para essa operação");
    }

    /**
     * @return Lista dos 10 individuais com mais gastos
     *
     * @throws InvalidAcessException Apenas o Admin pode aceder a esta informacao
     */
    public List<Individual> getTop10Gastam() throws InvalidAcessException{
        if(this.loggedIn instanceof Admin){
            Comparator<Individual> comp = new IndividualValorComparator().reversed();

            return this.entidadesMap.values()
                                    .stream()
                                    .filter(Individual.class::isInstance)
                                    .map(Individual.class::cast)
                                    .sorted(comp)
                                    .limit(10)
                                    .collect(Collectors.toList());
        }else
            throw new InvalidAcessException("So o admin pode executar essa operação");
    }

    /**
     * @param x Numero de empresas pretendidas
     * @return Lista das x empresas com mais faturas
     *
     * @throws InvalidAcessException Apenas o Admin pode aceder a esta informacao
     */
    public List<Empresa> getTopXEmpresas(Integer x) throws InvalidAcessException{
        if(this.loggedIn instanceof Admin)
            return this.entidadesMap.values().stream()
                                    .filter(Empresa.class::isInstance)
                                    .map(Empresa.class::cast).sorted(new EmpresaFaturaCountComparator().reversed())
                                    .limit(x)
                                    .collect(Collectors.toList());

        else
            throw new InvalidAcessException("So o admin pode executar essa operação");
    }

    /**
     * @return Lista das empresas com mais deducoes
     */
    public double getTopXDeduct(List<Empresa> le){
        double total = 0;
        double tmp = 0;
        for(Empresa e : le){
            tmp = this.getDeductIndividual(e);
            total += tmp;
            tmp = 0;
        }

        return total;
    }

    /**
     * @param c Comparator para organizar as fatuas
     * @return Faturas de uma empresa ordenadas conforme o comparador passado
     *
     * @throws InvalidAcessException Caso seja um consumidor a tentar aceder
     */
    public List<Fatura> getFaturasEmpresa_ordered(Comparator<Fatura> c) throws InvalidAcessException{
        if(this.loggedIn instanceof Empresa){
            Empresa e = (Empresa) this.loggedIn;
            Set<Fatura> tmp = e.getFaturas_ordered(c);
            return new ArrayList<>(tmp);

        }else
            throw new InvalidAcessException("So uma empresa pode executar esta operação");
    }

    /**
     * @param act Atividade economica a ser definida na fatura
     * @throws InvalidAcessException   Caso o utilizador nao tenha acesso a esta acao (se for empresa nao pode utilizar
     *                                 este metodo)
     * @throws FaturaNotFoundException Caso a fatura nao exista
     */
    public void changeFatura(ActividadeEconomica act, Fatura f) throws
                                                                InvalidAcessException,
                                                                FaturaNotFoundException{
        if(this.loggedIn instanceof Individual){
            Individual i = (Individual) this.loggedIn;
            i.changeFatura(f, act);
        }else
            throw new InvalidAcessException("So um contribuinte pode executar esta operação");


    }

    /**
     * Retorna um lista de faturas do consumidor x
     *
     * @param nif NIF do consumidor
     * @return Lista de faturas
     *
     * @throws InvalidAcessException Caso seja um utilizador a tentar aceder a esta informacao
     */
    public List<Fatura> getListFaturaContr(int nif) throws InvalidAcessException{
        if(this.getLoggedIn() instanceof Empresa){
            Empresa e = (Empresa) this.getLoggedIn();
            return e.getListaFact().stream().filter(f -> f.getNif_cliente() == nif).collect(Collectors.toList());
        }else
            throw new InvalidAcessException("So um contribuinte pode executar esta operação");
    }

    /**
     * @return O total faturado de uma empresa
     *
     * @throws InvalidAcessException Caso seja um consumidor a aceder a esta informacao
     */
    public double getTotalFaturado() throws InvalidAcessException{
        if(this.getLoggedIn() instanceof Empresa){
            Empresa e = (Empresa) this.getLoggedIn();
            return e.getTotal();
        }else
            throw new InvalidAcessException("So um contribuinte pode executar esta operação");
    }

    /**
     * Metodo que permite uma empresa gerar uma empresa
     *
     * @param nif      NIF do cliente
     * @param valor    Valor da despesa
     * @param des_desp Descricao da despesa
     * @throws InvalidAcessException Caso seja um utilizaor do tipo individual
     * @throws UserNotFoundException Caso introduza um NIF que nao exista
     */
    public void emiteFatura(int nif, float valor, String des_desp) throws InvalidAcessException, UserNotFoundException{
        if(this.getLoggedIn() instanceof Empresa){
            Empresa e = (Empresa) this.getLoggedIn();
            Individual user = (Individual) this.entidadesMap.get(nif);
            if(user == null) throw new UserNotFoundException();
            e.emiteFatura(user, valor, des_desp);
        }else
            throw new InvalidAcessException();
    }

    /**
     * Metodo que permite popular a "base de dados" com utilizadores e faturas
     */
    public void dummy_generator(){
        DummyTest gen = new DummyTest();
        gen.generateContribuintes(this);
        generateFacturas();
    }

    /**
     * Permite gerar faturas aleatoriamente nos utilizadores
     */
    public void generateFacturas(){
        List<Integer> issuers = entidadesMap.values()
                                            .stream()
                                            .filter(Empresa.class::isInstance)
                                            .map(Entidade::getNif)
                                            .collect(Collectors.toList());
        List<Integer> clients = entidadesMap.values()
                                            .stream()
                                            .filter(Individual.class::isInstance)
                                            .map(Entidade::getNif)
                                            .collect(Collectors.toList());

        Random r = new Random();
        for(int i = 0; i < 40; i++){
            Integer issuerNif = issuers.get(new Random().nextInt(issuers.size()));
            Integer clientNif = clients.get(new Random().nextInt(clients.size()));
            String description = issuerNif + " -> " + clientNif;
            float value = r.nextFloat() * 500;

            ((Empresa) this.entidadesMap.get(issuerNif))
                    .emiteFatura((Individual) this.entidadesMap.get(clientNif), value, description);
        }
    }

    public Entidade getInstanceEntidade(int nif) throws UserNotFoundException{
        Entidade e = this.entidadesMap.get(nif);
        if(e == null) throw new UserNotFoundException();
        else return e.clone();
    }
}


