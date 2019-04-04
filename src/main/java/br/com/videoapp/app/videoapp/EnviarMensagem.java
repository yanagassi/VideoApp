package br.com.video_app.app.video_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Scanner;

public class EnviarMensagem extends AppCompatActivity {

    private ImageLoader mImageLoader;
    private String js_id =  SessaoJs.getInstance().getidjs();
    private String idCurso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_mensagem);
        Intent it = getIntent();
        String textoMsg = it.getStringExtra("textoMsg");
        idCurso = it.getStringExtra("idCurso");
        String retorno = getString();

        final ImageView tut1= findViewById(R.id.fotoTutors2);
        final ImageView tut2= findViewById(R.id.fotoTutors3);
        final ImageView tut3= findViewById(R.id.fotoTutors4);

        final TextView text1 = findViewById(R.id.textView33);
        final TextView text2 = findViewById(R.id.textView36);
        final TextView text3 = findViewById(R.id.textView37);
        EditText mensagemParaTutor = findViewById(R.id.mensagemParaTutor);

        mensagemParaTutor.setText(textoMsg);

        //----------- Carregando Tutores ------------------//

        String [] array = retorno.split("</exp>");
        String [] dados = array[0].split(";");
        text1.setText(dados[0]);
        carregaFot(tut1, dados[2]);
        final String tutor1 = dados[0];

        dados = array[1].split(";");
        text2.setText(dados[0]);
        carregaFot(tut2, dados[2]);
        final String tutor2 = dados[0];

        dados = array[2].split(";");
        text3.setText(dados[0]);
        carregaFot(tut3, dados[2]);
        final String tutor3 = dados[0];

        //------------------------------------------------//


        final TextView textView39 = findViewById( R.id.textView39);
        tut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text1.setTypeface(null, Typeface.BOLD);
                text2.setTypeface(null, Typeface.NORMAL);
                text3.setTypeface(null, Typeface.NORMAL);

                text1.setTextColor(Color.BLACK);
                text2.setTextColor(Color.DKGRAY);
                text3.setTextColor(Color.DKGRAY);
                textView39.setText("Tutores: "+ tutor1);
            }
        });
        tut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text2.setTypeface(null, Typeface.BOLD);
                text1.setTypeface(null, Typeface.NORMAL);
                text3.setTypeface(null, Typeface.NORMAL);

                text2.setTextColor(Color.BLACK);
                text1.setTextColor(Color.DKGRAY);
                text3.setTextColor(Color.DKGRAY);

                textView39.setText("Tutores: "+ tutor2);
            }
        });
        tut3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text3.setTypeface(null, Typeface.BOLD);
                text1.setTypeface(null, Typeface.NORMAL);
                text2.setTypeface(null, Typeface.NORMAL);

                text3.setTextColor(Color.BLACK);
                text1.setTextColor(Color.DKGRAY);
                text2.setTextColor(Color.DKGRAY);

                text3.setTextColor(Color.BLACK);
                textView39.setText("Tutores: "+ tutor3);
            }
        });




        View view21 = findViewById(R.id.view21);
        view21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EnviarMensagem.this, "Função não disponível !", Toast.LENGTH_SHORT).show();
            }
        });

        View view19 = findViewById(R.id.view19);
        view19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EnviarMensagem.this, "Função não disponível !", Toast.LENGTH_SHORT).show();
            }
        });


        Button button3 = findViewById(R.id.button3);
        final String[] mensagem = new String[1];
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /// mensagem[0] = mensagemParaTutor.getText().toString(); idCurso;js_id;
                Toast.makeText(EnviarMensagem.this, "Funcao nao disponivel !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  String  getString()
    {
        try{
            String out;
            InputStream input = new URL("https://www.API_URL/apiapp/view/getTutores.php?id_curso="+idCurso).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            out = s.nextLine();
            return out;
        }catch (Exception e)
        {
            return  "";
        }

    }

    public void carregaFot(final ImageView image, final String link)
    {

        DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.e1)
                .showImageOnFail(R.drawable.e1)
                .showImageOnLoading(R.drawable.e1)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();


        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(EnviarMensagem.this)
                .defaultDisplayImageOptions(mDisplayImageOptions)
                .memoryCacheSize(10*1024*1024)
                .diskCacheSize(10*1024*1024)
                .threadPoolSize(3)
                .writeDebugLogs()
                .build();

        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(conf);

        mImageLoader.displayImage(link, image, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String uri, View view) {
                Log.d("", "onLoadingStarted");
            }

            @Override
            public void onLoadingFailed(String uri, View view, FailReason fail) {
                Log.d("", "onLoadingFailed: " + fail);
            }

            @Override
            public void onLoadingComplete(String uri, View view, Bitmap bitmap) {
                Log.d("", "onLoadingComplete");
            }

            @Override
            public void onLoadingCancelled(String uri, View view) {
                Log.d("", "onLoadingCancelled");
            }
        });
    }


}
