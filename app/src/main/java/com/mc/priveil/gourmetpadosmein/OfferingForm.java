package com.mc.priveil.gourmetpadosmein;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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




        ParseQuery query = new ParseQuery("User");
        query.whereEqualTo("username", email);
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {
                if (e == null) {
                    if (!list.isEmpty()) {
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


                if (!results.isEmpty()) {
                    result = results.get(results.size() - 1);
                    EditText lati = (EditText) findViewById(R.id.editText15);
                    EditText lon = (EditText) findViewById(R.id.editText16);
                    lati.setText(((String) result.get("Latitude")));
                    lon.setText(((String) result.get("Longitude")));
                    Log.i("Testing", "Came to lat and long");
//                        String longitude = (String)result.get("Longitude");


                } else {
                    Log.i("Testing1", "");
                }
//                    Log.i("Testing1",((String)result.get("username"))+" name: "+((String)result.get("name"))+" phoneNumber: "+((String)result.get("phoneNumber")));

            }
        });







        String objId = "4v0XFHmrue";
//        String tempN = "aaa"
        query = new ParseQuery("Offering");
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
                    Log.i("Testing",endTimeStr);

                    startTime.setText(startTimeStr);
                    endTime.setText(endTimeStr);

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


    public boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }



    public static boolean isDouble(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    boolean isNumeric(String cost) {
        String regex = "^[0-9]+$";
//        String te = "\\s";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(cost);

        if(m.find()) {
            return true;
        } else {
            return false;
        }
    }

    boolean isCuisine(String cuisine) {
        String regex = "^[a-zA-Z]+[,[a-zA-Z]+]*$";
//        String te = "\\s";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(cuisine);

        if(m.find()) {
            return true;
        } else {
            return false;
        }
    }

    boolean isAlphaNumeric(String str) {
        String regex = "^[a-zA-Z]+[0-9a-zA-Z\\s]*$";
//        String te = "\\s";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(str);

        if(m.find()) {
            return true;
        } else {
            return false;
        }
    }

    boolean isValidDate(String date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try
        {
            Date date1 = dateFormat.parse(date);
        }
        catch (java.text.ParseException e)
        {
            return false;
        }
        return true;

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

        else if(!isAlphaNumeric(offeringname.getText().toString()))
        {
            Toast.makeText(this, "Enter a valid Offering Name!![Alphanumeric string starting with letter]", Toast.LENGTH_LONG).show();
        }

        else if(!isDouble(cost.getText().toString()) || Double.parseDouble(cost.getText().toString())<0)
        {
            Toast.makeText(this, "Enter a vaid Cost!![Numeric value only]", Toast.LENGTH_LONG).show();
        }

        else if(!isCuisine(cuisine.getText().toString()))
        {
            Toast.makeText(this, "Enter valid comma seperated cuisines!!", Toast.LENGTH_LONG).show();
        }

        else if(!isAlphaNumeric(description.getText().toString()))
        {
            Toast.makeText(this, "Enter a valid Description!![Alphanumeric string starting with letter]", Toast.LENGTH_LONG).show();
        }

        else if(!isNumeric(capacity.getText().toString()) || capacity.getText().toString().length()>3 || Integer.parseInt(capacity.getText().toString())<=0)
        {
            Toast.makeText(this, "Enter a vaild Capacity!![Numeric value only > 0]", Toast.LENGTH_LONG).show();
        }

        else if(!isValidDate(startTime.getText().toString()))
        {
            Toast.makeText(this, "Enter Start Date in the given format only!!", Toast.LENGTH_LONG).show();
        }

        else if(!isValidDate(endTime.getText().toString()))
        {
            Toast.makeText(this, "Enter End Date in the given format only!!", Toast.LENGTH_LONG).show();
        }

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



//            String latitude;
//            String longitude;








            EditText lati = (EditText) findViewById(R.id.editText15);
            EditText lon = (EditText) findViewById(R.id.editText16);

            testObject.put("Latitude", lati.getText().toString());
            testObject.put("Longitude", lon.getText().toString());
            testObject.put("name", offeringname.getText().toString());
            testObject.put("cost", Double.parseDouble(cost.getText().toString()));
            String cuisineVal = cuisine.getText().toString();
            String[] arr = cuisineVal.split(",");
            ArrayList<String> allCuisines = new ArrayList<String>();
            for(String c:arr)
            {
                allCuisines.add(c);
            }
            testObject.put("cuisine", allCuisines);
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
