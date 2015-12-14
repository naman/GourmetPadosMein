package com.mc.priveil.gourmetpadosmein.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.mc.priveil.gourmetpadosmein.AcceptGuestActivity;
import com.mc.priveil.gourmetpadosmein.Forms.ReviewActivity;
import com.mc.priveil.gourmetpadosmein.OfferingViewActivity;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by Naman on 21-Nov-15.
 */
public class MyReceiver extends ParsePushBroadcastReceiver {

    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";

    public static Bitmap getCompressedImage(Bitmap bitmap){
        try {
            Integer scaledown = (921600 * 5 * 100) / bitmap.getByteCount();
            Log.i("scaledown", scaledown.toString());
            if (scaledown < 1) scaledown = 1;
            if (scaledown > 100) scaledown = 100;
            Log.i("size of image", String.valueOf(bitmap.getByteCount()));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, scaledown, stream);
            byte[] bitmapdata = stream.toByteArray();
            Log.i("size of image", String.valueOf(bitmap.getByteCount()));
            bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        }catch(Exception e) {
            Log.i("Failed compressing", e.getMessage());
        }
        return bitmap;
    }
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
                    Log.d("Notification", "Switching to  a new activity!");
                    ParsePush.unsubscribeInBackground(offering_id);

                    Intent newintent = new Intent(context.getApplicationContext(), ReviewActivity.class);
                    newintent.putExtra(MESSAGE_OBJECTID, offering_id);
                    newintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(newintent);

                } else if (type.equals("reject")) {
                    //intent to your activity
                    ParsePush.unsubscribeInBackground(offering_id);

                    Log.d("Notification", "Switching to  a new activity!");
                    Intent newintent1 = new Intent(context.getApplicationContext(), OfferingViewActivity.class);
                    newintent1.putExtra("objectid", offering_id);
                    newintent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(newintent1);
                }
}
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}