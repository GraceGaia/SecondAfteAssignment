package com.grace.mysecondafteassignment
//Create an app with at least 3 pages
//On the first page, have buttons to take you to the second and third page respectively

//On the second page, ensure the user can implement the following actions;
//i. Make a call ii. Send an sms iii. Dial a phone number iv.Send an email
//v.Share text content vi. Launch STK vii. Take a pic and set it on an ImageView

//On the Third page, capture the following data and store it on a SQLite DB
//name, email,phone, date of birth, gender and password
//Ensure to display the data and allow for data deletion of any record

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}