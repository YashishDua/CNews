package com.example.lenovo.cnews;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Lenovo on 16-08-2016.
 */
public class HandlerDekhNewsDescription extends DefaultHandler {
    boolean newItem = false;
   static boolean pubDateItem = false;
    String pubDateID = null;
    static String description = null;
    static String pubDate = null;
    public HandlerDekhNewsDescription(){

    }
    public HandlerDekhNewsDescription(String ID) {

        Log.i("SAX","entered DEKHNEWS adapter with ID"+ ID);
        pubDateID = ID;
        Log.i("SAX","PubDateId to find"+pubDateID);
      }

    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {

        if (qName.equals("item")) {
            newItem = true;
            Log.i("SAX","New Item");
        } 
        else if (qName.equals("pubDate") && newItem) {
            Log.i("SAX","Entered PubDate TAG");
            pubDate="";

        } else
        if(qName.equals("description")&&pubDateItem) {
                Log.i("SAX","Entered Description TAG");
                description = "";
            }



    }
    static String  tempdescription , temppubdate;
    public void endElement(String uri, String localName,
                           String qName) throws SAXException
    {


        if(qName.equals("pubDate") && newItem){
            Log.i("SAX","Parsed ID "+ pubDate + "And PUBDATEID"+pubDateID);
            temppubdate = pubDate;
            if(temppubdate.equals(pubDateID)){
                Log.i("SAX","Entered Desired pubDate");
                pubDateItem=true;
            }
            pubDate = null;
        }
        if(qName.equals("description") && pubDateItem){
            tempdescription = description;
            Log.i("SAX","Description:"+tempdescription);
            description = null;
            pubDateItem = false;

        }

    }

    public void characters(char ch[], int start, int length)
            throws SAXException
    {

        if(description != null)
            description += new String(ch,start,length);

        if(pubDate != null)
            pubDate += new String(ch,start,length);
    }


    public String getDescription(){
        Log.i("SAX","Description on sending"+ tempdescription);
        return tempdescription;

    }

}
