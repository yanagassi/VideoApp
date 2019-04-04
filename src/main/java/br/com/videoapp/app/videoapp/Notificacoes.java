package br.com.video_app.app.video_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Notificacoes extends AppCompatActivity {


    private FirebaseAnalytics mFirebaseAnalytics;
    private ImageLoader mImageLoader;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);
        try{
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            mFirebaseAnalytics.setCurrentScreen(this, "Notificações", null /* class override */);
            BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav_view);
            bottomNavigationView.getMenu().findItem(R.id.nav_not).setChecked(true);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    item.setChecked(true);
                    switch (item.getItemId()) {
                        case R.id.nav_cursos:
                            Intent intent = new Intent(Notificacoes.this, usuario.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            break;
                        case R.id.nav_contas:
                            Intent it = new Intent(Notificacoes.this, Conta.class);
                            startActivity(it);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;
                        case R.id.nav_destaque:
                            Intent itsss = new Intent(Notificacoes.this, Destaque.class);
                            startActivity(itsss);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            break;
                        case R.id.nav_not:
                            break;
                        case R.id.nav_rank:
                            Intent itss = new Intent(Notificacoes.this, Ranking_video_app.class);
                            startActivity(itss);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            break;
                    }
                    return false;
                }
            });


            DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.e1)
                    .showImageOnFail(R.drawable.e1)
                    .showImageOnLoading(R.drawable.e1)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();


            ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(Notificacoes.this)
                    .defaultDisplayImageOptions(mDisplayImageOptions)
                    .memoryCacheSize(50*1024*1024)
                    .diskCacheSize(50*1024*1024)
                    .threadPoolSize(5)
                    .writeDebugLogs()
                    .build();

            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(conf);

            ListView lva = findViewById(R.id.listViewNotificacoes);
            ArrayList<NotificacoesAdapter_Metodos> item = new ArrayList<>();
            String noti =  Sessao.getInstance().getnotificacao();
            String [] arrayListNoti = noti.split(",");
            String[] dados = new String [12];
            NotificacoesAdapter_Metodos es;
            int i;
            for(i=0;i<10;i++)
            {
                dados = arrayListNoti[i].split(";");
                es = new NotificacoesAdapter_Metodos(dados[0],"","",mImageLoader);
                item.add(es);
            }

            ArrayAdapter adapter = new NotificacoesAdapter(Notificacoes.this, item,mImageLoader);
            lva.setAdapter(adapter);

            lva.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        String noti =  Sessao.getInstance().getnotificacao();
                        String [] arrayListNoti = noti.split(",");
                        String[] dados = arrayListNoti[position].split(";");
                        if(dados[3].equals("link"))
                        {

                            String url = (dados[4]);
                            Intent intent = new Intent(Notificacoes.this, Web_View.class);
                            intent.putExtra("URL", url);
                            startActivity(intent);
                        }
                        else if(dados[3].equals("blog"))
                        {
                            String url = ("https://www.API_URL/blog/artigo/"+ dados[4]);
                            Intent intent = new Intent(Notificacoes.this, Web_View.class);
                            intent.putExtra("URL", url);
                            startActivity(intent);
                        }
                        else if (dados[3].equals("tickets"))
                        {
                            String url = (" https://www.API_URL/online/tickets/"+ dados[4]);
                            Intent intent = new Intent(Notificacoes.this, Web_View.class);
                            intent.putExtra("URL", url);
                            startActivity(intent);
                        }
                    }catch (Exception e){ }

                }
            });
        }catch (Exception e)
        {
            Intent sair =  new Intent(Notificacoes.this, Main.class);
            startActivity(sair);
        }
    }




    public ArrayList<NotificacoesAdapter_Metodos> addlista(ImageLoader mImageLoader){
        ArrayList<NotificacoesAdapter_Metodos> item = new ArrayList<>();
        try{
            String noti =  Sessao.getInstance().getnotificacao();
            String [] arrayListNoti = noti.split(",");
            String[] dados = new String [12];
            NotificacoesAdapter_Metodos es;
            int i;
            for(i=0;i<10;i++)
            {
                dados = arrayListNoti[i].split(";");
                es = new NotificacoesAdapter_Metodos(dados[0],"","",mImageLoader);
                item.add(es);
            }
        }catch (Exception e)
        {
            Intent sair =  new Intent(Notificacoes.this, Main.class);
            startActivity(sair);
        }
        return item;
    }



}
