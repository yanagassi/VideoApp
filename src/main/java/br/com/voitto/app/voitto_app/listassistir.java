package br.com.video_app.app.video_app;

public class listassistir {
    private String nome;
    private int imagem;
    private boolean visi;
    private String nomeMod;
    private  String tam;
    private  int check;
    private  boolean mds;
    //vai  armazenar  o  identificador  do  recurso
    private String endereco;

    public listassistir(String  nome, int imagem, boolean visi, String nomeMod, int check, String tam, boolean mds)  {
        this.nome=  nome;
        this.imagem=  imagem;
        this.visi = visi;
        this.nomeMod = nomeMod;
        this.check=check;
        this.tam = tam;
        this.mds = mds;
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

    public  boolean getMds(){return mds;}
    public void setTam(String tam) {
        this.tam = tam;
    }
    public String getTam() {
        return tam;
    }



    public void setVisi(boolean visi) {
        this.visi = visi;
    }
    public boolean getVisi(){ return visi ;}

    public int getcheck(){ return check ;}

    public void setnomeMod(String nomeMod) {
        this.nomeMod = nomeMod;
    }
    public String getNomeMod()
    {
        return nomeMod;
    }

}
