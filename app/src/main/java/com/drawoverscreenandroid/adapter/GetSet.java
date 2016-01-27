package com.drawoverscreenandroid.adapter;

import android.graphics.Bitmap;

public class GetSet {
    String name;
    Bitmap drawable;
    Boolean checkedBox = false;
    String imageThumb;
    int ContactId;

    public GetSet() {

    }


    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public int getContactId() {
        return ContactId;
    }

    public void setContactId(int contactId) {
        ContactId = contactId;
    }

    public Boolean getCheckedBox() {
        return checkedBox;
    }

    public void setCheckedBox(Boolean checkedBox) {
        this.checkedBox = checkedBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getDrawable() {
        return drawable;
    }

    public void setDrawable(Bitmap drawable) {
        this.drawable = drawable;
    }
}
