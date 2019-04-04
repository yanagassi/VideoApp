package br.com.video_app.app.video_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class DownloadsAction_Adapter extends ArrayAdapter {

   /// keytool -genkey -v -keystore minha-chave.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
    private final Context context;
    private final ArrayList<DonwloadsAction_Adapter_Metodis> elementos;

    public DownloadsAction_Adapter(Context context, ArrayList<DonwloadsAction_Adapter_Metodis> elementos)  {

        super(context, R.layout.linha_doloadsact,  elementos);
        this.context=context;
        this.elementos= elementos;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_doloadsact, parent, false);
        TextView text = rowView.findViewById(R.id.nomeDaLicaoDownload);
        text.setText(elementos.get(position).getNomeLIcao());
        final RadioButton radio = rowView.findViewById(R.id.radioButton);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(radio.isChecked())
               {
                   radio.setChecked(false);
                   try {
                      remove(getContext(), elementos.get(position).getLinkLicoa());
                   } catch (IOException e) { }
               }
               else{
                   radio.setChecked(true);
                   try {
                     addJson(getContext(),elementos.get(position).getLinkLicoa());
                   } catch (IOException e) {
                   }
               }


            }
        });
        return rowView;
    }



    private String addJson(Context context, String valor) throws IOException {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("linksParaDownload");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            //Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            //Log.e("login activity", "Can not read file: " + e.toString());
        }

        ret = ret+ valor+";";

        OutputStreamWriter ss = new OutputStreamWriter(context.openFileOutput("linksParaDownload", Context.MODE_PRIVATE));
        ss.write(ret);
        ss.close();
        return ret;

    }


    private String remove(Context context, String valor) throws IOException {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("linksParaDownload");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) { } catch (IOException e) { }

        String ac = ret.replace(valor,"");
        OutputStreamWriter ss = new OutputStreamWriter(context.openFileOutput("linksParaDownload", Context.MODE_PRIVATE));
        ss.write(ac);
        ss.close();
        return ac;

    }
}
