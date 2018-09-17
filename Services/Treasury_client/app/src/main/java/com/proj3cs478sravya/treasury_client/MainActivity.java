package com.proj3cs478sravya.treasury_client;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import com.proj3cs478sravya.common.*;
public class MainActivity extends Activity {
    private treasuryInterface serviceProxy;
    private boolean connect = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button create = (Button) findViewById(R.id.create_db);
        Button display = (Button) findViewById(R.id.display_db);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean val;
            try
            {
                if(connect == true) {
                    val = serviceProxy.createDatabase();
                    Toast.makeText(MainActivity.this,"serviced!!",Toast.LENGTH_SHORT).show();
                }
                else
                    Log.i(TAG,"fail");

            }
            catch (RemoteException e){
                System.out.print("failed");
            }
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] a;
                try
                {
                    if(connect == true)
                        a = serviceProxy.dailyCash(1,5,2017,3);
                    else
                        Log.i(TAG,"fail");
                }
                catch (RemoteException e){
                    System.out.print("failed");
                }

            }
        });
    }

    private final ServiceConnection connectServer = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceProxy = treasuryInterface.Stub.asInterface(service);
            connect = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connect = false;
        }
    };

    @Override
    protected void onResume(){
        super.onResume();
        if(!connect){
            boolean b = false;
            Intent i = new Intent(treasuryInterface.class.getName());
            @SuppressLint("WrongConstant") ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
            i.setComponent(new ComponentName(info.serviceInfo.packageName,info.serviceInfo.name));
            b = bindService(i,this.connectServer,Context.BIND_AUTO_CREATE);
            if(b){
                Log.i(TAG,"connection established");
            }
            else
            {
                Log.i(TAG,"connection failed");
            }

        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(connect)
            unbindService(this.connectServer);
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    @Override
    protected void onStop(){
        super.onStop();
    }
}
