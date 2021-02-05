package com.example.cosplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cosplan.data.cosplay.part.Part;

public class CosplayPartUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosplay_part_update);
        Intent intent=getIntent();
        Part part=intent.getParcelableExtra("part");
    }
}