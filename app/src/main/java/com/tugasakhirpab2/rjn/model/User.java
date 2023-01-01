package com.tugasakhirpab2.rjn.model;

public class User {
    private String email;
    private String fullName;
    private String gender;
    private String birthDate;
    private String address;
    private String password;

    public User() {
    }

    public User(String email, String fullName, String gender, String birthDate, String address, String password) {
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
