package com.mc.priveil.gourmetpadosmein;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.mc.priveil.gourmetpadosmein.Fragments.OfferingFragment;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class OfferingListActivity extends AppCompatActivity implements LocationListener {
    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";

    public static final String YOUR_APPLICATION_ID = "WU842Ed8GWCo7napgpaxk9FBSZ6LBqrhj6cv0XoO";
    public static final String YOUR_CLIENT_KEY = "Z5WO1weLaVu7ZAQdn97qEjTApHPoDG0BFM77OUqv";

    public static final String CLASS_NAME = "Offerings";
    public String name;
    public String email;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    LocationManager locationManager;

    double currLatitude = 28.5444498,currLongitude = 77.2726199;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerings);
        Intent intent = getIntent();
        name = intent.getStringExtra(MESSAGE_NAME);
        email = intent.getStringExtra(MESSAGE_EMAIL);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = this;

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);

        Log.i("Testing12", "Came here in Listing class");
        setUpToolbar();
        setUpNavDrawer();

        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        ParseUser.enableAutomaticUser();

        Log.i("Testing12", "Came here in Listing class 2");

        ParseQuery query = new ParseQuery("Offering");
//        query.whereEqualTo("name", "aaa");
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
                        Double lat = Double.parseDouble((String)p.get("Latitude"));
                        Double longi = Double.parseDouble((String) p.get("Longitude"));
                        float[] dist = new float[1];
                        Location.distanceBetween(currLatitude, currLongitude, lat, longi, dist);
                        Double distance = (double)dist[0];
                        distances.add(distance);
                    }
                    Log.d("List", String.valueOf(cuisines));


//                    ArrayList<ArrayList<String>> cuisines = new ArrayList<ArrayList<String>>();
//                    for (ParseObject p : itemlist) {
//                        cuisines.add((ArrayList<String>) p.get("cuisine"));
//                    }
//                    Log.d("List", String.valueOf(cuisines));
//
//
//
                    int sizeOfList = distances.size();
                    for(int iter1 = 0 ; iter1 < sizeOfList ; iter1++)
                    {
                        for(int iter2 = iter1+1 ; iter2 < sizeOfList ; iter2++)
                        {
                            if(distances.get(iter2) < distances.get(iter1))
                            {
                                double temp = distances.get(iter1);
                                distances.add(iter1,distances.get(iter2));
                                distances.add(iter2,temp);

                                String tempName = names.get(iter1);
                                names.add(iter1,names.get(iter2));
                                names.add(iter2,tempName);

                                ArrayList<String> tempCuisine = cuisines.get(iter1);
                                cuisines.add(iter1,cuisines.get(iter2));
                                cuisines.add(iter2,tempCuisine);

                                String tempObjectId = object_ids.get(iter1);
                                object_ids.add(iter1,object_ids.get(iter2));
                                object_ids.add(iter2,tempObjectId);

                            }
                        }
                    }

                    bundle.putSerializable("names", names);
                    bundle.putSerializable("cuisines", cuisines);
                    bundle.putSerializable("distances", distances);
                    bundle.putSerializable("object_ids", object_ids);

                    OfferingFragment fragment = new OfferingFragment();
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment).commit();
                } else
                    Log.i("Error!!", "NULL");
            }
        });


		/* Use application class to maintain global state. */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        Log.i("Testing12", "Came here in Listing class 3");

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

                            case R.id.new_offering:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, new OfferingFragment()).commit();
                                break;
                            case R.id.profile:
                                Intent ui = new Intent(OfferingListActivity.this, UserInfo.class);
                                ui.putExtra(MESSAGE_NAME, name);
                                ui.putExtra(MESSAGE_EMAIL, email);

//                                startActivity(intent);
                                startActivity(ui);
                                break;
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
        Log.i("Testing12", "Came out setUpDrawer");
    }
}
