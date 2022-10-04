package com.example.byete_hunger_mobileapp;

public class ReadWriteOrgUserDetails {

    public String organization,contactPerson,contactNo,location,emailAddress,fullName;

    public ReadWriteOrgUserDetails() {
    }

    public ReadWriteOrgUserDetails(String organizationtxt, String contactPersontxt, String contactNotxt, String locationtxt, String emailAddresstxt, String fullName) {
        this.organization = organizationtxt;
        this.contactPerson = contactPersontxt;
        this.contactNo = contactNotxt;
        this.location = locationtxt;
        this.emailAddress = emailAddresstxt;
        this.fullName = fullName;
    }

}
