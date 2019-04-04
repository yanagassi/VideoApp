package br.com.video_app.app.video_app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ItensOffiline extends AppCompatActivity {

    public String pasta ="";
    public String [] arquivo = new String[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_offiline);
        ListView listview = findViewById(R.id.listview);
        Intent it = getIntent();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        pasta = it.getStringExtra("diretorio");
        listaAdapter(listview);
        Toast.makeText(this, leituraDeJson(this), Toast.LENGTH_SHORT).show();



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String root_sd = Environment.getExternalStorageDirectory().toString();
                String nome = pasta + "/"+ arquivo[position] ;
                File file = new File(nome);
                Intent intent = new Intent(ItensOffiline.this, AssistirOffilne.class);
                intent.putExtra("dir",nome );
                Toast.makeText(ItensOffiline.this, nome, Toast.LENGTH_SHORT).show();
                 startActivity(intent);
            }
        });

    }


    private String leituraDeJson(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("cursos-offline");

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
    public void listaAdapter(ListView lista )
    {
        ArrayAdapter adapter = null;
        try {
            adapter = new AdapterListaDownloads(ItensOffiline.this, addListaDeVideos2(pasta));
        } catch (Exception e) {
            e.printStackTrace();
        }
        lista.setAdapter(adapter);
    }


    private ArrayList<metodos_AdapterListaDownloads> addListaDeVideos2(String pasta) throws Exception {

        ArrayList<metodos_AdapterListaDownloads> videos = new ArrayList<metodos_AdapterListaDownloads>();
        File file;
        String lsls;
        String root_sd = Environment.getExternalStorageDirectory().toString();
        file = new File( root_sd + "/Android/data/br.com.video_app.app.video_app/files/Download/"+pasta) ;
        File list[] = file.listFiles();

        for(int s = 0; s < list.length; s++)
        {
            lsls =list[s].getName();
            arquivo[s] = list[s].getName();
            lsls = lsls.replace(".vto","");
            metodos_AdapterListaDownloads e = new metodos_AdapterListaDownloads(lsls, R.drawable.e1, false);
            if(!lsls.equals("imagem.png"))
            {
                videos.add(e);
            }
        }
        return videos;
    }
}
