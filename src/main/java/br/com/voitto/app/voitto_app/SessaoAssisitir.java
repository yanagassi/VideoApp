package br.com.video_app.app.video_app;

public class SessaoAssisitir {
    private static SessaoAssisitir instance = null;

    private String sessaoAssisitir;
    private String titulo;
    private String idCurso;
    private String aovivo;
    private SessaoAssisitir(){
    }



    public void setAssistir(String sessaoAssisitir){
        this.sessaoAssisitir = sessaoAssisitir;
    }
    public void setTitulo(String titulo) {this.titulo  =  titulo;}
    public void setidCurso(String idCurso) {this.idCurso  =  idCurso;}
    public void setaovivo(String aovivo) {this.aovivo  =  aovivo;}


    public String getidAssistir(){
        return sessaoAssisitir;
    }
    public String getTitulo(){ return titulo; }
    public String getidCurso(){ return idCurso; }
    public String getaovivo(){ return aovivo; }


    public static SessaoAssisitir getInstance(){
        if(instance == null){
            instance = new SessaoAssisitir();
        }
        return instance;
    }
}
