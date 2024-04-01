package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Adapter.AddTaskAdapter;
import com.example.myapplication.Class.Group;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Task_list_detail_Layout extends AppCompatActivity {
    TextView nameG;
    private ImageView btnBack;
    JSONObject jsonObject;

    ListView listView;

    AddTaskAdapter adapter;
    Button btnAdd;

    List<Group> groupList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_detail_layout);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        nameG = (TextView) findViewById(R.id.nameGroup);
        listView = (ListView) findViewById(R.id.list_task);
        btnAdd = (Button) findViewById(R.id.btn_add_task);
        groupList = new ArrayList<>();
        Intent intent = getIntent();
        try {
            jsonObject = new JSONObject(intent.getStringExtra("data_task_details"));
            nameG.setText(jsonObject.optString("nameG"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupList.add(new Group());
                adapter = new AddTaskAdapter(Task_list_detail_Layout.this, R.layout.row_task, groupList );
                listView.setAdapter(adapter);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}