package com.mc.priveil.gourmetpadosmein;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OfferingForm extends AppCompatActivity {

    public static final String YOUR_APPLICATION_ID = "WU842Ed8GWCo7napgpaxk9FBSZ6LBqrhj6cv0XoO";
    public static final String YOUR_CLIENT_KEY = "Z5WO1weLaVu7ZAQdn97qEjTApHPoDG0BFM77OUqv";
    public int flag = 0;
    public ParseObject result = null;

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



        String objId = "lol";
//        String tempN = "aaa"
        ParseQuery query = new ParseQuery("Offering");
        query.whereEqualTo("objectId", objId);
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {
                if (e == null) {
                    if (!list.isEmpty()) {
//                        EditText email = (EditText) findViewById(R.id.editText);
//                        EditText name = (EditText) findViewById(R.id.editText2);
//                        EditText address = (EditText) findViewById(R.id.editText3);
//                        EditText mobile = (EditText) findViewById(R.id.editText4);
//                        EditText emergencyName = (EditText) findViewById(R.id.editText5);
//                        EditText emergencyNumber = (EditText) findViewById(R.id.editText6);
                        Log.i("Testing", list.get(0).toString());
//                        address.setText();
//                        mobile.setText("9876543211");
//                        emergencyName.setText("aaa");
//                        emergencyNumber.setText("9876543211");
                    } else {
                        Log.i("Testing", "List returned by Parse is empty!");
                    }
                } else {
                    Log.i("Error!!", "Error in querying parse!");
                }
            }

            @Override
            public void done(Object o, Throwable throwable) {
//                Log.i("Testing",throwable.getMessage().toString());
                Log.i("Testing1", o.toString());

                List<ParseObject> results = ((List<ParseObject>) o);
//                Log.i("Testing1",results.toString());
//                for(ParseObject result: results)
//                {
//                    Log.i("Testing1",((String)result.get("username"))+" name: "+((String)result.get("name"))+" phoneNumber: "+((String)result.get("phoneNumber")));
//
//                }
//                Log.i("Testing2",((String)result.get("username"))+" name: "+((String)result.get("name"))+" phoneNumber: "+((String)result.get("phoneNumber")));


                if (!results.isEmpty()) {
                    result = results.get(results.size() - 1);
                    EditText offeringName = (EditText) findViewById(R.id.editText8);
                    EditText cost = (EditText) findViewById(R.id.editText9);
                    EditText cuisine = (EditText) findViewById(R.id.editText10);
                    EditText startTime = (EditText) findViewById(R.id.editText11);
                    EditText endTime = (EditText) findViewById(R.id.editText14);
                    EditText description = (EditText) findViewById(R.id.editText12);
                    EditText capacity = (EditText) findViewById(R.id.editText13);

//                    Log.i("Testing2", ((String) result.get("address")));
                    Log.i("Testing2", (result.get("cost")).toString());

                    offeringName.setText(((String) result.get("name")));
                    cost.setText((result.get("cost")).toString());
                    String cuisinesStr = TextUtils.join(",",((ArrayList<String>)result.get("cuisine")));
                    cuisine.setText(cuisinesStr);
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String startTimeStr = dateFormat.format(result.get("startTime"));
                    String endTimeStr = dateFormat.format(result.get("endTime"));
                    Log.i("Testing",startTimeStr);
//                    Log.i("Testing",endTimeStr);

                    startTime.setText(startTimeStr);
//                    endTime.setText(endTimeStr);

//                    startTime.setText((result.get("startTime")).toString());
//                    endTime.setText((result.get("endTime")).toString());
                    description.setText(((String) result.get("description")));
                    capacity.setText((result.get("capacity")).toString());
                    CheckBox packingYes = (CheckBox) findViewById(R.id.checkBox);
                    CheckBox veg = (CheckBox) findViewById(R.id.checkBox2);
                    if((result.get("packing")).toString()=="true")
                    {
                        packingYes.setChecked(true);
                    }
                    if((result.get("veg")).toString()=="true")
                    {
                        veg.setChecked(true);
                    }

                    flag = 1;
                } else {
                    Log.i("Testing1", "");
                }
//                    Log.i("Testing1",((String)result.get("username"))+" name: "+((String)result.get("name"))+" phoneNumber: "+((String)result.get("phoneNumber")));

            }
        });


    }


    public void submitForm(View view) {
        EditText email = (EditText) findViewById(R.id.editText7);
        EditText offeringname = (EditText) findViewById(R.id.editText8);
        EditText cost = (EditText) findViewById(R.id.editText9);
        EditText cuisine = (EditText) findViewById(R.id.editText10);
        EditText startTime = (EditText) findViewById(R.id.editText11);
        EditText endTime = (EditText) findViewById(R.id.editText14);

        EditText description = (EditText) findViewById(R.id.editText12);
        EditText capacity = (EditText) findViewById(R.id.editText13);

        CheckBox packingYes = (CheckBox) findViewById(R.id.checkBox);
        CheckBox veg = (CheckBox) findViewById(R.id.checkBox2);

        if(email.getText().toString().isEmpty() || offeringname.getText().toString().isEmpty() || cost.getText().toString().isEmpty() || cuisine.getText().toString().isEmpty() || description.getText().toString().isEmpty() || capacity.getText().toString().isEmpty())
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
            ParseObject testObject;
            if(flag==1)
            {
                testObject = result;
            }
//            Log.i("test123","Came in else statement 1");
            else
            {
                testObject = new ParseObject("Offering");
            }
//            ParseObject testObject = new ParseObject("Offering");
            testObject.put("username", email.getText().toString());
            testObject.put("name", offeringname.getText().toString());
            testObject.put("cost", Double.parseDouble(cost.getText().toString()));
            String cuisineVal = cuisine.getText().toString();
            String[] arr = cuisineVal.split(",");
            ArrayList<String> allCuisines = new ArrayList<String>();
            for(String c:arr)
            {
                allCuisines.add(c);
            }
            testObject.addAll("cuisine", allCuisines);
            testObject.put("description", description.getText().toString());
//            testObject.put("deadline", deadline.getText().toString());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            //dateFormat.format(cal.getTime()))

            try
            {
                testObject.put("startTime", dateFormat.parse(startTime.getText().toString()));
            }
            catch(Exception e)
            {
                Toast.makeText(this, "Error in startTime!", Toast.LENGTH_LONG).show();
            }
            try
            {
                testObject.put("endTime", dateFormat.parse(endTime.getText().toString()));
            }
            catch(Exception e)
            {
                Toast.makeText(this, "Error in endTime!", Toast.LENGTH_LONG).show();
            }

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
