package com.proj3cs478sravya.chicago_tour_guide;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Sravya on 3/31/2018.
 */

public class Receiver1 extends BroadcastReceiver {

    public static final String Intent_a = "com.proj3cs478sravya.chicago_tour.Attractions";
    public static final String Intent_r = "com.proj3cs478sravya.chicago_tour.Restaurants";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Intent_a)){
            Toast.makeText(context,"Intent for attractions received",Toast.LENGTH_SHORT).show();
            Intent intent_attr = new Intent(context.getApplicationContext(),MainActivity.class);
            intent_attr.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.getApplicationContext().startActivity(intent_attr);
        }else if (action.equals(Intent_r))
        {
            Toast.makeText(context,"Intent for restaurants received",Toast.LENGTH_SHORT).show();
            Intent intent_res = new Intent(context.getApplicationContext(),ChicagoRestaurants.class);
            intent_res.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.getApplicationContext().startActivity(intent_res);

        }
    }
}
