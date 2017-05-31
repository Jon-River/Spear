package com.spear.android.pojo;

/**
 * Created by Pablo on 26/3/17.
 */

public class UserInfo {

    public String name;
    public String email;
    public String province;

    public UserInfo() {

    }

    public UserInfo(String name, String email, String province) {
        this.name = name;
        this.email = email;
        this.province = province;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}

