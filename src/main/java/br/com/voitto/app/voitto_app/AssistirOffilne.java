package br.com.video_app.app.video_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class AssistirOffilne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistir_offilne);
        Intent it = getIntent();
        String dir = it.getStringExtra("dir");
        VideoView videoview =  findViewById(R.id.videoView);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/Android/data/br.com.video_app.app.video_app/files/Download/"+dir);
        videoview.setVideoURI(uri);
        videoview.setMediaController(new MediaController(this));
        videoview.start();
    }
    public void onBackPressed(){
        Intent intent = new Intent(AssistirOffilne.this, usuario.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
