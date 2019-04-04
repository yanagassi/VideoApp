package br.com.video_app.app.video_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Sobre_video_appApp extends AppCompatActivity {

    private int i =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre__video_app);

        ImageView imageView9 = findViewById(R.id.imageView9);
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = i+ 1;
                if(i == 10000)
                {
                    Toast.makeText(Sobre_video_appApp.this, "Ai meu dedo !!!", Toast.LENGTH_SHORT).show();
                    i=0;
                }
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(Sobre_video_appApp.this, Conta.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
