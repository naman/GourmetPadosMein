package com.mc.priveil.gourmetpadosmein;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class OfferingForm extends AppCompatActivity {

    public static final String YOUR_APPLICATION_ID = "WU842Ed8GWCo7napgpaxk9FBSZ6LBqrhj6cv0XoO";
    public static final String YOUR_CLIENT_KEY = "Z5WO1weLaVu7ZAQdn97qEjTApHPoDG0BFM77OUqv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("testing123", "Came to offering listing");

        setContentView(R.layout.activity_offering_form);
//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        ParseUser.enableAutomaticUser();

        Intent intent = getIntent();
        String email = intent.getStringExtra(OfferingListActivity.MESSAGE_EMAIL);

        EditText editText = (EditText) findViewById(R.id.editText7);
        editText.setText(email);
        editText.setKeyListener(null);
    }


    public void submitForm(View view) {
        EditText email = (EditText) findViewById(R.id.editText7);
        EditText offeringname = (EditText) findViewById(R.id.editText8);
        EditText cost = (EditText) findViewById(R.id.editText9);
        EditText cuisine = (EditText) findViewById(R.id.editText10);
        EditText deadline = (EditText) findViewById(R.id.editText11);
        EditText description = (EditText) findViewById(R.id.editText12);
        EditText capacity = (EditText) findViewById(R.id.editText13);
        CheckBox packingYes = (CheckBox) findViewById(R.id.checkBox);
        CheckBox veg = (CheckBox) findViewById(R.id.checkBox2);

        if(email.getText().toString().isEmpty() || offeringname.getText().toString().isEmpty() || cost.getText().toString().isEmpty() || cuisine.getText().toString().isEmpty() || deadline.getText().toString().isEmpty() || description.getText().toString().isEmpty() || capacity.getText().toString().isEmpty())
        {
            Toast.makeText(this, "All form fields are required!!", Toast.LENGTH_LONG).show();
        }
//        else if(mobile.getText().length()!=10)
//        {
//            Toast.makeText(this, "Enter a valid Mobile Number!!", Toast.LENGTH_LONG).show();
//        }
//        else if(emergencyNumber.getText().length()!=10)
//        {
//            Toast.makeText(this, "Enter a valid Emergency Mobile Number!!", Toast.LENGTH_LONG).show();
//        }


        else
        {
            ParseObject testObject = new ParseObject("Offering");
            testObject.put("username", email.getText().toString());
            testObject.put("name", offeringname.getText().toString());
            testObject.put("cost", Double.parseDouble(cost.getText().toString()));
//            testObject.put("cuisine", cuisine.getText().toString());
//            testObject.put("description", description.getText().toString());
//            testObject.put("deadline", deadline.getText().toString());
            testObject.put("capacity", Integer.parseInt(capacity.getText().toString()));
            if(packingYes.isChecked())
                testObject.put("packing", true);
            else
                testObject.put("packing", false);
            if(veg.isChecked())
                testObject.put("veg", true);
            else
                testObject.put("veg", false);
            testObject.saveInBackground();
            Intent intent = new Intent(this, OfferingListActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offering_form, menu);
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
