package com.example.blog.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Blog implements Parcelable {
    String Blog_name ;
    String Blog_text ;
    String Date ;

    String categ ;
    byte[] imageURL ;

    protected Blog(Parcel in) {
        Blog_name = in.readString();
        Blog_text = in.readString();
        Date = in.readString();
        categ = in.readString();



    }

    public static final Creator<Blog> CREATOR = new Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel in) {
            return new Blog(in);
        }

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };

    public Blog(String blog_name, String blog_text, String date, String categ) {
        Blog_name = blog_name;
        Blog_text = blog_text;
        Date = date;
        this.categ = categ;
    }

    public byte[] getImageURL() {
        return imageURL;
    }

    public void setImageURL(byte[] imageURL) {
        this.imageURL = imageURL;
    }

    public Blog(){

    }
    public Blog(String blog_name, String blog_text, String date, String categ, byte[] imageURL) {
        Blog_name = blog_name;
        Blog_text = blog_text;
        Date = date;

        this.categ = categ;
        this.imageURL = imageURL;
    }

    public String getBlog_name() {
        return Blog_name;
    }

    public void setBlog_name(String blog_name) {
        Blog_name = blog_name;
    }

    public String getBlog_text() {
        return Blog_text;
    }

    public void setBlog_text(String blog_text) {
        Blog_text = blog_text;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Blog_name);
        parcel.writeString(Blog_text);
        parcel.writeString(Date);

        parcel.writeString(categ);


    }
}
