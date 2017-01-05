package com.example.lenovo.cnews;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Lenovo on 02-10-2016.
 */
public class HandlerHindustanTimes extends DefaultHandler {
    Boolean newItem;
    String title;
    public HandlerHindustanTimes() {
        Log.i("----","Entered Handler !");
    }
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {
        Log.i("----","Started parsing tags...");

        if (qName.equals("item")) {
            newItem = true;
        } else if (qName.equals("title") && newItem)
            title = "";
        }

    static String temptitle;
    public void endElement(String uri, String localName,
                           String qName) throws SAXException
    {
        if (qName.equals("title") && newItem)
        {
            temptitle = title;
            Log.i("----","Title = "+title);
            title = null;
        }
    }

    public void characters(char ch[], int start, int length)
            throws SAXException
    {
        if (title != null)
            title += new String(ch, start, length);

    }
}
