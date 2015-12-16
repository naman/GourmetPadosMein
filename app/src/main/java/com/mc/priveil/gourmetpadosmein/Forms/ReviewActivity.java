package com.mc.priveil.gourmetpadosmein.Forms;

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

import com.mc.priveil.gourmetpadosmein.Models.AuthHelper;
import com.mc.priveil.gourmetpadosmein.MyOfferingsActivity;
import com.mc.priveil.gourmetpadosmein.OfferingListActivity;
import com.mc.priveil.gourmetpadosmein.R;
import com.mc.priveil.gourmetpadosmein.ViewUserProfileActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";
    public ParseObject result = null;
    float ratingUser = 1;
    String offererId;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private RatingBar ratingBar;
    private EditText comment;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Notification", "Switching to  a new activity!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);



        ParseUser.enableAutomaticUser();


        String objId = getIntent().getExtras().getString(MESSAGE_OBJECTID);


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
        String email = (new AuthHelper(ReviewActivity.this)).getLoggedInUserEmail();
//        email = "Naman Hi rahoge!!";

        testObject.put("reviewer_id", email);
        testObject.put("Comment", getComment);
        testObject.put("Rating", ratingUser);
        testObject.put("offerer_id", offererId);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Saving Your Reviews!!");
        progress.setMessage("please wait...");
        try{ progress.show(); } catch(Exception exc){ }
        Log.i("test", "Failed Here 2!!");

        ParseQuery query = new ParseQuery("User");
        query.whereEqualTo("username", offererId);
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
                    Log.i("test123", "Dekho Maggi aa gayi!!");
                    result = results.get(results.size() - 1);
                    float userRating = Float.parseFloat(result.get("rating").toString());
                    int numRating = Integer.parseInt(result.get("numRatings").toString());
                    float tempRatingSum = userRating * numRating;
                    tempRatingSum += ratingUser;
                    numRating += 1;
                    float finalRating = tempRatingSum / numRating;
                    result.put("rating", finalRating);
                    result.put("numRatings", numRating);
                    result.saveInBackground();
                } else {
                    Log.i("Testing1", "User Profile is not created");
                }

            }
        });


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
        try{ progress.dismiss(); } catch(Exception exc){ }
        Log.i("Testing", "about to submit form 4!!!");
        Intent intent = new Intent(this, OfferingListActivity.class);
        startActivity(intent);
    }

    void myObjectSaveDidNotSucceed(ProgressDialog progress){
        try{ progress.dismiss(); } catch(Exception exc){ }
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
                                startActivity(new Intent(ReviewActivity.this, OfferingListActivity.class));
                                break;

                            case R.id.profile:
                                startActivity(new Intent(ReviewActivity.this, ViewUserProfileActivity.class));
                                break;

                            case R.id.my_offerings:
                                startActivity(new Intent(ReviewActivity.this, MyOfferingsActivity.class));
                                break;

                            case R.id.log_me_out:
                                (new AuthHelper(ReviewActivity.this)).logOut();
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
