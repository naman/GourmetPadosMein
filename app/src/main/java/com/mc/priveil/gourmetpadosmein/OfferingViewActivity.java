package com.mc.priveil.gourmetpadosmein;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mc.priveil.gourmetpadosmein.Forms.OfferingForm;
import com.mc.priveil.gourmetpadosmein.Models.AuthHelper;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class OfferingViewActivity extends AppCompatActivity {
//    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";
    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";
//    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
//    public String fromWhereItCame = "null";
    public ParseObject result = null;
    public String email;
    Calendar cal = Calendar.getInstance();
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String objectid;
    private String cost;
    private String description;
    private String foodname;
    private String capacity;
    private String cuisines;
    private String start;
    private String finishTime;
    private String parse_username;
    private String applied;
    private List<String> bhukkads;
    //    private String name;
    private TextView food;
    private TextView cuisine_type;
    private TextView money;
    private TextView desc;
    private TextView cap;
    private TextView when;
    private String latitude;
    private String longitude;
    private Integer currentLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offering_view);

        setUpToolbar();
        setUpNavDrawer();

        // Use application class to maintain global state.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_offering_view, menu);
        return true;
    }

    private void loadOffering(){
        final LinearLayout visitor = (LinearLayout) findViewById(R.id.visitor);
        final LinearLayout owner = (LinearLayout) findViewById(R.id.owner);
        visitor.setVisibility(View.GONE);
        owner.setVisibility(View.GONE);

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
            final Button button_apply = (Button) findViewById(R.id.button_apply);
            View button_view_guests = findViewById(R.id.button_view_guests);
            final View button_end_offering = findViewById(R.id.button_end_offering);
            final View button_view_host = findViewById(R.id.button_view_host);

            ParseUser.enableAutomaticUser();

            Intent intent = getIntent();
            objectid = intent.getStringExtra("objectid");
//            Log.i("fromWhereItCame",fromWhereItCame);
//            fromWhereItCame = intent.getStringExtra("activity");
//            Log.i("fromWhereItCame",fromWhereItCame);
            email  = (new AuthHelper(OfferingViewActivity.this)).getLoggedInUserEmail();

            ParseQuery query = new ParseQuery("Offering");
            query.whereEqualTo("objectId", objectid);

            final ProgressDialog progress = new ProgressDialog(OfferingViewActivity.this);
            progress.setTitle("Loading Offering Information");
            progress.setMessage("please wait...");
            try{ progress.show(); } catch(Exception exc){ }

            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    try{ progress.dismiss(); } catch(Exception exc){ }
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
                            start = String.valueOf(p.get("startTime"));
                            finishTime = String.valueOf(p.get("endTime"));
                            Log.i("test99",finishTime);

                            try {
                                applied = String.valueOf(p.get("bhukkads"));
                                currentLeft = Integer.parseInt(capacity);
                                ArrayList<String> bhukkads = (ArrayList<String>) p.get("bhukkads");
                                currentLeft -=  bhukkads.size();
                            }catch(Exception e){
                                Log.e("Testing123", "Failed" + e.getMessage());
                            }

                            go();

                            //get array of bhukkads
                            List<String> bhukkads;
                            if (p.getList("bhukkads")!=null) {
                                bhukkads =  p.getList("bhukkads");
                            }
                            else
                            {
                                bhukkads =  null;
                            }
//                            if (bhukkads!=null) {
//
//                                if (bhukkads.contains(email)) {
//                                    button_cancel.setVisibility(View.VISIBLE);
//                                    bhukkads.remove(email);
//                                    p.put("bhukkads", bhukkads);
//                                    p.saveInBackground(new SaveCallback() {
//                                        @Override
//                                        public void done(ParseException arg0) {
//                                            Toast.makeText(OfferingViewActivity.this, "HAHAHAH!!!!!!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                                }else{
//                                    button_apply.setVisibility(View.VISIBLE);
//                                }
//                            }

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
//                        money.setTextSize(20);
//                        money.setTextColor(Color.DKGRAY);
                        money.setText("Cost: ₹" + cost);

                        desc = (TextView) findViewById(R.id.description);
//                        desc.setTextSize(20);
//                        desc.setTextColor(Color.DKGRAY);
                        desc.setText(description);

                        cap = (TextView) findViewById(R.id.capacity);
//                        cap.setTextSize(20);
//                        cap.setTextColor(Color.DKGRAY);
                        try {
                            cap.setText("Seats Left : " + currentLeft.toString());
                        }catch(Exception e){
                            Log.e("Testing123","Failed in getting capacity");
                            cap.setText("Seats Left : " + capacity);
                        }


                        when = (TextView) findViewById(R.id.when);
//                        when.setTextSize(18);
//                        when.setTextColor(Color.DKGRAY);
                        when.setText("Starts: " + start);

                        when = (TextView) findViewById(R.id.whendone);
//                        when.setTextSize(18);
//                        when.setTextColor(Color.DKGRAY);
                        when.setText("Ends: " + finishTime);

                        View button_edit = findViewById(R.id.button_edit);
                        final View button_apply = findViewById(R.id.button_apply);
                        View button_view_guests = findViewById(R.id.button_view_guests);
                        final View button_view_host = findViewById(R.id.button_view_host);

                        if(currentLeft <=0){
                            final Button button_apply_ = (Button) findViewById(R.id.button_apply);
                            button_apply_.setEnabled(false);
                            if(applied.contains(email)){
                                goo();
                            }else {
                                button_apply_.setText("Out of Capacity");
                                button_apply_.setTextColor(Color.WHITE);
                                button_apply_.setBackgroundColor(Color.RED);
                            }
                        }else {
                            button_apply.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    //if already applied
                                    if (applied.contains(email)) {
                                        go();
                                        Toast.makeText(OfferingViewActivity.this, "You have already applied!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OfferingViewActivity.this);
                                        alertDialogBuilder.setMessage("Are you sure want to apply?");

                                        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                // Guest subscribes to the host's notifications
                                                ParsePush.subscribeInBackground(objectid);

                                                ParseObject apply = ParseObject.createWithoutData("Offering", objectid);
                                                apply.addUnique("bhukkads", email);

                                                final ProgressDialog progress = new ProgressDialog(OfferingViewActivity.this);
                                                progress.setTitle("Sending Request");
                                                progress.setMessage("please wait...");
                                                try{ progress.show(); } catch(Exception exc){ }

                                                apply.saveInBackground(new SaveCallback() {
                                                    public void done(ParseException e) {
                                                        try{ progress.dismiss(); } catch(Exception exc){ }
                                                        if (e == null) {
                                                            goo();
                                                        } else {
                                                            Log.d("Testing123", "Failed, Internet issue");
                                                        }
                                                    }
                                                });
//                                        button_apply.setVisibility(View.GONE);
//                                        button_cancel.setVisibility(View.VISIBLE);

                                            // Find devices associated with these users
                                            ParseQuery pushQuery = ParseInstallation.getQuery();
                                            pushQuery.whereEqualTo("username", parse_username);

                                            // Create time interval
                                            long weekInterval = 60 * 60 * 24 * 7; // 1 week
                                            String alert = email.split("@")[0] + " wants to eat your meal!";
                                            String apply_ = "apply";
                                            JSONObject data = null;
                                            try {
                                                data = new JSONObject("{\"alert\": \""+alert+"\", \"offeringId\":\""+ objectid + "\", \"type\":\""+apply_+"\" }");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            Log.d("JSON",data.toString());
                                            // Send push notification to query
                                            ParsePush push = new ParsePush();
                                            push.setExpirationTimeInterval(weekInterval);
                                            push.setQuery(pushQuery); // Set our Installation query
//                                            push.setMessage(email.split("@")[0] + " wants to eat your meal!");
                                            push.setData(data);
                                            push.sendInBackground(new SendCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    Toast.makeText(OfferingViewActivity.this, "Notification Sent!", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                                Intent intent = new Intent(OfferingViewActivity.this, OfferingViewActivity.class);
                                                Log.i("test","Printing OBJ ID -: " + objectid);
                                                intent.putExtra("objectid", objectid);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });

                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }
                                }
                            });
                        }


