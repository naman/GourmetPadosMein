package com.mc.priveil.gourmetpadosmein;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OfferingForm extends AppCompatActivity {

    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";

    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";

    public String name2;
    public String email;
    public String objId;
    public int flag = 0;
    public ParseObject result = null;
    public ParseObject resultGlobal = null;
    Calendar myCalendar = Calendar.getInstance();
    private EditText pickdate;
    private EditText picktime;
    private EditText pickdate1;
    private EditText picktime1;

    private DatePickerDialog.OnDateSetListener date;
    private DatePickerDialog.OnDateSetListener date1;

    public static boolean isDouble(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("testing123", "Came to offering listing");

        setContentView(R.layout.activity_offering_form);
        Intent intent = getIntent();
        name2 = intent.getStringExtra(MESSAGE_NAME);
        email = intent.getStringExtra(MESSAGE_EMAIL);
        objId = intent.getStringExtra(MESSAGE_OBJECTID);

//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        ParseUser.enableAutomaticUser();

//        Intent intent = getIntent();
//        String email = intent.getStringExtra(OfferingListActivity.MESSAGE_EMAIL);

        EditText editText = (EditText) findViewById(R.id.editText7);
        editText.setText(email);
        editText.setKeyListener(null);

        pickdate1 = (EditText) findViewById(R.id.enddate);
        picktime1 = (EditText) findViewById(R.id.endtime);
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        pickdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate1.setInputType(InputType.TYPE_NULL);
                new DatePickerDialog(OfferingForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        picktime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picktime1.setInputType(InputType.TYPE_NULL);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(OfferingForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        picktime1.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


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
                    Log.i("Testing1", "why did it come here?");
                    Intent intent = new Intent(OfferingForm.this, UserInfo.class);
                    intent.putExtra(MESSAGE_NAME, name2);
                    intent.putExtra(MESSAGE_EMAIL, email);
                    startActivity(intent);
                }
//                    Log.i("Testing1",((String)result.get("username"))+" name: "+((String)result.get("name"))+" phoneNumber: "+((String)result.get("phoneNumber")));

            }
        });








//        String objId = "lol";
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
                    resultGlobal = results.get(results.size() - 1);
                    EditText offeringName = (EditText) findViewById(R.id.editText8);
                    EditText cost = (EditText) findViewById(R.id.editText9);
                    EditText cuisine = (EditText) findViewById(R.id.editText10);
                    EditText enddate = (EditText) findViewById(R.id.enddate);
                    EditText endtime = (EditText) findViewById(R.id.endtime);

//                    EditText startTime = (EditText) findViewById(R.id.editText11);
//                    EditText endTime = (EditText) findViewById(R.id.editText14);
                    EditText description = (EditText) findViewById(R.id.editText12);
                    EditText capacity = (EditText) findViewById(R.id.editText13);

//                    Log.i("Testing2", ((String) resultGlobal.get("address")));
                    Log.i("Testing2", (resultGlobal.get("cost")).toString());

                    offeringName.setText(((String) resultGlobal.get("name")));
                    cost.setText((resultGlobal.get("cost")).toString());
                    String cuisinesStr = TextUtils.join(",",((ArrayList<String>)resultGlobal.get("cuisine")));
                    cuisine.setText(cuisinesStr);
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


                    String startTimeStr = dateFormat.format(resultGlobal.get("startTime"));
                    String endTimeStr = dateFormat.format(resultGlobal.get("endTime"));

                    enddate.setText(endTimeStr.split(" ")[0]);
                    endtime.setText(endTimeStr.split(" ")[1]);



                    Log.i("Testing",startTimeStr);
                    Log.i("Testing",endTimeStr);

//
//                    picktime.setText(startTimeStr);
//                    picktime.setText(endTimeStr);

//                    startTime.setText((resultGlobal.get("startTime")).toString());
//                    endTime.setText((resultGlobal.get("endTime")).toString());
                    description.setText(((String) resultGlobal.get("description")));
                    capacity.setText((resultGlobal.get("capacity")).toString());
                    CheckBox packingYes = (CheckBox) findViewById(R.id.checkBox);
                    CheckBox veg = (CheckBox) findViewById(R.id.checkBox2);
                    if((resultGlobal.get("packing")).toString()=="true")
                    {
                        packingYes.setChecked(true);
                    }
                    if((resultGlobal.get("veg")).toString()=="true")
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

    boolean isNumeric(String cost) {
        String regex = "^[0-9]+$";
//        String te = "\\s";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(cost);

        return m.find();
    }

    boolean isCuisine(String cuisine) {
        String regex = "^[a-zA-Z]+[,[a-zA-Z]+]*$";
//        String te = "\\s";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(cuisine);

        return m.find();
    }

    boolean isAlphaNumeric(String str) {
        String regex = "^[a-zA-Z]+[0-9a-zA-Z\\s]*$";
//        String te = "\\s";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(str);

        return m.find();
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
//        EditText startTime = (EditText) findViewById(R.id.);
//        EditText endTime = (EditText) findViewById(R.id.editText14);
        EditText enddate = (EditText) findViewById(R.id.enddate);
        EditText endtime = (EditText) findViewById(R.id.endtime);
        String endtimeStr = enddate.getText().toString()+" "+endtime.getText().toString();

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

/*
        else if(!isValidDate(startTime.getText().toString()))
        {
            Toast.makeText(this, "Enter Start Date in the given format only!!", Toast.LENGTH_LONG).show();
        }

        else if(!isValidDate(endTime.getText().toString()))
        {
            Toast.makeText(this, "Enter End Date in the given format only!!", Toast.LENGTH_LONG).show();
        }
*/

        else
        {
            ParseObject testObject;
            Log.i("Testing","about to submit form 3!!!");
            if(flag==1)
            {
                testObject = resultGlobal;
            }
//            Log.i("test123","Came in else statement 1");
            else
            {
                testObject = new ParseObject("Offering");
            }
            Log.i("Testing","about to submit form 2!!!");
//  ParseObject testObject = new ParseObject("Offering");
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
//            testObject.put("endTime", endtimeStr);

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Calendar cal = Calendar.getInstance();
            //dateFormat.format(cal.getTime()))

            try
            {
                testObject.put("startTime", cal.getTime());
            }

            catch(Exception e)
            {
                Toast.makeText(this, "Error in startTime!", Toast.LENGTH_LONG).show();
            }

            try
            {
                testObject.put("endTime", dateFormat.parse(endtimeStr.toString()));
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
            Log.i("Testing", "about to submit form!!!");
            testObject.saveInBackground();
            Log.i("Testing", "about to submit form 4!!!");
            Intent intent = new Intent(this, OfferingListActivity.class);
            intent.putExtra(MESSAGE_EMAIL,email.getText().toString());
            intent.putExtra(MESSAGE_NAME, name2);

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
    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        pickdate1.setText(sdf.format(myCalendar.getTime()));
    }
}
