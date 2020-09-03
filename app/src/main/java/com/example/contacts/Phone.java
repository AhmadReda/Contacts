package com.example.contacts;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phone {
    @ColumnInfo(name = "mobile")
    @SerializedName("mobile")
    @Expose
    private String phoneMobile;
    @ColumnInfo(name = "home")
    @SerializedName("home")
    @Expose
    private String phoneHome;
    @ColumnInfo(name = "office")
    @SerializedName("office")
    @Expose
    private String phoneOffice;

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    public String getPhoneOffice() {
        return phoneOffice;
    }

    public void setPhoneOffice(String phoneOffice) {
        this.phoneOffice = phoneOffice;
    }
}
