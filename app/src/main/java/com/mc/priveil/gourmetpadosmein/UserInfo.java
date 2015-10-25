package com.mc.priveil.gourmetpadosmein;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class UserInfo extends AppCompatActivity {
    public static final String YOUR_APPLICATION_ID = "WU842Ed8GWCo7napgpaxk9FBSZ6LBqrhj6cv0XoO";
    public static final String YOUR_CLIENT_KEY = "Z5WO1weLaVu7ZAQdn97qEjTApHPoDG0BFM77OUqv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info2);
        Intent intent = getIntent();

        String name = intent.getStringExtra(OfferingListActivity.MESSAGE_NAME);
        String email = intent.getStringExtra(OfferingListActivity.MESSAGE_EMAIL);

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(email);
        editText.setKeyListener(null);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setText(name);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
    }

    public void submitForm(View view) {
        EditText email = (EditText) findViewById(R.id.editText);
        EditText name = (EditText) findViewById(R.id.editText2);
        EditText address = (EditText) findViewById(R.id.editText3);
        EditText mobile = (EditText) findViewById(R.id.editText4);
        EditText emergencyName = (EditText) findViewById(R.id.editText5);
        EditText emergencyNumber = (EditText) findViewById(R.id.editText6);
        if(email.getText().toString().isEmpty() || name.getText().toString().isEmpty() || address.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() || emergencyName.getText().toString().isEmpty() || emergencyNumber.getText().toString().isEmpty())
        {
            Toast.makeText(this, "All form fields are required!!", Toast.LENGTH_LONG).show();
        }
        else if(mobile.getText().length()!=10)
        {
            Toast.makeText(this, "Enter a valid Mobile Number!!", Toast.LENGTH_LONG).show();
        }
        else if(emergencyNumber.getText().length()!=10)
        {
            Toast.makeText(this, "Enter a valid Emergency Mobile Number!!", Toast.LENGTH_LONG).show();
        }


        else
        {
            ParseObject testObject = new ParseObject("User");
            testObject.put("username", email);
            testObject.put("name", name);
            testObject.put("address", address);
            testObject.put("phoneNumber", mobile);
            testObject.put("emergencyContactName", emergencyName);
            testObject.put("emergencyContactNumber", emergencyNumber);
            testObject.saveInBackground();
            Intent intent = new Intent(this, OfferingForm.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
