package com.example.login.login;

import android.widget.EditText;

public class UserHelperClass {
    String familyname, firstname,telephone,  email, password;

    public UserHelperClass(String familyname, String firstname, String telephone, String email, String password) {
        this.familyname = familyname;
        this.firstname = firstname;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
    }

    public UserHelperClass() {
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
