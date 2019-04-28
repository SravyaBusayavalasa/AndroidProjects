package com.midtronicsinterview.interviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.concurrent.ExecutionException;


/**
 * Created by Sravya on 4/25/2019.
 */

public class Country_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_layout);
        Intent k = getIntent();
        Bundle l = k.getExtras();
        String data = l.getString("country");
        //Some url endpoint that you may have
        TextView capital_tv = (TextView)findViewById(R.id.Capital);
        TextView population_tv = (TextView)findViewById(R.id.Population);
        TextView subregion_tv = (TextView)findViewById(R.id.SubRegion);
        TextView region_tv = (TextView)findViewById(R.id.Region);
        TextView area_tv = (TextView)findViewById(R.id.Area);
        TextView header_tv = (TextView)findViewById(R.id.Header);
        String myUrl = "https://restcountries.eu/rest/v1/name/"+data;
        //String to place our result in
        String result = "Hello";
        String capital = "";
        String area = "";
        String population = "";
        String region = "";
        String subregion = "";
        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();
        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
            JSONTokener tokener = new JSONTokener(result);
            JSONArray finalResult = new JSONArray(tokener);
            JSONObject jsonObject1 = finalResult.getJSONObject(0);
            capital = jsonObject1.optString("capital");
            area = jsonObject1.optString("area");
            population = jsonObject1.optString("population");
            region = jsonObject1.optString("region");
            subregion = jsonObject1.optString("subregion");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
         capital_tv.setText(capital);
         population_tv.setText(population);
         area_tv.setText(area);
         region_tv.setText(region);
         subregion_tv.setText(subregion);
         header_tv.setText(data);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //	Handle	item	selec@on

        Intent vc = new Intent(this,Profile_Activity.class);
        startActivity(vc);
        return true;
    }
}
