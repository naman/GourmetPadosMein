package com.mc.priveil.gourmetpadosmein;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OfferingForm extends AppCompatActivity {

    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";

    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public String name;
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
    private EditText pickdate2;
    private EditText picktime2;
    private DatePickerDialog.OnDateSetListener date;
    private DatePickerDialog.OnDateSetListener date1;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ImageView mImageView;
    private int PICK_IMAGE_REQUEST = 2;
    private Bitmap bitmap;

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
        setContentView(R.layout.activity_offering_form);

        Log.i("testing123", "Came to offering listing");
        setUpToolbar();
        setUpNavDrawer();
                /* Use application class to maintain global state. */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));


        Intent intent = getIntent();
        name = intent.getStringExtra(MESSAGE_NAME);
        email = intent.getStringExtra(MESSAGE_EMAIL);
        objId = intent.getStringExtra(MESSAGE_OBJECTID);
//        Log.i("testingOBJ",objId);

//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        ParseUser.enableAutomaticUser();

//        Intent intent = getIntent();
//        String email = intent.getStringExtra(OfferingListActivity.MESSAGE_EMAIL);

        EditText editText = (EditText) findViewById(R.id.editText7);
        editText.setText(email);
        editText.setKeyListener(null);

        pickdate2 = (EditText) findViewById(R.id.startdate);
        picktime2 = (EditText) findViewById(R.id.starttime);
        myCalendar = Calendar.getInstance();
        date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };
        pickdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate2.setInputType(InputType.TYPE_NULL);
