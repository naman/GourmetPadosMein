package com.mc.priveil.gourmetpadosmein;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MyOfferings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offerings);

        Toast.makeText(MyOfferings.this, "My Hostings", Toast.LENGTH_SHORT).show();
    }
}
