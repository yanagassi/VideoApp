package br.com.video_app.app.video_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class NotificacoesAdapter extends ArrayAdapter<NotificacoesAdapter_Metodos> {
    private final Context context;
    private final ArrayList<NotificacoesAdapter_Metodos> elementos;
    private  int i =0;
    private ImageLoader mImageLoader;

    public NotificacoesAdapter(Context context,ArrayList<NotificacoesAdapter_Metodos>  elementos, ImageLoader mImageLoader )  {

        super(context,R.layout.linha_downloads,  elementos);
        this.context=context;
        this.elementos= elementos;
        this.mImageLoader = mImageLoader;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.line_notifi, parent, false);

            TextView titulo = rowView.findViewById(R.id.title_notifi);
            TextView subTitulo = rowView.findViewById(R.id.subTitulo);
            titulo.setText(elementos.get(position).gettitulo());
            String noti = Sessao.getInstance().getnotificacao();
            String[] arrayListNoti = noti.split(",");
            String[] dados;
            ImageView ima = rowView.findViewById(R.id.imageNotificacao);
            dados = arrayListNoti[position].split(";");
            subTitulo.setText(dados[1].toString());

            mImageLoader.displayImage(dados[2],ima, null, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String uri, View view) {
                    Log.d("", "onLoadingStarted");
                }
                @Override
                public void onLoadingFailed(String uri, View view, FailReason fail) {
                    Log.d("", "onLoadingFailed: " +fail);
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

        return rowView;
    }
}