//                        button_cancel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                ParseObject updated_bhukkads = ParseObject.createWithoutData("Offering", objectid);
//                                if (bhukkads!=null) {
//
//                                    if (bhukkads.contains(email)) {
////                                        button_cancel.setVisibility(View.VISIBLE);
//                                        bhukkads.remove(email);
//                                        updated_bhukkads.put("bhukkads", bhukkads);
//                                        updated_bhukkads.saveInBackground(new SaveCallback() {
//                                            @Override
//                                            public void done(ParseException arg0) {
//                                                Toast.makeText(OfferingViewActivity.this, "Application Removed!", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//
//                                    }else{
//                                        button_apply.setVisibility(View.VISIBLE);
//                                    }
//                                }
//                            }
//                        });



                        if (email.equals(parse_username)) {
                            //show edit button
                            owner.setVisibility(View.VISIBLE);
//                            button_edit.setVisibility(View.VISIBLE);
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

                            //show view_guests button
//                            button_view_guests.setVisibility(View.VISIBLE);
                            button_view_guests.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Start NewActivity.class
                                    Intent myIntent = new Intent(OfferingViewActivity.this,
                                            AcceptGuestActivity.class);
                                    myIntent.putExtra(MESSAGE_OBJECTID, objectid);
//                                myIntent.putExtra(MESSAGE_NAME, name);
//                                    myIntent.putExtra(MESSAGE_EMAIL, email);
                                    startActivity(myIntent);
                                }
                            });

                            //button to end the offering
                            button_end_offering.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    //Dialog Box
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OfferingViewActivity.this);
                                    alertDialogBuilder.setMessage("Are you sure you want to end this offering?");

                                    alertDialogBuilder.setPositiveButton("End offering", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            //send notification
/*
                                            Toast.makeText(OfferingViewActivity.this, "Thank you for hosting with us!", Toast.LENGTH_LONG).show();
                                            ParsePush push = new ParsePush();
                                            push.setChannel(objectid);
                                            push.setMessage("Thank you for having food with us! Hope to serve you soon again.");
                                            push.sendInBackground();
*/

                                            // Create time interval
                                            long weekInterval = 60 * 60 * 24 * 7; // 1 week
                                            String alert = "Thank you for having food with us! Hope to serve you soon again.";
                                            String apply_ = "rating";
                                            JSONObject data = null;
                                            try {
                                                data = new JSONObject("{\"alert\": \"" + alert + "\", \"offeringId\":\"" + objectid + "\", \"type\":\"" + apply_ + "\" }");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            Log.d("JSON", data.toString());
                                            // Send push notification to query
                                            ParsePush push = new ParsePush();
                                            push.setChannel(objectid);
                                            push.setExpirationTimeInterval(weekInterval);
                                            push.setData(data);
                                            push.sendInBackground(new SendCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    Toast.makeText(OfferingViewActivity.this, "Thank you for hosting with us!", Toast.LENGTH_LONG).show();
                                                }
                                            });

                                            fin();
                                            startListingActivity();
                                        }
                                    });

                                    alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //do nothing
                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();

                                }
                            });

                        } else {
                            visitor.setVisibility(View.VISIBLE);
//                            button_apply.setVisibility(View.VISIBLE);
//                            button_view_host.setVisibility(View.VISIBLE);
                        }


                    } else
                        Log.i("Error!!", "NULL");
                    try{ progress.dismiss(); } catch(Exception exc){ }
                }
            });

        }

    }

    private void startListingActivity() {
        Intent intent = new Intent(OfferingViewActivity.this, OfferingListActivity.class);
        startActivity(intent);
    }

    private void go(){
        final Button button_apply = (Button) findViewById(R.id.button_apply);
        if(applied.contains(email)){
            goo();
        }

    }

    private void goo(){
        final Button button_apply = (Button) findViewById(R.id.button_apply);
//        button_apply.getBackground().setColorFilter(Color.rgb(131, 208, 201), PorterDuff.Mode.MULTIPLY);
        button_apply.setBackgroundColor(Color.rgb(131, 208, 201));
        button_apply.setHint("Attending!");
//        button_apply.setEnabled(false);
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
                                startActivity(new Intent(OfferingViewActivity.this, OfferingListActivity.class));
                                break;

                            case R.id.profile:
                                startActivity(new Intent(OfferingViewActivity.this, ViewUserProfileActivity.class));
                                break;

                            case R.id.my_offerings:
                                startActivity(new Intent(OfferingViewActivity.this, MyOfferingsActivity.class));
                                break;

                            case R.id.log_me_out:
                                (new AuthHelper(OfferingViewActivity.this)).logOut();
                                break;
                        }


                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
        Log.i("Testing12", "Came out setUpDrawer");
    }



