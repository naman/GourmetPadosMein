package com.mc.priveil.gourmetpadosmein;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mc.priveil.gourmetpadosmein.Forms.EditUserProfileActivity;
import com.mc.priveil.gourmetpadosmein.Models.AuthHelper;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ViewUserProfileActivity extends AppCompatActivity {

//    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";
//    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
    public static final int PICK_CONTACT = 3;
    public String name;
    public int flag = 0;
    public ParseObject result = null;
    private TextView emergencyName;
    private TextView emergencyNumber;
    private Button emergency_button;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String email;
    private Bitmap bitmap;
    private String tolookup;

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_profile_noparallax);

        setUpToolbar();
        setUpNavDrawer();


//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("Title");
//        collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);

        setUpToolbar();
        setUpNavDrawer();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if(isConnected() != true){
            Toast.makeText(ViewUserProfileActivity.this, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }

        else{

//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
            ParseUser.enableAutomaticUser();
            Intent intent = getIntent();
            Log.i("test123", "Came here!!!");
            name = null;
            email = (new AuthHelper(ViewUserProfileActivity.this)).getLoggedInUserEmail();
            String sidebar_tap = "F";
            try {
                sidebar_tap = intent.getStringExtra(OfferingListActivity.SIDEBAR_TAP);
                Log.d("test123",sidebar_tap);
            }catch(Exception e){
                Log.d("test123","failed to get tap");
            }
            if("T".equals(sidebar_tap)){
                Log.d("test123","Hiding");
                View skoop = findViewById(R.id.button3);
                skoop.setVisibility(View.GONE);
            }

            Log.i("test123", "Came here again???!!!");


//            emergency_button = (Button) findViewById(R.id.emergency);
//            emergency_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                Intent intent = new Intent(Intent.ACTION_PICK, Contacts.People.CONTENT_URI);
////                startActivityForResult(intent, PICK_CONTACT);
//
//                    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//                    startActivityForResult(contactPickerIntent, PICK_CONTACT);
//
//                }
//            });
            tolookup = email;

            Log.i("test123", "Dekho Maggi aa gayi 222 !!");
            try{
                Intent currentIntent = getIntent();
                String userBeingViewed = currentIntent.getStringExtra("viewingUser");
                Log.d("who","he:"+userBeingViewed);
                if (userBeingViewed != null) {
                    tolookup = userBeingViewed;
                }
            }catch(Exception e){
                Log.d("shit", "oh no "+e.getMessage());
            }
            TextView emailView = (TextView) findViewById(R.id.textView);
            emailView.setText(tolookup);
            if(tolookup.equals(email)){
                findViewById(R.id.button6).setVisibility(View.VISIBLE);
            }
//            RatingBar rb = (RatingBar) findViewById(R.id.ratingBar2);
//            rb.setRating(3.75f);
            ParseQuery query = new ParseQuery("User");
            query.whereEqualTo("username", tolookup);

            final ProgressDialog progress = new ProgressDialog(ViewUserProfileActivity.this);
            progress.setTitle("Loading User Information");
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
                    if (throwable == null) {
                        List<ParseObject> results = ((List<ParseObject>) o);


                        if (!results.isEmpty()) {
                            Log.i("test123", "Dekho Maggi aa gayi!!");
                            result = results.get(results.size() - 1);
                            if (name == null) {
                                TextView perName = (TextView) findViewById(R.id.textView2);
                                perName.setText(((String) result.get("name")));
                            } else {
                                TextView perName = (TextView) findViewById(R.id.textView2);
                                perName.setText(name);
                            }
                            TextView address = (TextView) findViewById(R.id.textView3);
                            TextView mobile = (TextView) findViewById(R.id.textView4);
                            emergencyName = (TextView) findViewById(R.id.textView5);
                            emergencyNumber = (TextView) findViewById(R.id.textView6);
                            Log.i("Testing2", ((String) result.get("address")));
                            Log.i("Testing2", ((String) result.get("name")));
                            address.setText(((String) result.get("address")));
                            mobile.setText((String) result.get("phoneNumber"));
                            emergencyName.setText(((String) result.get("emergencyContactName")));
                            emergencyNumber.setText(((String) result.get("emergencyContactNumber")));
                            RatingBar rb = (RatingBar) findViewById(R.id.ratingBar2);
                            rb.setRating(Float.parseFloat(result.get("rating").toString()));
                            TextView rat = (TextView) findViewById(R.id.textView7);
                            rat.setText(result.get("rating").toString());


                            try {
                                ParseFile fileObject = (ParseFile) result
                                        .get("image");

                                fileObject
                                        .getDataInBackground(new GetDataCallback() {

                                            @SuppressLint("NewApi")
                                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                            public void done(byte[] data,
                                                             ParseException e) {
                                                if (e == null) {
                                                    Log.d("test",
                                                            "We've got data in data.");
                                                    // Decode the Byte[] into
                                                    // Bitmap
                                                    bitmap = BitmapFactory
                                                            .decodeByteArray(
                                                                    data, 0,
                                                                    data.length);

                                                    // Get the ImageView from
                                                    // main.xml
                                                    ImageView image = (ImageView) findViewById(R.id.backdrop);
                                                    Drawable d = new BitmapDrawable(getResources(), bitmap);
                                                    image.setBackground(d);
                                                    //                                                image.setBackgroundColor(0);

                                                    // Set the Bitmap into the
                                                    // ImageView
                                                    //                                                image.setImageBitmap(bitmap);

                                                } else {
                                                    Log.d("test",
                                                            "There was a problem downloading the data.");
                                                }
                                            }
                                        });
                            } catch (Exception e) {
                                Log.d("test123", "Failed to get image!" + e.getLocalizedMessage());
                            }

                            flag = 1;
                        } else {
                            Log.i("Testing1", "User Profile is not created");
                            if(tolookup.equals(email)) {
                                Intent intent = new Intent(ViewUserProfileActivity.this, EditUserProfileActivity.class);
                                //                        intent.putExtra(MESSAGE_NAME, name);
                                //                        intent.putExtra(MESSAGE_EMAIL, email);
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewUserProfileActivity.this);
                                alertDialogBuilder.setMessage("This user hasn't set up his profile yet on GMP");

                                alertDialogBuilder.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        finish();
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                            }
                        }
                        try{ progress.dismiss(); } catch(Exception exc){ }
                    }
                }
            });



