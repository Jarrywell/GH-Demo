package com.android.test.demo.lambda;

import android.util.Log;

import java.util.List;

/**
 * des:
 * Date: 18-2-7 14:09
 */
public class Task {

    private int mId;
    private String mTitle;
    private int mValue;
    private List<String> mTags;

    public Task(int id, String title, int value) {
        this(id, title, value, null);
    }

    public Task(int id, String title, int value, List<String> tags) {
        mId = id;
        mTitle = title;
        mValue = value;
        mTags = tags;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        this.mValue = value;
    }

    public List<String> getTags() {
        return mTags;
    }

    public void setTags(List<String> tags) {
        mTags = tags;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        return mId == ((Task) obj).getId();
    }

}
