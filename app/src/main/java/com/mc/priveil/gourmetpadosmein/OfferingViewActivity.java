package com.mc.priveil.gourmetpadosmein;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

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
    public String name;
    public String email;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String objectid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offering_view);
        setUpToolbar();
        setUpNavDrawer();

        ParseUser.enableAutomaticUser();

        Intent intent = getIntent();
        objectid = intent.getStringExtra("objectid");

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
