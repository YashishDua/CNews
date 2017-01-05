package com.example.lenovo.cnews;

import android.util.Log;
import android.widget.Toast;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Lenovo on 14-08-2016.
 */
public class DekhNewsHandler extends DefaultHandler {
    boolean newItem = false;
    String title = null;
    String description = null;
    static String content = null;
    String publisher = "DekhNews";
    String pubDataID = null;
    static ArrayList<String> stringArrayList;
    static ArrayList<ContentData> contentData ,tempData ;
    public DekhNewsHandler() {
/*
        Log.i("SAX","Constructor Called.");
*/
        contentData =new ArrayList<>();
        stringArrayList = new ArrayList<>();
    }

    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {

        if (qName.equals("item")) {
            newItem = true;
        } else if (qName.equals("title") && newItem)
            title = "";
        if (qName.equals("description") && newItem)
            description = "";
        else
            if (qName.equals("pubDate")){
                pubDataID = "";
            }
        else if (qName.equals("content:encoded") && newItem) {
/*
            Log.i("SAX", "TAG Received Encoded");
*/
            content = "";
        }
    }

    static String temptitle ,tempdescription , temppubDataID;
    public void endElement(String uri, String localName,
                           String qName) throws SAXException
    {
        ContentData d = new ContentData();
        if (qName.equals("title") && newItem)
        {
            temptitle = title;/*
            Log.i("SAX","Title = "+title);*/
            title = null;
        }
        if(qName.equals("description") && newItem){
            tempdescription = description;
            description = null;

        }
        if(qName.equals("pubDate") && newItem){
            temppubDataID = pubDataID;
            pubDataID = null;
        }
        if(qName.equals("content:encoded") && newItem){

            String[] s = content.split("a href=");
            String[] x = s[1].split("><img");

            d.setDescription(tempdescription);
            d.setTitle(temptitle);
            d.setImageSRC(x[0]);
            d.setPublisher(publisher);
            d.setPubDataID(temppubDataID);
/*
            Log.i("SAX","ImageSRC="+x[0]);
*/

            contentData.add(d);
            content = null;
        }
    }

    public void characters(char ch[], int start, int length)
            throws SAXException
    {
        if (title != null)
            title += new String(ch, start, length);

        if(description != null)
            description += new String(ch,start,length);

        if(content!=null)
            content += new String(ch,start,length);

        if(pubDataID!=null)
            pubDataID += new String(ch,start,length);

    }


    public ArrayList<ContentData> getData(){
        return contentData;
        
    }

}
