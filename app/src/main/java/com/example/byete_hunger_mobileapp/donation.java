package com.example.byete_hunger_mobileapp;

public class donation {
    private String type, weight, datePurchased, dateExpired, contactNo, notes;
    //private boolean isShrink = false;

    public donation() {
    }

    public donation(String type, String weight, String datePurchased, String dateExpired, String contactNo, String notes) {
        this.type = type;
        this.weight = weight;
        this.datePurchased = datePurchased;
        this.dateExpired = dateExpired;
        this.contactNo = contactNo;
        this.notes = notes;
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

    /*
    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
    */

}
