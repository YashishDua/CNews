package com.example.lenovo.cnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class SplashActivity extends Activity implements DownloadData.DownloadDataInterface {

    DekhNewsHandler handler ;
    private static int SPLASH_TIME_OUT = 2000;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = getSharedPreferences("CategoryScreen",MODE_PRIVATE);
        String url = "http://www.dekhnews.com/feed/";
        if(isNetworkAvailable()) {
            new DownloadData(this).execute(url);
            handler = new DekhNewsHandler();
        }else if(!isNetworkAvailable()) {
            new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

                @Override
                public void run() {

                    Intent i = null;
                    if(sp.contains("CategoryScreenDisplayedBefore")) {
                        if (sp.getString("CategoryScreenDisplayedBefore", null).compareTo("Yes") == 0) {
                            Log.i("----","Sp saved before opening main");
                            i = new Intent(SplashActivity.this, MainActivity.class);
                            i.putExtra("IsNetworkOn", "NO");
                            startActivity(i);
                            finish();

                        }
                    }
                    else {
                        i = new Intent(SplashActivity.this, ActivityCategories.class);
                        i.putExtra("IsNetworkOn", "NO");
                        startActivity(i);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("CategoryScreenDisplayedBefore", "Yes");
                        editor.apply();
                        finish();

                    }
                }
            }, SPLASH_TIME_OUT);
        }



    }


    ArrayList<ContentData> data = new ArrayList<>();

    @Override
    public void dataprocessed() throws IOException {
        data.addAll(handler.getData());


        Intent i = null;
        if(sp.contains("CategoryScreenDisplayedBefore")){
        if(sp.getString("CategoryScreenDisplayedBefore",null).compareTo("Yes")==0) {
            i = new Intent(SplashActivity.this, MainActivity.class);
            i.putExtra("IsNetworkOn","YES");
            Bundle b = new Bundle();
            b.putSerializable("DATA",data);
            i.putExtra("DATA",b);
            startActivity(i);
            finish();
        }
        }
        else {
            i = new Intent(SplashActivity.this, ActivityCategories.class);
            i.putExtra("IsNetworkOn", "YES");
            Bundle b = new Bundle();
            b.putSerializable("DATA", data);
            i.putExtra("DATA", b);
            startActivity(i);

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("CategoryScreenDisplayedBefore", "Yes");
            editor.apply();
            // close this activity
            finish();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}


