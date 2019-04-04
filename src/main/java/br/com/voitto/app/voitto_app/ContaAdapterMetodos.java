package br.com.video_app.app.video_app;

public class ContaAdapterMetodos {

    private String nome;
    private  int  image;
    private String subTitulo;
    private int switchS;
    public ContaAdapterMetodos(String  nome, int image, String subTitulo,int switchS)  {
        this.nome=  nome;
        this.image = image;
        this.subTitulo = subTitulo;
        this.switchS = switchS;

    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getImageSK() {
        return image;
    }
    public void setImageSK(int image) {
        this.image = image;
    }

    public String getsubTitulo() {
        return subTitulo;
    }
    public void setsubTitulo(String nome) {
        this.subTitulo = subTitulo;
    }


    public int getswitchS() { return switchS; }
    public void setswitchS(int switchS) {
        this.switchS = switchS;
    }

}
