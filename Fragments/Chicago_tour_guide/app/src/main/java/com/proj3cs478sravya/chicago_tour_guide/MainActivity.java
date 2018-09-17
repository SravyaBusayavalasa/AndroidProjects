package com.proj3cs478sravya.chicago_tour_guide;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


//activity to display attractions and their links
public class MainActivity extends AppCompatActivity implements ChicagoAttractionsFragment.SelectionListener {

    public static String[] attractions_array;
    public static String[] weblinks_array;

    private Webfragment weblinkFragment = new Webfragment();
    private static ChicagoAttractionsFragment AttractionsFragment;
    // fragment manager instance
    private FragmentManager mFragmentManager;
    private FrameLayout mAttractionsFrameLayout, mWebsiteFrameLayout;
    //create tags to retain fragments
    private static final String TAG_RETAINED_FRAGMENT = "FragmentsRetainedFragment";
    private static final String TAG_RETAINED_FRAGMENT2 = "WebsiteRetainedFragment";
    private int curr_index = -1;

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "MainActivity";
    boolean isSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //isSelected = false;
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        attractions_array = getResources().getStringArray(R.array.AttractionsTitles);
        weblinks_array = getResources().getStringArray(R.array.Websites);

        setContentView(R.layout.activity_main);

        mAttractionsFrameLayout = (FrameLayout) findViewById(R.id.ar_container);
        mWebsiteFrameLayout = (FrameLayout) findViewById(R.id.w_container);

        mFragmentManager = getFragmentManager();
        AttractionsFragment = (ChicagoAttractionsFragment) getFragmentManager().findFragmentByTag(TAG_RETAINED_FRAGMENT);
// add tags to fragment instances
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (savedInstanceState == null) {
            AttractionsFragment = new ChicagoAttractionsFragment();
            weblinkFragment = new Webfragment();
            ft.replace(R.id.ar_container, AttractionsFragment, TAG_RETAINED_FRAGMENT).commit();
        }

        //save the selected index position into bundle
        if (null != savedInstanceState && savedInstanceState.getInt("INDEX_SELECTED") >= 0) {
            curr_index = savedInstanceState.getInt("INDEX_SELECTED");
        }
        setLayout(isSelected || (curr_index >= 0));

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                isSelected = false;
                setLayout(isSelected);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        if (curr_index >= 0) {
            ((ChicagoAttractionsFragment) AttractionsFragment).getListView().setItemChecked(curr_index, true);
            onListSelection(curr_index);
        }
    }

    private void setLayout(boolean isSelected) {
        // manage layout dynamically for different configuration
        boolean display_frag1 = !isSelected || Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation;
        boolean showWebsite = isSelected;
        if (display_frag1 && !showWebsite) {
            mAttractionsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        } else if (display_frag1 && showWebsite) {
            mAttractionsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1f));
            mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 2f));
        } else if (showWebsite) {
            mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mAttractionsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        }
    }

    // Called when the user selects an item in the attractions fragment
    @Override
    public void onListSelection(int index) {
        weblinkFragment = (Webfragment) getFragmentManager().findFragmentByTag(TAG_RETAINED_FRAGMENT2);
        curr_index = index;
        if (null ==  weblinkFragment) {
            weblinkFragment = new Webfragment();
        }
        //save fragment on backstack explicitly
        if (!weblinkFragment.isAdded()) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.w_container, weblinkFragment, TAG_RETAINED_FRAGMENT2)
                    .addToBackStack(TAG_RETAINED_FRAGMENT2)
                    .commit();
            fm.executePendingTransactions();
        }

        isSelected = true;
        ((Webfragment) weblinkFragment).showwebviewAtIndex(index);
        setLayout(isSelected);
    }
    //set the selected index into bundle
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("INDEX_SELECTED", curr_index);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();

    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }


    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_attractions:
                Toast.makeText(getBaseContext(),"Attractions",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_restaurants:
                Intent ij = new Intent(MainActivity.this,ChicagoRestaurants.class);
                startActivity(ij);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}


