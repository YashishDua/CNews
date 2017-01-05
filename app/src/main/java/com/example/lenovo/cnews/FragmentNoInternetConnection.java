package com.example.lenovo.cnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Lenovo on 20-08-2016.
 */
public class FragmentNoInternetConnection extends Fragment {

    Button refresh;
    FragmentNoInternetConnectionInterface mlistener ;

    public FragmentNoInternetConnection() {
    }

    public void setListener(FragmentNoInternetConnectionInterface mlistener){
        this.mlistener = mlistener;
    }


    interface FragmentNoInternetConnectionInterface{
        void onclick();
}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =LayoutInflater.from(getContext()).inflate(R.layout.fragment_no_internet,container,false);
        refresh = (Button)v.findViewById(R.id.button_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 mlistener.onclick();
            }
        });
        return v;
    }

}
