package com.proj3cs478sravya.chicago_tour_guide;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
//import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Sravya on 4/2/2018.
 */
// activity to display restaurants and their websites
public class ChicagoRestaurants extends AppCompatActivity implements Chicagorestaurantsfragment.ListSelectionListener{

        public static String[] mRestaurantsArray;
        public static String[] RWebsiteArray;

        private Rlinks RWebsiteFragment = new Rlinks();
        private static Chicagorestaurantsfragment mRestaurantsFragment;

        private FragmentManager mFragmentManager;
        private FrameLayout mRestaurantsFrameLayout, mWebsiteFrameLayout;

        private static final String TAG_RETAINED_FRAGMENT = "FragmentsRetainedFragment";
        private static final String TAG_RETAINED_FRAGMENT2 = "WebsiteRetainedFragment";
        private int mCurrIdx = -1;

        private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
        private static final String TAG = "RestaurantsActivity";
        boolean isSelected = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                //isSelected = false;
                Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
                super.onCreate(savedInstanceState);
                //retreive names and weblinks
                mRestaurantsArray = getResources().getStringArray(R.array.RestaurantsTitles);
                RWebsiteArray = getResources().getStringArray(R.array.RWebsites);

                setContentView(R.layout.res_activity);
//retrieve framelayouts from activity resource file
                mRestaurantsFrameLayout = (FrameLayout) findViewById(R.id.ar2_container);
                mWebsiteFrameLayout = (FrameLayout) findViewById(R.id.w2_container);
//fragment manager instance
                mFragmentManager = getFragmentManager();
                mRestaurantsFragment = (Chicagorestaurantsfragment) getFragmentManager().findFragmentByTag(TAG_RETAINED_FRAGMENT);
// assign tags to fragments
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                if (null == savedInstanceState) {
                        mRestaurantsFragment= new Chicagorestaurantsfragment();
                        RWebsiteFragment = new Rlinks();
                        ft.replace(R.id.ar2_container, mRestaurantsFragment, TAG_RETAINED_FRAGMENT).commit();
                }
                if (null != savedInstanceState && savedInstanceState.getInt("SELECTED_INDEX") >= 0) {
                        mCurrIdx = savedInstanceState.getInt("SELECTED_INDEX");
                }
                setLayout(isSelected || (mCurrIdx >= 0));

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
                if (mCurrIdx >= 0) {
                        ((Chicagorestaurantsfragment)mRestaurantsFragment ).getListView().setItemChecked(mCurrIdx, true);
                        onListSelection(mCurrIdx);
                }
        }

        private void setLayout(boolean isSelected) {
                // Set the layout for both the Website and restaurants
                boolean showMonuments = !isSelected || Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation;
                boolean showWebsite = isSelected;
                if (showMonuments && !showWebsite) {
                        mRestaurantsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.MATCH_PARENT));
                } else if (showMonuments && showWebsite) {
                        mRestaurantsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                        mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.MATCH_PARENT, 2f));
                } else if (showWebsite) {
                        mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        mRestaurantsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.MATCH_PARENT));
                }
        }

        // Called when the user selects an item in the RestaurantsFragment
        @Override
        public void onListSelection(int index) {
                RWebsiteFragment = (Rlinks) getFragmentManager().findFragmentByTag(TAG_RETAINED_FRAGMENT2);
                mCurrIdx = index;
                if (null == RWebsiteFragment) {
                        RWebsiteFragment = new Rlinks();
                }
                if (!RWebsiteFragment.isAdded()) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.w2_container, RWebsiteFragment, TAG_RETAINED_FRAGMENT2)
                                .addToBackStack(TAG_RETAINED_FRAGMENT2)
                                .commit();
                        fm.executePendingTransactions();
                }

                isSelected = true;
                ((Rlinks) RWebsiteFragment).showWebsiteAtIndex(index);
                setLayout(isSelected);
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
                outState.putInt("SELECTED_INDEX", mCurrIdx);
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

        //options menu to navigate between activities
        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
                switch(item.getItemId())
                {
                        case R.id.menu_attractions:
                                Intent intent = new Intent(ChicagoRestaurants.this,MainActivity.class);
                                startActivity(intent);
                                return true;
                        case R.id.menu_restaurants:
                                Toast.makeText(getBaseContext(),"Restaurants",Toast.LENGTH_SHORT).show();
                                return true;
                        default:
                                return super.onOptionsItemSelected(item);

                }
        }

}