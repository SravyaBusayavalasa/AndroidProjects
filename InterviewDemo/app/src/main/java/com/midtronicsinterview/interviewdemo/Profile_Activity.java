package com.midtronicsinterview.interviewdemo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Sravya on 4/26/2019.
 */

public class Profile_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        TextView education_tv = (TextView)findViewById(R.id.textView5);
        TextView work_tv = (TextView)findViewById(R.id.textView7);
        education_tv.setText("Education:\n" +
                "Masters of Science: Computer Science, University of Illinois at Chicago, May 2019, GPA 3.72\n" +
                "Bachelor of Technology: Computer Science & Engineering, ANITS, May 2014, GPA 8.65");
        work_tv.setText("Work:\n"+"A proactive and organized individual with around 4 years of extensive development experience with a solid grasp of " +
                "data structures and object-oriented designs. Expert in writing full-stack code to support multiple platforms, including web, android. " +
                "Looking to leverage industry hardened technical skills into a developer position to build a strong in a dynamic workplace.");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.countriesoption, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent vc = new Intent(this,MainActivity.class);
        startActivity(vc);
        return true;
    }
}
