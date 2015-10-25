package com.mc.priveil.gourmetpadosmein;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.parse.ParseObject;

public class OfferingForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offering_form);

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
        RadioButton packingYes = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton packingNo = (RadioButton) findViewById(R.id.radioButton4);

        RadioButton veg = (RadioButton) findViewById(R.id.radioButton);

        RadioButton nonVeg = (RadioButton) findViewById(R.id.radioButton2);

        if(email.getText().toString().isEmpty() || offeringname.getText().toString().isEmpty() || cost.getText().toString().isEmpty() || cuisine.getText().toString().isEmpty() || deadline.getText().toString().isEmpty() || description.getText().toString().isEmpty() || capacity.getText().toString().isEmpty() || (veg.isChecked() ^ nonVeg.isChecked()) || (packingYes.isChecked() ^ packingNo.isChecked()))
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
            testObject.put("username", email);
            testObject.put("name", offeringname);
            testObject.put("cost", cost);
            testObject.put("cuisine", cuisine);
            testObject.put("description", description);
            testObject.put("deadline", deadline);
            testObject.put("capacity", capacity);
            if(packingYes.isChecked())
                testObject.put("packing", 0);
            else
                testObject.put("packing", 1);
            if(veg.isChecked())
                testObject.put("veg", 0);
            else
                testObject.put("veg", 1);
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
