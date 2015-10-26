package com.mc.priveil.gourmetpadosmein;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mc.priveil.gourmetpadosmein.Fragments.OfferingFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class OfferingViewActivity extends AppCompatActivity {
    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";

    public static final String CLASS_NAME = "Offerings";
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

    private String username;
    private String email_intent;
    private String parse_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offering_view);


        setUpToolbar();
        setUpNavDrawer();

        ParseUser.enableAutomaticUser();

        Intent intent = getIntent();
        objectid = intent.getStringExtra("objectid");
        email  = intent.getStringExtra("email");

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


                    }

                    Log.d("foodname", foodname);
                    Log.d("cost", cost);
                    Log.d("description", description);
                    Log.d("capacity", capacity);
                    Log.d("cuisine", cuisines);

                    TextView food = (TextView) findViewById(R.id.foodname);
                    food.setTextSize(40);
                    food.setTextColor(Color.DKGRAY);
                    food.setText(foodname);

                    TextView cuisine_type = (TextView) findViewById(R.id.cuisine);
                    cuisine_type.setTextSize(15);
                    cuisine_type.setTextColor(Color.DKGRAY);
                    cuisine_type.setText(cuisines.substring(1, cuisines.length()-1));

                    TextView money = (TextView) findViewById(R.id.cost);
                    money.setTextSize(20);
                    money.setTextColor(Color.DKGRAY);
                    money.setText("Rs " + cost);

                    TextView desc = (TextView) findViewById(R.id.description);
                    desc.setTextSize(15);
                    desc.setTextColor(Color.DKGRAY);
                    desc.setText(description);

                    TextView cap = (TextView) findViewById(R.id.capacity);
                    cap.setTextSize(15);
                    cap.setTextColor(Color.DKGRAY);
                    cap.setText(capacity);

                    View button_edit = findViewById(R.id.button_edit);


                    if(email.equals(parse_username)){
                        //show edit button

                        button_edit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View arg0) {
                                // Start NewActivity.class
                                Intent myIntent = new Intent(OfferingViewActivity.this,
                                        OfferingForm.class);
                                startActivity(myIntent);
                            }
                        });
                    }
                    else {
                        button_edit.setVisibility(View.GONE);
                    }


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

                            case R.id.my_hostings:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, new OfferingFragment()).commit();
                                break;
                            case R.id.profile:
//                                Intent ui = new Intent(OfferingListActivity.this, UserInfo.class);
//                                ui.putExtra(MESSAGE_NAME, name);
//                                ui.putExtra(MESSAGE_EMAIL, email);
//
//                                startActivity(intent);
//                                startActivity(ui);
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
