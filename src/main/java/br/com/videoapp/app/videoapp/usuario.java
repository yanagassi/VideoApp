package br.com.video_app.app.video_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import static br.com.video_app.app.video_app.R.*;

public class usuario extends AppCompatActivity {

    public String[] idCourses = new String[1000];
    public String DataUser;
    private FirebaseAnalytics mFirebaseAnalytics;
    public String ids = Sessao.getInstance().getUsuario();
    private ImageLoader mImageLoader;


    public void onBackPressed(){
    }

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_usuario);
        //aletass();
        aovivo();
        DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.e1)
                .showImageOnFail(R.drawable.e1)
                .showImageOnLoading(R.drawable.e1)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(usuario.this)
                .defaultDisplayImageOptions(mDisplayImageOptions)
                .memoryCacheSize(50*1024*1024)
                .diskCacheSize(50*1024*1024)
                .threadPoolSize(5)
                .writeDebugLogs()
                .build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(conf);



        try{
            seguranca seguranca = new seguranca();
            seguranca.verificacao(this);
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            mFirebaseAnalytics.setCurrentScreen(this, "Lista de Cursos", null /* class override */);
            DataUser = leituraDeJson(this);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            final ListView lista = findViewById(R.id.lvCursos);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, final int position, final long id) {
                    final Dialog[] dialogs = new Dialog[1];
                    final Handler MHandler = new Handler()
                    {
                        public void handleMessage(Message msg)
                        {
                            dialogs[0] = ProgressDialog.show(usuario.this, "", "Carregando...", true);
                        }
                    };

                    new Thread(new Runnable() {
                        public void run() {
                            String out = null;
                            MHandler.sendEmptyMessage(0);
                            boolean a = true;

                            try {
                                Intent it = new Intent(usuario.this, assistir.class);
                                TextView texto =  view.findViewById(R.id.nomeDuvida);
                                it.putExtra("position", position);

                                it.putExtra("titulo", texto.getText());
                                try {
                                    it.putExtra("videoId", ultimoVideo(ids,idCourses[position]));
                                } catch (IOException e) {
                                    it.putExtra("videoId","");
                                    e.printStackTrace();
                                }
                                it.putExtra("id_usuario", id);
                                it.putExtra("idCurso", idCourses[position]);
                                cursoDownloads(position);
                               // checkID(idCourses[position]);
                                startActivity(it);
                                dialogs[0].dismiss();
                            } catch (Exception e) {
                                dialogs[0].dismiss();
                            }
                        }

                    }).start();
                }
            });
            lista.setAdapter(null);
            inss = 1;
            ListaAdapter(lista);

            final Button concluido = findViewById(id.concluido);
            final Button NIniciado = findViewById(id.NIniciado);
            final Button EmAndamento = findViewById(id.EmAndamento);
            EmAndamento.setBackgroundColor(Color.DKGRAY);

            EmAndamento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lista.setAdapter(null);
                    inss = 1;
                    ListaAdapter(lista);

                    EmAndamento.setBackgroundColor(Color.DKGRAY);
                    concluido.setBackgroundColor(Color.TRANSPARENT);
                    NIniciado.setBackgroundColor(Color.TRANSPARENT);

                }
            });

            concluido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lista.setAdapter(null);
                    inss = 2;
                    ListaAdapter(lista);
                    EmAndamento.setBackgroundColor(Color.TRANSPARENT);
                    concluido.setBackgroundColor(Color.DKGRAY);
                    NIniciado.setBackgroundColor(Color.TRANSPARENT);
                }
            });

            NIniciado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lista.setAdapter(null);
                    inss = 3;
                    ListaAdapter(lista);
                    EmAndamento.setBackgroundColor(Color.TRANSPARENT);
                    concluido.setBackgroundColor(Color.TRANSPARENT);
                    NIniciado.setBackgroundColor(Color.DKGRAY);
                }
            });

            BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(id.bottom_nav_view);
            bottomNavigationView.getMenu().findItem(id.nav_cursos).setChecked(true);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                            item.setChecked(true);
                    switch (item.getItemId()) {
                        case id.nav_cursos:
                            break;
                        case id.nav_contas:
                            Intent intent = new Intent(usuario.this, Conta.class);
                            startActivity(intent);
                            overridePendingTransition(anim.slide_in_right, anim.slide_out_left);
                            break;
                        case id.nav_destaque:
                            Intent itss = new Intent(usuario.this, Destaque.class);
                            startActivity(itss);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            break;
                        case id.nav_not:
                            Intent its = new Intent(usuario.this, Notificacoes.class);
                            startActivity(its);
                            overridePendingTransition(anim.slide_in_right, anim.slide_out_left);
                            break;
                        case id.nav_rank:
                            Intent it = new Intent(usuario.this, Ranking_video_app.class);
                            startActivity(it);
                            overridePendingTransition(anim.slide_in_left, anim.slide_out_right);
                            break;
                    }
                    return false;

                }
            });



            final SwipeRefreshLayout swipeLayout;
            swipeLayout = findViewById(R.id.swipe_container);
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    final ListView lista =  findViewById(R.id.lvCursos);
                    lista.setAdapter(null);
                    ListaAdapter(lista);
                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {
                            swipeLayout.setRefreshing(false);
                        }
                    }, 2000); // Delay in millis
                }
            });


        }catch (Exception e){}


        View event = findViewById(id.view8);
        event.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String assas = SessaoAssisitir.getInstance().getaovivo();
                if(assas.equals("false"))
                {
                    Intent linkedim = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.API_URL/digital/formacao-consultores-gestao"));
                    startActivity(linkedim);
                }else{
                    Intent is = new Intent(usuario.this, aovivo.class);
                    startActivity(is);
                }
            }
        });


        ImageView imgprof = findViewById(R.id.imageView4);
        TextView tituloprof = findViewById(R.id.textView25);



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


        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(usuario.this)
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

    public void aovivo()
    {
        ImageView imgprof = findViewById(R.id.imageView4);
        TextView tituloprof = findViewById(R.id.textView25);

        ImageView aovivo = findViewById(R.id.imageView12);
        String out = SessaoAssisitir.getInstance().getaovivo();
        if(!out.equals("false")){
            String [] array = out.split("</exp>");
            tituloprof.setText(array[0]);
            aovivo.setVisibility(View.VISIBLE);
            carregaFot(imgprof, array[2]);
        }
    }
    private void cursoDownloads(int position)
    {
        try{
            String dadosDownload = new Scanner(new URL("https://www.API_URL/apiapp/view/listadowloads.php?id_curso="+idCourses[position]).openStream(), "UTF-8").useDelimiter("\\A").next();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("dadosDownloads", Context.MODE_PRIVATE));
            outputStreamWriter.write(dadosDownload);
            outputStreamWriter.close();
        }
        catch(Exception e)
        {

        }
    }

    private String leituraDeJson(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("usuario.txt");

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

        return ret;
    }

    public String retornoArray(String id, int i) {
        final Handler MHandler = new Handler() {
            public void handleMessage(Message msg) {
                Dialog dialogs = ProgressDialog.show(usuario.this, "", "Entrando ...", true);
            }
        };
        String json;
        if(!leituraDeJson(this, i+"" ).equals(""))
        {
            json = leituraDeJson(this, i+"" );
            return json;
        }else{
            String out = null;
            MHandler.sendEmptyMessage(0);
            try {
                InputStream input = new URL("https://www.API_URL/apiapp/view/cursos.php?id=" + id + "&status=" + i).openStream();
                Reader reader = new InputStreamReader(input, "UTF-8");
                Scanner s = new Scanner(reader);
                json = s.nextLine();
                MHandler.removeMessages(0);
                escritaDoJson(json,i+"");
                return json;
            } catch (Exception e) {
                MHandler.removeMessages(0);
                return "";
            }
        }
    }



    public String renova( int i)
    {
        String arquivo = "";
        String json ="";
        switch (i)
        {
            case 1:
                arquivo = "cursos-1";
                break;
            case 2:
                arquivo = "cursos-2";
                break;
            case 3:
                arquivo = "cursos-3";
                break;
        }
        try{
            OutputStreamWriter ss = new OutputStreamWriter(this.openFileOutput(arquivo, Context.MODE_PRIVATE));
            ss.write("");
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        try {
            InputStream input = new URL("https://www.API_URL/apiapp/view/cursos.php?id=" + ids + "&status=" + i).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            json = s.nextLine();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(arquivo, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
            return "Json: " +json;
        }
        catch (IOException e) {

        }
        return "";
    }

    public ArrayList<String> preencherDados() {
        ArrayList<String> dados = new ArrayList<String>();
        dados.add("Teste");
        dados.add("Teste2");
        return dados;
    }


    public String[] removeRepetitions(String[] strArray) {
        List<String> list = new ArrayList<String>();
        int c;
        for (String si : strArray) {
            c = 0;
            for (String sj : strArray) {
                if (sj.equals(si)) {
                    if (++c > 1) {
                        break;
                    }
                }
            }
            if (c == 1) {
                list.add(si);
            }
        }
        return list.toArray(new String[0]);
    }

    public int inss = 1;

    public String[] sss(String id) {
        Intent it = getIntent();
        String json = retornoArray(id, inss);

        json = json.replace("[", "");
        json = json.replace("]", "");
        String[] array = json.split(",");
        array = removeRepetitions(array);
        return array;
    }


    public void ListaAdapter(ListView lista) {
        String[] array = sss(ids);
        ArrayAdapter adapter = null;
        try {
            adapter = new CursoAdapter(this, adicionarEscolas(array), mImageLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lista.setAdapter(adapter);

    }

    //atributo da classe.
    private AlertDialog alerta;

    private void aletass() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Alerta !!!");
        //define a mensagem
        builder.setMessage("O sistema de progresso esta passando por manutenção !");
        //define um botão como positivo
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        //cria o AlertDialog
        alerta = builder.create();

        alerta.show();
    }




    private ArrayList<curso> adicionarEscolas(String[] array) throws Exception {
        ArrayList<curso> escolas = new ArrayList<curso>();
        filtro ft = new filtro();
        String[] aIn = new String[1000];
        String[] array2 = new String[2];
        for (int s = 0; s < array.length; s++) {
            array2 = array[s].split("/");
            array2[0] = ft.filtro(array2[0]);
            idCourses[s] = array2[2];
            aIn[s] = array2[1];
            curso e = new curso(array2[0], "0", drawable.e1, aIn[s], idCourses[s], ids);
            escolas.add(e);
        }

        return escolas;
    }

    public String ultimoVideo(String ID_USUARIO, String ID_CURSO) throws IOException {
        String link = new Scanner(new URL("http://www.API_URL/apiapp/view/primeiraLicao.php?ID_USUARIO=" + ID_USUARIO + "&ID_CURSO=" + ID_CURSO).openStream(), "UTF-8").useDelimiter("\\A").next();
        filtroS filtro = new filtroS();
        link = filtro.frt(link);
        link = link.replace(" ", "");
        if(link.equals("0") || link.equals(""))
        {
            link = "KgRwCy4pqVg";
        }
        return link;
    }

    private String leituraDeJson(Context context, String i) {

        String ret = "";
        String arquivo = "";
        switch (Integer.parseInt(i))
        {
            case 1:
                arquivo = "cursos-1";
                break;
            case 2:
                arquivo = "cursos-2";
                break;
            case 3:
                arquivo = "cursos-3";
                break;
        }
        try {
            InputStream inputStream = context.openFileInput(arquivo);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        return ret;
    }


    public void escritaDoJson(String json, String i) {
        int var = Integer.parseInt(i);
        String arquivo = "";
           switch (var)
           {
               case 1:
                   arquivo = "cursos-1";
                   break;
               case 2:
                   arquivo = "cursos-2";
                   break;
               case 3:
                   arquivo = "cursos-3";
                   break;
           }
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(arquivo, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
        }
        catch (IOException e) {
        }
    }

    private String checkID(String q) {
        try {
            InputStream input = new URL("https://www.API_URL/apiapp/view/getidprogresso.php?id_curso=" + q + "&id_pessoa=" + Sessao.getInstance().getUsuario()).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String jst = s.nextLine();
            SessaoJs.getInstance().setCheckedId(jst);
            return jst;
        } catch (Exception e) {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
            return "";
        }
    }


}



class filtro {
    public String filtro(String arrays)
    {
        arrays = arrays.replaceAll("ONLINE - ", "");
        arrays = arrays.replaceAll("GRATUITO ", "");
        arrays = arrays.replaceAll("]", "");
        arrays = arrays.replace("[","");
        return arrays;
    }
}