package br.com.video_app.app.video_app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CursoAdapter extends ArrayAdapter<curso> {
    public int ss= 0;
    private final Context context;
    private final ArrayList<curso> elementos;
    final Dialog[] dialog = new Dialog[1];

    private ImageLoader mImageLoader;
    public CursoAdapter(Context context, ArrayList<curso> elementos,  ImageLoader mImageLoader)  {

        super(context,R.layout.linha,  elementos);
        this.context=context;
        this.mImageLoader = mImageLoader;
        this.elementos=  elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {


        Context contexts = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha, parent, false);
        TextView nomeCurso = (TextView) rowView.findViewById(R.id.nomeDuvida);
        TextView progresso = (TextView) rowView.findViewById(R.id.endereco);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imagem);


        ProgressBar progressBar = rowView.findViewById(R.id.progressBar);
        nomeCurso.setText(elementos.get(position).getNome());
        progresso.setText(elementos.get(position).getEndereco()+ "%");


        String idUsuario = elementos.get(position). getIds();
        String idCurso = elementos.get(position).getidCurso();

        String i = elementos.get(position).getEndereco();
        i = i.replace(" ", "");

        String mag = elementos.get(position).getSis();
        progressBar.setProgress(Integer.parseInt(i));

        String url = "https://www.API_URL/img_curso.php/capas/"+mag+".png?size=100" ;

        mImageLoader.displayImage(url,imagem, null, new ImageLoadingListener() {
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
        try {
            progressoS(idUsuario, idCurso, progresso, progressBar);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rowView;

    }


    public void progressoS(final String idDoUsusario, final String idDoCurso, final TextView prog, final ProgressBar progressBar) throws InterruptedException {

        Thread t =new Thread(new Runnable() {
            public void run() {
                String progresso = "0";
                InputStream input = null;
                try {
                    input = new URL("https://www.API_URL/apiapp/view/progressStatus.php?ID_USUARIO=" + idDoUsusario + "&ID_CURSO=" + idDoCurso).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Reader reader = null;
                try {
                    reader = new InputStreamReader(input, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Scanner s = new Scanner(reader);
                String json = s.nextLine();
                String[] array = json.split("/");
                progresso = array[0];
                final String finalProgresso = progresso;

                prog.post(new Runnable() {
                    public void run() {
                        prog.setText(finalProgresso+"%");
                        String i = finalProgresso;
                        i = i.replace(" ", "");
                        progressBar.setProgress(Integer.parseInt(i));
                    }
                });

            }
        });

        t.start();
    }

}
