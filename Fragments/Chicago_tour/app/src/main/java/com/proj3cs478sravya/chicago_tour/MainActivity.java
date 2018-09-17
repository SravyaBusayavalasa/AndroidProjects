package com.proj3cs478sravya.chicago_tour;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
// activity to display text views and buttons
public class MainActivity extends Activity {
    public static final String INTENT_A = "com.proj3cs478sravya.chicago_tour.Attractions";
    public static final String INTENT_R = "com.proj3cs478sravya.chicago_tour.Restaurants";

    Button rButton;
    Button aButton;
    public static final String PERMISSION = "edu.uic.cs478.sp18.project3" ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //check if app has permission if not granted, ask user acceptance
        checkPermissionAndBroadcast();
        rButton = (Button) findViewById(R.id.b_attractions) ;
        aButton = (Button) findViewById(R.id.b_restaurants);
        //trigger intents for corresponding button
        rButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkPermissionAndBroadcast1();
            }
        }) ;
        aButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkPermissionAndBroadcast2();
            }
        }) ;

    }

    private void checkPermissionAndBroadcast() {
        // permission if granted display toast message
        int result = ContextCompat.checkSelfPermission(this, PERMISSION);
        if (PackageManager.PERMISSION_GRANTED == result) {
            Toast.makeText(getBaseContext(),"granted",Toast.LENGTH_SHORT).show();
            // if not granted then request for permission
        } else if (PackageManager.PERMISSION_DENIED == result) {
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION}, 0);
        }
    }
    //send intent with action com.proj3cs478sravya.chicago_tour.attractions when attractions button pressed
    private void checkPermissionAndBroadcast1() {

        int result = ContextCompat.checkSelfPermission(this, PERMISSION);
        if (PackageManager.PERMISSION_GRANTED == result) {
            Intent intent_attr = new Intent();
            intent_attr.setAction(INTENT_A);
            intent_attr.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intent_attr);
        } else {
            checkPermissionAndBroadcast();
        }
    }
    //Send intent with action com.proj3cs478sravya.chicago_tour.restaurants when restaurants button is pressed
    private void checkPermissionAndBroadcast2() {

        int result = ContextCompat.checkSelfPermission(this, PERMISSION);
        if (PackageManager.PERMISSION_GRANTED == result) {
            Intent intent_res = new Intent();
            intent_res.setAction(INTENT_R);
            intent_res.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intent_res);
        } else {
            checkPermissionAndBroadcast();
        }


    }
    //check permission result
    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        if (results.length > 0) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent_attr = new Intent();
                intent_attr.setAction(INTENT_A);
                intent_attr.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent_attr);
            } else if (results[1] == PackageManager.PERMISSION_GRANTED) {
                Intent intent_res = new Intent();
                intent_res.setAction(INTENT_R);
                intent_res.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent_res);
            }else
            {
                Toast.makeText(getBaseContext(),"permission_denied",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
