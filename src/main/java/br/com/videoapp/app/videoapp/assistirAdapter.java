package br.com.video_app.app.video_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class assistirAdapter extends ArrayAdapter<listassistir> {

    public int ss = 0;
    private final Context context;
    private final ArrayList<listassistir> elementos;
    private ImageLoader mImageLoader;
    private  String s = SessaoJs.getInstance().getCheckedId();
    assistirAdapter(Context contexts, ArrayList<listassistir> elementos) {
        super(contexts, R.layout.linhaassistir, elementos);
        this.context = contexts;
        this.elementos = elementos;
    }
    ConstraintLayout constraintLayout;
    ConstraintSet constraintSet;
    @NonNull
    public View getView(final int position, View convertView, ViewGroup parent) {
        ss = ss + 1;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView;
        rowView = inflater.inflate(R.layout.linhaassistir, parent, false);
        final TextView nomeCurso =  rowView.findViewById(R.id.nomeDuvida);
        ImageView imagem =  rowView.findViewById(R.id.imagem);
        final View vis =  rowView.findViewById(R.id.modum);
        final TextView endereco =  rowView.findViewById(R.id.endereco);
        nomeCurso.setText(elementos.get(position).getNome());
        imagem.setImageResource(elementos.get(position).getImagem());



        if(elementos.get(position).getVisi())
        {
            vis.setVisibility(View.VISIBLE);
            endereco.setVisibility(View.VISIBLE);
            endereco.setText(elementos.get(position).getNomeMod());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) nomeCurso.getLayoutParams();
            mlp.setMargins(50, 20, 0, 0);
            params.gravity = Gravity.RIGHT;
        }else{
            vis.setOnClickListener(null);
            constraintLayout =  rowView.findViewById(R.id.linear);
            constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(nomeCurso.getId(), ConstraintSet.TOP, ConstraintSet.VERTICAL, ConstraintSet.TOP, 10);
            constraintSet.constrainDefaultHeight(nomeCurso.getId(), 0);
            constraintSet.applyTo(constraintLayout);
            int paddingDp = 30;
            float density = context.getResources().getDisplayMetrics().density;
            int paddingPixel = (int)(paddingDp * density);
            nomeCurso.setPadding(paddingPixel,paddingPixel,0,0);
            endereco.setOnClickListener(null);
        }


        if (elementos.get(position).getMds()) {

            imagem.setImageResource(R.drawable.check);
        }
        return rowView;
    }

    public void carregaFot(final ImageView image)
    {




    }
}
