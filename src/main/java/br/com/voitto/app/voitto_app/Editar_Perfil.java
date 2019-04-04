package br.com.video_app.app.video_app;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Scanner;

public class Editar_Perfil extends AppCompatActivity {
    ProgressDialog dialogs;
    String id;
    public void onBackPressed(){
        Intent is = new Intent(Editar_Perfil.this, Conta.class);
        startActivity(is);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__perfil);
        reload();

          final TextView emailEditar = (TextView)findViewById(R.id.emailEditar);
          final TextView cpfEditar = findViewById(R.id.cpfEditar);
          final TextView rua = findViewById(R.id.rua);
          final TextView numeroEditar = findViewById(R.id.numeroEditar);
          final TextView bairroEditar = findViewById(R.id.bairroEditar);
          final TextView complementoEditar = findViewById(R.id.complementoEditar);
          final TextView cidadeEditar = findViewById(R.id.cidadeEditar);
          final TextView estado =  findViewById(R.id.estado);
          final TextView telefoneEditara = findViewById(R.id.telefoneEditara);
          final TextView celuarEditara = findViewById(R.id.celuarEditara);
          final TextView DataNascimentoEditara = findViewById(R.id.DataNascimentoEditara);
          final TextView cepCon = findViewById(R.id.cepCon);
        Button salvar = findViewById(R.id.salvar);

        String dados = Sessao.getInstance().getDADOS();
        String[] dadosUsuario = dados.split(";");

        emailEditar.setText(Sessao.getInstance().getEmail());
        cpfEditar.setText(dadosUsuario[0]);
        rua.setText(dadosUsuario[1]);
        numeroEditar.setText(dadosUsuario[2]);
        bairroEditar.setText(dadosUsuario[3]);
        cidadeEditar.setText(dadosUsuario[5]);
        estado.setText(dadosUsuario[6]);
        complementoEditar.setText(dadosUsuario[7]);
        telefoneEditara.setText(dadosUsuario[8]);
        celuarEditara.setText(dadosUsuario[9]);
        DataNascimentoEditara.setText(dadosUsuario[10]);
        cepCon.setText(dadosUsuario[4]);

        salvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Dialog[] dialogs = new Dialog[1];
                final Handler MHandler = new Handler()
                {
                    public void handleMessage(Message msg)
                    {
                        dialogs[0] = ProgressDialog.show(Editar_Perfil.this, "", "Salvando ...", true);
                    }
                };
                final Handler MHandler2 = new Handler()
                {
                    public void handleMessage(Message msg)
                    {
                        Toast.makeText(Editar_Perfil.this, "Dados alterados com sucesso !", Toast.LENGTH_SHORT).show();
                    }
                };
                new Thread(new Runnable() {
                    public void run() {
                        MHandler.sendEmptyMessage(0);
                        String out = null;
                        boolean a = true;
                        try {
                            String  ss = new Scanner(new URL("https://www.API_URL/apiapp/view/dados_pessoais_set.php?"+"id_usuario="
                                    +Sessao.getInstance().getUsuario()+
                                    "&cpf="+cpfEditar.getText()+
                                    "&nome="+Sessao.getInstance().getnomeUsuario()+
                                    "&email="+emailEditar.getText()+
                                    "&data_nascimento="+DataNascimentoEditara.getText()+
                                    "&endereco="+rua.getText()+
                                    "&numero="+numeroEditar.getText()+
                                    "&bairro="+bairroEditar.getText()+
                                    "&cep="+cepCon.getText()+
                                    "&cidade="+cidadeEditar.getText()+
                                    "&estado="+estado.getText()+
                                    "&complemento="+complementoEditar.getText()+
                                    "&telefone="+telefoneEditara.getText()+
                                    "&celular="+celuarEditara.getText())
                                    .openStream(), "UTF-8").useDelimiter("\\A").next();


                            String dadosDemais = new Scanner(new URL("https://www.API_URL/apiapp/view/dados_pessoais_get.php?id_usuario="+Sessao.getInstance().getUsuario()).openStream(), "UTF-8").useDelimiter("\\A").next();
                            Sessao.getInstance().setDADOS(dadosDemais);
                            dialogs[0].dismiss();
                            MHandler2.sendEmptyMessage(0);
                            Intent is = new Intent(Editar_Perfil.this, Editar_Perfil.class);
                            startActivity(is);
                        } catch (Exception e) {
                            dialogs[0].dismiss();
                        }
                    }
                }).start();
            }
        });
    }

    public void reload()
    {

    }
}
