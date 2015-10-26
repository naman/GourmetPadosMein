package com.mc.priveil.gourmetpadosmein;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfo extends AppCompatActivity {
    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";
    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
    public String name;
    public int flag = 0;
    public ParseObject result = null;
    double lat;
    double longi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info2);
//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        ParseUser.enableAutomaticUser();
        Intent intent = getIntent();
        Log.i("test123", "Came here!!!");
        name = intent.getStringExtra(MESSAGE_NAME);
        String email = intent.getStringExtra(MESSAGE_EMAIL);

        String sidebar_tap = "F";
        try {
            sidebar_tap = intent.getStringExtra(OfferingListActivity.SIDEBAR_TAP);
            Log.d("test123",sidebar_tap);
        }catch(Exception e){
            Log.d("test123","failed to get tap");
        }
        if("T".equals(sidebar_tap)){
            Log.d("test123","Hiding");
            View skoop = findViewById(R.id.button3);
            skoop.setVisibility(View.GONE);
        }
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(email);
        editText.setKeyListener(null);

        Log.i("test123", "Came here again!!!");

        ParseQuery query = new ParseQuery("User");
        query.whereEqualTo("username", email);
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {
                if (e == null) {
                    if (!list.isEmpty()) {
                        EditText email = (EditText) findViewById(R.id.editText);
                        EditText name = (EditText) findViewById(R.id.editText2);
                        EditText address = (EditText) findViewById(R.id.editText3);
                        EditText mobile = (EditText) findViewById(R.id.editText4);
                        EditText emergencyName = (EditText) findViewById(R.id.editText5);
                        EditText emergencyNumber = (EditText) findViewById(R.id.editText6);
                        Log.i("Testing",list.get(0).toString());
//                        address.setText();
//                        mobile.setText("9876543211");
//                        emergencyName.setText("aaa");
//                        emergencyNumber.setText("9876543211");
                    }
                    else {
                        Log.i("Testing","List returned by Parse is empty!");
                    }
                } else {
                    Log.i("Error!!", "Error in querying parse!");
                }
            }

            @Override
            public void done(Object o, Throwable throwable) {
//                Log.i("Testing",throwable.getMessage().toString());
                Log.i("Testing1",o.toString());

                List<ParseObject> results = ((List<ParseObject>)o);
//                Log.i("Testing1",results.toString());
//                for(ParseObject result: results)
//                {
//                    Log.i("Testing1",((String)result.get("username"))+" name: "+((String)result.get("name"))+" phoneNumber: "+((String)result.get("phoneNumber")));
//
//                }
//                Log.i("Testing2",((String)result.get("username"))+" name: "+((String)result.get("name"))+" phoneNumber: "+((String)result.get("phoneNumber")));


                if(!results.isEmpty()) {
                    result = results.get(results.size()-1);
                    if(name==null)
                    {
                        EditText perName = (EditText) findViewById(R.id.editText2);
                        perName.setText(((String) result.get("name")));
                    }
                    else {
                        EditText perName = (EditText) findViewById(R.id.editText2);
                        perName.setText(name);
                    }
                    EditText address = (EditText) findViewById(R.id.editText3);
                    EditText mobile = (EditText) findViewById(R.id.editText4);
                    EditText emergencyName = (EditText) findViewById(R.id.editText5);
                    EditText emergencyNumber = (EditText) findViewById(R.id.editText6);
                    Log.i("Testing2",((String) result.get("address")));
                    Log.i("Testing2",((String) result.get("name")));
                    address.setText(((String) result.get("address")));
                    mobile.setText((String)result.get("phoneNumber"));
                    emergencyName.setText(((String) result.get("emergencyContactName")));
                    emergencyNumber.setText(((String) result.get("emergencyContactNumber")));
                    flag = 1;
                }
                else {
                    Log.i("Testing1","");
                }
//                    Log.i("Testing1",((String)result.get("username"))+" name: "+((String)result.get("name"))+" phoneNumber: "+((String)result.get("phoneNumber")));

            }
        });



