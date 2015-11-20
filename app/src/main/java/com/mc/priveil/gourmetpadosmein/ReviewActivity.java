package com.mc.priveil.gourmetpadosmein;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private RatingBar ratingBar;
    private EditText comment;
    private Button btnSubmit;
    float ratingUser = 1;
    public ParseObject result = null;
    String offererId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);



        String objId = "M1VnxQGNLS";

        ParseQuery query = new ParseQuery("Offering");
        query.whereEqualTo("objectId", objId);
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {
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
//                Log.i("Testing",throwable.getMessage().toString());
                Log.i("Testing1", o.toString());

                List<ParseObject> results = ((List<ParseObject>) o);


                if (!results.isEmpty()) {
                    result = results.get(results.size() - 1);
                    offererId = (String)result.get("username");

                    Log.i("Testing", offererId);
//                        String longitude = (String)result.get("Longitude");


                } else {
                    Log.i("Testing1", "why did it come here?");
                }
//                    Log.i("Testing1",((String)result.get("username"))+" name: "+((String)result.get("name"))+" phoneNumber: "+((String)result.get("phoneNumber")));

            }
        });




        setUpToolbar();
        setUpNavDrawer();
                /* Use application class to maintain global state. */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Toast.makeText(ReviewActivity.this, String.valueOf(rating), Toast.LENGTH_SHORT).show();
                ratingUser = rating;
            }
        });



        //if click on me, then display the current rating value.
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });




        //if rating value is changed,
        //display the current rating value in the result (textview) automatically



    }

    public void submitRating(View view)
    {
        comment = (EditText) findViewById(R.id.comment);
        String getComment = comment.getText().toString();

        final ParseObject testObject;
        testObject = new ParseObject("Review");
//        String email = Plus.AccountApi.getAccountName(LogIn.mGoogleApiClient);
        String email = "Naman Hi rahoge!!";

        testObject.put("reviewer_id", email);
        testObject.put("Comment", getComment);
        testObject.put("Rating", ratingUser);
        testObject.put("offerer_id", offererId);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Saving Your Reviews!!");
        progress.setMessage("please wait...");
        progress.show();
        Log.i("test", "Failed Here 2!!");

        testObject.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {

                if (e == null) {
                    myObjectSavedSuccessfully(testObject, progress);
                } else {
                    myObjectSaveDidNotSucceed(progress);
                }
            }
        });
        Log.i("test", "Failed Here 8!!");

        Toast.makeText(ReviewActivity.this,
                "Submit!",
                Toast.LENGTH_SHORT).show();
    }


    void myObjectSavedSuccessfully(ParseObject po,ProgressDialog progress){
        progress.dismiss();
        Log.i("Testing", "about to submit form 4!!!");
        Intent intent = new Intent(this, OfferingListActivity.class);
        startActivity(intent);
    }

    void myObjectSaveDidNotSucceed(ProgressDialog progress){
        progress.dismiss();
        Toast.makeText(this, "Failed while trying to save, please check internet connection and try again!", Toast.LENGTH_LONG);
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
                                /*Intent n = new Intent(OfferingForm.this, OfferingListActivity.class);
                                n.putExtra(MESSAGE_NAME, name);
                                n.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n);
                                */
                                break;

                            case R.id.profile:
                                /*Intent ui = new Intent(OfferingForm.this, UserInfo.class);
                                ui.putExtra(MESSAGE_NAME, name);
                                ui.putExtra(MESSAGE_EMAIL, email);

                                startActivity(ui);
                                */
                                break;
                            case R.id.my_offerings:
                                /*Intent n1 = new Intent(OfferingForm.this, MyOfferings.class);
                                n1.putExtra(MESSAGE_NAME, name);
                                n1.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n1);
                                */
                                break;

                            case R.id.log_me_out:
                                try {
                                    Plus.AccountApi.clearDefaultAccount(LogIn.mGoogleApiClient);
                                    LogIn.mGoogleApiClient.disconnect();
                                    LogIn.mGoogleApiClient.connect();
                                } catch (Exception e) {
                                    Log.e("test123", "Failed to Logout, might be already out?");
                                }

//                                startActivity(new Intent(OfferingForm.this, LogIn.class));

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