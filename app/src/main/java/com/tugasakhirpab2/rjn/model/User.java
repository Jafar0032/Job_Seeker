package com.tugasakhirpab2.rjn.model;

public class User {
    private String address;
    private String birthDate;
    private String email;
    private String fullName;
    private String gender;

    public User() {
    }

    public User(String address, String birthDate, String email, String fullName, String gender) {
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
