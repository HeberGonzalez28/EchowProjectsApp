package com.example.echowprojectsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.echowprojectsapp.Screens.LoginActivity;
import com.example.echowprojectsapp.Screens.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }
}