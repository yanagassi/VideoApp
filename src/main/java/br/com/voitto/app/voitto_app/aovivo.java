package br.com.video_app.app.video_app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;



public class aovivo  extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String API_KEY = "AIzaSyAMANGFUSQRCFbBGpKN_xI1gEVvTnpiHh4";
    public String VIDEO_ID;
    private String link;
    public YouTubePlayerView youTubePlayerView;
    public YouTubePlayer players;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aovivo);
        String out = SessaoAssisitir.getInstance().getaovivo();
        String[] array = out.split("</exp>");
        link = array[3];
        link = link.replace("https://www.youtube.com/watch?v=","");
        VIDEO_ID = link;
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_playeraovivo);
        youTubePlayerView.initialize(API_KEY,  this);


        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView,true);
        CookieSyncManager.getInstance().startSync();

        webView.loadUrl("https://API_URL/view/chatAovivo.php");

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Falha ao iniciar o video." + result.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
            player.setPlayerStateChangeListener(playerStateChangeListener);
            player.setPlaybackEventListener(playbackEventListener);
            players = player;
            player.loadVideo(VIDEO_ID);

    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
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

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {

        }
        @Override
        public void onVideoStarted() {
        }
    };



}


