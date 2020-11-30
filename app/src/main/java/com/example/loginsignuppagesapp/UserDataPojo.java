package com.example.loginsignuppagesapp;

public class UserDataPojo {
   private String firstname,lastname,email,userid;

    public UserDataPojo() {
    }

    public UserDataPojo(String firstname, String lastname, String email, String userid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
