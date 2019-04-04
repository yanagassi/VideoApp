package br.com.video_app.app.video_app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class Main extends AppCompatActivity {
    public String status;
    private android.app.AlertDialog.Builder dialog;
    public String[] arraysList;
    public  ProgressDialog dialogs;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase: ", "Token: " + token);
        if(tutorial(this).equals("") ||tutorial(this).equals("1")  ){
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("tutor.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write("2");

                outputStreamWriter.close();
                Intent ins = new Intent(Main.this, tutorial.class);
                startActivity(ins);
            }
            catch (IOException e) {
            }
        }


        try{

            TextView esqueceu =  findViewById(R.id.esuqeceuSenha);
            TextView cadastro = findViewById(R.id.cadastro);
            TextView sEmail = findViewById(R.id.email);
            TextView sSenha = findViewById(R.id.senha);
            esqueceu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    esqueceuSenhas();
                }
            });
            cadastro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cadastro();
                }
            });

            if(!conexao.VerificaConexao(this))
            {
                Intent SS = new Intent(Main.this, VideoDownload.class);
                startActivity(SS);
            } if(!leituraDeJson(this).equals("") && !leituraDeJson(this).equals("/"))
            {
                String json = leituraDeJson(Main.this);
                String[] a = json.split("/");
                sEmail.setText(a[0]);
                sSenha.setText(a[1]);
                 login(a[0], a[1]);


            }

        }catch(Exception e){
            Toast.makeText(this, "Erro ao fazer login.", Toast.LENGTH_SHORT).show();
        }

    }

    private String tutorial(Context context)
    {
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("tutor.txt");

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
        catch (Exception e) {
            //Log.e("login activity", "File not found: " + e.toString());
        }

        return ret;


    }

    public String ids;
    public String sena;
    public boolean i5 = false;
    public void login(final String email, final String senha)
    {
        sena = senha;
        final Handler MHandler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                try{
                    dialogs = ProgressDialog.show(Main.this, "", "Entrando ...", true);
                }catch (Exception e){ }
            }
        };

        final Handler MHandler2 = new Handler()
        {
            public void handleMessage(Message msg)
            {
                Toast.makeText(Main.this, "Email ou senha incorretos !", Toast.LENGTH_SHORT).show();
            }
        };

        final boolean[] error = {};

        new Thread(new Runnable() {
            public void run() {
                String out = null;
                MHandler.sendEmptyMessage(0);
                try {

                    String aovivo = new Scanner(new URL("https://www.API_URL/apiapp/view/status_promocional.php").openStream(), "UTF-8").useDelimiter("\\A").next();
                    SessaoAssisitir.getInstance().setaovivo(aovivo);
                    out = new Scanner(new URL("https://www.API_URL/apiapp/view/login.php?email="+email+"&senha="+senha).openStream(), "UTF-8").useDelimiter("\\A").next();
                    String[] array = out.split("/");
                    out = new Scanner(new URL("http://www.API_URL/apiapp/view/dados_usuario.php?id_usuario="+array[1]).openStream(), "UTF-8").useDelimiter("\\A").next();

                    String[] array2 = out.split(",");
                    String dadosDemais = new Scanner(new URL("https://www.API_URL/apiapp/view/dados_pessoais_get.php?id_usuario="+array[1]).openStream(), "UTF-8").useDelimiter("\\A").next();

                    String video_appcoins = new Scanner(new URL("https://www.API_URL/apiapp/view/video_appcoins.php?id_usuario="+array[1]).openStream(), "UTF-8").useDelimiter("\\A").next();
                    String dadosRank = new Scanner(new URL("https://www.API_URL/apiapp/view/ranker.php").openStream(), "UTF-8").useDelimiter("\\A").next();
                    String dadosColocacaoRank = new Scanner(new URL("https://www.API_URL/apiapp/view/colocacao.php?id_pessoa="+array[1]).openStream(), "UTF-8").useDelimiter("\\A").next();
                    String acumuladoNoMes = new Scanner(new URL("https://www.API_URL/apiapp/view/acumulado.php?id_pessoa="+array[1]).openStream(), "UTF-8").useDelimiter("\\A").next();


                    InputStream input = new URL("https://www.API_URL/apiapp/view/notificacoeslist.php?id_pessoa="+array[1]).openStream();
                    Reader reader = new InputStreamReader(input, "UTF-8");
                    Scanner s = new Scanner(reader);
                    String not = s.nextLine();

                    if(array[0].equals("sucess"))
                    {
                        i5 = false;
                        //Chmando outra Tela
                        Intent it = new Intent(Main.this, usuario.class);
                        it.putExtra("id", array[1]); //Passando o parametro ID para a outra tela.
                        renova(array[1], 1);
                        renova(array[1], 2);
                        renova(array[1], 3);

                        Sessao sessao = Sessao.getInstance();
                        sessao.setUsuario(array[1]);
                        sessao.setnomeUsuario(array2[0]);
                        sessao.setEmail(array2[1]);
                        sessao.setFoto(array2[2]);
                        sessao.setvideo_appcoins(video_appcoins);
                        sessao.setDadosRank(dadosRank);
                        sessao.setColocacao(dadosColocacaoRank);
                        Sessao.getInstance().setDADOS(dadosDemais);
                        sessao.setacumulado(acumuladoNoMes);
                        sessao.setnotificacao(not);
                        escritaDoJson(Sessao.getInstance().getEmail(),sena,Sessao.getInstance().getUsuario());
                        dialogs.dismiss();
                        startActivity(it);
                    }else{
                        i5= true;
                        MHandler2.sendEmptyMessage(0);
                        dialogs.dismiss();
                    }

                } catch (Exception e) {
                    Intent SS = new Intent(Main.this, VideoDownload.class);
                    startActivity(SS);
                   // dialogs.dismiss();
                }
            }
        }).start();

    }

    public void showToast(Context ctx)
    {
        new AlertDialog.Builder(ctx).setTitle("").setMessage("Função não disponivel no momento !!!").show();
    }
    public String pegarDadoUser(String email, String senha, String dado) throws IOException, JSONException {    //Metodo para retornar dado
       try{
           InputStream input = new URL("https://API_URL/apiapp/view/login.php?email="+email+"&senha="+ senha).openStream();
           Reader reader = new InputStreamReader(input, "UTF-8");
           Scanner s = new Scanner(reader);
           String json = s.nextLine();
           JSONObject jsonObj = new JSONObject(json);  //Transformando o Json em Objeto
           String dadoJson = jsonObj.getString(dado);
           return dadoJson;
       }
       catch (Exception e){
           return "";
        }
    }

    public void indisponivel(View view)
    {
        new AlertDialog.Builder(this).setTitle("").setMessage("Função não disponivel no momento !!!").show();
    }

    public void clickOk(final View view) throws JSONException, IOException {
        try{
            conexao con = new conexao();
            if (con.VerificaConexao(this)){
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
                EditText email = findViewById(R.id.email);
                EditText senha = findViewById(R.id.senha);

                final String emails = email.getText().toString();
                final String senhas  = senha.getText().toString();
                if(!emails.equals("") && !senhas.equals(""))
                {
                    login(emails,senhas);
                }
            }
            else{
                new AlertDialog.Builder(Main.this).setTitle("Erro no login!").setMessage("Sem conexão !!!").show();
            }
        }catch (Exception e)
        {

        }
    }


    public void esqueceuSenhas ()
    {
        try{
            Uri uri = Uri.parse("https://www.API_URL/recuperar-senha");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }catch (Exception e)
        {

        }
    }

    public void cadastro()
    {
        Uri uri = Uri.parse("https://www.API_URL");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    public void escritaDoJson(String email, String senha, String idJSO) throws IOException {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("usuario.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(email+"/"+senha+"/"+idJSO);
            outputStreamWriter.close();
        }
        catch (IOException e) {
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
        catch (Exception e) {
            //Log.e("login activity", "File not found: " + e.toString());
        }

        return ret;
    }

    public String renova(String id, int i)
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

            InputStream input = new URL("https://www.API_URL/apiapp/view/cursos.php?id=" + id + "&status=" + i).openStream();
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

}

class Task extends AsyncTask<Void, Void, String> {

    private ProgressDialog dialog;
    private Context context;

    public Task(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Aguarde...");
        dialog.show();
    }
}

class conexao {
    public static boolean VerificaConexao(Context contexto){
        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);//Pego a conectividade do contexto o qual o metodo foi chamado
        NetworkInfo netInfo = cm.getActiveNetworkInfo();//Crio o objeto netInfo que recebe as informacoes da NEtwork
        if ( (netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable()) ) //Se o objeto for nulo ou nao tem conectividade retorna false
            return true;
        else
            return false;
    }
}