package br.com.video_app.app.video_app;


import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VideoDownload extends AppCompatActivity {

    public int progress_bar_type;
    ProgressDialog pDialog;
    public boolean tiper = true;
    public String [] arrayGlobal = new String[100];
    public String [] arquivo = new String[100];
    public String pastast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_download);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final ListView lista = (ListView) findViewById(R.id.lv);
        listaAdapter(lista);
        tiper = true;
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(VideoDownload.this, ItensOffiline.class);
                intent.putExtra("diretorio", arrayGlobal[position]);
                startActivity(intent);
            }
        });

        Toast.makeText(this, "Segure para apagar o video !", Toast.LENGTH_SHORT).show();

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, final int posicao, long id) {
                android.app.AlertDialog.Builder adb = new android.app.AlertDialog.Builder(VideoDownload.this);
                adb.setTitle("Deseja apagar "+ arrayGlobal[posicao] + " ?");
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleta(posicao);
                        listaAdapter(lista);
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { }
                });
                adb.show();
                return  true;
            }
        });



        ImageView img = findViewById(R.id.info);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(VideoDownload.this);
                builder1.setMessage("Modo off-line – Nele, você poderá ver as lições baixadas de seus cursos, os progressos gerados por essas lições serão contabilizados assim que o aplicativo tiver acesso à internet.");
                builder1.setPositiveButton("ok", null);
                builder1.show();
            }
        });


    }

    private void deleta(int position)
    {
        String root_sd = Environment.getExternalStorageDirectory().toString();
        File dir = new File( root_sd + "/Android/data/br.com.video_app.app.video_app/files/Download/"+arrayGlobal[position] ) ;
        try{
            File[] arquivos = dir.listFiles();
            for(File path:arquivos){
                path.delete();
            }
        }catch(Exception e){ }
        dir.delete();
    }

    private ArrayList<metodos_AdapterListaDownloads> addListaDeVideos() throws Exception {

        ArrayList<metodos_AdapterListaDownloads> videos = new ArrayList<metodos_AdapterListaDownloads>();
        File file;
        String lsls;
        String root_sd = Environment.getExternalStorageDirectory().toString();
        file = new File( root_sd + "/Android/data/br.com.video_app.app.video_app/files/Download" ) ;
        File list[] = file.listFiles();

        for(int s = 0; s < list.length; s++)
        {
            lsls =list[s].getName();
            arrayGlobal[s] = list[s].getName();
            lsls = lsls.replace(".vto","");
            metodos_AdapterListaDownloads e = new metodos_AdapterListaDownloads(lsls, R.drawable.e1,true);
            videos.add(e);
        }
        return videos;
    }



    public void listaAdapter(ListView lista )
    {
        ArrayAdapter adapter = null;
        try {
            adapter = new AdapterListaDownloads(VideoDownload.this, addListaDeVideos());
        } catch (Exception e) {
            e.printStackTrace();
        }
        lista.setAdapter(adapter);
    }


}



