package com.mc.priveil.gourmetpadosmein.Models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.plus.Plus;
import com.mc.priveil.gourmetpadosmein.Utils.LogIn;

/**
 * Created by magusverma on 27/11/15.
 */
public class AuthHelper {

    public static final String appPreferences = "gourmetPadosMeinPrefs" ;
    public static final String authenticatedUserEmailKey = "email";
    Context context;

    public AuthHelper(Context context) {
        this.context = context;
    }

    public void authenticated(String email){
        SharedPreferences sharedpreferences = context.getSharedPreferences(appPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(authenticatedUserEmailKey, email);
        editor.commit();
    }

    public String getLoggedInUserEmail(){
        SharedPreferences sharedPref = context.getSharedPreferences(appPreferences, Context.MODE_PRIVATE);
        String email = sharedPref.getString(authenticatedUserEmailKey, null);
        if (email == null){
            logOut();
        }
        return email;
    }

    public void logOut(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(appPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(authenticatedUserEmailKey);
        editor.commit();
        try {
            Plus.AccountApi.clearDefaultAccount(LogIn.mGoogleApiClient);
            LogIn.mGoogleApiClient.disconnect();
            LogIn.mGoogleApiClient.connect();
        } catch (Exception e) {
            Log.e("test123", "Failed to Logout, might be already out?");
        }
        context.startActivity(new Intent(context, LogIn.class));
    }



}
