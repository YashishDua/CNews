package com.example.lenovo.cnews;

import android.os.AsyncTask;

import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Lenovo on 14-08-2016.
 */
public class DownloadData extends AsyncTask<String , Void , ContentData> {


    public interface DownloadDataInterface {
        void dataprocessed() throws IOException;
    }

    DownloadDataInterface listener;

    public DownloadData(DownloadDataInterface listener) {
        this.listener = listener;
    }


    @Override
    protected ContentData doInBackground(String... strings) {
        try
        {
            SAXParser parser =
                    SAXParserFactory.newInstance().newSAXParser();
            DefaultHandler handler = new DekhNewsHandler();
            parser.parse(strings[0], handler);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ContentData contentData) {
        try {
            listener.dataprocessed();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
