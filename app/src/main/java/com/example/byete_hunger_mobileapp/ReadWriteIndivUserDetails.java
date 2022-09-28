package com.example.byete_hunger_mobileapp;

public class ReadWriteIndivUserDetails {

    public String lastName,firstName,contactNo,location,emailAddress;

    public ReadWriteIndivUserDetails() {
    }

    public ReadWriteIndivUserDetails(String LastNametxt, String FirstNametxt, String ContactNotxt, String Locationtxt, String EmailAddresstxt) {
        this.lastName = LastNametxt;
        this.firstName = FirstNametxt;
        this.contactNo = ContactNotxt;
        this.location = Locationtxt;
        this.emailAddress = EmailAddresstxt;
    }

}
