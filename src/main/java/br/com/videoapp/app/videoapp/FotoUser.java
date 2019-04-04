package br.com.video_app.app.video_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class FotoUser extends AppCompatActivity {

    private ImageLoader mImageLoader;
    boolean a = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_user);

        ImageView image = findViewById(R.id.ImagemUser_perfil);
        this.carregaFot(image,Sessao.getInstance().getFoto());

        final ConstraintLayout cont = findViewById(R.id.viewAll_fotoc);
        final TextView texto = findViewById(R.id.FotoPerfil_User);
        final View topoView = findViewById(R.id.topoView);
        cont.setBackgroundColor(Color.DKGRAY);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(a)
                {
                    topoView.setVisibility(View.VISIBLE);
                    texto.setVisibility(View.VISIBLE);
                    cont.setBackgroundColor(Color.DKGRAY);
                    a=false;
                }else{
                    topoView.setVisibility(View.INVISIBLE);
                    texto.setVisibility(View.INVISIBLE);
                    cont.setBackgroundColor(Color.BLACK);


                    a=true;
                }

                return false;
            }
        });



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


        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(FotoUser.this)
                .defaultDisplayImageOptions(mDisplayImageOptions)
                .memoryCacheSize(50*1024*1024)
                .diskCacheSize(50*1024*1024)
                .threadPoolSize(5)
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