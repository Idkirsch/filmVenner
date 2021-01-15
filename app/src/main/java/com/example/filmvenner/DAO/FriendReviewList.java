package com.example.filmvenner.DAO;

public class FriendReviewList {

    private int imageRes;
    private String textFriendName;
    private String textFriendReview;

    public FriendReviewList(int mImageResource, String mTextFriendName, String mTextFriendReview){

        imageRes = mImageResource;
        textFriendName = mTextFriendName;
        textFriendReview = mTextFriendReview;

    }

    public int getimageresource() {
        return imageRes;
    }

    public String getTextFriendName(){
        return textFriendName;
    }

    public String getTextFriendReview(){
        return textFriendReview;
    }


}
