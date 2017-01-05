package com.example.lenovo.cnews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ActivityCategories extends AppCompatActivity {

    ArrayList<String> data;
    RecyclerView recyclerView;
    AdapterCategories adapter;
    Bundle b;
    Button button_skip , button_done;
    ArrayList<String> categories;
    Boolean network = Boolean.FALSE;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_categories);
        sp = getSharedPreferences("CATEGORIES",MODE_PRIVATE);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_categories);
        button_skip = (Button)findViewById(R.id.button_skip);
        button_done = (Button)findViewById(R.id.button_done);
        String IsnetworkOn = getIntent().getStringExtra("IsNetworkOn");
        if(IsnetworkOn.compareTo("YES")==0){
            network = Boolean.TRUE;
            b = getIntent().getBundleExtra("DATA");
        }
        else if(IsnetworkOn.compareTo("NO")==0){
            network = Boolean.FALSE;
        }
        data = new ArrayList<>();
        data.add(ConstantsCategories.CATEGORY_ITEM_INDIA);
        data.add(ConstantsCategories.CATEGORY_ITEM_WORLD);
        data.add(ConstantsCategories.CATEGORY_ITEM_POLITICS);
        data.add(ConstantsCategories.CATEGORY_ITEM_BUSINESS);
        data.add(ConstantsCategories.CATEGORY_ITEM_SPORTS);
        data.add(ConstantsCategories.CATEGORY_ITEM_CRICKET);
        data.add(ConstantsCategories.CATEGORY_ITEM_BOLLYWOOD);
        data.add(ConstantsCategories.CATEGORY_ITEM_LIFESTYLE);
        adapter = new AdapterCategories(this,data);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityCategories.this,MainActivity.class);
                if(network) {
                    i.putExtra("IsNetworkOn","YES");
                    i.putExtra("DATA",b);
                }
                else if(!network){
                    i.putExtra("IsNetworkOn","NO");
                }

                startActivity(i);
                finish();
            }
        });
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categories = adapter.getCategories();
                Log.i("categories","Category size"+categories.size());

                Intent i = new Intent(ActivityCategories.this,MainActivity.class);
                if(network) {
                    i.putExtra("IsNetworkOn","YES");
                    i.putExtra("DATA",b);
                    i.putExtra("CATEGORIES",categories);
                }
                else if(!network){
                    i.putExtra("IsNetworkOn","NO");
                    i.putExtra("CATEGORIES",categories);

                }
                //Adding to sharedpreferences
                SharedPreferences.Editor editor = sp.edit();

                Set<String> set = new HashSet<String>();
                set.addAll(categories);
                editor.putStringSet("key", set);
                editor.apply();

                startActivity(i);
                finish();
            }
        });

    }
}
