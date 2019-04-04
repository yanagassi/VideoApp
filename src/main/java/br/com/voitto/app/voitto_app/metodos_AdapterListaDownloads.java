package br.com.video_app.app.video_app;

import android.graphics.drawable.Drawable;

public class metodos_AdapterListaDownloads {
    private String nome;
    private int imagem;
    private boolean del;
    //vai  armazenar  o  identificador  do  recurso

    public metodos_AdapterListaDownloads(String  nome,  int imagem,boolean del)  {
        this.nome=  nome;
        this.imagem=  imagem;
        this.del = del;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getImagem() {
        return imagem;
    }
    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
    public boolean getdel() {
        return del;
    }
}
