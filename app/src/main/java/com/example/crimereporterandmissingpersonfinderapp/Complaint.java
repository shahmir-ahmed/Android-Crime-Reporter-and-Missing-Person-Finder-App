package com.example.crimereporterandmissingpersonfinderapp;


public class Complaint {

    private int id;
//    private String personName;
    private String status;
    private String address;
    private String city;
    private String zipCode;
    private String subject;
    private String complaintDetails;

    private String userName;

    private String userGender;

    private String userCNIC;

    private String userContact;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

//    public String getPersonName() {
//        return personName;
//    }
//
//    public void setPersonName(String personName) {
//        this.personName = personName;
//    }

    public String getComplaintDetails() {
        return complaintDetails;
    }

    public void setComplaintDetails(String complaintDetails) {
        this.complaintDetails = complaintDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String statusDescription) {
        this.city = statusDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }
}

