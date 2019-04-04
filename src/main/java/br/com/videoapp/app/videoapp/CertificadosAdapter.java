package br.com.video_app.app.video_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static br.com.video_app.app.video_app.R.layout.line_certificados;

public class CertificadosAdapter extends ArrayAdapter<CertificadosAdapter_Metodos> {

    private final Context context;
    private final ArrayList<CertificadosAdapter_Metodos> elementos;
    private ImageLoader mImageLoader;

    public CertificadosAdapter(Context context,ArrayList<CertificadosAdapter_Metodos>  elementos,ImageLoader mImageLoader)  {

        super(context, R.layout.line_certificados,  elementos);
        this.context=context;
        this.elementos= elementos;
        this.mImageLoader = mImageLoader;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.line_certificados, parent, false);
        TextView text = rowView.findViewById(R.id.TituloCertificado);
        ImageView image = rowView.findViewById(R.id.FotoCertificado);
        String link = elementos.get(position).getfoto();
        link = "https://www.API_URL/img_curso.php/capas/"+link +".png?size=100";
        text.setText(elementos.get(position).gettitulo());
        mImageLoader.displayImage(link, image, null, new ImageLoadingListener() {
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
