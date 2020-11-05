package com.example.filmvenner;

public class ExampleItem {
    private int mImageResource;
    private String mName, mText2, mTitle;

    public ExampleItem(int ImageResource, String Name, String Text2, String Title){
       mImageResource = ImageResource;
       mName = Name;
       mText2 =Text2;
       mTitle = Title;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getName() {
        return mName;
    }

    public String getText2() {
        return mText2;
    }

    public String getTitle() {
        return mTitle;
    }
}


