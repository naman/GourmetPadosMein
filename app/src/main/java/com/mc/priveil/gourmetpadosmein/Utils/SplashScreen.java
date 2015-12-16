package com.mc.priveil.gourmetpadosmein.Utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mc.priveil.gourmetpadosmein.R;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);

        startActivity(new Intent(SplashScreen.this, SignIn.class));
/*        //thread for splash screen running
        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                }finally{


                }
                finish();
            }
        };
        logoTimer.start();*/
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
            startActivity(new Intent(SplashScreen.this, WelcomeScreen.class));
        }
    }
}