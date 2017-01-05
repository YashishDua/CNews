package com.example.lenovo.cnews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lenovo on 25-08-2016.
 */
public class AdapterEditCategories extends RecyclerView.Adapter<AdapterEditCategories.OurHolder> {


    Context context;
    ArrayList<String> Data ;
    AdapterEditCategoriesInterface mlistener ;

    public interface AdapterEditCategoriesInterface {
        void notifyItemClicked(String pubDataID, String title, String publisher, String imageURL);
    }


    public AdapterEditCategories(Context context, ArrayList<String> Data /*, AdapterEditCategoriesInterface mlistener*/ ) {
        this.context = context;
        this.Data = Data;
       // this.mlistener = mlistener;
    }

    public class OurHolder extends RecyclerView.ViewHolder{

        TextView categoryName  ;

        public OurHolder(View itemView) {
            super(itemView);
            categoryName = (TextView)itemView.findViewById(R.id.textview_category_name);

        }
    }
    @Override
    public AdapterEditCategories.OurHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(context).inflate(R.layout.item_edit_categories,parent , false);

        return new OurHolder(v);
    }


    @Override
    public void onBindViewHolder(final AdapterEditCategories.OurHolder holder, int position) {
     holder.categoryName.setText(Data.get(position));

    }


    @Override
    public int getItemCount() {
        return Data.size();
    }

}
