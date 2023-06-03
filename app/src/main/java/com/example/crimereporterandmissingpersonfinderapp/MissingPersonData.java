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

        private String userName;
        private String userContact;

        private String userGender;

        private String userCNIC;

        // old contrsructor int=itail class
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

    // constructor with missing person id for USER dashboard to delete and update the missing person report against this id
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

    // constructor with user and missing person report details for search missing person activity adapter
    public MissingPersonData(String userName, String userContact, String missingPersonName, String missingPersonAge, String missingPersonGender, String missingPersonZipCode, String missingPersonLastSeenLocation, String missingPersonReportStatus, String missingPersonReportDetails, Bitmap missingPersonImage) {
//        System.out.println("here"+userName+userContact);

//        System.out.println("here"+missingPersonName);
        this.userName = userName;
        this.userContact = userContact;
        this.missingPersonName = missingPersonName;
        this.missingPersonAge = missingPersonAge;
        this.missingPersonGender = missingPersonGender;
        this.missingPersonZipCode = missingPersonZipCode;
        this.missingPersonLastSeenLocation = missingPersonLastSeenLocation;
        this.missingPersonReportStatus = missingPersonReportStatus;
        this.missingPersonReportDetails = missingPersonReportDetails;
        this.missingPersonImage = missingPersonImage;
    }

    // constructor for ADMIN dashboard data for missing persons and user details
    public MissingPersonData(String missingPersonId, String userName, String userGender, String userCNIC, String userContact, String missingPersonName, String missingPersonAge, String missingPersonGender, String missingPersonZipCode, String missingPersonLastSeenLocation, String missingPersonReportStatus, String missingPersonReportDetails, Bitmap missingPersonImage) {
        this.missingPersonId = missingPersonId;
        this.userName = userName;
        this.userGender = userGender;
        this.userCNIC = userCNIC;
        this.userContact = userContact;
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
        return this.missingPersonName;
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

    public String getUserName() {
        return this.userName;
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

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserCNIC() {
        return userCNIC;
    }

    public void setUserCNIC(String userCNIC) {
        this.userCNIC = userCNIC;
    }
}
