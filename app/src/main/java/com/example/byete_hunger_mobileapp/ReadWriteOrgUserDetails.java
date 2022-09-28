package com.example.byete_hunger_mobileapp;

public class ReadWriteOrgUserDetails {

    public String organization,contactPerson,contactNo,location,emailAddress;

    public ReadWriteOrgUserDetails() {
    }

    public ReadWriteOrgUserDetails(String Organizationtxt, String ContactPersontxt, String ContactNotxt, String Locationtxt, String EmailAddresstxt) {
        this.organization = Organizationtxt;
        this.contactPerson = ContactPersontxt;
        this.contactNo = ContactNotxt;
        this.location = Locationtxt;
        this.emailAddress = EmailAddresstxt;
    }

}
