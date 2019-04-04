package br.com.video_app.app.video_app;

import com.nostra13.universalimageloader.core.ImageLoader;

public class duvidasAdapter_Metodos {

    private String nome;
    private String mensage;
    private String foto;
    private String fotoTutor;
    private String mensagemTutor;
    private String nomeTutor;

    public duvidasAdapter_Metodos(String nome, String mensage,  String foto, String fotoTutor, String mensagemTutor, String nomeTutor)  {
        this.nome = nome;
        this.mensage = mensage;
        this.foto = foto;

        this.fotoTutor = fotoTutor;
        this.mensagemTutor = mensagemTutor;
        this.nomeTutor = nomeTutor;
    }

    public String getnome() {
        return nome;
    }
    public String getFoto(){return foto;}
    public String getMensage() {
        return mensage;
    }

    public String getNomeTutor(){return nomeTutor;}
    public String getFotoTuto(){return  fotoTutor;}
    public String getMensagemTutor(){return mensagemTutor;}
}