//    @Override
//    public void onBackPressed() {
//        Log.d("CDA", "onBackPressed Called");
//        Intent intent;
//        Log.i("fromWhereItCame2",fromWhereItCame);
//        if(fromWhereItCame.equals("myOffering"))
//        {
//            intent = new Intent(this, MyOfferingsActivity.class);
//            startActivity(intent);
//        }
//        else {
//            intent = new Intent(this, OfferingListActivity.class);
//            startActivity(intent);
//        }
////        intent.putExtra(MESSAGE_NAME, name);
////        intent.putExtra(MESSAGE_EMAIL, email);
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                share();
                return true;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);

    }

    public void viewHost(View view){
        Intent myIntent = new Intent(OfferingViewActivity.this,
                ViewUserProfileActivity.class);
        myIntent.putExtra("viewingUser", parse_username);
        startActivity(myIntent);

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

    public void share() {
        String message = "Hey! Check out this delicious " + foodname + "! #gourmetpadosmein";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(share, "Choose where you want to share!"));
    }



    public void fin()
    {

        cal.add(Calendar.MINUTE, 320);
        ParseQuery query = new ParseQuery("Offering");
        query.whereEqualTo("objectId", objectid);
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {
                if (e == null) {
                    if (!list.isEmpty()) {
                        Log.i("never", "here");
                    } else {
                        Log.i("Testing", "List returned by Parse is empty!");
                    }
                } else {
                    Log.i("Error!!", "Error in querying parse!");
                }
            }


            @Override
            public void done(Object o, Throwable throwable) {
                Log.i("Testing1", o.toString());

                List<ParseObject> results = ((List<ParseObject>) o);

                if (!results.isEmpty()) {
                    result = results.get(results.size() - 1);
                    result.put("endTime", cal.getTime());
                    result.saveInBackground();
                    Log.i("changeEndTime", cal.getTime().toString());
                } else {
                    Log.i("Testing1", "");
                }
            }
        });



    }

    public void makeUserConnectIfHeIsntConnected(){
        if(isConnected() != true){
            createNetErrorDialog();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        makeUserConnectIfHeIsntConnected();
        loadOffering();
    }

    public void createNetErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.")
                .setTitle("Unable to connect")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                startActivity(i);
                            }
                        }
                ).setNegativeButton("Retry",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
