package br.com.video_app.app.video_app;

import com.nostra13.universalimageloader.core.ImageLoader;

public class NotificacoesAdapter_Metodos {

    private String titulo;
    private String foto;
    private String subtitulo;
    private  ImageLoader mImageLoader;

    public NotificacoesAdapter_Metodos(String titulo, String subtitulo, String foto, ImageLoader mImageLoader)  {
        this.titulo = titulo;
        this.foto = foto;
        this.subtitulo = subtitulo;
        this.mImageLoader = mImageLoader;
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

    public String getSubtitulo() {
        return subtitulo;
    }
    public void setsubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public ImageLoader getMimageLoader() {
        return mImageLoader;
    }

}
