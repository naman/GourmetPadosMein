package com.mc.priveil.gourmetpadosmein.Forms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mc.priveil.gourmetpadosmein.Models.AuthHelper;
import com.mc.priveil.gourmetpadosmein.MyOfferingsActivity;
import com.mc.priveil.gourmetpadosmein.OfferingListActivity;
import com.mc.priveil.gourmetpadosmein.R;
import com.mc.priveil.gourmetpadosmein.Utils.GPMNotificationReceiver;
import com.mc.priveil.gourmetpadosmein.ViewUserProfileActivity;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditUserProfileActivity extends AppCompatActivity {
    public static final int PICK_CONTACT = 3;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public String name;
    public int flag = 0;
    public ParseObject result = null;
    double lat;
    double longi;
    double rating = 0;
    double numRatings = 0;
    private EditText emergencyName;
    private EditText emergencyNumber;
    private Button emergency_button;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String email;
    private ImageView mImageView;
    private int PICK_IMAGE_REQUEST = 2;
    private Bitmap bitmap;
    private Boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        setUpToolbar();
        setUpNavDrawer();

                /* Use application class to maintain global state. */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if(isConnected() != true){
            Toast.makeText(EditUserProfileActivity.this, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }



        else{
            edit = false;
//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
            ParseUser.enableAutomaticUser();
            Intent intent = getIntent();
            Log.i("test123", "Came here!!!");
            name = null;
            email = (new AuthHelper(EditUserProfileActivity.this)).getLoggedInUserEmail();

            String sidebar_tap = "F";
            try {
                sidebar_tap = intent.getStringExtra(OfferingListActivity.SIDEBAR_TAP);
                Log.d("test123",sidebar_tap);
            }catch(Exception e){
                Log.d("test123","failed to get tap");
            }
            if("T".equals(sidebar_tap)){
                edit = true;
                Log.d("test123","Hiding");
                View skoop = findViewById(R.id.button3);
                skoop.setVisibility(View.GONE);
            }
            EditText editText = (EditText) findViewById(R.id.editText);
            editText.setText(email);
            editText.setKeyListener(null);

            Log.i("test123", "Came here again!!!");


            emergency_button = (Button) findViewById(R.id.emergency);
            emergency_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, Contacts.People.CONTENT_URI);
//                startActivityForResult(intent, PICK_CONTACT);

                    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(contactPickerIntent, PICK_CONTACT);

                }
            });

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
                        emergencyName = (EditText) findViewById(R.id.editText5);
                        emergencyNumber = (EditText) findViewById(R.id.editText6);
                        Log.i("Testing2",((String) result.get("address")));
                        Log.i("Testing2",((String) result.get("name")));
                        address.setText(((String) result.get("address")));
                        mobile.setText((String)result.get("phoneNumber"));
                        emergencyName.setText(((String) result.get("emergencyContactName")));
                        emergencyNumber.setText(((String) result.get("emergencyContactNumber")));
                        rating = Double.parseDouble(result.get("rating").toString());
                        numRatings = Double.parseDouble(result.get("numRatings").toString());
                        try {
                            ParseFile fileObject = (ParseFile) result
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
                                                Drawable d = new BitmapDrawable(getResources(), bitmap);
                                                image.setBackground(d);
                                            } else {
                                                Log.d("test",
                                                        "There was a problem downloading the data.");
                                            }
                                        }
                                    });
                        }catch(Exception e){
                            Log.d("test123", "Failed to get image!" + e.getLocalizedMessage());
                        }

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
                                startActivity(new Intent(EditUserProfileActivity.this, OfferingListActivity.class));
                                break;

                            case R.id.profile:
                                startActivity(new Intent(EditUserProfileActivity.this, ViewUserProfileActivity.class));
                                break;

                            case R.id.my_offerings:
                                startActivity(new Intent(EditUserProfileActivity.this, MyOfferingsActivity.class));
                                break;

                            case R.id.log_me_out:
                                (new AuthHelper(EditUserProfileActivity.this)).logOut();
                                break;
                        }


                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
        Log.i("Testing12", "Came out setUpDrawer");
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
//        intent.putExtra(MESSAGE_EMAIL, email);
        intent.putExtra("SKIPCLICK", "Y");
