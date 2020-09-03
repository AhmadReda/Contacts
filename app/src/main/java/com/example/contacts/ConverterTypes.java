package com.example.contacts;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class ConverterTypes {

    @TypeConverter
    public String fromPhonetoString(Phone phone){
        return new Gson().toJson(phone);
    }

    @TypeConverter
    public Phone fromStringtoPhone(String stringPhone){
        return new Gson().fromJson(stringPhone,Phone.class);
    }
}
