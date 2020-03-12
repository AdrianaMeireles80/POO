package efatura;

public enum Concelho {
    Abrantes(0.132f),
    Albufeira(0.156f),
    Alcanena(0.191f),
    Alcobaca(0.204f),
    Amarante(0.220f),
    Arganil(0.128f),
    Arouca(0.351f),
    Arronches(0.249f),
    Avis(0.227f),
    Barcelos(0.170f),
    Beja(0.129f),
    Braga(0.255f),
    Braganca(0.260f),
    Cabeceiras_de_Basto(0.306f),
    Caldas_das_Rainhas(0.150f),
    Castelo_Branco(0.188f),
    Covilha(0.169f),
    Fafe(0.224f),
    Felgueiras(0.250f),
    Faro(0.242f),
    Guimaraes(0.256f),
    Lisboa(0.257f),
    Lousada(0.195f),
    Odivelas(0.172f),
    Ovar(0.144f),
    Vagos(0.181f),
    Viseu(0.267f),
    OUTRO(0);

    private float bonificacao;

    Concelho(float b){
        this.bonificacao = b;
    }

    public float getBonificacao(){
        return this.bonificacao;
    }
}
