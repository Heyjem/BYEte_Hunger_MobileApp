package com.example.byete_hunger_mobileapp;

public class donationcard_info {
    private String item;
    private String type;
    private String weight;
    private String dateofpurchase;
    private String expirationdate;
    private String notes;
    private int imagefile;
    private boolean isShrink = true;

    public donationcard_info() {
    }

    public donationcard_info(String item, String type, String weight, String dateofpurchase, String expirationdate, String notes, int imagefile) {
        this.item = item;
        this.type = type;
        this.weight = weight;
        this.dateofpurchase = dateofpurchase;
        this.expirationdate = expirationdate;
        this.notes = notes;
        this.imagefile = imagefile;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDateofpurchase() {
        return dateofpurchase;
    }

    public void setDateofpurchase(String dateofpurchase) {
        this.dateofpurchase = dateofpurchase;
    }

    public String getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getImagefile() {
        return imagefile;
    }

    public void setImagefile(int imagefile) {
        this.imagefile = imagefile;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
