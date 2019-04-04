package br.com.video_app.app.video_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class Ranking_video_app extends AppCompatActivity {
    private ImageLoader mImageLoaderee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking__video_app);
        try{

            DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageForEmptyUri(R.drawable.com_facebook_profile_picture_blank_square)
                    .showImageOnFail(R.drawable.com_facebook_profile_picture_blank_square)
                    .showImageOnLoading(R.drawable.com_facebook_profile_picture_blank_square)
                    .build();


            ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(Ranking_video_app.this)
                    .defaultDisplayImageOptions(mDisplayImageOptions)
                    .memoryCacheSize(50*1024*1024)
                    .diskCacheSize(50*1024*1024)
                    .threadPoolSize(5)
                    .writeDebugLogs()
                    .build();

            mImageLoaderee = ImageLoader.getInstance();
            mImageLoaderee.init(conf);

            ListView lv = findViewById(R.id.listviewRank);
            ListaAdapter(lv);



            BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav_view);
            bottomNavigationView.getMenu().findItem(R.id.nav_rank).setChecked(true);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    item.setChecked(true);

                    switch (item.getItemId()) {
                        case R.id.nav_cursos:
                            Intent intent = new Intent(Ranking_video_app.this, usuario.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;
                        case R.id.nav_contas:
                            Intent it = new Intent(Ranking_video_app.this, Conta.class);
                            startActivity(it);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;
                        case R.id.nav_destaque:
                            Intent itss = new Intent(Ranking_video_app.this, Destaque.class);
                            startActivity(itss);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            break;
                        case R.id.nav_not:
                            Intent its = new Intent(Ranking_video_app.this, Notificacoes.class);
                            startActivity(its);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;
                        case R.id.nav_rank:
                            break;
                    }
                    return false;

                }
            });

            filtro ft = new filtro();
            TextView nomeRankUser = findViewById(R.id.nomeRankUser);
            String[] nome = Sessao.getInstance().getnomeUsuario().split(" ");
            nomeRankUser.setText(ft.filtro(Sessao.getInstance().getColocacao() + "ᵒ -" + nome[0] + " "+ nome[1]) );
            TextView video_appcoinsRankUser = findViewById(R.id.video_appcoinsRankUser);
            video_appcoinsRankUser.setText(Sessao.getInstance().getacumulado() + " vt$");
            ImageView images = findViewById(R.id.FotoRankUser);
            if(!(Sessao.getInstance().getFoto().equals("0")) && !(Sessao.getInstance().getFoto().equals(""))) {
                adicionarFotos(images, Sessao.getInstance().getFoto());
            }
        }catch (Exception e){

        }

    }


    public void ListaAdapter(ListView lista) {
        ArrayAdapter adapter = null;
        try {
            adapter = new RankingAdapter(this, sss(), mImageLoaderee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lista.setAdapter(adapter);

    }


    private ArrayList<RankingMetodosAdapter> sss() throws Exception {
        ArrayList<RankingMetodosAdapter> sis = new ArrayList<>();

        String pessoa = Sessao.getInstance().getDadosRank();
        String [] arrayDados;
        String [] arrayPessoa = pessoa.split(",");
        RankingMetodosAdapter e;
        for(int i =1; i<arrayPessoa.length;i++)
        {
            arrayDados = arrayPessoa[i].split("-");
            e = new RankingMetodosAdapter(arrayDados[0], i+" ",arrayDados[4],arrayDados[2] + " - "+arrayDados[3], arrayDados[1]);
            sis.add(e);
        }
        return sis;
    }

    private void adicionarFotos(final ImageView image, final String link)
    {

        mImageLoaderee.displayImage(link, image, null, new ImageLoadingListener() {
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
