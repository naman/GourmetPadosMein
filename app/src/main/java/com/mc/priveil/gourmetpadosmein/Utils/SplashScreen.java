package com.mc.priveil.gourmetpadosmein.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mc.priveil.gourmetpadosmein.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //thread for splash screen running
        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                } finally {
                    startActivity(new Intent(SplashScreen.this, WelcomeScreen.class));
                }
                finish();
            }
        };
        logoTimer.start();
    }

}
