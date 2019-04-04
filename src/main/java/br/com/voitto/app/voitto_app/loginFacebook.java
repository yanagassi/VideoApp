package br.com.video_app.app.video_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.lang.reflect.Array;

public class loginFacebook extends AppCompatActivity{

    private LoginButton loginButon;
    private CallbackManager callbackManager;

    //@Override
    protected void  onCreate() {
        //super.onCreate(savedInstanceState);


        //callbackManager = CallbackManager.Factory.create();
        //loginButon = (LoginButton) findViewById(R.id.facebookButton);
        // loginButon.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data)
    {
       // super.onActivityResult(requestCode, resultCode, data);
        //callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
