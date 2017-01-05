package com.example.lenovo.cnews;

import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Lenovo on 16-08-2016.
 */
public class DownloadDescriptionTask extends AsyncTask<String , Void , String> {
    String id ;
    public interface DownloadDescriptionTaskInterface {
        void dataprocessed() throws IOException;
    }

    DownloadDescriptionTaskInterface listener;

    public DownloadDescriptionTask(DownloadDescriptionTaskInterface listener , String ID) {
        this.listener = listener;
        id = ID;
    }


    @Override
    protected String doInBackground(String... strings) {
        try
        {
            SAXParser parser =
                    SAXParserFactory.newInstance().newSAXParser();
            Log.i("Adapter","ID inside adapter"+id);
            DefaultHandler handler = new HandlerDekhNewsDescription(id);
            parser.parse(strings[0], handler);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        try {
            listener.dataprocessed();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
