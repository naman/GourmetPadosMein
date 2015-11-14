package com.mc.priveil.gourmetpadosmein;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class OfferingViewActivity extends AppCompatActivity {
//    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";
    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";
//    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";

    public String email;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String objectid;

    private String cost;
    private String description;
    private String foodname;
    private String capacity;
    private String cuisines;
    private String parse_username;
    private String name;
    private TextView food;
    private TextView cuisine_type;
    private TextView money;
    private TextView desc;
    private TextView cap;
    private String latitude;
    private String longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offering_view);

        setUpToolbar();
        setUpNavDrawer();

        // Use application class to maintain global state.

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        Log.i("Testing12", "Came here in OfferingViewActivity");

        if(isConnected() != true){
            Toast.makeText(OfferingViewActivity.this, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }

        else{
            View button_edit = findViewById(R.id.button_edit);

            button_edit.setVisibility(View.GONE);

            ParseUser.enableAutomaticUser();

            Intent intent = getIntent();
            objectid = intent.getStringExtra("objectid");
            email  = Plus.AccountApi.getAccountName(LogIn.mGoogleApiClient);
            name = null;

            ParseQuery query = new ParseQuery("Offering");
            query.whereEqualTo("objectId", objectid);
            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    if (e == null) {
                        if (!list.isEmpty()) {
                            Log.i("Testing", list.get(0).toString());
                        } else {
                            Log.i("Testing", "List returned by Parse is empty!");
                        }
                    } else {
                        Log.i("Error!!", "Error in querying parse!");
                    }
                }

                @Override
                public void done(Object o, Throwable throwable) {
                    if (o != null) {
                        List<ParseObject> itemlist;
                        itemlist = (List<ParseObject>) o;
                        Log.i("LIST", objectid);
                        Log.i("LIST", itemlist.toString());

                        for (ParseObject p : itemlist) {
                            foodname = String.valueOf(p.get("name"));
                            cost = String.valueOf(p.get("cost"));
                            description = String.valueOf(p.get("description"));
                            capacity = String.valueOf(p.get("capacity"));
                            cuisines = String.valueOf(p.get("cuisine"));
                            parse_username = String.valueOf(p.get("username"));
                            try {
                                ParseGeoPoint point = (ParseGeoPoint)p.get("Location");
                                latitude = String.valueOf(point.getLatitude());
                                longitude = String.valueOf(point.getLongitude());
                            } catch (Exception e) {
                                Log.e("test123", "Failed to get location");
                            }
                            try {
                                ParseFile fileObject = (ParseFile) p
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
                                                    Bitmap bmp = BitmapFactory
                                                            .decodeByteArray(
                                                                    data, 0,
                                                                    data.length);

                                                    // Get the ImageView from
                                                    // main.xml
                                                    ImageView image = (ImageView) findViewById(R.id.imageView3);
                                                    image.setBackgroundColor(0);

                                                    // Set the Bitmap into the
                                                    // ImageView
                                                    image.setImageBitmap(bmp);

                                                } else {
                                                    Log.d("test",
                                                            "There was a problem downloading the data.");
                                                }
                                            }
                                        });
                            } catch (Exception e) {
                                Log.d("test123", "Failed to get image!" + e.getLocalizedMessage());
                            }

                        }

                        food = (TextView) findViewById(R.id.foodname);
                        food.setTextSize(40);
                        food.setTextColor(Color.DKGRAY);
                        food.setText(foodname);

                        cuisine_type = (TextView) findViewById(R.id.cuisine);
                        cuisine_type.setTextSize(15);
                        cuisine_type.setTextColor(Color.DKGRAY);
                        cuisine_type.setText(cuisines.substring(1, cuisines.length() - 1));

                        money = (TextView) findViewById(R.id.cost);
                        money.setTextSize(20);
                        money.setTextColor(Color.DKGRAY);
                        money.setText("Cost: ₹" + cost);

                        desc = (TextView) findViewById(R.id.description);
                        desc.setTextSize(15);
                        desc.setTextColor(Color.DKGRAY);
                        desc.setText(description);

                        cap = (TextView) findViewById(R.id.capacity);
                        cap.setTextSize(15);
                        cap.setTextColor(Color.DKGRAY);
                        cap.setText("Capacity: " + capacity);


                        View button_edit = findViewById(R.id.button_edit);

                        if (email.equals(parse_username)) {
                            //show edit button
                            button_edit.setVisibility(View.VISIBLE);
                            button_edit.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View arg0) {
                                    // Start NewActivity.class
                                    Intent myIntent = new Intent(OfferingViewActivity.this,
                                            OfferingForm.class);
                                    myIntent.putExtra(MESSAGE_OBJECTID, objectid);
//                                myIntent.putExtra(MESSAGE_NAME, name);
//                                    myIntent.putExtra(MESSAGE_EMAIL, email);
                                    startActivity(myIntent);
                                }
                            });
                        } else {

                        }


                    } else
                        Log.i("Error!!", "NULL");
                }
            });


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
                                Intent n = new Intent(OfferingViewActivity.this, OfferingListActivity.class);
//                                n.putExtra(MESSAGE_NAME, name);
//                                n.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n);
                                break;

                            case R.id.profile:
                                Intent ui = new Intent(OfferingViewActivity.this, UserInfo.class);
//                                ui.putExtra(MESSAGE_NAME, name);
//                                ui.putExtra(MESSAGE_EMAIL, email);

                                startActivity(ui);
                                break;
                            case R.id.my_offerings:
                                Intent n1 = new Intent(OfferingViewActivity.this, MyOfferings.class);
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
                                startActivity(new Intent(OfferingViewActivity.this, LogIn.class));

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
    public void onStart(){
        super.onStart();
        try{
        if(!LogIn.mGoogleApiClient.isConnected()){
            startActivity(new Intent(OfferingViewActivity.this, LogIn.class));
        }}
            catch(Exception e){
                Log.e("test123", "failed with getting login details");
            }
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent intent = new Intent(this, OfferingListActivity.class);
//        intent.putExtra(MESSAGE_NAME, name);
//        intent.putExtra(MESSAGE_EMAIL, email);
        startActivity(intent);
    }

    public void takeMe(View view){
        String url = "http://maps.google.com/maps?f=d&daddr="+ latitude+","+longitude+"&dirflg=d&layer=t";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
