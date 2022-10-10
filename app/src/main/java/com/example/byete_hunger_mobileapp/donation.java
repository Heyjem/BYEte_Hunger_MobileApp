package com.example.byete_hunger_mobileapp;

import android.graphics.drawable.Drawable;

public class donation {
    private String type, weight, datePurchased, dateExpired, contactNo, notes, id, dateAdded, dateAddedTime;
    //private Drawable uploadImage;
    //private boolean isShrink = false;

    public donation() {
    }

    public donation(String type, String weight, String datePurchased, String dateExpired, String contactNo, String notes, String id, String dateAdded, String dateAddedTime /*, Drawable uploadImage*/) {
        this.type = type;
        this.weight = weight;
        this.datePurchased = datePurchased;
        this.dateExpired = dateExpired;
        this.contactNo = contactNo;
        this.notes = notes;
        this.id = id;
        this.dateAdded = dateAdded;
        this.dateAddedTime = dateAddedTime;
        //this.uploadImage = uploadImage;
    }


    public String getType() {return type;}

    public String getWeight() {
        return weight;
    }

    public String getDatePurchased() {return datePurchased;}

    public String getDateExpired() {
        return dateExpired;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getNotes() {
        return notes;
    }

    public String getId() {
        return id;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getDateAddedTime() {
        return dateAddedTime;
    }

    //public Drawable getUploadImage(){ return uploadImage;}




    /*
    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
    */

}
