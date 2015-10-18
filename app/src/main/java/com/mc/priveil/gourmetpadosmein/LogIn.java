package com.mc.priveil.gourmetpadosmein;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.IntentSender;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

public class LogIn extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private boolean mIsResolving = false;

    private boolean mShouldResolve = false;
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "talha111";
    private static final int RC_SIGN_IN = 0;
    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .build();
        mGoogleApiClient.connect();

        setContentView(R.layout.activity_log_in);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

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


    private SignInButton button_signIn;

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


        if(email!=null) {
            Toast.makeText(this, "Logged in as " + email, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, OfferingListActivity.class);
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