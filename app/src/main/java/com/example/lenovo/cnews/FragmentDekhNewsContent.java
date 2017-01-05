package com.example.lenovo.cnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lenovo on 20-08-2016.
 */
public class FragmentDekhNewsContent extends Fragment implements DownloadData.DownloadDataInterface , AdapterDekhNews.AdapterDekhNewsInterface{
    ArrayList<ContentData> data;
    AdapterDekhNews adapter ;
    RecyclerView recyclerView;
    DekhNewsHandler dekhNewsHandler;
    Boolean isReloaded = Boolean.FALSE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dekhnews_content,container,false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        data = new ArrayList<>();
        if(!isReloaded) {
            Bundle b = getArguments();
            data.addAll((ArrayList<ContentData>) b.getSerializable("DATA"));
        }

        adapter = new AdapterDekhNews(getContext(),data,this);


        LinearLayoutManager lm = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        lm.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.addItemDecoration(new ItemDecoratorAdapterDekhNews(getContext()));
        recyclerView.setAdapter(adapter);
        dekhNewsHandler = new DekhNewsHandler();

        return v;
    }

    @Override
    public void dataprocessed() throws IOException {
        data.addAll(dekhNewsHandler.getData());
        adapter.notifyDataSetChanged();
        i.dataLoaded();

    }

    public void ReloadData(){
            isReloaded = Boolean.TRUE;
            String url = "http://www.dekhnews.com/feed/";
            new DownloadData(this).execute(url);
            dekhNewsHandler = new DekhNewsHandler();
    }

    @Override
    public void notifyItemClicked(String pubDataID, String title, String publisher, String imageURL) {
        i.notifyPass(pubDataID,title,publisher,imageURL);
    }

    public interface FragementDekhNewsContentInterface {
        void notifyPass(String pubDataID, String title, String publisher, String imageURL);
        void dataLoaded();
    }
    FragementDekhNewsContentInterface i;
    public void setListener(FragementDekhNewsContentInterface i1){
        i=i1;
    }


}
