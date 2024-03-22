package com.example.fetchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
    }

    public void letsGoButtonClick (View v){
        //Opens main activity on button click
        Intent openActivity = new Intent(this, MainActivity.class);
        startActivity(openActivity);
    }
}