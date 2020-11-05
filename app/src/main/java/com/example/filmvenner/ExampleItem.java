package com.example.filmvenner;

public class ExampleItem {
    private int mImageResource;
    private String mText1, mText2;

    public ExampleItem(int ImageResource, String Text1, String Text2){
       mImageResource = ImageResource;
       mText1 = Text1;
       mText2 =Text2;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }
}


