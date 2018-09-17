package com.proj3cs478sravya.srv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.proj3cs478sravya.treasury_client.dailycash.DailyCash;

public class BalanceService extends Service {
    private TreasuryDatabaseHelper tdh;
    private Context ct;
    @Override
    public void onCreate() {
        super.onCreate();

        ct = this;
    }
 private void clearAll(){
        try {
            tdh.getWritableDatabase().delete(TreasuryDatabaseHelper.TB_NAME, null, null);
        }
        catch (Exception e){
            System.out.print(e);
        }
 }
    // Implement the Stub for this Object
    private final treasuryInterface.Stub mBinder = new treasuryInterface.Stub() {
     @Override
       public boolean createDatabase(){
            tdh = new TreasuryDatabaseHelper(ct);
            clearAll();
            String strs[];
                InputStream instream = getApplicationContext().getResources().openRawResource(R.raw.treasury);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line, line1 = "";
                    try {
                        while ((line = buffreader.readLine()) != null) {
                            strs = line.split(",");
                           tdh.insert_db(strs);
                        }
                    }
//try{
//    vals.put(tdh.Year, 1992);
//    vals.put(tdh.Date,"tuesdy");
//    tdh.getWritableDatabase().insert(TreasuryDatabaseHelper.TB_NAME,null,vals);
//        }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            return true;
                }
        @Override
        public List<DailyCash> dailyCash(int day, int month, int year, int range) throws RemoteException {

           List<DailyCash> dc = new ArrayList<DailyCash>();
           return dc;
        }

       };







    @Override
    public void onDestroy() {
        super.onDestroy();
      tdh.getWritableDatabase().close();
       tdh.deleteDB();

    }

    // Return the Stub defined above
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}