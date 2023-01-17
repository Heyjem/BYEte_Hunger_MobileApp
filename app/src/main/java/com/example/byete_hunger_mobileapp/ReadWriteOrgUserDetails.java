package com.example.byete_hunger_mobileapp;

import java.util.Map;

public class ReadWriteOrgUserDetails {

    public String organization,contactPerson,contactNo,location,emailAddress,fullName,status;
    private Map timestamp;

    public ReadWriteOrgUserDetails() {
    }

    public ReadWriteOrgUserDetails(String organizationtxt, String contactPersontxt, String contactNotxt, String locationtxt, String emailAddresstxt, String fullName, String status, Map timestamp) {
        this.organization = organizationtxt;
        this.contactPerson = contactPersontxt;
        this.contactNo = contactNotxt;
        this.location = locationtxt;
        this.emailAddress = emailAddresstxt;
        this.fullName = fullName;
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
