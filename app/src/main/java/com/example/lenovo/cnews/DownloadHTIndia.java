package com.example.lenovo.cnews;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.picasso.Downloader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Lenovo on 02-10-2016.
 */
public class DownloadHTIndia extends AsyncTask<String , Void, String> {
    public DownloadHTIndia(MainActivity mainActivity) {
        Log.i("----","Entered HT Download Task !");
    }

    @Override
    protected String doInBackground(String... strings) {

        SAXParser parser =
                null;
        try {
            parser = SAXParserFactory.newInstance().newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        DefaultHandler handler = new HandlerHindustanTimes();
        try {
            strings[0] = strings[0].replaceAll("&", "&amp;");
            InputSource source = new InputSource();
            source.setCharacterStream(new StringReader(strings[0]));
            Log.i("----",strings[0]+"");
            source.setEncoding("ISO-8859-1");
            if (parser != null) {
                parser.parse(source, handler);
            }
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("----","Download complete HT!");
    }
}
