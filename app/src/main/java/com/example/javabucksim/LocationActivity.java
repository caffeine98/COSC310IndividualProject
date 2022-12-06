package com.example.javabucksim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }

    public void onClickBranch1(View view) {
        Intent intent = new Intent(LocationActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public void onClickBranch2(View view) {
        Intent intent = new Intent(LocationActivity.this, MapsActivity2.class);
        startActivity(intent);
    }

    public void onClickBackLocation(View view) {
        finish();
    }
}