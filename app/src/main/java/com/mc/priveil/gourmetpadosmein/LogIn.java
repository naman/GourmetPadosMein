package com.mc.priveil.gourmetpadosmein;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class LogIn extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";
    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";

    private static final String TAG = "talha111";
    private static final int RC_SIGN_IN = 0;
    public String email;
    GoogleApiClient mGoogleApiClient;
    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;
    private String personName;
    private SignInButton button_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Yeah", "yeh kya ho raha hai 3!!");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .build();
        mGoogleApiClient.connect();
        Log.i("Yeah", "yeh kya ho raha hai 2!!");
        setContentView(R.layout.activity_log_in);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        Log.i("Yeah","yeh kya ho raha hai!!");

//        if(mGoogleApiClient.isConnected()) {
//            email = Plus.AccountApi.getAccountName(mGoogleApiClient);
//            if (email != null) {
//                Toast.makeText(this, "Logged in as " + email, Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(this, OfferingListActivity.class);
//                startActivity(intent);
//            }
//        }

    }

    protected void onResume() {
        super.onResume();
        if(mGoogleApiClient.isConnected()) {
            email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            if (email != null) {
                finish();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                Log.d(TAG, "onConnectionFailed:" + connectionResult);
            }
        } else {
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            onSignInClicked();
        }
    }

    private void onSignInClicked() {

        Log.d(TAG, "Came here twice!!!");
        mShouldResolve = true;
        mGoogleApiClient.connect();

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mShouldResolve = false;
            }

            mIsResolving = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    public void signOut(View view) {
//        senSensorManager.unregisterListener(this);
        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
        mGoogleApiClient.disconnect();
        mGoogleApiClient.connect();
        Intent testIntent = getIntent();
        finish();
        startActivity(testIntent);
        Toast.makeText(this, "Successfully Logged Out!!!", Toast.LENGTH_LONG).show();
        button_signIn = (SignInButton) findViewById(R.id.sign_in_button);
        button_signIn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnected(Bundle bundle) {
//        onSignInClicked();
        Log.d(TAG,"Came here!!!");
        mShouldResolve = false;
        button_signIn = (SignInButton)findViewById(R.id.sign_in_button);
        //button_signIn.setVisibility(View.GONE);
        //Toast.makeText(this, "Logged in as ", Toast.LENGTH_LONG).show();
        email = Plus.AccountApi.getAccountName(mGoogleApiClient);

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            personName = currentPerson.getDisplayName();
            String personPhoto = currentPerson.getImage().getUrl();
            String personGooglePlusProfile = currentPerson.getUrl();
        }

        if(email!=null) {
            Toast.makeText(this, "Logged in as " + email, Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(this, UserInfo.class);
//            Intent intent = new Intent(this, OfferingListActivity.class);
            Intent intent = new Intent(this, OfferingForm.class);
            if(personName==null)
            {
                Log.i("Testing","No name");
            }
            else
                Log.i("Testing",personName);

            intent.putExtra(MESSAGE_NAME, personName);
            intent.putExtra(MESSAGE_EMAIL, email);
//            intent.putExtra(MESSAGE_OBJECTID,"lol");

            startActivity(intent);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
