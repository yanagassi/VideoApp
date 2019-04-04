package br.com.video_app.app.video_app;

public class curso {
    private String nome;
    private int imagem;
    private String endereco;
    private String sis;
    private String idCurso;
    private String ids;
    public curso(String  nome,  String endereco,int imagem, String sis,  String idCurso, String ids)  {
        this.nome=  nome;
        this.endereco=endereco;
        this.imagem=  imagem;
        this.sis = sis;
        this.ids = ids;
        this.idCurso = idCurso;
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
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getSis() {
        return sis;
    }
    public String getIds() {
        return ids;
    }
    public String getidCurso() {
        return idCurso;
    }
}
