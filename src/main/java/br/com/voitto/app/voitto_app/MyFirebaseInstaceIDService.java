package br.com.video_app.app.video_app;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstaceIDService extends FirebaseInstanceIdService {
    private final String TAG = "MyFirebaseInstanceIDService";
        @Override
        public  void onTokenRefresh()
        {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d("","Novo Token: "+ refreshedToken);
        }
}
