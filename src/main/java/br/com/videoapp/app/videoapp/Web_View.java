package br.com.video_app.app.video_app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Web_View extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web__view);



        Intent it = getIntent();
        String url = it.getStringExtra("URL");
        WebView view= this.findViewById(R.id.webview_des);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }
}
