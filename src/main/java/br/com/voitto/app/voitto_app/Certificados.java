package br.com.video_app.app.video_app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Freezable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Certificados extends AppCompatActivity {
    private  String texto;
    private ImageLoader mImageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificados);
        final ListView listView = findViewById(R.id.ListViewCertificados);


        DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.e1)
                .showImageOnFail(R.drawable.e1)
                .showImageOnLoading(R.drawable.e1)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();


        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(Certificados.this)
                .defaultDisplayImageOptions(mDisplayImageOptions)
                .memoryCacheSize(5*1024*1024)
                .diskCacheSize(5*1024*1024)
                .threadPoolSize(2)
                .writeDebugLogs()
                .build();

        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(conf);

        ListaAdapter(listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String [] arraydados = texto.split("/");
                String [] arrayId = arraydados[position].split(";");
                String idCurso = arrayId[2];
                String idUsuarip = Sessao.getInstance().getUsuario();
                String link ="https://API_URL/apiapp/view/baixacertificado.php?id_usuario="+idUsuarip+"&id_curso="+idCurso;
                Uri uri = Uri.parse(link);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }




    public void ListaAdapter(ListView lista) {
        ArrayAdapter adapter = null;
        try {
            adapter = new CertificadosAdapter(Certificados.this, addlista(), mImageLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lista.setAdapter(adapter);

    }

    public void onBackPressed(){
        Intent intent = new Intent(Certificados.this, Conta.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public ArrayList<CertificadosAdapter_Metodos> addlista(){
        final ArrayList<CertificadosAdapter_Metodos> item = new ArrayList<>();
        try{
            CertificadosAdapter_Metodos es;
            String id = Sessao.getInstance().getUsuario();
            texto = new Scanner(new URL("https://www.API_URL/apiapp/view/listacertificados.php?id_usuario="+id).openStream(), "UTF-8").useDelimiter("\\A").next();
            String [] arrayCurso;
            String [] array = texto.split("/");
            for(int i =0; i< array.length; i++)
            {
                arrayCurso = array[i].split(";");
                es = new CertificadosAdapter_Metodos(arrayCurso[0], arrayCurso[1]);
                item.add(es);
            }

        }catch (Exception e){
            Toast.makeText(this, "Erro ao carregar certificados!", Toast.LENGTH_SHORT).show();
        }

        return item;
    }

}
