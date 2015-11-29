package com.mc.priveil.gourmetpadosmein.Utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mc.priveil.gourmetpadosmein.R;

public class SplashScreen extends AppCompatActivity {
    String data;
    SharedPreferences prefs = null;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.mc.priveil.gourmetpadosmein", MODE_PRIVATE);
        setContentView(R.layout.activity_splash);
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//
//        if(extras != null) {
//            data = extras.getString("Bool");
//            Log.i("firstTime",data);
//        }
        //thread for splash screen running

    }

    @Override
    protected void onResume() {
        super.onResume();

        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                } finally {

                    if (prefs.getBoolean("firstrun", true)) {

                        Log.i("FirstTime", "This will be printed just once!");

                        startActivity(new Intent(SplashScreen.this, WelcomeScreen.class));
                        prefs.edit().putBoolean("firstrun", false).commit();

                    } else {

                        startActivity(new Intent(SplashScreen.this, LogIn.class));
                    }


                }
                finish();
            }
        };
        logoTimer.start();


    }

}