//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        Log.i("test123", "Came here also!!!");
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

    public boolean isName(String name) {
        String regex = "[a-zA-Z\\s]+";
//        String te = "\\s";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(name);

        return m.find();
    }


    boolean isNumeric2(String phone) {
        String regex = "^[0-9]+$";
//        String te = "\\s";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(phone);

        return m.find();
    }
    boolean addressValidation(String address) {
        String regex = "^[a-zA-Z0-9.,-.\\s]+$";
//        String te = "\\s";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(address);

        return m.find();
    }


    boolean getLatLong(String addr) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        try
        {
            addresses = geocoder.getFromLocationName(addr, 1);
        }
        catch(Exception e)
        {
            return false;
        }
        if (addresses.size() > 0) {
            lat = addresses.get(0).getLatitude();
            longi = addresses.get(0).getLongitude();

            return true;
        }
        return false;
    }


    public void onSkip(View view) {
        Intent intent = new Intent(this, OfferingListActivity.class);
        EditText emailBox = (EditText) findViewById(R.id.editText);
        String email = emailBox.getText().toString();
        intent.putExtra(MESSAGE_EMAIL, email);
//            intent.putExtra(MESSAGE_EMAIL, email.getText().toString());
        startActivity(intent);
    }

    public void submitForm(View view) {
        EditText email = (EditText) findViewById(R.id.editText);
        EditText name = (EditText) findViewById(R.id.editText2);
        EditText address = (EditText) findViewById(R.id.editText3);
        EditText mobile = (EditText) findViewById(R.id.editText4);
        EditText emergencyName = (EditText) findViewById(R.id.editText5);
        EditText emergencyNumber = (EditText) findViewById(R.id.editText6);

//        address.setText("aa");
//        mobile.setText("9876543211");
//        emergencyName.setText("aaa");
//        emergencyNumber.setText("9876543211");
        if(email.getText().toString().isEmpty() || name.getText().toString().isEmpty() || address.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() || emergencyName.getText().toString().isEmpty() || emergencyNumber.getText().toString().isEmpty())
        {
            Toast.makeText(this, "All form fields are required!!", Toast.LENGTH_LONG).show();
        } else if (mobile.getText().length()!=10 || !isNumeric2(mobile.getText().toString()))
        {
            Toast.makeText(this, "Enter a valid Mobile Number!!", Toast.LENGTH_LONG).show();
        }
        else if(emergencyNumber.getText().length()!=10 || !isNumeric2(emergencyNumber.getText().toString()))
        {
            Toast.makeText(this, "Enter a valid Emergency Mobile Number!!", Toast.LENGTH_LONG).show();
        }
        else if(!isName(name.getText().toString()))
        {
            Toast.makeText(this, "Enter a valid Name!!", Toast.LENGTH_LONG).show();
        }

        else if(!isName(emergencyName.getText().toString()))
        {
            Toast.makeText(this, "Enter a valid Emergency Contact Name!!", Toast.LENGTH_LONG).show();
        }

        else if(!addressValidation(address.getText().toString()))
        {
            Toast.makeText(this, "Enter a valid address!!Only [a-zA-Z0-9.,-] are allowed", Toast.LENGTH_LONG).show();
        }

        else if(!getLatLong(address.getText().toString())) {
            Toast.makeText(this, "Couldn't locate Address!!", Toast.LENGTH_LONG).show();
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
                testObject = new ParseObject("User");
            }
            testObject.put("username", email.getText().toString());
            testObject.put("name", name.getText().toString());
            testObject.put("address", address.getText().toString());

            testObject.put("Latitude", String.valueOf(lat));
            testObject.put("Longitude", String.valueOf(longi));

            testObject.put("phoneNumber", mobile.getText().toString());
            testObject.put("emergencyContactName", emergencyName.getText().toString());
            testObject.put("emergencyContactNumber", emergencyNumber.getText().toString());
//            Log.i("test123","Came in else statement 2");
            testObject.saveInBackground();
            Intent intent = new Intent(this, OfferingListActivity.class);
            intent.putExtra(MESSAGE_EMAIL, email.getText().toString());
            intent.putExtra(MESSAGE_NAME, name.getText().toString());
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
