package com.example.byete_hunger_mobileapp;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class donation {
    private String type, weight, datePurchased, dateExpired, contactNo, notes, id, dateAdded, dateAddedTime, imageUrl;


    public donation() {
    }

    public donation(String type, String weight, String datePurchased, String dateExpired, String contactNo, String notes, String id, String dateAdded, String dateAddedTime, String imageUrl) {
        this.type = type;
        this.weight = weight;
        this.datePurchased = datePurchased;
        this.dateExpired = dateExpired;
        this.contactNo = contactNo;
        this.notes = notes;
        this.id = id;
        this.dateAdded = dateAdded;
        this.dateAddedTime = dateAddedTime;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }


}
