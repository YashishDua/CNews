package com.example.lenovo.cnews;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo on 22-08-2016.
 */
public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.OurHolder> {
    Context context;
    ArrayList<String> Data ;
    ArrayList<String> addcategories ;
    static int counter_india=0 , counter_world=0 , counter_politics=0 , counter_bollywood=0 ,
            counter_lifestyle=0 , counter_cricket=0 , counter_sports=0  ,counter_business=0 ;



    public AdapterCategories(Context context, ArrayList<String> data ) {
        this.context = context;
        Data = data;

    }

    public class OurHolder extends RecyclerView.ViewHolder{

        Button categoryButton ;

        public OurHolder(View itemView) {
            super(itemView);
            categoryButton = (Button) itemView.findViewById(R.id.button_category);

        }
    }
    @Override
    public AdapterCategories.OurHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(context).inflate(R.layout.item_categories_button,parent , false);
        return new OurHolder(v);
    }


    @Override
    public void onBindViewHolder(final AdapterCategories.OurHolder holder, final int position) {

        holder.categoryButton.setText(Data.get(position));
        holder.categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Data.get(position).compareTo(ConstantsCategories.CATEGORY_ITEM_INDIA)==0){
                   counter_india++ ;
                    if(counter_india%2!=0)
                    holder.categoryButton.setSelected(true);
                    else holder.categoryButton.setSelected(false);

                }
                if(Data.get(position).compareTo(ConstantsCategories.CATEGORY_ITEM_WORLD)==0){
                   counter_world++ ;
                    if(counter_world%2!=0)
                        holder.categoryButton.setSelected(true);
                    else holder.categoryButton.setSelected(false);

                }
                if(Data.get(position).compareTo(ConstantsCategories.CATEGORY_ITEM_BUSINESS)==0){
                   counter_business++ ;
                    if(counter_business%2!=0)
                        holder.categoryButton.setSelected(true);
                    else holder.categoryButton.setSelected(false);

                }
                if(Data.get(position).compareTo(ConstantsCategories.CATEGORY_ITEM_SPORTS)==0){
                   counter_sports++ ;
                    if(counter_sports%2!=0)
                        holder.categoryButton.setSelected(true);
                    else holder.categoryButton.setSelected(false);

                }
                if(Data.get(position).compareTo(ConstantsCategories.CATEGORY_ITEM_CRICKET)==0){
                   counter_cricket++ ;
                    if(counter_cricket%2!=0)
                        holder.categoryButton.setSelected(true);
                    else holder.categoryButton.setSelected(false);

                }
                if(Data.get(position).compareTo(ConstantsCategories.CATEGORY_ITEM_POLITICS)==0){
                   counter_politics++ ;
                    if(counter_politics%2!=0)
                        holder.categoryButton.setSelected(true);
                    else holder.categoryButton.setSelected(false);

                }
                if(Data.get(position).compareTo(ConstantsCategories.CATEGORY_ITEM_BOLLYWOOD)==0){
                   counter_bollywood++ ;
                    if(counter_bollywood%2!=0)
                        holder.categoryButton.setSelected(true);
                    else holder.categoryButton.setSelected(false);

                }
                if(Data.get(position).compareTo(ConstantsCategories.CATEGORY_ITEM_LIFESTYLE)==0){
                   counter_lifestyle++ ;
                    if(counter_lifestyle%2!=0)
                        holder.categoryButton.setSelected(true);
                    else holder.categoryButton.setSelected(false);
                }


            }
        });


    }


    @Override
    public int getItemCount() {
        return Data.size();
    }

    public ArrayList<String> getCategories(){

        addcategories = new ArrayList<>();
        if (counter_lifestyle%2!=0){
            addcategories.add(ConstantsCategories.CATEGORY_ITEM_LIFESTYLE);
        }
        if (counter_politics%2!=0){
            addcategories.add(ConstantsCategories.CATEGORY_ITEM_POLITICS);
        }
        if (counter_bollywood%2!=0){
            addcategories.add(ConstantsCategories.CATEGORY_ITEM_BOLLYWOOD);
        }
        if (counter_sports%2!=0){
            addcategories.add(ConstantsCategories.CATEGORY_ITEM_SPORTS);
        }
        if (counter_world%2!=0){
            addcategories.add(ConstantsCategories.CATEGORY_ITEM_WORLD);
        }
        if (counter_cricket%2!=0){
            addcategories.add(ConstantsCategories.CATEGORY_ITEM_CRICKET);
        }
        if (counter_business%2!=0){
            addcategories.add(ConstantsCategories.CATEGORY_ITEM_BUSINESS);
        }
        if (counter_india%2!=0){
            addcategories.add(ConstantsCategories.CATEGORY_ITEM_INDIA);
        }
        return addcategories;

    }
}
