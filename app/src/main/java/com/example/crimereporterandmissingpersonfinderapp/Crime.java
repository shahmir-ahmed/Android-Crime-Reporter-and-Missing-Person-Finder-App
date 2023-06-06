package com.example.crimereporterandmissingpersonfinderapp;

import android.graphics.Bitmap;

public class Crime {
    private int id;
    private String type;
    private String streetDetails;
    private String city;
    private String zipCode;
    private String crimeDetails;
    private Bitmap crimeImage;
    private String status;


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
}

