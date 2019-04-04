package br.com.video_app.app.video_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class AdapterListaDownloads extends ArrayAdapter<metodos_AdapterListaDownloads> {

    public int ss= 0;
    private final Context context;
    private final ArrayList<metodos_AdapterListaDownloads> elementos;

    public AdapterListaDownloads(Context context,ArrayList<metodos_AdapterListaDownloads>  elementos)  {

        super(context,R.layout.linha_downloads,  elementos);
        this.context=context;
        this.elementos= elementos;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Context contexts = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_downloads, parent, false);
        TextView nomeCurso =  rowView.findViewById(R.id.nomeDuvida);
        ImageView imagem =  rowView.findViewById(R.id.imagem);
        ImageView del = rowView.findViewById(R.id.apagaLinhaSS);
        if(!elementos.get(position).getdel())
        {
            del.setVisibility(rowView.INVISIBLE);
        }
        File path = android.os.Environment.getExternalStorageDirectory();
        nomeCurso.setText(elementos.get(position).getNome());
        imagem.setImageResource(elementos.get(position).getImagem());

        String root_sd = Environment.getExternalStorageDirectory().toString();
        Bitmap bm = BitmapFactory.decodeFile(root_sd + "/Android/data/br.com.video_app.app.video_app/files/Download"+"/"+elementos.get(position).getNome()+"/imagem.png");
        imagem.setImageBitmap(bm);
        return rowView;
    }

}
