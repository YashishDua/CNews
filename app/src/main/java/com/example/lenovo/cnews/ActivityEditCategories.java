package com.example.lenovo.cnews;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ActivityEditCategories extends AppCompatActivity {

    AdapterEditCategories adapterEditCategories;
    ArrayList<String> categories , AllCategories;
    RecyclerView recyclerView;
    FloatingActionButton fab , fab_save;
    android.support.v7.app.ActionBar actionBar;
    AdapterDialogAddCategory adapterDialogAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);

        fab_save = (FloatingActionButton)findViewById(R.id.fab_save);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_edit_categories);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Categories");

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }


        AllCategories = new ArrayList<>();
        AllCategories.add(ConstantsCategories.CATEGORY_ITEM_INDIA);
        AllCategories.add(ConstantsCategories.CATEGORY_ITEM_BOLLYWOOD);
        AllCategories.add(ConstantsCategories.CATEGORY_ITEM_WORLD);
        AllCategories.add(ConstantsCategories.CATEGORY_ITEM_BUSINESS);
        AllCategories.add(ConstantsCategories.CATEGORY_ITEM_LIFESTYLE);
        AllCategories.add(ConstantsCategories.CATEGORY_ITEM_POLITICS);
        AllCategories.add(ConstantsCategories.CATEGORY_ITEM_CRICKET);
        AllCategories.add(ConstantsCategories.CATEGORY_ITEM_SPORTS);


        categories = new ArrayList<>();
        categories.addAll(getIntent().getStringArrayListExtra("CATEGORIES"));
        adapterEditCategories = new AdapterEditCategories(this,categories);
        LinearLayoutManager l = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(adapterEditCategories);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN  | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                int i = viewHolder.getAdapterPosition();
                int j = target.getAdapterPosition();
                String first = categories.get(i);
                String second = categories.get(j);
                String temp = first;
                first = second;
                second=temp;
                categories.set(i,second);
                categories.set(j,first);
                Log.i("----","Categories list in editcategoryactivity"+categories.get(0)+categories.get(1));

                adapterEditCategories.notifyItemMoved(i,j);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int i = viewHolder.getAdapterPosition();
                categories.remove(i);
                adapterEditCategories.notifyItemRemoved(i);

            }
        };

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("EditedCategories",categories);
                setResult(1,i);
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder db = new AlertDialog.Builder(ActivityEditCategories.this);
                db.setTitle("Add Categories");
                final View v = LayoutInflater.from(ActivityEditCategories.this).inflate(R.layout.dialog_add_category,null);
                db.setView(v);

                AllCategories.removeAll(categories);

                adapterDialogAddCategory = new AdapterDialogAddCategory(ActivityEditCategories.this,AllCategories);

                ListView listView = (ListView)v.findViewById(R.id.listview_dialog);
                listView.setAdapter(adapterDialogAddCategory);

              //  final View view1 = LayoutInflater.from(ActivityEditCategories.this).inflate(R.layout.item_dialog_edit_categories,null);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i("----","Item Clicked !");

                        final CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox);
                        final TextView text = (TextView)view.findViewById(R.id.textview_dialog_edit_categories);

                        checkBox.setChecked(!checkBox.isChecked());

                        if(checkBox.isChecked())
                        {
                            Log.i("----","Adding to categories");
                            categories.add(String.valueOf(text.getText()));

                        }
                        else {

                            Log.i("----","Removing from categories");
                            categories.remove(text.getText());

                        }

                    }
                });
                db.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        adapterEditCategories.notifyDataSetChanged();
                        dialogInterface.dismiss();

                    }
                });
                db.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                db.create().show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        this.finish();
        return true;
    }
}
