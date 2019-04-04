package br.com.video_app.app.video_app;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Conta extends AppCompatActivity {
    private ImageLoader mImageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                try{
                    ImageView image =  findViewById(R.id.FotoUser_Conta);
                    ListView lista = findViewById(R.id.listview_conta);
                    TextView video_appcoins = findViewById(R.id.video_appcoins);
                    TextView nome = findViewById(R.id.nomeDuvida);
                    TextView email = findViewById(R.id.email_usuario);
                    BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
                    bottomNavigationView.getMenu().findItem(R.id.nav_contas).setChecked(true);

                    filtro ft = new filtro();
                    video_appcoins.setText(Sessao.getInstance().getvideo_appcoins());
                    nome.setText(ft.filtro(Sessao.getInstance().getnomeUsuario()));
                    email.setText(Sessao.getInstance().getEmail());
                    String foto = Sessao.getInstance().getFoto();


                    image.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            Intent foto = new Intent(Conta.this, FotoUser.class);
                            startActivity(foto);
                        }
                    });
                    if(!foto.equals("") && !foto.equals("0") && !foto.equals(" "))
                    {
                        carregaFot(image, foto);
                    }
                    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            item.setChecked(true);
                            actionMenu(item);
                            return false;
                        }
                    });
                            ListView lv = findViewById(R.id.listview_conta);
                            ListaAdapter(lv);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            funcionalidades_botao(position);
                        }
                    });

                }catch (Exception e)
                {
                    Intent sair =  new Intent(Conta.this, Main.class);
                    startActivity(sair);
                }




    }

    public void onBackPressed(){
        Intent intent = new Intent(Conta.this, usuario.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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


        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(Conta.this)
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


    public void actionMenu(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.nav_cursos:
                Intent intent = new Intent(Conta.this, usuario.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.nav_contas:
                break;
            case R.id.nav_destaque:
                Intent itss = new Intent(Conta.this, Destaque.class);
                startActivity(itss);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.nav_not:
                Intent its = new Intent(Conta.this, Notificacoes.class);
                startActivity(its);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.nav_rank:
                Intent it = new Intent(Conta.this, Ranking_video_app.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }

    }

    public void ListaAdapter(final ListView lista) {

                ArrayAdapter[] adapter = {null};
                try {
                    adapter[0] = new ContaAdapter(Conta.this, addlista());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lista.setAdapter(adapter[0]);


    }


    private ArrayList<ContaAdapterMetodos> addlista(){
        ArrayList<ContaAdapterMetodos> item = new ArrayList<>();
                ContaAdapterMetodos e = new ContaAdapterMetodos("Receber notificação do blog", R.drawable.eve_notificacoes, "Conta", 1);
                item.add(e);
                e = new ContaAdapterMetodos("Videos offline", R.drawable.meus_arquivos_baixados, "", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Certificado", R.drawable.certificados, "", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Editar Perfil", R.drawable.eve_conta, "", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Avalie o App", R.drawable.positive_icon, "Social", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Página do Facebook", R.drawable.facebook, "", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Perfil no Instagram", R.drawable.instagram, "", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Página no LinkedIn", R.drawable.certificado_linkedin, "", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Sobre a video_app", R.drawable.sobre_o_video_appapp, "Ajuda ", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Dúvidas Frequentes", R.drawable.duvidas_frequentes, "", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Fale Conosco", R.drawable.fale_conosco, "", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Sobre o video_app App", R.drawable.sobre_a_video_app, "", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Termos de Uso", R.drawable.termos_de_uso, "Outros", 0);
                item.add(e);
                e = new ContaAdapterMetodos("Sair", R.drawable.sair, "", 0);
                item.add(e);



        return item;

    }


    private void funcionalidades_botao(int position)
    {
        switch(position)
        {
            case 0:
                break;
            case 1:
                Intent ssss =  new Intent(Conta.this, VideoDownload.class);
                startActivity(ssss);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case 2:
                Intent Certificados =  new Intent(Conta.this, Certificados.class);
                startActivity(Certificados);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case 3:
                Intent editar =  new Intent(Conta.this, Editar_Perfil.class);
                startActivity(editar);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case 4:
                break;
            case 5:
                Intent facebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/grupovideo_app/"));
                startActivity(facebook);
                break;
            case 6:
                Intent instagram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/grupovideo_app/"));
                startActivity(instagram);
                break;
            case 7:
                Intent linkedim = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pt.linkedin.com/school/video_app-treinamento-e-desenvolvimento/"));
                startActivity(linkedim);
                break;
            case 8:
                Intent sobre =  new Intent(Conta.this, Sobre_video_appApp.class);
                startActivity(sobre);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case 9:
                Intent sss = new Intent(Conta.this, Duvidas.class);
                startActivity(sss);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                break;
            case 10:
                Intent is = new Intent(Conta.this, fale_conosco.class);
                startActivity(is);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case 11:
                Intent sobres=  new Intent(Conta.this, Sobre_video_appApp.class);
                startActivity(sobres);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case 12:
                Intent termos =  new Intent(Conta.this, Termos.class);
                startActivity(termos);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case 13:
                Sessao.getInstance().setUsuario("");
                Intent sair =  new Intent(Conta.this, Main.class);
                try{
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("usuario.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write("");
                    outputStreamWriter.close();
                    deleta();
                }catch (Exception e){

                }
                startActivity(sair);
                break;
        }

    }
    private void deleta()
    {

        File dir = new File( Environment.getExternalStorageDirectory().toString()+ "/Android/data/br.com.video_app.app.video_app/files/") ;
        try{
            File[] arquivos = dir.listFiles();
            for(File path:arquivos){
                path.delete();
            }
        }catch(Exception e){ }
        dir.delete();
    }
}
