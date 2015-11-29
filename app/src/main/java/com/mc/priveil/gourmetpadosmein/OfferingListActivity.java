package com.mc.priveil.gourmetpadosmein;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mc.priveil.gourmetpadosmein.Forms.UserInfo;
import com.mc.priveil.gourmetpadosmein.Fragments.OfferingFragment;
import com.mc.priveil.gourmetpadosmein.Models.AlertDialogHelper;
import com.mc.priveil.gourmetpadosmein.Models.AuthHelper;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class OfferingListActivity extends AppCompatActivity implements LocationListener {
//    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
//    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";

    public final static String SIDEBAR_TAP = "LOL";
    public static final String CLASS_NAME = "Offerings";
    static String fromSkip = "N";
    private static Boolean fromLogin;
//    public String name;
    public String email;
    public ParseObject result = null;
    LocationManager locationManager;
    double currLatitude = 28.5444498,currLongitude = 77.2726199;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    public void onLocationChanged(Location location) {
        currLatitude = location.getLatitude();
        currLongitude = location.getLongitude();
//        Log.d("Testing", "Latitude: " + Double.toString(currLatitude) + " Longitude: " + Double.toString(currLongitude));
//        float[] dist = new float[1];
//        Location.distanceBetween(collegeLatitude, collegeLongitude, currLatitude, currLongitude, dist);
//        Log.d("Testing", "Distance from IIITD is: " + Double.toString(dist[0]));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
//    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Check if user triggered a refresh:
            case R.id.menu_refresh:
                Log.i("talha123", "Refresh menu item selected");

                // Signal SwipeRefreshLayout to start the progress indicator
//                mSwipeRefreshLayout.setRefreshing(true);

                Log.i("talha123", "Refresh2 menu item selected");

                // Start the refresh background task.
                // This method calls setRefreshing(false) when it's finished.
                myUpdateOperation();

                return true;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerings);
        Log.i("myTest", "This is my Test!");
        Log.i("Testing12", "Came here in Listing class");
        setUpToolbar();
        setUpNavDrawer();

        /* Use application class to maintain global state. */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        Log.i("Testing12", "Came here in Listing class 3");

        if(isConnected() != true){
            Toast.makeText(OfferingListActivity.this, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }

        else{
            Intent intent = getIntent();
            email = new AuthHelper(OfferingListActivity.this).getLoggedInUserEmail();

            try{
                fromLogin = ("Y".equals(intent.getStringExtra("fromLogin")));
            }catch(Exception e){
                fromLogin = false;
            }
            Log.d("FromLogin", fromLogin.toString());


            try{
                fromSkip = intent.getStringExtra("SKIPCLICK");
                Log.d("test123", fromSkip);
            }catch(Exception e){
                Log.d("test123","failed to get skipclick");
            }


            try {
                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                installation.put("username", email);
                installation.saveInBackground();
            }catch(Exception e){
                Log.d("TEST", "Unable to get email and send it to ParseInsta");
            }

            ParseQuery query = new ParseQuery("User");
            query.whereEqualTo("username", email);

            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Fetching Your User Information");
            progress.setMessage("please wait...");
            try{ progress.show(); } catch(Exception exc){ }



            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    try{ progress.dismiss(); } catch(Exception exc){ }
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
                    try{ progress.dismiss(); } catch(Exception exc){ }
                    boolean goingOut = false;
//                Log.i("Testing",throwable.getMessage().toString());
                    if (throwable == null) {
                        Log.i("Testing1", o.toString());

                        List<ParseObject> results = ((List<ParseObject>) o);


                        if (!results.isEmpty()) {
                            result = results.get(results.size() - 1);
                            Log.i("Testing", "Came to lat and long");


                        } else {
                            Log.i("Testing1", "");
                            if (!"Y".equals(fromSkip) && fromLogin) {
                                goingOut = true;
                                Intent intent = new Intent(OfferingListActivity.this, UserInfo.class);
                                startActivity(intent);
                            } else {
                                fromSkip = "N";
                            }
                        }

                    }

                    if (!goingOut) {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        LocationListener locationListener = OfferingListActivity.this;


                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, locationListener);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);


                        //        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
                        ParseUser.enableAutomaticUser();

                        Log.i("Testing12", "Came here in Listing class 2????");
                        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.MINUTE, 330);
                        Log.i("date111", cal.getTime().toString());


                        ParseQuery query2 = new ParseQuery("Offering");
                        query2.whereGreaterThan("endTime", cal.getTime());
                        //        query.whereEqualTo("name", "aaa");
                        final ProgressDialog progress2 = new ProgressDialog(OfferingListActivity.this);
                        progress2.setTitle("Fetching offerings around you");
                        progress2.setMessage("please wait...");
                        progress2.show();

                        query2.findInBackground(new FindCallback() {
                            @Override
                            public void done(List list, ParseException e) {
                                progress2.dismiss();
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
                                progress2.dismiss();
                                if (o != null) {
                                    List<ParseObject> itemlist;
                                    itemlist = (List<ParseObject>) o;
                                    Log.i("LIST", itemlist.get(0).get("name").toString());

                                    Bundle bundle = new Bundle();
                                    ArrayList<String> names = new ArrayList<String>();
                                    for (ParseObject p : itemlist) {
                                        names.add(p.get("name").toString());
                                    }

                                    Log.d("List", String.valueOf(names));

                                    ArrayList<ArrayList<String>> cuisines = new ArrayList<ArrayList<String>>();
                                    for (ParseObject p : itemlist) {
                                        cuisines.add((ArrayList<String>) p.get("cuisine"));
                                    }
                                    Log.d("List", String.valueOf(cuisines));

                                    ArrayList<String> object_ids = new ArrayList<String>();
                                    for (ParseObject p : itemlist) {
                                        object_ids.add(p.getObjectId());
                                    }


                                    ArrayList<Double> distances = new ArrayList<>();
                                    for (ParseObject p : itemlist) {
                                        ParseGeoPoint point = (ParseGeoPoint) p.get("Location");
                                        Double lat = Double.parseDouble((String.valueOf(point.getLatitude())));
                                        Double longi = Double.parseDouble((String.valueOf(point.getLongitude())));
                                        float[] dist = new float[1];
                                        Location.distanceBetween(currLatitude, currLongitude, lat, longi, dist);
                                        Double distance = (double) dist[0];
                                        distances.add(distance);
                                    }
                                    Log.d("List", String.valueOf(cuisines));

                                    ArrayList<String> costs = new ArrayList<>();
                                    for (ParseObject p : itemlist) {
                                        costs.add(p.get("cost").toString());
                                    }


                                    //                    ArrayList<ArrayList<String>> cuisines = new ArrayList<ArrayList<String>>();
                                    //                    for (ParseObject p : itemlist) {
                                    //                        cuisines.add((ArrayList<String>) p.get("cuisine"));
                                    //                    }
                                    //                    Log.d("List", String.valueOf(cuisines));
                                    //
                                    //
                                    //
                                    int sizeOfList = distances.size();
                                    for (int iter1 = 0; iter1 < sizeOfList; iter1++) {
                                        for (int iter2 = iter1 + 1; iter2 < sizeOfList; iter2++) {
                                            if (distances.get(iter2) < distances.get(iter1)) {
                                                double temp = distances.get(iter1);
                                                distances.set(iter1, distances.get(iter2));
                                                distances.set(iter2, temp);

                                                String tempName = names.get(iter1);
                                                names.set(iter1, names.get(iter2));
                                                names.set(iter2, tempName);

                                                ArrayList<String> tempCuisine = cuisines.get(iter1);
                                                cuisines.set(iter1, cuisines.get(iter2));
                                                cuisines.set(iter2, tempCuisine);

                                                String tempObjectId = object_ids.get(iter1);
                                                object_ids.set(iter1, object_ids.get(iter2));
                                                object_ids.set(iter2, tempObjectId);

                                                String tempCost = costs.get(iter1);
                                                costs.set(iter1, costs.get(iter2));
                                                costs.set(iter2, tempCost);
                                            }
                                        }
                                    }

                                    bundle.putSerializable("names", names);
                                    bundle.putSerializable("cuisines", cuisines);
                                    bundle.putSerializable("distances", distances);
                                    bundle.putSerializable("object_ids", object_ids);
                                    bundle.putSerializable("costs", costs);
                                    Log.i("test123", "Before Buggy again in create!!");
                                    //                        bundle.putSerializable(MESSAGE_NAME, name);
                                    //                        bundle.putSerializable("email", email);

                                    OfferingFragment fragment = new OfferingFragment();
                                    fragment.setArguments(bundle);

                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    Log.i("test123", "Before Buggy again in create 222 what !!");
                                    try {
                                        fragmentManager.beginTransaction()
                                                .replace(R.id.content_frame, fragment).commit();
                                        Log.i("test123", "try for fragment");
                                    } catch (Exception e) {
                                        Log.e("test123", "Failed to inflate at OfferingListActivity Line 299");
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                } else
                                    Log.i("Error!!", "NULL");
                            }
                        });
                    }

                }
            });






        }

        Log.i("test123","Successfully exiting create");
    }

    public void myUpdateOperation()
    {
        Log.i("refreshTest", "It came here!!");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 330);
        ParseQuery query = new ParseQuery("Offering");
        query.whereGreaterThan("endTime", cal.getTime());
        if(isConnected() != true){
            Toast.makeText(OfferingListActivity.this, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }

        else {
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Refreshing Listing Information");
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
                    try{ progress.dismiss(); } catch(Exception exc){ }
                    if (o != null && throwable == null) {
                        List<ParseObject> itemlist;
                        itemlist = (List<ParseObject>) o;
                        Log.i("LIST", itemlist.get(0).get("name").toString());

                        Bundle bundle = new Bundle();
                        ArrayList<String> names = new ArrayList<String>();
                        for (ParseObject p : itemlist) {
                            names.add(p.get("name").toString());
                        }

                        Log.d("List", String.valueOf(names));

                        ArrayList<ArrayList<String>> cuisines = new ArrayList<ArrayList<String>>();
                        for (ParseObject p : itemlist) {
                            cuisines.add((ArrayList<String>) p.get("cuisine"));
                        }
                        Log.d("List", String.valueOf(cuisines));

                        ArrayList<String> object_ids = new ArrayList<String>();
                        for (ParseObject p : itemlist) {
                            object_ids.add(p.getObjectId());
                        }


                        ArrayList<Double> distances = new ArrayList<>();
                        for (ParseObject p : itemlist) {
                            ParseGeoPoint point = (ParseGeoPoint)p.get("Location");
                            Double lat = Double.parseDouble((String.valueOf(point.getLatitude())));
                            Double longi = Double.parseDouble((String.valueOf(point.getLongitude())));
                            float[] dist = new float[1];
                            Location.distanceBetween(currLatitude, currLongitude, lat, longi, dist);
                            Double distance = (double) dist[0];
                            distances.add(distance);
                        }
                        Log.d("List", String.valueOf(cuisines));

                        ArrayList<String> costs = new ArrayList<>();
                        for (ParseObject p : itemlist) {
                            costs.add(p.get("cost").toString());
                        }


//                    ArrayList<ArrayList<String>> cuisines = new ArrayList<ArrayList<String>>();
//                    for (ParseObject p : itemlist) {
//                        cuisines.add((ArrayList<String>) p.get("cuisine"));
//                    }
//                    Log.d("List", String.valueOf(cuisines));
//
//
//
                        int sizeOfList = distances.size();
                        for (int iter1 = 0; iter1 < sizeOfList; iter1++) {
                            for (int iter2 = iter1 + 1; iter2 < sizeOfList; iter2++) {
                                if (distances.get(iter2) < distances.get(iter1)) {
                                    double temp = distances.get(iter1);
                                    distances.set(iter1, distances.get(iter2));
                                    distances.set(iter2, temp);

                                    String tempName = names.get(iter1);
                                    names.set(iter1, names.get(iter2));
                                    names.set(iter2, tempName);

                                    ArrayList<String> tempCuisine = cuisines.get(iter1);
                                    cuisines.set(iter1, cuisines.get(iter2));
                                    cuisines.set(iter2, tempCuisine);

                                    String tempObjectId = object_ids.get(iter1);
                                    object_ids.set(iter1, object_ids.get(iter2));
                                    object_ids.set(iter2, tempObjectId);

                                    String tempCost = costs.get(iter1);
                                    costs.set(iter1, costs.get(iter2));
                                    costs.set(iter2, tempCost);
                                }
                            }
                        }

                        bundle.putSerializable("names", names);
                        bundle.putSerializable("cuisines", cuisines);
                        bundle.putSerializable("distances", distances);
                        bundle.putSerializable("object_ids", object_ids);
                        bundle.putSerializable("costs", costs);

                    Log.i("test123", "Before Buggy!!");
//                    bundle.putSerializable(MESSAGE_NAME, name);
//                    bundle.putSerializable("email", email);

                    OfferingFragment fragment = new OfferingFragment();
                    fragment.setArguments(bundle);
                    Log.i("test123", "Before Buggy again!!");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment).commit();
                    Log.i("test123", "Before Buggy again??? !!");
                } else
                    Log.i("Error!!", "NULL");
            }
        });

    }
    }




    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
                             /*   Intent n = new Intent(OfferingListActivity.this, MyOfferings.class);
                                n.putExtra(MESSAGE_NAME, name);
                                n.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n);*/

                                break;

                            case R.id.profile:
                                Intent ui = new Intent(OfferingListActivity.this, UserViewProfile.class);
//                                ui.putExtra(MESSAGE_NAME, name);
//                                ui.putExtra(MESSAGE_EMAIL, email);

                                startActivity(ui);
                                break;

                            case R.id.log_me_out:
                                (new AuthHelper(OfferingListActivity.this)).logOut();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
        myUpdateOperation();
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
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

}
