package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Task_list_Layout extends AppCompatActivity {
    TextView textView, nameGroup ;
    ImageView btnBack;
    JSONObject jsonObject;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_layout);
        textView = (TextView) findViewById(R.id.btnAddTaskList);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        nameGroup = (TextView) findViewById(R.id.nameGroup);
        Intent intent = getIntent();
        try {
            jsonObject = new JSONObject(intent.getStringExtra("data_task"));
            nameGroup.setText(jsonObject.optString("nameG"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Task_list_Layout.this, Task_list_detail_Layout.class);
                intent1.putExtra("data_task_details", jsonObject.toString());
                startActivity(intent1);
            }
        });
    }

}