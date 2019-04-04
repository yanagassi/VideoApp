package br.com.video_app.app.video_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class sobre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        String url = "https://www.API_URL/apiapp/view/sobre.php";
        WebView view= this.findViewById(R.id.webSobre);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void onBackPressed(){
        Intent intent = new Intent(sobre.this, Conta.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }



}