//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
            Log.i("test123", "Came here also!!!");
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
                                startActivity(new Intent(ViewUserProfileActivity.this, OfferingListActivity.class));
                                break;

                            case R.id.profile:
                                break;

                            case R.id.my_offerings:
                                startActivity(new Intent(ViewUserProfileActivity.this, MyOfferingsActivity.class));
                                break;

                            case R.id.log_me_out:
                                (new AuthHelper(ViewUserProfileActivity.this)).logOut();
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
        getMenuInflater().inflate(R.menu.menu_profile_view, menu);
        return true;
    }


    public void editProfile(View view)
    {
        Intent n = new Intent(ViewUserProfileActivity.this, EditUserProfileActivity.class);
        n.putExtra(OfferingListActivity.SIDEBAR_TAP, "T");
        //OfferingListActivity.SIDEBAR_TAP
//                                n.putExtra(MESSAGE_NAME, name);
//                                n.putExtra(MESSAGE_EMAIL, email);
        startActivity(n);
    }

    public void sendEmail(View view)
    {
        TextView v = (TextView) findViewById(R.id.textView);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", v.getText().toString(), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void sendCall(View view)
    {
        TextView v = (TextView) findViewById(R.id.textView4);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + v.getText()));
        startActivity(intent);
    }

    public void sendEmergencyCall(View view)
    {
        TextView v = (TextView) findViewById(R.id.textView6);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + v.getText()));
        startActivity(intent);
    }
    public void viewAddressOnMap(View view) {
        TextView v = (TextView) findViewById(R.id.textView3);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q="+v.getText().toString()));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_profile:
                Toast.makeText(ViewUserProfileActivity.this, "Edit!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
