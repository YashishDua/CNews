package com.example.lenovo.cnews;

import java.util.ArrayList;

/**
 * Created by Lenovo on 14-08-2016.
 */
public class ContentData implements java.io.Serializable{
    String title ;
    String description ;
    String imageSRC ;
    String publisher ;
    String pubDataID ;

    public ContentData(){}

    public ContentData(String title, String description, String imageSRC, String publisher) {
        this.title = title;
        this.description = description;
        this.imageSRC = imageSRC;
        this.publisher = publisher;
    }

    public String getPubDataID() {
        return pubDataID;
    }

    public void setPubDataID(String pubDataID) {
        this.pubDataID = pubDataID;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageSRC() {
        return imageSRC;
    }

    public void setImageSRC(String imageSRC) {
        this.imageSRC = imageSRC;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
