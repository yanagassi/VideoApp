package br.com.video_app.app.video_app;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DownloadsAction extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads_action);
        try {
            limapr(DownloadsAction.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListView lista = findViewById(R.id.listviewDownloadsAction);
        ListaAdapter(lista);
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("As lições estão sendo baixadas.");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });



        ImageView fotoUss = findViewById(R.id.fotoUss);
        fotoUss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String[] array =  retron(DownloadsAction.this).split(";");
                AlertDialog alert11 = builder1.create();
                alert11.show();
               for(int a=0;a<array.length;a++)
               {
                   if(!array[a].equals(""))
                   {
                       try{
                           Intent it = getIntent();
                           String id = it.getStringExtra("id");
                           String tituloView = it.getStringExtra("titulo");
                           String name = "";
                           name = tituloView + "/" + nomeGet(array[a], id);
                           download(array[a],id,name);

                       }
                       catch (Exception e)
                       {

                       }
                   }

               }
            }
        });


    }


    public void ListaAdapter(ListView lista) {
        ArrayAdapter adapter;
        adapter = new DownloadsAction_Adapter(this, listarAdapter());
        lista.setAdapter(adapter);
    }


    private ArrayList<DonwloadsAction_Adapter_Metodis> listarAdapter(){
        ArrayList<DonwloadsAction_Adapter_Metodis> sis = new ArrayList<>();


        DonwloadsAction_Adapter_Metodis e;
        String [] arrayDados = retronoJson(this).split("</exp>");
        String  [] nome = null;
        for(int a =0;a<arrayDados.length;a++)
        {
            nome = arrayDados[a].split("===");
            filtroS ft = new filtroS();
            e = new DonwloadsAction_Adapter_Metodis(ft.frt(nome[0]), nome[1]);
            sis.add(e);
        }
        return sis;
    }

    private String retronoJson(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("dadosDownloads");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            //Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            //Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }


    private String retron(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("linksParaDownload");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (Exception e) { }
        return ret;
    }

    private String remove(Context context) throws IOException {
        String ret = "";
        OutputStreamWriter ss = new OutputStreamWriter(context.openFileOutput("linksParaDownload", Context.MODE_PRIVATE));
        ss.write("");
        ss.close();

        return ret;

    }


    public void download(final String js_id, final String idCurso, String name){

        try {

            InputStream input = new URL("https://www.API_URL/apiapp/view/videodownload.php?js_id=" + js_id + "&idcurso=" + idCurso).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String json;
            json = s.nextLine();
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(json);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle(name);
            File sdCard = Environment.getExternalStorageDirectory();
            String folder = sdCard.getAbsolutePath() + "/listem";
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, name + ".mp4");
            Long reference = downloadManager.enqueue(request);
            new Thread(new Runnable() {
                public void run() {

                }
            }).start();
        }
        catch (Exception e) { }
    }

    public String nomeGet(String js_idS, String IDcurso){

        try{
            InputStream input = new URL("https://www.API_URL/apiapp/view/videonome.php?id_js=" + js_idS + "&id_curso=" + IDcurso).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String link;
            filtroS filtro = new filtroS();
            link = s.nextLine();
            link = filtro.frt(link);
            //Toast.makeText(this, link.toString(), Toast.LENGTH_SHORT).show();
            return link;
        }
        catch (Exception e){
            return "";
        }
    }

    private void limapr(Context context) throws IOException {
        OutputStreamWriter ss = new OutputStreamWriter(context.openFileOutput("linksParaDownload", Context.MODE_PRIVATE));
        ss.write("");
        ss.close();
    }


}
