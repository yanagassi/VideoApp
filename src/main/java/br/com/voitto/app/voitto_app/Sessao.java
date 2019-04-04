package br.com.video_app.app.video_app;

public class Sessao{
    private static Sessao instance = null;
    private String usuario;
    private String nomeUsuario;
    private String email;
    private String foto;
    private String video_appcoins;
    private String DADOS;
    private String DadosRank;
    private String colocacao;
    private String acumulado;
    private String notificacao;
    private Sessao(){
    }


    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    public String getUsuario(){
        return usuario;
    }


    public void setnomeUsuario(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
    }
    public String getnomeUsuario(){
        return nomeUsuario;
    }



    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void setFoto(String foto){
        this.foto = foto;
    }
    public String getFoto(){
        return foto;
    }


    public void setDADOS(String DADOS){
        this.DADOS = DADOS;
    }
    public String getDADOS(){
        return DADOS;
    }

    public void setvideo_appcoins(String video_appcoins){
        this.video_appcoins = video_appcoins;
    }
    public String getvideo_appcoins(){
        return video_appcoins;
    }

    public void setDadosRank(String DadosRank){
        this.DadosRank = DadosRank;
    }
    public String getDadosRank(){
        return DadosRank;
    }

    public void setColocacao(String colocacao){ this.colocacao = colocacao;}
    public String getColocacao(){return  colocacao;}

    public void setacumulado(String acumulado){ this.acumulado = acumulado;}
    public String getacumulado(){return  acumulado;}

    public void setnotificacao(String notificacao){ this.notificacao = notificacao;}
    public String getnotificacao(){return  notificacao;}

    public static Sessao getInstance(){
        if(instance == null){
            instance = new Sessao();
        }
        return instance;
    }
}