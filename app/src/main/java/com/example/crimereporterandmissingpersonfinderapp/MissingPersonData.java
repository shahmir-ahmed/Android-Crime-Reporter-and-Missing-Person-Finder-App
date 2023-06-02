package com.example.crimereporterandmissingpersonfinderapp;

import android.graphics.Bitmap;

public class MissingPersonData {

        private String missingPersonId;
        private String missingPersonName;
        private String missingPersonAge;
        private String missingPersonGender;
        private String missingPersonZipCode;
        private String missingPersonLastSeenLocation;
        private String missingPersonReportStatus;
        private String missingPersonReportDetails;
        private Bitmap missingPersonImage;


    public MissingPersonData(String missingPersonName, String missingPersonAge, String missingPersonGender, String missingPersonZipCode, String missingPersonLastSeenLocation, String missingPersonReportStatus, String missingPersonReportDetails, Bitmap missingPersonImage) {
        this.missingPersonName = missingPersonName;
        this.missingPersonAge = missingPersonAge;
        this.missingPersonGender = missingPersonGender;
        this.missingPersonZipCode = missingPersonZipCode;
        this.missingPersonLastSeenLocation = missingPersonLastSeenLocation;
        this.missingPersonReportStatus = missingPersonReportStatus;
        this.missingPersonReportDetails = missingPersonReportDetails;
        this.missingPersonImage = missingPersonImage;
    }

    // constructor with missing person id for user dashboard to delete and update the missing person report against this id
    public MissingPersonData(String missingPersonId, String missingPersonName, String missingPersonAge, String missingPersonGender, String missingPersonZipCode, String missingPersonLastSeenLocation, String missingPersonReportStatus, String missingPersonReportDetails, Bitmap missingPersonImage) {
        this.missingPersonId = missingPersonId;
        this.missingPersonName = missingPersonName;
        this.missingPersonAge = missingPersonAge;
        this.missingPersonGender = missingPersonGender;
        this.missingPersonZipCode = missingPersonZipCode;
        this.missingPersonLastSeenLocation = missingPersonLastSeenLocation;
        this.missingPersonReportStatus = missingPersonReportStatus;
        this.missingPersonReportDetails = missingPersonReportDetails;
        this.missingPersonImage = missingPersonImage;
    }


    public String getMissingPersonId() {
        return missingPersonId;
    }

    public void setMissingPersonId(String missingPersonId) {
        this.missingPersonId = missingPersonId;
    }
    public String getMissingPersonName() {
        return missingPersonName;
    }

    public void setMissingPersonName(String missingPersonName) {
        this.missingPersonName = missingPersonName;
    }

    public String getMissingPersonAge() {
        return missingPersonAge;
    }

    public void setMissingPersonAge(String missingPersonAge) {
        this.missingPersonAge = missingPersonAge;
    }

    public String getMissingPersonGender() {
        return missingPersonGender;
    }

    public void setMissingPersonGender(String missingPersonGender) {
        this.missingPersonGender = missingPersonGender;
    }

    public String getMissingPersonZipCode() {
        return missingPersonZipCode;
    }

    public void setMissingPersonZipCode(String missingPersonZipCode) {
        this.missingPersonZipCode = missingPersonZipCode;
    }

    public String getMissingPersonLastSeenLocation() {
        return missingPersonLastSeenLocation;
    }

    public void setMissingPersonLastSeenLocation(String missingPersonLastSeenLocation) {
        this.missingPersonLastSeenLocation = missingPersonLastSeenLocation;
    }

    public String getMissingPersonReportStatus() {
        return missingPersonReportStatus;
    }

    public void setMissingPersonReportStatus(String missingPersonReportStatus) {
        this.missingPersonReportStatus = missingPersonReportStatus;
    }

    public String getMissingPersonReportDetails() {
        return missingPersonReportDetails;
    }

    public void setMissingPersonReportDetails(String missingPersonReportDetails) {
        this.missingPersonReportDetails = missingPersonReportDetails;
    }

    public Bitmap getMissingPersonImage() {
        return missingPersonImage;
    }

    public void setMissingPersonImage(Bitmap missingPersonImage) {
        this.missingPersonImage = missingPersonImage;
    }
}
