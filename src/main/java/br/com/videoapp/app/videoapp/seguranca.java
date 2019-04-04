package br.com.video_app.app.video_app;

import android.content.Context;
import android.content.Intent;

public class seguranca {
    public void verificacao(Context context)
    {
        String sessao = Sessao.getInstance().getUsuario();
        if(sessao == "")
        {
            Intent it = new Intent(context, usuario.class);
            context.startActivity(it);
        }
    }
}
