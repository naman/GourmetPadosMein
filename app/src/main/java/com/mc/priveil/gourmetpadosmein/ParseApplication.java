package com.mc.priveil.gourmetpadosmein;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by magusverma on 18/10/15.
 */
public class ParseApplication extends Application {
    public static final String YOUR_APPLICATION_ID = "WU842Ed8GWCo7napgpaxk9FBSZ6LBqrhj6cv0XoO";
    public static final String YOUR_CLIENT_KEY = "Z5WO1weLaVu7ZAQdn97qEjTApHPoDG0BFM77OUqv";

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

        // Test creation of object
        ParseObject testObject = new ParseObject("User");
        testObject.put("username", "magus");
        testObject.saveInBackground();
    }
}