//            intent.putExtra(MESSAGE_EMAIL, email.getText().toString());
        startActivity(intent);
    }

    public void submitForm(View view) {
        if(isConnected() != true){
            Toast.makeText(EditUserProfileActivity.this, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }



        else {

            final EditText email = (EditText) findViewById(R.id.editText);
            final EditText name = (EditText) findViewById(R.id.editText2);
            EditText address = (EditText) findViewById(R.id.editText3);
            EditText mobile = (EditText) findViewById(R.id.editText4);
            EditText emergencyName = (EditText) findViewById(R.id.editText5);
            EditText emergencyNumber = (EditText) findViewById(R.id.editText6);

//        address.setText("aa");
//        mobile.setText("9876543211");
//        emergencyName.setText("aaa");
//        emergencyNumber.setText("9876543211");
            if (email.getText().toString().isEmpty() || name.getText().toString().isEmpty() || address.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() || emergencyName.getText().toString().isEmpty() || emergencyNumber.getText().toString().isEmpty()) {
                Toast.makeText(this, "All form fields are required!!", Toast.LENGTH_LONG).show();
            } else if (mobile.getText().length() != 10 || !isNumeric2(mobile.getText().toString())) {
                Toast.makeText(this, "Enter a valid Mobile Number!!", Toast.LENGTH_LONG).show();
            }
/*
        else if(emergencyNumber.getText().length()!=10 || !isNumeric2(emergencyNumber.getText().toString()))
        {
            Toast.makeText(this, "Enter a valid Emergency Mobile Number!!", Toast.LENGTH_LONG).show();
        }
*/
            else if (!isName(name.getText().toString())) {
                Toast.makeText(this, "Enter a valid Name!!", Toast.LENGTH_LONG).show();
            }

/*
        else if(!isName(emergencyName.getText().toString()))
        {
            Toast.makeText(this, "Enter a valid Emergency Contact Name!!", Toast.LENGTH_LONG).show();
        }
*/

            else if (!addressValidation(address.getText().toString())) {
                Toast.makeText(this, "Enter a valid address!!Only [a-zA-Z0-9.,-] are allowed", Toast.LENGTH_LONG).show();
            } else if (!getLatLong(address.getText().toString())) {
                Toast.makeText(this, "Couldn't locate Address!!", Toast.LENGTH_LONG).show();
            } else {
                final ParseObject testObject;
                if (flag == 1) {
                    testObject = result;
                }
//            Log.i("test123","Came in else statement 1");
                else {
                    testObject = new ParseObject("User");
                }
                testObject.put("username", email.getText().toString());
                testObject.put("rating", rating);
                testObject.put("numRatings", numRatings);
                testObject.put("name", name.getText().toString());
                testObject.put("address", address.getText().toString());

                ParseGeoPoint point = new ParseGeoPoint(lat, longi);
//            ParseObject object = new ParseObject("PlaceObject");
                testObject.put("Location", point);

//            testObject.put("Latitude", String.valueOf(lat));
//            testObject.put("Longitude", String.valueOf(longi));

                testObject.put("phoneNumber", mobile.getText().toString());
                testObject.put("emergencyContactName", emergencyName.getText().toString());
                testObject.put("emergencyContactNumber", emergencyNumber.getText().toString());
//            Log.i("test123","Came in else statement 2");
                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imagefile = stream.toByteArray();
                    ParseFile file = new ParseFile("offerimage.png", imagefile);
                    file.saveInBackground();
                    testObject.put("image", file);
                } catch (Exception e) {
                    Log.d("test123", "Failed to attach image");
                }


                final ProgressDialog progress = new ProgressDialog(this);
                progress.setTitle("Updating your Profile!");
                progress.setMessage("Please wait...");
                try{ progress.show(); } catch(Exception exc){ }
                testObject.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        try{ progress.dismiss(); } catch(Exception exc){ }
                        if (e == null) {
                            myObjectSavedSuccessfully(testObject, email, name, progress);

                        } else {
                            myObjectSaveDidNotSucceed(progress);
                        }
                    }
                });


            }
        }
    }
    void myObjectSavedSuccessfully(ParseObject po,EditText email,EditText name,ProgressDialog progress){
        Intent intent;
        if(edit){
            intent = new Intent(this, ViewUserProfileActivity.class);
        }else {
            Log.i("Testing", "about to submit form 4!!!");
            intent = new Intent(this, OfferingListActivity.class);
        }
        startActivity(intent);
    }

    void myObjectSaveDidNotSucceed(ProgressDialog progress){
        Toast.makeText(this, "Failed while trying to save, please check internet connection and try again!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = GPMNotificationReceiver.getCompressedImage(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView5);
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                imageView.setBackground(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
             bitmap = GPMNotificationReceiver.getCompressedImage((Bitmap) extras.get("data"));
            mImageView=(ImageView)findViewById(R.id.imageView5);
             Drawable d = new BitmapDrawable(getResources(), bitmap);
             mImageView.setBackground(d);
        }
        if (requestCode == PICK_CONTACT && resultCode == Activity.RESULT_OK) {
             Log.d("test123", "Came in pick contact");
                   /* Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndexOrThrow(Contacts.People.NAME));
                        int phone = c.getInt(c.getColumnIndexOrThrow(Contacts.People.N));
                        //
                        Log.d("USerInfo",name);
                        Log.d("USerInfo", String.valueOf(phone));

                        emergencyName.setText(name);
                        emergencyNumber.setText(String.valueOf(phone));
                    }*/
                Cursor cursor = null;
                try {
                    String phoneNo = null;
                    String name = null;
                    // getData() method will have the Content Uri of the selected contact
                    Uri uri = data.getData();
                    //Query the content uri
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    // column index of the phone number
                    int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    // column index of the contact name
                    int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    phoneNo = cursor.getString(phoneIndex);
                    name = cursor.getString(nameIndex);
                    // Set the value to the textviews
                    emergencyName = (EditText) findViewById(R.id.editText5);
                    emergencyNumber = (EditText) findViewById(R.id.editText6);
                    emergencyName.setText(name);
                    emergencyNumber.setText(phoneNo);
                } catch (Exception e) {
               Log.d("test123","Exception in contact!!!");
//      Log.d("test123",String.valueOf(e.printStackTrace());
                }
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

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
