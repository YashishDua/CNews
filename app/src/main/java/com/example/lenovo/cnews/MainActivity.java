package com.example.lenovo.cnews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements FragmentNoInternetConnection.FragmentNoInternetConnectionInterface ,FragmentDekhNewsContent.FragementDekhNewsContentInterface {

    Boolean isNetworkOn;
    Bundle b;
    ArrayList<String> categories;
    TabLayout tabLayout;
    SharedPreferences sp , sp_profile;
    SwipeRefreshLayout swipeRefreshLayout;
    ProfileTracker mProfileTracker;
    CallbackManager callbackManager;
    HandlerHindustanTimes handlerHindustanTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadHTIndia(this).execute("http://www.hindustantimes.com/ht-feed/htsyndication.aspx?c=india&cat=india&Sec=True");
      //  handlerHindustanTimes = new HandlerHindustanTimes();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //call fragment
                onclick();
                //testing....
                test();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        sp = getSharedPreferences("CATEGORIES", MODE_PRIVATE);
        sp_profile = getSharedPreferences("PROFILE", MODE_PRIVATE);

        categories = new ArrayList<>();
        categories.addAll(sp.getStringSet("key", null));

        setTabsInTabLayout(categories);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        String network = getIntent().getStringExtra("IsNetworkOn");

        if (network.compareTo("YES") == 0) {
            isNetworkOn = Boolean.TRUE;
            b = getIntent().getBundleExtra("DATA");
        } else isNetworkOn = Boolean.FALSE;

        if (isNetworkOn) {
            //call content fragment
            FragmentDekhNewsContent df = new FragmentDekhNewsContent();
            df.setListener(MainActivity.this);
            df.setArguments(b);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, df);
            fragmentTransaction.commit();

        } else callNoInternetFragment();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (isNetworkAvailable()) {
                    //call content fragment
                    b = getIntent().getBundleExtra("DATA");
                    FragmentDekhNewsContent df = new FragmentDekhNewsContent();

                    if(b.isEmpty())
                    df.ReloadData();

                    df.setListener(MainActivity.this);
                    df.setArguments(b);

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, df);
                    fragmentTransaction.commit();

                } else callNoInternetFragment();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        swipeRefreshLayout.setRefreshing(false);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                setTabsInTabLayout(data.getStringArrayListExtra("EditedCategories"));

                SharedPreferences.Editor editor = sp.edit();
                Set<String> set = new HashSet<String>();
                set.addAll(data.getStringArrayListExtra("EditedCategories"));
                editor.putStringSet("key", set);
                editor.apply();

                //updating our categories so that next time intent send updatedcategory list
                categories.clear();
                categories.addAll(set);

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final TextView loginName;
        if (item.getItemId() == R.id.edit) {
            Intent i = new Intent(MainActivity.this, ActivityEditCategories.class);
            Log.i("----", "Before Edit" + categories.size());
            i.putExtra("CATEGORIES", categories);
            startActivityForResult(i, 1);
        }
        if(item.getItemId()==R.id.login){

            if(sp_profile.getBoolean("LOGGED",Boolean.TRUE)){
                //start activity
            Toast.makeText(MainActivity.this,"Already Signed In",Toast.LENGTH_SHORT).show();
            }
            else{

                final AlertDialog.Builder db = new AlertDialog.Builder(MainActivity.this);
                final View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_login,null);
                db.setView(v);
                loginName = (TextView)v.findViewById(R.id.login_name);
                final SharedPreferences.Editor editor = sp_profile.edit();
                loginName.setText(sp.getString("LOGINID",null));

                LoginButton loginButton = (LoginButton)v.findViewById(R.id.login_button);
                callbackManager = CallbackManager.Factory.create();
                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        if(Profile.getCurrentProfile()==null)
                        {
                            mProfileTracker = new ProfileTracker() {
                                @Override
                                protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                                    // profile2 is the new profile
                                    loginName.setText(profile2.getFirstName());
                                    mProfileTracker.stopTracking();

                                    editor.putBoolean("LOGGED",Boolean.TRUE);
                                    editor.putString("LOGINID",profile2.getFirstName());
                                    editor.apply();
                                }
                            };
                        }
                        else {
                            loginName.setText(Profile.getCurrentProfile().getFirstName());

                            editor.putBoolean("LOGGED", Boolean.TRUE);
                            editor.putString("LOGINID", Profile.getCurrentProfile().getFirstName());
                            editor.apply();
                        }

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(MainActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                    }
                });
                db.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                db.create().show();

            }
        }
        if(item.getItemId()==R.id.logout){
            SharedPreferences.Editor editor = sp_profile.edit();
            editor.putBoolean("LOGGED",Boolean.FALSE);
            editor.apply();
            Toast.makeText(MainActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();

        }
        return true;
    }

    @Override
    public void onclick() {
        if (isNetworkAvailable()) {

            //call content fragment
            FragmentDekhNewsContent df = new FragmentDekhNewsContent();
            df.setListener(this);
            df.ReloadData();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, df);
            fragmentTransaction.commit();


        } else callNoInternetFragment();

      //  swipeRefreshLayout.setRefreshing(false);


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void notifyPass(String pubDataID, String title, String publisher, String imageURL) {
        Log.i("----", "Fragment Notified Clicked");
        if (isNetworkAvailable()) {
            Intent i = new Intent(MainActivity.this, ActivityDekhNewsSingleItem.class);
            Log.i("Adapter", "Sending ID to SingleItemActivity " + pubDataID);
            i.putExtra("ID", pubDataID);
            i.putExtra("TITLE", title);
            i.putExtra("PUBLISHER", publisher);
            i.putExtra("URL", imageURL);
            startActivity(i);
        } else callNoInternetFragment();

    }

    //this is a call from fragmentdekhnews to stop refreshing button
    @Override
    public void dataLoaded() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void setTabsInTabLayout(ArrayList<String> tabs) {

        tabLayout.removeAllTabs();
        for (int i = 0; i < tabs.size(); i++)
            tabLayout.addTab(tabLayout.newTab().setText(tabs.get(i)));

    }

    public void callNoInternetFragment() {
        FragmentNoInternetConnection nf = new FragmentNoInternetConnection();
        nf.setListener(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, nf);
        fragmentTransaction.commit();

    }
    //testing.....
    void test(){
        String url = "http://www.hindustantimes.com/ht-feed/htsyndication.aspx?c=india&cat=india&Sec=True";
        new DownloadHTIndia(MainActivity.this).execute(url);

    }


}



