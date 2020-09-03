package com.example.contacts;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DetialActivity extends AppCompatActivity {
    private TextView contactName;
    private TextView contactMobilePhone;
    private TextView contactEmail;
    private TextView contactAddress;
    private TextView contactGender;
    private TextView contactHomePhone;
    private TextView contactOfficePhone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);
        getSupportActionBar().hide();
        initUI();
        setUI();
    }
    private void initUI(){
        contactName = findViewById(R.id.tv_name_detial);
        contactMobilePhone =  findViewById(R.id.tv_phone_detial);
        contactEmail = findViewById(R.id.tv_email);
        contactAddress = findViewById(R.id.tv_address);
        contactGender = findViewById(R.id.tv_gender);
        contactHomePhone = findViewById(R.id.tv_home_phone);
        contactOfficePhone = findViewById(R.id.tv_office_phone);
    }
    private void setUI(){
        contactName.setText(getIntent().getExtras().getString(ContactConstants.NAME));
        contactMobilePhone.setText(getIntent().getExtras().getString(ContactConstants.MOBILE_PHONE));
        contactEmail.setText(getIntent().getExtras().getString(ContactConstants.EMAIL));
        contactAddress.setText(getIntent().getExtras().getString(ContactConstants.ADDRESS));
        contactGender.setText(getIntent().getExtras().getString(ContactConstants.GENDER));
        contactHomePhone.setText(getIntent().getExtras().getString(ContactConstants.HOME_PHONE));
        contactOfficePhone.setText(getIntent().getExtras().getString(ContactConstants.OFFICE_PHONE));
    }
}
