package br.com.video_app.app.video_app;

public class CertificadosAdapter_Metodos {

    private String titulo;
    private String foto;

    public CertificadosAdapter_Metodos(String titulo, String foto)  {
        this.titulo = titulo;
        this.foto = foto;
    }

    public String gettitulo() {
        return titulo;
    }
    public void settitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getfoto() {
        return foto;
    }
    public void setfoto(String foto) {
        this.foto = foto;
    }



}
