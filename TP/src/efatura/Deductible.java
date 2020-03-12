package efatura;

public interface Deductible {

    /**
     * Caso uma Atividade economica possa ser deduzida, esta implementara esta interface para o utilizador
     * conseguir saber quanto deduzira nas faturas com essa atividade
     */

    /**
     * Metodo que permite saber o montante de deducao numa fatura
     *
     * @param f       Fatura a ser consutlada
     * @param user    Utilizador a quem pertence a fatura
     * @param empresa Empresa responsável pela fatura
     * @return Montante de deducao
     */
    float deduct_fatura(Fatura f, Entidade user, Empresa empresa);

    /**
     * Metodo que permite saber o total de deducao. Caso o total seja inferior ao limite definido em cada
     * Actividade Economica, o montante de deducao sera o recebido, caso contrario retorna o limite
     *
     * @param total Montante total deduzido
     * @param user  Utilizador (necessario para saber se pertence a uma Familia Numerosa)
     * @return Total de deducao
     */
    double deduct_total(Double total, Entidade user);
}
