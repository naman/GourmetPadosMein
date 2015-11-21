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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.plus.Plus;
import com.parse.ParseUser;

import java.util.ArrayList;

public class AcceptGuestActivity extends AppCompatActivity {

    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    ArrayList<String> guests;
    private String objectid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_guest);

        setUpToolbar();
        setUpNavDrawer();
                /* Use application class to maintain global state. */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        guests = new ArrayList<String>();

        //fetch guests
        ParseUser.enableAutomaticUser();

        Intent intent = getIntent();
        objectid = intent.getStringExtra(MESSAGE_OBJECTID);
        Log.d("objectid", objectid);

        guests.add("Item 1");
        guests.add("Item 2");
        guests.add("Item 3");
        guests.add("Item 4");
        guests.add("Item 5");
        guests.add("Item 6");

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_accept_guest_listview, guests);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

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
                                Intent n = new Intent(AcceptGuestActivity.this, OfferingListActivity.class);
//                                n.putExtra(MESSAGE_NAME, name);
//                                n.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n);
                                break;

                            case R.id.profile:
                                Intent ui = new Intent(AcceptGuestActivity.this, UserInfo.class);
//                                ui.putExtra(MESSAGE_NAME, name);
//                                ui.putExtra(MESSAGE_EMAIL, email);

                                startActivity(ui);
                                break;
                            case R.id.my_offerings:
                                Intent n1 = new Intent(AcceptGuestActivity.this, MyOfferings.class);
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

                                startActivity(new Intent(AcceptGuestActivity.this, LogIn.class));

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
