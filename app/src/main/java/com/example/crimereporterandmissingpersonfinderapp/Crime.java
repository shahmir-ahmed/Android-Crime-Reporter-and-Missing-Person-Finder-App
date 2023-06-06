package com.example.crimereporterandmissingpersonfinderapp;

import android.graphics.Bitmap;

public class Crime {
    private int id;
    private String type, streetDetails, city, zipCode, crimeDetails, status;
    private Bitmap crimeImage;

    private String userName, userContact, userEmail, userCNIC;

    // default constructor
    public Crime(){

    }

    public Crime(String crimeType, String streetDetails, String city, String zipCode, String crimeDetails, Bitmap bitmap, String reportStatus){
        type = crimeType;
        this.streetDetails = streetDetails;
        this.city = city;
        this.zipCode = zipCode;
        this.crimeDetails = crimeDetails;
        crimeImage = bitmap;
        status = reportStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreetDetails() {
        return streetDetails;
    }

    public void setStreetDetails(String streetDetails) {
        this.streetDetails = streetDetails;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCrimeDetails() {
        return crimeDetails;
    }

    public void setCrimeDetails(String crimeDetails) {
        this.crimeDetails = crimeDetails;
    }

    public Bitmap getCrimeImage() {
        return crimeImage;
    }

    public void setCrimeImage(Bitmap crimeImage) {
        this.crimeImage = crimeImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCNIC() {
        return userCNIC;
    }

    public void setUserCNIC(String userCNIC) {
        this.userCNIC = userCNIC;
    }
}

