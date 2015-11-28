package com.mc.priveil.gourmetpadosmein;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Naman on 21-Nov-15.
 */
public class MyReceiver extends ParsePushBroadcastReceiver {

    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";


    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);

        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String jsonData = extras.getString("com.parse.Data");
                JSONObject json;
                json = new JSONObject(jsonData);
                String pushContent = json.getString("alert");
                String offering_id = json.getString("offeringId");
                String type = json.getString("type");

                if(type.equals("apply")){

                    //intent to your activity
                    Log.d("Notification", "Switching to  a new activity!");
                    Intent newintent =  new Intent(context.getApplicationContext(), AcceptGuestActivity.class);
                    newintent.putExtra(MESSAGE_OBJECTID,offering_id);
                    newintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(newintent);

                }else if(type.equals("rating")){

                } else if (type.equals("reject")) {
                    //intent to your activity
                    Log.d("Notification", "Switching to  a new activity!");
                    Intent newintent = new Intent(context.getApplicationContext(), OfferingViewActivity.class);
                    newintent.putExtra(MESSAGE_OBJECTID, offering_id);
                    newintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(newintent);
                }
}
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }


}