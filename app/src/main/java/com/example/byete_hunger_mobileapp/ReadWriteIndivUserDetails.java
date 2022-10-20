package com.example.byete_hunger_mobileapp;

public class ReadWriteIndivUserDetails {

    public String lastName,firstName,fullName,contactNo,location,emailAddress, organization, contactPerson, status;

    public ReadWriteIndivUserDetails() {
    }

    public ReadWriteIndivUserDetails(String lastNametxt, String firstNametxt, String fullName, String contactNotxt, String locationtxt,
                                     String emailAddresstxt, String organization, String contactPerson, String status) {
        this.lastName = lastNametxt;
        this.firstName = firstNametxt;
        this.fullName = fullName;
        this.contactNo = contactNotxt;
        this.location = locationtxt;
        this.emailAddress = emailAddresstxt;
        this.organization = organization;
        this.contactPerson = contactPerson;
        this.status = status;
    }

}
