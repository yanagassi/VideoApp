package br.com.video_app.app.video_app;

public class RankingMetodosAdapter {

    private String nome;
    private  String posicao;
    private  String video_appcoins;
    private String cidade;
    private String Link;

    public RankingMetodosAdapter(String  nome, String posicao, String video_appcoins, String cidade, String Link)  {
        this.nome=  nome;
        this.posicao = posicao;
        this.video_appcoins = video_appcoins;
        this.cidade =  cidade;
        this.Link = Link;
    }

    public String getNomeD() {
        return nome;
    }
    public String getposicao() {
        return posicao;
    }
    public String getLinkImg(){ return  Link;}
    public String getvideo_appcoins() {
        return video_appcoins;
    }
    public String getcidade() {
        return cidade;
    }
}
