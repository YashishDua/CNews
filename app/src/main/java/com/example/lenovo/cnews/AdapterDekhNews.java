package com.example.lenovo.cnews;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo on 14-08-2016.
 */
public class AdapterDekhNews extends RecyclerView.Adapter<AdapterDekhNews.OurHolder> {

    Context context;
    ArrayList<ContentData> contentData ;
    ProgressDialog mProgress;
    AdapterDekhNewsInterface mlistener ;

    public interface AdapterDekhNewsInterface {
        void notifyItemClicked(String pubDataID, String title, String publisher, String imageURL);
    }


    public AdapterDekhNews(Context context, ArrayList<ContentData> contentData , AdapterDekhNewsInterface mlistener ) {
        this.context = context;
        this.contentData = contentData;
        this.mlistener = mlistener;
  /*
        mProgress = new ProgressDialog(context);
        mProgress.setCancelable(true);
        mProgress.setMessage("Wait Loading...");
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setCancelable(false);
        mProgress.show();*/

    }

    public class OurHolder extends RecyclerView.ViewHolder{

        TextView title , publisher ;
        ImageView image;

        public OurHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.textview_title);
            publisher = (TextView)itemView.findViewById(R.id.textview_publisher);
            image = (ImageView) itemView.findViewById(R.id.imageview);

        }
    }
    @Override
    public AdapterDekhNews.OurHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(context).inflate(R.layout.item_dekhnews,parent , false);
        DisplayMetrics displaymetrics ;
        displaymetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;

        v.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height/6));
        return new OurHolder(v);
    }


    @Override
    public void onBindViewHolder(final AdapterDekhNews.OurHolder holder, int position) {
        final ContentData tempContent = contentData.get(position);
        holder.title.setText(tempContent.getTitle());
        holder.publisher.setText(tempContent.getPublisher());

       //Extracting URL
        String[] a = tempContent.getImageSRC().split("\"");
        final String[] b = a[1].split("\"");

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.build().load(b[0]).fit().into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Adapter","PUBDATEID to pass inside adapter "+tempContent.getPubDataID());
                Log.i("----","Item Clicked ! with Listener "+ mlistener.toString());
                mlistener.notifyItemClicked(tempContent.getPubDataID(),tempContent.getTitle() , tempContent.getPublisher() , b[0]);
            }
        });


    }


    @Override
    public int getItemCount() {
        return contentData.size();
    }
}
