package br.com.video_app.app.video_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class duvidasAdapter extends ArrayAdapter<duvidasAdapter_Metodos> {

    private final Context context;
    private final ArrayList<duvidasAdapter_Metodos> elementos;
    private ImageLoader mImageLoader;

    public duvidasAdapter(Context context, ArrayList<duvidasAdapter_Metodos> elementos, ImageLoader mImageLoader)  {

        super(context,R.layout.line_duvida,  elementos);
        this.context=context;
        this.elementos= elementos;
        this.mImageLoader =mImageLoader;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.line_duvida, parent, false);

        TextView nome = rowView.findViewById(R.id.nomeDuvida);
        TextView menssage = rowView.findViewById(R.id.menssgeDuvida);
        ImageView image = rowView.findViewById(R.id.fotoUserDuvida);

        nome.setText(elementos.get(position).getnome());
        menssage.setText(elementos.get(position).getMensage());
        mImageLoader.displayImage(elementos.get(position).getFoto(),image, null, new ImageLoadingListener() {
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



        TextView nomeTutor = rowView.findViewById(R.id.nomeTutor);
        TextView menssageTutor = rowView.findViewById(R.id.tutorMensagem);
        ImageView imageTutor = rowView.findViewById(R.id.FotoTutor);

        if(!(elementos.get(position).getNomeTutor().equals("")))
        {
            nomeTutor.setText(elementos.get(position).getNomeTutor());
            menssageTutor.setText(elementos.get(position).getMensagemTutor());
            mImageLoader.displayImage(elementos.get(position).getFotoTuto(),imageTutor, null, new ImageLoadingListener() {
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


        }else{
            nomeTutor.setVisibility(View.INVISIBLE);
            menssageTutor.setVisibility(View.INVISIBLE);
            imageTutor.setVisibility(View.INVISIBLE);
        }
        return rowView;

    }
}
