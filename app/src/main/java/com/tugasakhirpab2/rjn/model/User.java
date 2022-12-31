package com.tugasakhirpab2.rjn.model;

public class User {
    private String email;
    private String realName;
    private String userName;
    private String gender;
    private String birthDate;
    private String address;

    public User() {
    }

    public User(String email, String realName, String userName, String gender, String birthDate, String address) {
        this.email = email;
        this.realName = realName;
        this.userName = userName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
