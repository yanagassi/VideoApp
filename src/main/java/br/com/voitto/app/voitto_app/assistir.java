package br.com.video_app.app.video_app;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class assistir extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String API_KEY = "AIzaSyAMANGFUSQRCFbBGpKN_xI1gEVvTnpiHh4";
    public String[] ListaVideos = new String[10000];
    ;
    public YouTubePlayerView youTubePlayerView;
    public String VIDEO_ID;
    public YouTubePlayer players;


    public String IdCursos;
    public String[] js_id = new String[10000];
    public String id_usuario;
    public String js_id_str;
    public int PositionIndex;
    public int positionLista;
    public String TituloVideos;
    public String tituloVideo;
    public String posicao;
    public static boolean sistemas = false;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ImageLoader mImageLoader;

    @Override
    public void onBackPressed() {
        Intent it = getIntent();
        id_usuario = Sessao.getInstance().getUsuario();
        Intent intent = new Intent(assistir.this, usuario.class);
        intent.putExtra("id", id_usuario);
        startActivity(intent);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            players.setFullscreen(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            players.setFullscreen(false);
        }
    }
    public String ultimoVideo(String ID_USUARIO, String ID_CURSO) throws IOException {
        try{
            String link = new Scanner(new URL("http://www.API_URL/apiapp/view/primeiraLicao.php?ID_USUARIO=" + ID_USUARIO + "&ID_CURSO=" + ID_CURSO).openStream(), "UTF-8").useDelimiter("\\A").next();
            filtroS filtro = new filtroS();
            link = filtro.frt(link);
            if(link.equals("0"))
            {
                link = ultimoVideo(id_usuario,IdCursos);
            }
            return link;
        }catch (Exception e ){
           String  link = "KgRwCy4pqVg";
            return link;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistir);

        ImageView imageView8 = findViewById(R.id.imageView8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ins = new Intent(assistir.this, DownloadsAction.class);
                ins.putExtra("id",IdCursos);
                ins.putExtra("titulo",SessaoAssisitir.getInstance().getTitulo());
                startActivity(ins);

            }
        });

        ImageView enviarMSG = findViewById(R.id.imageView13);
        final TextView MSGenviar = findViewById(R.id.editText5);

        enviarMSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iss = new Intent(assistir.this, EnviarMensagem.class );
                iss.putExtra("textoMsg",MSGenviar.getText());
                iss.putExtra("idCurso",IdCursos);
                Toast.makeText(assistir.this, "Função do indisponível, acesse Conta > Fale conosco em caso de duvida !", Toast.LENGTH_SHORT).show();
               // startActivity(iss);
            }
        });
        try{
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            mFirebaseAnalytics.setCurrentScreen(this, "Tela de assistir.", null /* class override */);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            seguranca seguranca = new seguranca();
            seguranca.verificacao(this);
            Intent it = getIntent();
            Switch simpleSwitch =  findViewById(R.id.switch1);
            final Boolean switchState = simpleSwitch.isChecked();
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            final String tituloView = it.getStringExtra("titulo");
            VIDEO_ID = it.getStringExtra("videoId");
            IdCursos = it.getStringExtra("idCurso");

            SessaoAssisitir.getInstance().setidCurso(IdCursos);
            id_usuario = Sessao.getInstance().getUsuario();
            SessaoAssisitir sessaoAssistir = SessaoAssisitir.getInstance();
            sessaoAssistir.setTitulo(tituloView);
            TituloVideos = tituloView;
            simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sistemas = isChecked;
                    if (sistemas) {
                        Toast.makeText(assistir.this, "Clique no video para baixar !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            youTubePlayerView =  findViewById(R.id.youtube_player);
            youTubePlayerView.initialize(API_KEY, this);
            final ListView lista = (ListView) findViewById(R.id.licoes);
            ArrayList<listassistir> listaassi = new ArrayList<listassistir>();
            ArrayAdapter adapter = null;

            adapter = new assistirAdapter(this, adicionarlicoes(IdCursos));
            lista.setAdapter(adapter);
            lista.setSelection(Integer.parseInt(posicao));
            lista.setVisibility(View.VISIBLE);


            TextView titulo = findViewById(R.id.titulo);
            titulo.setText(tituloView);


            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if (!sistemas) {
                        PositionIndex = position;
                        try {
                            if (ListaVideos[position].equals("0")) {
                                String out = getVideoUser(position);
                                Bundle params = new Bundle();
                                params.putInt("AssistirVideo", view.getId());
                                mFirebaseAnalytics.logEvent("Video: "+VIDEO_ID,params);

                                if (out.equals("video")) {
                                    final Handler MHandler2 = new Handler()
                                    {
                                        public void handleMessage(Message msg)
                                        {
                                            Toast.makeText(assistir.this, "Aguarde o lançamento do video !", Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                    MHandler2.sendEmptyMessage(0);
                                } else if (out.equals("aovivo")) {

                                    final Handler MHandler2 = new Handler()
                                    {
                                        public void handleMessage(Message msg)
                                        {
                                            Toast.makeText(assistir.this, "Aguarde o lançamento do video !", Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                    MHandler2.sendEmptyMessage(0);

                                } else if (out.equals("arquivo")) {
                                    AlertDialog.Builder adb = new AlertDialog.Builder(assistir.this);
                                    adb.setTitle("Deseja fazer o download dos arquivos ?");
                                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                Uri uri = Uri.parse(anexo(js_id[position], IdCursos));
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                            } catch (IOException e) {
                                                final Handler MHandler2 = new Handler()
                                                {
                                                    public void handleMessage(Message msg)
                                                    {
                                                        Toast.makeText(assistir.this, "Erro ao abrir link !", Toast.LENGTH_SHORT).show();
                                                    }
                                                };
                                                MHandler2.sendEmptyMessage(0);
                                            }
                                        }
                                    });
                                    adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    adb.show();
                                } else if (out.equals("apresentacao")) {
                                    AlertDialog.Builder adb = new AlertDialog.Builder(assistir.this);
                                    adb.setTitle("Deseja fazer o download da apresentação ?");
                                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                Uri uri = Uri.parse(anexo(js_id[position], IdCursos));
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                                //Toast.makeText(assistir.this, anexo(js_id[position], IdCursos), Toast.LENGTH_SHORT).show();
                                            } catch (IOException e) {
                                                final Handler MHandler2 = new Handler()
                                                {
                                                    public void handleMessage(Message msg)
                                                    {
                                                        Toast.makeText(assistir.this, "Erro ao abrir link !", Toast.LENGTH_SHORT).show();
                                                    }
                                                };
                                                MHandler2.sendEmptyMessage(0);

                                            }
                                        }
                                    });
                                    adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    adb.show();
                                } else {
                                    final Handler MHandler2 = new Handler()
                                    {
                                        public void handleMessage(Message msg)
                                        {
                                            Toast.makeText(assistir.this, "Acesse a plataforma WEB !!", Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                    MHandler2.sendEmptyMessage(0);
                                }
                            } else {
                                Intent it = new Intent(assistir.this, assistir.class);
                                SessaoAssisitir sessaoAssistir = SessaoAssisitir.getInstance();
                                sessaoAssistir.setAssistir(position + "");
                                SessaoJs sessaojs = SessaoJs.getInstance();
                                sessaojs.setidjs(js_id[position]);
                                it.putExtra("videoId", ListaVideos[position]);
                                it.putExtra("titulo", tituloView);
                                it.putExtra("idCurso", IdCursos);
                                it.putExtra("id_usuario", id_usuario);
                                tituloVideo = tituloView;

                                startActivity(it);
                            }
                        } catch (Exception e) { }
                    } else {
                        AlertDialog.Builder adb = new AlertDialog.Builder(assistir.this);
                        adb.setTitle("Deseja baixar essa lição ?");
                        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String nomeT = "";
                                try {
                                    nomeT = tituloView + "/" + nomeGet(js_id[position], IdCursos);
                                    download(js_id[position], IdCursos, nomeT);
                                    downloadIMG(IdCursos, "/" + tituloView + "/imagem");
                                    Toast.makeText(assistir.this, "Download Iniciado !", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    Toast.makeText(assistir.this, "Erro ao fazer o download !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        adb.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(assistir.this, "Cancelado !", Toast.LENGTH_SHORT).show();
                            }
                        });
                        adb.show();
                    }
                }
            });

            DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.e1)
                    .showImageOnFail(R.drawable.e1)
                    .showImageOnLoading(R.drawable.e1)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(assistir.this)
                    .defaultDisplayImageOptions(mDisplayImageOptions)
                    .memoryCacheSize(50*1024*1024)
                    .diskCacheSize(50*1024*1024)
                    .threadPoolSize(5)
                    .writeDebugLogs()
                    .build();
            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(conf);


            final View licoes = findViewById(R.id.view10);
            final View duvidas = findViewById(R.id.view13);
            final ListView listvide = findViewById(R.id.listiview_duvidas);
            final ConstraintLayout  duvidasConst = findViewById(R.id.duvidasconst);
            final ImageView foto = findViewById(R.id.fotoUsert);
            if(!Sessao.getInstance().getFoto().equals("0"))
            {
                carregaFot(foto,Sessao.getInstance().getFoto(), this);
            }
            final TextView editText5 = findViewById(R.id.editText5);
            final ImageView imageView13 = findViewById(R.id.imageView13);
            final ListView listiview_duvidas = findViewById(R.id.listiview_duvidas);
            final View view12 = findViewById(R.id.view12);;
            ListaAdapter(listvide);
            listvide.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Intent it = new Intent(assistir.this, duvidas_dados.class);
                    it.putExtra("id_curso", IdCursos);
                    startActivity(it);
                }
            });
            licoes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = Color.parseColor("#f2f2f2");
                        duvidas.setBackgroundColor(i);
                        lista.setVisibility(View.VISIBLE);
                        int a = Color.parseColor("#bfbfbf");
                        licoes.setBackgroundColor(a);
                        duvidasConst.setVisibility(View.INVISIBLE);
                        foto.setVisibility(View.INVISIBLE);
                        editText5.setVisibility(View.INVISIBLE);
                        imageView13.setVisibility(View.INVISIBLE);
                        listiview_duvidas.setVisibility(View.INVISIBLE);
                        view12.setVisibility(View.INVISIBLE);
                    }
                });
            duvidas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lista.setVisibility(View.INVISIBLE);
                    int i = Color.parseColor("#e6e6e6");
                    duvidas.setBackgroundColor(i);
                    int a = Color.parseColor("#f2f2f2");
                    licoes.setBackgroundColor(a);
                    lista.setVisibility(View.INVISIBLE);
                    duvidasConst.setVisibility(View.VISIBLE);
                    foto.setVisibility(View.VISIBLE);
                    editText5.setVisibility(View.VISIBLE);
                    imageView13.setVisibility(View.VISIBLE);
                    listiview_duvidas.setVisibility(View.VISIBLE);
                    view12.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e)
        {
            Intent sair =  new Intent(assistir.this, Main.class);
            startActivity(sair);
        }
    }


    public String getVideoUser(final int position) {
        String out ="";
        try {
            InputStream input = new URL("https://www.API_URL/apiapp/view/verificacaovideo.php?id_js=" + js_id[position] + "&id_curso=" + IdCursos).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            out = s.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }
        public void downloadIMG(String idCurso, String name) throws IOException {
            InputStream input = new URL("http://www.API_URL/apiapp/view/imgcurso.php?id_curso=" + idCurso).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String json;
            json = s.nextLine();
            json = json.replace(" ", "");
            json = "https://www.API_URL/img_curso.php/capas/" + json + ".png?size=250";
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(json);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle(name);
            File sdCard = Environment.getExternalStorageDirectory();
            String folder = sdCard.getAbsolutePath() + "/listem";
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, name + ".png");
            Long reference = downloadManager.enqueue(request);
    }


    public void ListaAdapter(ListView lista) throws IOException {
        ArrayAdapter adapter = null;
        try {
            adapter = new duvidasAdapter(this, addDuvidas(), mImageLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lista.setAdapter(adapter);
    }



    private String getString(String tipo) throws IOException {
        try{
            InputStream input = new URL("https://www.API_URL/apiapp/view/duvidas_allGet.php?id_curso="+ IdCursos + "&id_licao="+ SessaoJs.getInstance().getidjs() + "&tipo="+tipo).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String json;
            json = s.nextLine();
            return json;
        }catch (Exception e)
        {
            return "";
        }

    }


    private ArrayList<duvidasAdapter_Metodos> addDuvidas() throws Exception {
        filtroS ft = new filtroS();
        ArrayList<duvidasAdapter_Metodos> duvidsa = new ArrayList<>();
        try{
            String resposta = "";
            String nome = getString("pergunta_nome");
            if(!nome.equals(""))
            {
                TextView t = findViewById(R.id.naoah);
                t.setText("");
            }
            if(getString("resposta").substring(0, 5).equals("https"))
            {
                resposta = "Video";
            }else{
                resposta = ft.frt(getString("resposta"));
            }
            duvidasAdapter_Metodos e = new duvidasAdapter_Metodos(nome, ft.frt(getString("pergunta")),getString("pergunta_foto"), getString("foto_responde"),resposta, getString("nome_responde") );
            duvidsa.add(e);
        }catch (Exception e) {}
        return duvidsa;
    }





    public void download(final String js_id, final String idCurso, String name) throws IOException {
        try {
            InputStream input = new URL("https://www.API_URL/apiapp/view/videodownload.php?js_id=" + js_id + "&idcurso=" + idCurso).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String json;
            json = s.nextLine();
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(json);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle(name);
            File sdCard = Environment.getExternalStorageDirectory();
            String folder = sdCard.getAbsolutePath() + "/listem";
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, name + ".vto");
            Long reference = downloadManager.enqueue(request);
            new Thread(new Runnable() {
                public void run() {
                    inserir_progresso(id_usuario, js_id, idCurso);
                }
            }).start();
        }
        catch (Exception e) { }
    }



    public String anexo(String js_id_ss, String IDCurso) throws IOException {
        try{
            InputStream input = new URL("https://www.API_URL/apiapp/view/anexo.php?id_js=" + js_id_ss + "&id_curso=" + IDCurso).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String link;
            link = s.next();
            return link;
        }
        catch (Exception e)
        {
            return "";
        }

    }


    public String nomeGet(String js_idS, String IDcurso) throws IOException {

        try{
            InputStream input = new URL("https://www.API_URL/apiapp/view/videonome.php?id_js=" + js_idS + "&id_curso=" + IDcurso).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String link;
            filtroS filtro = new filtroS();
            link = s.nextLine();
            link = filtro.frt(link);
            //Toast.makeText(this, link.toString(), Toast.LENGTH_SHORT).show();
            return link;
        }
      catch (Exception e){
            return "";
      }
    }
    public ArrayList<listassistir> adicionarlicoes(String IdCursos){
        ArrayList<listassistir> licoes = new ArrayList<>();
        listassistir e;
        String[] dados;
        String ac = "";

        try{
            OutputStreamWriter ss = new OutputStreamWriter(this.openFileOutput("licoes.txt", Context.MODE_PRIVATE));
            String jst = new Scanner(new URL("https://www.API_URL/apiapp/view/lista.php?id_curso=" +IdCursos ).openStream(), "UTF-8").useDelimiter("\\A").next();
            ss.write(jst);
            ss.close();



            jst = leituraDeJson(this,"licoes.txt");

            jst = jst.replace("[", "");
            jst = jst.replace("]", "");
            String[] arrayAux;
            arrayAux = jst.split(",");
            filtroS filtro = new filtroS();
            String a = check();
            checkID();
            String[] sss = new String[10000];
            String [] sssw = SessaoJs.getInstance().getCheckedId().split(",");

            for(int i =0;i<1000;i++) {
                sss[i]= "0";
            }

            for(int i =0;i< sssw.length;i++)
            {
                if(sssw[i].equals("1")) {
                    sss[i] = "1";
                }
            }
            posicao = a;
            boolean mds;

            String k =  SessaoJs.getInstance().getCheckedId();
            String[] karray = jst.split(",");

            int numero = karray.length;


            for (int i = 0; i < karray.length; i++) {


                dados = karray[i].split("</exp>");


                ListaVideos[i] = dados[2];
                js_id[i] = dados[3];

                if(sss[i].equals("1")){
                    mds = true;
                }else{ mds=false;}



                if (!ac.equals(dados[0])) {
                    e = new listassistir(filtro.frt(dados[1]), R.drawable.eve_licao, true, filtro.frt(dados[0]), i,a,mds);
                    ac = dados[0];
                } else {
                    e = new listassistir(filtro.frt(dados[1]), R.drawable.eve_licao, false, "",i,a,mds);
                }
                licoes.add(e);
            }

        } catch (Exception es) {
        }
        return licoes;
    }

    private String check()
    {
        try{
            InputStream input = new URL("https://www.API_URL/apiapp/view/getCheck.php?id_curso=" +SessaoAssisitir.getInstance().getidCurso() +"&id_pessoa="+Sessao.getInstance().getUsuario()).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String jst = s.nextLine();
            return jst;
        }
        catch (Exception e)
        {
            return "0";
        }
    }

    private String checkID()
    {
        try{
            InputStream input = new URL("https://www.API_URL/apiapp/view/getidprogresso.php?id_curso=" +SessaoAssisitir.getInstance().getidCurso() +"&id_pessoa="+Sessao.getInstance().getUsuario()).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String jst = s.nextLine();
            SessaoJs.getInstance().setCheckedId(jst);
            return jst;
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
            return "";
        }
    }






    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Falha ao iniciar o video." + result.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
       try{
           player.setPlayerStateChangeListener(playerStateChangeListener);
           player.setPlaybackEventListener(playbackEventListener);
           players = player;
           if (!wasRestored) {

               if (VIDEO_ID.equals("0") || VIDEO_ID.equals("")) {

                   try {
                       VIDEO_ID = ultimoVideo(id_usuario,IdCursos);
                       Intent it = new Intent(assistir.this, assistir.class);
                       it.putExtra("titulo", TituloVideos);
                       it.putExtra("videoId", VIDEO_ID);
                       Log.d("", VIDEO_ID);
                       it.putExtra("id_usuario", id_usuario);
                       it.putExtra("idCurso", IdCursos);
                       //startActivity(it);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
               player.loadVideo(VIDEO_ID);
           }
       }catch (Exception e){ }
    }

    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }
    };

    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
            try{
                SessaoAssisitir sessaoAssistir = SessaoAssisitir.getInstance();
                sessaoAssistir.setAssistir(SessaoAssisitir.getInstance().getidAssistir() + 1) ;


                try{
                    inserir_progresso(id_usuario,js_id_str,IdCursos);

                }catch (Exception e) { }

                Intent it = new Intent(assistir.this, assistir.class);
                SessaoJs sessaojs = SessaoJs.getInstance();
                sessaojs.setidjs(js_id[PositionIndex]);
                it.putExtra("videoId", ultimoVideo(id_usuario,IdCursos));
                it.putExtra("titulo", SessaoAssisitir.getInstance().getTitulo());
                it.putExtra("idCurso", IdCursos);
                it.putExtra("id_usuario", id_usuario);
                //startActivity(it);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        @Override
        public void onVideoStarted() {
        }
    };

    public boolean inserir_progresso(String idUsuario, String idLicao, String idCurso){
        try{
            InputStream input = new URL("https://www.API_URL/apiapp/view/progresso.php?ID_USUARIO=" + idUsuario + "&ID_LICAO="+ SessaoJs.getInstance().getidjs() + "&ID_CURSO="+idCurso).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            Scanner s = new Scanner(reader);
            String json;
            json = s.nextLine();
            Toast.makeText(this, json, Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    public void carregaFot(final ImageView image, final String link, Context context)
    {

        ImageLoader mImageLoader;
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


    private String leituraDeJson(Context context, String arquivpo) throws IOException {

        String ret="";
        try {
            InputStream inputStream = context.openFileInput(arquivpo);

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


}





class filtroS{
    public String frt(String teste)
    {
        teste = teste.replace("&ccedil","ç");
        teste = teste.replace("&Ccedil","Ç");
        teste = teste.replace("&atilde","ã");
        teste = teste.replace("&oacute","ó");
        teste = teste.replace("&eacute","é");
        teste = teste.replace("&otilde","õ");
        teste = teste.replace("&aacute","á");
        teste = teste.replace("&iacute","í");
        teste = teste.replace("&uacute","ú");
        teste = teste.replace("&oacute","ó");
        teste = teste.replace( "&Aacute;","Á");
        teste = teste.replace( "&acirc;","â");
        teste = teste.replace("&Acirc;","Â");
        teste = teste.replace("&agrave;","à");
        teste = teste.replace("&Agrave;","À");
        teste = teste.replace("&atilde;","ã");
        teste = teste.replace("&Atilde;","Ã");
        teste = teste.replace("&ordf;","ª");
        teste = teste.replace("&Iacute;","Í");
        teste = teste.replace("&iacute;","í");
        teste = teste.replace("&Icirc;","Î");
        teste = teste.replace("&icirc;","î");
        teste = teste.replace("&Igrave;","Ì");
        teste = teste.replace("&igrave;","ì");
        teste = teste.replace("&nbsp;"," ");
        teste = teste.replace("&Iuml;","Ï");
        teste = teste.replace("&iuml;","ï");
        teste = teste.replace("&oacute;","ó");
        teste = teste.replace("&Oacute;","Ó");
        teste = teste.replace("&ograve;","ò");
        teste = teste.replace("&Ograve;","Ò");
        teste = teste.replace("&ocirc;","ô");
        teste = teste.replace("&Ocirc;","Ô");
        teste = teste.replace("&otilde;","õ");
        teste = teste.replace("&Otilde;","Õ");
        teste = teste.replace("&ordm;","º");
        teste = teste.replace("&Uacute;","Ú");
        teste = teste.replace("&uacute;","ú");
        teste = teste.replace("&Ucirc;","Û");
        teste = teste.replace("&ucirc;","û");
        teste = teste.replace("&Ugrave;","Ù");
        teste = teste.replace("&ugrave;","ù");
        teste = teste.replace("&Uuml;","Ü");
        teste = teste.replace("&uuml;","ü");
        teste = teste.replace("&#37;","%");
        teste = teste.replace("&ndash", "-");
        teste = teste.replace(";","");
        return teste;
    }





}
