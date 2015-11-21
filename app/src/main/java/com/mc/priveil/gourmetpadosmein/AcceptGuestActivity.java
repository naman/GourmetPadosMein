package com.mc.priveil.gourmetpadosmein;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.plus.Plus;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static com.mc.priveil.gourmetpadosmein.R.layout.activity_accept_guest_listview;

public class AcceptGuestActivity extends AppCompatActivity {

    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    ArrayList<String> guests;
    private String objectid;
    public ParseObject result = null;
    ArrayList<String> bhukkads = new ArrayList<String>();
    int numBhukkads = 0;
    ListView listView;
    String emailGuest;
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
//        objectid = "LHYkhTJ3oH";




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
                Log.i("Testing1", o.toString());

                List<ParseObject> results = ((List<ParseObject>) o);


                if (!results.isEmpty()) {
                    Log.i("test123", "Dekho Maggi aa gayi kya?!!");
                    result = results.get(results.size() - 1);
                    try {
                        bhukkads = (ArrayList<String>) result.get("bhukkads");
                        Log.d("objectid", objectid);
                        Log.i("test", "size of bhukkads " + String.valueOf(bhukkads.size()));

                        for (int iter = 0; iter < bhukkads.size(); iter++) {
                            guests.add(String.valueOf(bhukkads.get(iter)));
                        }

                        ArrayAdapter adapter = new ArrayAdapter<String>(AcceptGuestActivity.this, activity_accept_guest_listview, guests);

                        listView = (ListView) findViewById(R.id.mobile_list);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                emailGuest = (String) listView.getItemAtPosition(position);
//                                Toast.makeText(AcceptGuestActivity.this, temp, Toast.LENGTH_SHORT).show();




//                                Toast.makeText(AcceptGuestActivity.this, "Item click check!", Toast.LENGTH_SHORT).show();

                                //Dialog Box
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AcceptGuestActivity.this);
                                alertDialogBuilder.setMessage("View User or Reject User?");

                                alertDialogBuilder.setPositiveButton("View Profile", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        Toast.makeText(AcceptGuestActivity.this, "You clicked profile!", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(AcceptGuestActivity.this, UserViewProfile.class);
                                        intent.putExtra("viewingUser", emailGuest);
                                        startActivity(intent);

                                    }
                                });

                                alertDialogBuilder.setNegativeButton("Reject User",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        bhukkads.remove(bhukkads.indexOf(emailGuest));
                                        result.put("bhukkads", bhukkads);
                                        result.saveInBackground();

                                        Intent intent = new Intent(AcceptGuestActivity.this, AcceptGuestActivity.class);
                                        intent.putExtra(MESSAGE_OBJECTID, objectid);
                                        startActivity(intent);

//                                        Toast.makeText(AcceptGuestActivity.this, "You clicked reject!", Toast.LENGTH_SHORT).show();;
                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                            }
                        });

                    } catch (Exception e) {
                        Log.i("test", "Null Value encountered!");
                        numBhukkads = -1;
                    }
//                    Log.i("test11", String.valueOf(bhukkads.size()));
                } else {
                    Log.i("Testing1", "User Profile is not created");
                }
//                    Log.i("Testing1",((String)result.get("username"))+" name: "+((String)result.get("name"))+" phoneNumber: "+((String)result.get("phoneNumber")));

            }
        });




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
