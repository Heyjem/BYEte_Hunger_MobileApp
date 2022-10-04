package com.example.byete_hunger_mobileapp;

public class ReadWriteIndivUserDetails {

    public String lastName,firstName,contactNo,location,emailAddress, Representative;

    public ReadWriteIndivUserDetails() {
    }

    public ReadWriteIndivUserDetails(String LastNametxt, String FirstNametxt, String ContactNotxt, String Locationtxt, String EmailAddresstxt, String Representative) {
        this.lastName = LastNametxt;
        this.firstName = FirstNametxt;
        this.contactNo = ContactNotxt;
        this.location = Locationtxt;
        this.emailAddress = EmailAddresstxt;
        this.Representative = Representative;
    }

}
