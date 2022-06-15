package com.example.stefanbaynegreenflag;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageButton firstImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstImageButton = findViewById(R.id.imageButton2);
        firstImageButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

    }
}