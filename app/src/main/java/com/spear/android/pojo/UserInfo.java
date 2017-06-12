package com.spear.android.pojo;

/**
 * Created by Pablo on 26/3/17.
 */

public class UserInfo {

    public String name;
    public String email;


    public UserInfo() {

    }

    public UserInfo(String name, String email) {
        this.name = name;
        this.email = email;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

