package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class GroupLayout extends AppCompatActivity {

    ImageView btnTaskList, btnOptions, file, calendar;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_layout);
        btnTaskList = (ImageView) findViewById(R.id.newTaskList);
        btnOptions = (ImageView) findViewById(R.id.btnOption);
        file = (ImageView) findViewById(R.id.file);
        calendar = (ImageView) findViewById(R.id.Calendar);
        txt = (EditText) findViewById(R.id.textContent);
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOptions.setVisibility(View.INVISIBLE);
                file.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.VISIBLE);
            }
        });

        btnTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupLayout.this, Task_list_Layout.class);
                startActivity(intent);
            }
        });
    }
}