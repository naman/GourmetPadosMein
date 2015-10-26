package com.mc.priveil.gourmetpadosmein.Models;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mc.priveil.gourmetpadosmein.R;

public class MyHostings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hostings);

        Toast.makeText(MyHostings.this, "My Hostings", Toast.LENGTH_SHORT).show();
    }
}
