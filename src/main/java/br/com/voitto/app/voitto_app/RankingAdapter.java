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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class RankingAdapter extends ArrayAdapter<RankingMetodosAdapter> {

    private final Context context;
    private final ArrayList<RankingMetodosAdapter> elementos;
    private ImageLoader mImageLoaderee;

    public RankingAdapter(Context contexts, ArrayList<RankingMetodosAdapter> elementos, ImageLoader mImageLoaderee ) {
        super(contexts, R.layout.linha_rank, elementos);
        this.context = contexts;
        this.elementos = elementos;
        this.mImageLoaderee = mImageLoaderee;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_rank, parent, false);

        final ImageView images = rowView.findViewById(R.id.FotoRank);
        TextView nomeRank = rowView.findViewById(R.id.nomeRankUser);
        TextView video_appcoins = rowView.findViewById(R.id.video_appcoinsRank);
        TextView cidaderank = rowView.findViewById(R.id.cidaderank);
        nomeRank.setText(elementos.get(position).getposicao() + "ᵒ - " +elementos.get(position).getNomeD());

        video_appcoins.setText(elementos.get(position).getvideo_appcoins() + " vt$ -  ");
        cidaderank.setText(elementos.get(position).getcidade());

        String link = elementos.get(position).getLinkImg().toString();


        mImageLoaderee.displayImage(link, images, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String uri, View view) {
                Log.d("", "onLoadingStarted");
                images.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
            }

            @Override
            public void onLoadingFailed(String uri, View view, FailReason fail) {
                images.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                Log.d("", "onLoadingFailed: " + fail);
            }

            @Override
            public void onLoadingComplete(String uri, View view, Bitmap bitmap) {
                Log.d("", "onLoadingComplete");

            }

            @Override
            public void onLoadingCancelled(String uri, View view) {
                Log.d("", "onLoadingCancelled");
                images.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
            }

        });
        images.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
        return rowView;

    }
}