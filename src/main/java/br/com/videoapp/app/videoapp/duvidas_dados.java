package br.com.video_app.app.video_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Scanner;

public class duvidas_dados extends AppCompatActivity {

    public ImageLoader mImageLoader;
    private String js_id = SessaoJs.getInstance().getidjs();
    private String idCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvidas_dados);
        Intent it = getIntent();
        idCurso = it.getStringExtra("id_curso");


        // ----------------  DUVIDA USUARIO ----------------------
        ImageView image = findViewById(R.id.fotoUserDuvidaDados);
        TextView nome = findViewById(R.id.textView6);
        TextView mensagem = findViewById(R.id.textView29);
        filtroS frt = new filtroS();
        nome.setText(getString("pergunta_nome"));
        mensagem.setText(frt.frt(getString("pergunta")));
        carregaFot(image, getString("pergunta_foto"));

        // ----------------  RESPOSTA TUTOR  ----------------------
        TextView nomeTutor = findViewById(R.id.textView30);
        TextView mensagemTutor = findViewById(R.id.textView31);
        ImageView imageTutor = findViewById(R.id.fotoTutor);
        VideoView videoView2 = findViewById(R.id.videoView2);
        nomeTutor.setText(getString("nome_responde"));
        carregaFot(imageTutor, getString("foto_responde"));

        if(getString("resposta").substring(0, 5).equals("https"))
        {
            try{
                mensagemTutor.setVisibility(View.INVISIBLE);
                videoView2.setVideoURI(Uri.parse(getString("resposta")));
                videoView2.setMediaController(new MediaController(this));
                videoView2.requestFocus();
                videoView2.start();
            }
            catch (Exception e)
            {
                Log.d("", e.toString());
            }

        }else
        {
            videoView2.setVisibility(View.INVISIBLE);
            mensagemTutor.setText(frt.frt(getString("mensagem")));
        }
    }


    private String getString(String tipo){

        try {
            InputStream input = new URL("https://www.API_URL/apiapp/view/duvidas_allGet.php?id_curso=" + idCurso + "&id_licao=" + js_id+ "&tipo=" + tipo).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String json;
            json = s.nextLine();
            return json;
        } catch (Exception e) {
            return "Campo Vazio !!";
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


        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(duvidas_dados.this)
                .defaultDisplayImageOptions(mDisplayImageOptions)
                .memoryCacheSize(5*1024*1024)
                .diskCacheSize(5*1024*1024)
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


