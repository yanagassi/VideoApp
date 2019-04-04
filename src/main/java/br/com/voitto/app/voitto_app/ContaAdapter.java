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
import android.widget.Switch;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class ContaAdapter extends ArrayAdapter<ContaAdapterMetodos> {

    private final Context context;
    private final ArrayList<ContaAdapterMetodos> elementos;

    private ImageLoader mImageLoader;
    public ContaAdapter(Context context, ArrayList<ContaAdapterMetodos> elementos)  {
        super(context,R.layout.linha_conta,  elementos);
        this.context=context;
        this.elementos= elementos;
    }
    ConstraintLayout constraintLayout;
    ConstraintSet constraintSet;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.linha_conta, parent, false);
        final TextView nomeItem = rowView.findViewById(R.id.nome_contas);
        TextView SubItem =  rowView.findViewById(R.id.subItem);
        final ImageView icon = rowView.findViewById(R.id.iconConta);
        Switch switchs = rowView.findViewById(R.id.switchConta);

        if(elementos.get(position).getswitchS() == 0)
        {
            switchs.setVisibility(View.INVISIBLE);
            int paddingDp = 30;
            float density = context.getResources().getDisplayMetrics().density;
            int paddingPixel = (int)(paddingDp * density);
            nomeItem.setPadding(10,0,0,20);
        }

        if((elementos.get(position).getsubTitulo()).equals("")) {
           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

           SubItem.setVisibility(View.INVISIBLE);
            constraintLayout =  rowView.findViewById(R.id.linearConta);

            constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);

            constraintSet.connect(nomeItem.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            constraintSet.constrainDefaultHeight(nomeItem.getId(), 0);
            constraintSet.applyTo(constraintLayout);

            int paddingDp = 30;
            float density = context.getResources().getDisplayMetrics().density;
            int paddingPixel = (int)(paddingDp * density);
                nomeItem.setPadding(10,20,0,10);


           rowView.setLayoutParams(params);
        }

        else{

            SubItem.setText(elementos.get(position).getsubTitulo());
            nomeItem.setPadding(0,10,0,20);
        }



      //  icon.setImageResource(elementos.get(position).getImageSK());
        carregaFot( icon, "drawable://" + elementos.get(position).getImageSK());
        nomeItem.setText(elementos.get(position).getNome());
        return rowView;
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


        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(context)
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
