package com.mc.priveil.gourmetpadosmein;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserViewProfile extends AppCompatActivity {

//    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";
//    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
    public static final int PICK_CONTACT = 3;
    public String name;
    public int flag = 0;
    public ParseObject result = null;
    private TextView emergencyName;
    private TextView emergencyNumber;
    private Button emergency_button;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String email;
    private Bitmap bitmap;

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_profile);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Title");
        collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);

        setUpToolbar();
        setUpNavDrawer();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if(isConnected() != true){
            Toast.makeText(UserViewProfile.this, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }

        else{

//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
            ParseUser.enableAutomaticUser();
            Intent intent = getIntent();
            Log.i("test123", "Came here!!!");
            name = null;
            try {
                email = Plus.AccountApi.getAccountName(LogIn.mGoogleApiClient);
            } catch(Exception e){
                LogIn.mGoogleApiClient.connect();
            }

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

            Log.i("test123", "Came here again???!!!");


//            emergency_button = (Button) findViewById(R.id.emergency);
//            emergency_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                Intent intent = new Intent(Intent.ACTION_PICK, Contacts.People.CONTENT_URI);
////                startActivityForResult(intent, PICK_CONTACT);
//
//                    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//                    startActivityForResult(contactPickerIntent, PICK_CONTACT);
//
//                }
//            });
            String tolookup = email;

            Log.i("test123", "Dekho Maggi aa gayi 222 !!");
            try{
                Intent currentIntent = getIntent();
                String userBeingViewed = currentIntent.getStringExtra("viewingUser");
                Log.d("who","he:"+userBeingViewed);
                if (userBeingViewed != null) {
                    tolookup = userBeingViewed;
                }
            }catch(Exception e){
                Log.d("shit", "oh no "+e.getMessage());
            }
            TextView emailView = (TextView) findViewById(R.id.textView);
            emailView.setText(tolookup);
            if(tolookup.equals(email)){
                findViewById(R.id.button6).setVisibility(View.VISIBLE);
            }
//            RatingBar rb = (RatingBar) findViewById(R.id.ratingBar2);
//            rb.setRating(3.75f);
            ParseQuery query = new ParseQuery("User");
            query.whereEqualTo("username", tolookup);
            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    if (e == null) {
                        if (!list.isEmpty()) {
                            Log.i("Testing",list.get(0).toString());
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
                        Log.i("test123","Dekho Maggi aa gayi!!");
                        result = results.get(results.size()-1);
                        if(name==null)
                        {
                            TextView perName = (TextView) findViewById(R.id.textView2);
                            perName.setText(((String) result.get("name")));
                        }
                        else {
                            TextView perName = (TextView) findViewById(R.id.textView2);
                            perName.setText(name);
                        }
                        TextView address = (TextView) findViewById(R.id.textView3);
                        TextView mobile = (TextView) findViewById(R.id.textView4);
                        emergencyName = (TextView) findViewById(R.id.textView5);
                        emergencyNumber = (TextView) findViewById(R.id.textView6);
                        Log.i("Testing2",((String) result.get("address")));
                        Log.i("Testing2", ((String) result.get("name")));
                        address.setText(((String) result.get("address")));
                        mobile.setText((String)result.get("phoneNumber"));
                        emergencyName.setText(((String) result.get("emergencyContactName")));
                        emergencyNumber.setText(((String) result.get("emergencyContactNumber")));
                        RatingBar rb = (RatingBar) findViewById(R.id.ratingBar2);
                        rb.setRating(Float.parseFloat(result.get("rating").toString()));
                        TextView rat = (TextView) findViewById(R.id.textView7);
                        rat.setText(result.get("rating").toString());
                        try {
                            ParseFile fileObject = (ParseFile) result
                                    .get("image");
                            fileObject
                                    .getDataInBackground(new GetDataCallback() {

                                        @SuppressLint("NewApi")
                                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
                                                CollapsingToolbarLayout image = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                                                Drawable d = new BitmapDrawable(getResources(), bitmap);
                                                image.setBackground(d);
//                                                image.setBackgroundColor(0);

                                                // Set the Bitmap into the
                                                // ImageView
//                                                image.setImageBitmap(bitmap);

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
                        Log.i("Testing1","User Profile is not created");
                        Intent intent = new Intent(UserViewProfile.this, UserInfo.class);
//                        intent.putExtra(MESSAGE_NAME, name);
//                        intent.putExtra(MESSAGE_EMAIL, email);
                        startActivity(intent);
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
                                Intent n = new Intent(UserViewProfile.this, OfferingListActivity.class);
//                                n.putExtra(MESSAGE_NAME, name);
//                                n.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n);
                                break;

                            case R.id.profile:
                            /*
                                Intent ui = new Intent(OfferingViewActivity.this, UserInfo.class);
                                ui.putExtra(MESSAGE_NAME, name);
                                ui.putExtra(MESSAGE_EMAIL, email);

                                startActivity(ui);*/
                                break;
                            case R.id.my_offerings:
                                Intent n1 = new Intent(UserViewProfile.this, MyOfferings.class);
//                                n1.putExtra(MESSAGE_NAME, name);
//                                n1.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n1);

                                break;

                            case R.id.log_me_out:
                                try {
                                    Plus.AccountApi.clearDefaultAccount(LogIn.mGoogleApiClient);
                                    LogIn.mGoogleApiClient.disconnect();
                                    LogIn.mGoogleApiClient.connect();
                                } catch (Exception e) {
                                    Log.e("test123", "Failed to Logout, might be already out?");
                                }

                                startActivity(new Intent(UserViewProfile.this, LogIn.class));

                                break;


                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
        Log.i("Testing12", "Came out setUpDrawer");
    }


    public void editProfile(View view)
    {
        Intent n = new Intent(UserViewProfile.this, UserInfo.class);
//                                n.putExtra(MESSAGE_NAME, name);
//                                n.putExtra(MESSAGE_EMAIL, email);
        startActivity(n);
    }

    public void sendEmail(View view)
    {
        TextView v = (TextView) findViewById(R.id.textView);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", v.getText().toString(), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void sendCall(View view)
    {
        TextView v = (TextView) findViewById(R.id.textView4);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + v.getText()));
        startActivity(intent);
    }

    public void sendEmergencyCall(View view)
    {
        TextView v = (TextView) findViewById(R.id.textView6);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + v.getText()));
        startActivity(intent);
    }
    public void viewAddressOnMap(View view) {
        TextView v = (TextView) findViewById(R.id.textView3);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q="+v.getText().toString()));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_view_profile, menu);
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
