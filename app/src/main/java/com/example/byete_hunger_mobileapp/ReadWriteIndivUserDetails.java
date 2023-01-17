package com.example.byete_hunger_mobileapp;

import java.util.Map;

public class ReadWriteIndivUserDetails {

    public String lastName,firstName,fullName,contactNo,location,emailAddress, organization, contactPerson, status;
    private Map timestamp;

    public ReadWriteIndivUserDetails() {
    }

    public ReadWriteIndivUserDetails(String lastNametxt, String firstNametxt, String fullName, String contactNotxt, String locationtxt,
                                     String emailAddresstxt, String organization, String contactPerson, String status, Map timestamp) {
        this.lastName = lastNametxt;
        this.firstName = firstNametxt;
        this.fullName = fullName;
        this.contactNo = contactNotxt;
        this.location = locationtxt;
        this.emailAddress = emailAddresstxt;
        this.organization = organization;
        this.contactPerson = contactPerson;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Map getTimestamp() {return timestamp;}
}
