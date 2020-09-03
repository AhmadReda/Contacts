package com.example.contacts;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
interface ContactDAO {

    @Insert
    void insertContact(ContactData ...contactData);

    @Query("select * from contact_table")
    List<ContactData> getContacts();

}
