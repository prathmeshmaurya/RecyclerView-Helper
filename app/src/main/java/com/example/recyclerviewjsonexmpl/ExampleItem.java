package com.example.recyclerviewjsonexmpl;

public class ExampleItem {
    private String mImageUrl;
    private String mCreator;
    private int mLikes;

    public ExampleItem(String mImage, String mCreator, int mLikes) {
        this.mImageUrl = mImage;
        this.mCreator = mCreator;
        this.mLikes = mLikes;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public int getmLikes() {
        return mLikes;
    }
}
