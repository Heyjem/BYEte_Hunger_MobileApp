package com.example.byete_hunger_mobileapp;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class donation {
    private String type, weight, datePurchased, dateExpired, contactNo,location, notes, id, dateAdded, dateAddedTime , imageUrl, donationStatus, customDonationUID;
    //private Map timestamp;

    public donation() {
    }


    public donation(String type, String weight, String datePurchased, String dateExpired, String contactNo,String location, String notes, String id, String dateAdded, String dateAddedTime, String imageUrl, String donationStatus, String customDonationUID /*Map timestamp*/) {
        this.type = type;
        this.weight = weight;
        this.datePurchased = datePurchased;
        this.dateExpired = dateExpired;
        this.contactNo = contactNo;
        this.location = location;
        this.notes = notes;
        this.id = id;
        this.dateAdded = dateAdded;
        this.dateAddedTime = dateAddedTime;
        this.imageUrl = imageUrl;
        this.donationStatus = donationStatus;
        this.customDonationUID = customDonationUID;
        //this.timestamp = timestamp;
    }

    public donation(String imageUrl, String url) {
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

    public String getLocation() {
        return location;
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

    public String getCustomDonationUID() {
        return customDonationUID;
    }

    public String getDonationStatus(){return donationStatus; }

    /*public Map getTimestamp() {return timestamp;}

    public void setTimestamp(Map timestamp) {
        this.timestamp = timestamp;
    }
     */

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}