//                DatePickerDialog start = new DatePickerDialog(
//                        OfferingForm.this, date1,
//                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH));
//                start.getDatePicker().setMinDate(System.currentTimeMillis());
//                start.show();
                new DatePickerDialog(OfferingForm.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        picktime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picktime2.setInputType(InputType.TYPE_NULL);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(OfferingForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        picktime2.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });




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
//                DatePickerDialog stop = new DatePickerDialog(
//                        OfferingForm.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH));
//                stop.getDatePicker().setMinDate(System.currentTimeMillis());
//                stop.show();
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
                    intent.putExtra(MESSAGE_NAME, name);
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

                    EditText startdate = (EditText) findViewById(R.id.startdate);
                    EditText starttime = (EditText) findViewById(R.id.starttime);

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

                    startdate.setText(startTimeStr.split(" ")[0]);
                    starttime.setText(startTimeStr.split(" ")[1]);

                    try {
                        ParseFile fileObject = (ParseFile) resultGlobal
                                .get("image");
                        fileObject
                                .getDataInBackground(new GetDataCallback() {

                                    public void done(byte[] data,
                                                     ParseException e) {
                                        if (e == null) {
                                            Log.d("test",
                                                    "We've got data in data.");
                                            // Decode the Byte[] into
                                            // Bitmap
                                            bitmap = BitmapFactory
                                                    .decodeByteArray(
                                                            data, 0,
                                                            data.length);

                                            // Get the ImageView from
                                            // main.xml
                                            ImageView image = (ImageView) findViewById(R.id.imageView5);
                                            image.setBackgroundColor(0);

                                            // Set the Bitmap into the
                                            // ImageView
                                            image.setImageBitmap(bitmap);

                                        } else {
                                            Log.d("test",
                                                    "There was a problem downloading the data.");
                                        }
                                    }
                                });
                    }catch(Exception e){
                        Log.d("test123", "Failed to get image!" + e.getLocalizedMessage());
                    }

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
    @Override
    public void onStart(){
        super.onStart();
        if(!LogIn.mGoogleApiClient.isConnected()){
            startActivity(new Intent(OfferingForm.this, LogIn.class));
        }
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
        String regex = "^[a-z\\sA-Z]+[,[a-z\\sA-Z]+]*$";
//        String te = "\\s";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(cuisine);

        return m.find();
    }

    boolean isAlphaNumeric(String str) {
        String regex = "^[a-zA-Z]+[0-9a-zA-Z,.\\s]*$";
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

    private void setUpToolbar() {
        Log.i("Testing12", "Came in setUpToolBar");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        Log.i("Testing12", "Came out setUpToolBar");
    }

    private void setUpNavDrawer() {
        Log.i("Testing12", "Came in setUpNav");
        if (mToolbar != null) {
            final android.support.v7.app.ActionBar ab = getSupportActionBar();
            assert ab != null;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
            mActionBarDrawerToggle.syncState();
        }
        Log.i("Testing12", "Came out setUpNav");
    }

    private void setupDrawerContent(NavigationView navigationView) {
        Log.i("Testing12", "Came in setUpDrawer");
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.offering_list:
                                Intent n = new Intent(OfferingForm.this, OfferingListActivity.class);
                                n.putExtra(MESSAGE_NAME, name);
                                n.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n);
                                break;

                            case R.id.profile:
                                Intent ui = new Intent(OfferingForm.this, UserInfo.class);
                                ui.putExtra(MESSAGE_NAME, name);
                                ui.putExtra(MESSAGE_EMAIL, email);

                                startActivity(ui);
                                break;
                            case R.id.my_offerings:
                                Intent n1 = new Intent(OfferingForm.this, MyOfferings.class);
                                n1.putExtra(MESSAGE_NAME, name);
                                n1.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n1);

                                break;

                            case R.id.log_me_out:
                                try {
                                    Plus.AccountApi.clearDefaultAccount(LogIn.mGoogleApiClient);
                                    LogIn.mGoogleApiClient.disconnect();
                                    LogIn.mGoogleApiClient.connect();
                                }catch(Exception e){
                                    Log.e("test123","Failed to Logout, might be already out?");
                                }

                                startActivity(new Intent(OfferingForm.this, LogIn.class));

                                break;


                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
        Log.i("Testing12", "Came out setUpDrawer");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView5);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            mImageView=(ImageView)findViewById(R.id.imageView5);
            mImageView.setImageBitmap(bitmap);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void gallerypick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public void camerapick(View view) {
        dispatchTakePictureIntent();
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

        EditText startdate = (EditText) findViewById(R.id.startdate);
        EditText starttime = (EditText) findViewById(R.id.starttime);
        String starttimeStr = startdate.getText().toString()+" "+starttime.getText().toString();


        EditText description = (EditText) findViewById(R.id.editText12);
        EditText capacity = (EditText) findViewById(R.id.editText13);

        CheckBox packingYes = (CheckBox) findViewById(R.id.checkBox);
        CheckBox veg = (CheckBox) findViewById(R.id.checkBox2);


        EditText lati = (EditText) findViewById(R.id.editText15);
        EditText lon = (EditText) findViewById(R.id.editText16);
        String dateValidity = "OK";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date startdateD= myCalendar.getTime(), enddateD=myCalendar.getTime();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 330);
        try
        {
            startdateD = dateFormat.parse(starttimeStr.toString());
        }

        catch(Exception e)
        {
//            Toast.makeText(this, "Error in startTime!", Toast.LENGTH_LONG).show();
            dateValidity = "ERROR IN STARTTIME";
        }

        try
        {
            enddateD = dateFormat.parse(endtimeStr.toString());

        }
        catch(Exception e)
        {
            dateValidity="ERROR IN ENDTIME";
//            Toast.makeText(this, "Error in endTime!", Toast.LENGTH_LONG).show();
        }


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

        else if(!isDouble(lati.getText().toString()) || !isDouble(lon.getText().toString()))
        {
            Toast.makeText(this, "Could not locate address", Toast.LENGTH_LONG).show();
        }
        else if(dateValidity!="OK")
        {
            Toast.makeText(this, dateValidity, Toast.LENGTH_LONG).show();
        }
        else if(!startdateD.after(cal.getTime()))
        {
            Toast.makeText(this, "Startdate-time should be after current-time", Toast.LENGTH_LONG).show();
        }
        else if(!enddateD.after(startdateD))
        {
            Toast.makeText(this, "Enddate-time should be after startdate-time", Toast.LENGTH_LONG).show();
            Log.i("talha123",cal.getTime().toString());
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
            final ParseObject testObject;
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

//            Calendar cal = Calendar.getInstance();
            //dateFormat.format(cal.getTime()))


            testObject.put("startTime",startdateD);
            testObject.put("endTime",enddateD);

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

            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imagefile = stream.toByteArray();
                ParseFile file = new ParseFile("offerimage.png", imagefile);
                file.saveInBackground();
                testObject.put("image", file);
            }catch(Exception e){
                Log.d("test123", "Failed to attach image");
            }

            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Creating new listing");
            progress.setMessage("please wait...");
            progress.show();
            testObject.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        myObjectSavedSuccessfully(testObject,progress);
                    } else {
                        myObjectSaveDidNotSucceed(progress);
                    }
                }
            });
        }
    }

    void myObjectSavedSuccessfully(ParseObject po,ProgressDialog progress){
        progress.dismiss();
        Log.i("Testing", "about to submit form 4!!!");
        Intent intent = new Intent(this, OfferingViewActivity.class);
        intent.putExtra("objectid", po.getObjectId());
        intent.putExtra("email",  email);
        intent.putExtra(OfferingViewActivity.MESSAGE_NAME, name);
        startActivity(intent);
    }

    void myObjectSaveDidNotSucceed(ProgressDialog progress){
        progress.dismiss();
        Toast.makeText(this, "Failed while trying to save, please check internet connection and try again!", Toast.LENGTH_LONG);
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

    private void updateLabel1() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        pickdate2.setText(sdf.format(myCalendar.getTime()));
    }
}
