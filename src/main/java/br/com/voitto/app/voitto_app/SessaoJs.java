package br.com.video_app.app.video_app;

public class SessaoJs {
    private static SessaoJs instance = null;
    private String idjs;
    private String checked;


    private SessaoJs(){
    }

    public void setidjs(String idjs){
        this.idjs = idjs;
    }


    public String getidjs(){
        return idjs;
    }

    public void setCheckedId(String checked){
        this.checked = checked;
    }

    public String getCheckedId(){
        return checked;
    }




    public static SessaoJs getInstance(){
        if(instance == null){
            instance = new SessaoJs();
        }
        return instance;
    }
}
