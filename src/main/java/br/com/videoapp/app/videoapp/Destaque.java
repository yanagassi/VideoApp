package br.com.video_app.app.video_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Destaque extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destaque);

        try{
            final WebView view= findViewById(R.id.DestaqueWeb);
            view.getSettings().setJavaScriptEnabled(true);

            final Handler MHandler = new Handler()
            {
                public void handleMessage(Message msg)
                {
                    try{
                        ProgressDialog dialogs = ProgressDialog.show(Destaque.this, "", "Carregando  ...", true);
                    }catch (Exception e){ }
                }
            };


            view.loadUrl("https://www.API_URL/App/");

            view.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    TextView textView42 = findViewById(R.id.textView42);
                    textView42.setVisibility(View.INVISIBLE);
                }
            });



            BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav_view);
            bottomNavigationView.getMenu().findItem(R.id.nav_destaque).setChecked(true);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    item.setChecked(true);
                    switch (item.getItemId()) {
                        case R.id.nav_cursos:
                            Intent intent = new Intent(Destaque.this, usuario.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;
                        case R.id.nav_contas:
                            Intent it = new Intent(Destaque.this, Conta.class);
                            startActivity(it);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;
                        case R.id.nav_destaque:
                            break;
                        case R.id.nav_not:
                            Intent its = new Intent(Destaque.this, Notificacoes.class);
                            startActivity(its);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;
                        case R.id.nav_rank:
                            Intent itsx = new Intent(Destaque.this, Ranking_video_app.class);
                            startActivity(itsx);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;
                    }
                    return false;
                }
            });
            MHandler.removeMessages(0);

        }catch (Exception e)
        {
            Intent sair =  new Intent(Destaque.this, Main.class);
            startActivity(sair);
        }


    }




}
