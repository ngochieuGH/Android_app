package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.TaskAdapter;
import com.example.myapplication.Class.Group;
import com.example.myapplication.Class.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Task_list_Layout extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 100;
    TaskAdapter taskAdapter;
    ListView listView;
    List<Task> tasks;
    TextView nameGroup ;
    ImageView btnBack, btnCreate;
    JSONObject jsonObject;
    int id_user;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_layout);
        btnCreate = (ImageView) findViewById(R.id.btnAddTaskList);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        nameGroup = (TextView) findViewById(R.id.nameGroup);
        listView = (ListView) findViewById(R.id.taskList);
        tasks = new ArrayList<>();
        Intent intent = getIntent();
        try {
            jsonObject = new JSONObject(intent.getStringExtra("data_task"));
            nameGroup.setText(jsonObject.optString("nameG"));
            id_user = intent.getIntExtra("id_user", 0);
            if (id_user != jsonObject.getJSONObject("leader").optInt("id")){
                btnCreate.setVisibility(View.GONE);
            } else {
                btnCreate.setVisibility(View.VISIBLE);
            }
            GetTaskList(jsonObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Task_list_Layout.this, Task_list_detail_Layout.class);
                intent1.putExtra("data_task_details", jsonObject.toString());
                startActivityForResult(intent1, MY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(MY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            try {
                tasks.clear();
                GetTaskList(jsonObject);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            onRestart();
        }
    }

    public void GetTaskList(JSONObject jsonObject) throws JSONException {
        Group group = new Group();
        group.setIdgroup(jsonObject.getInt("id"));
        RequestQueue requestQueue = Volley.newRequestQueue(Task_list_Layout.this);
        String url = "http://" + getString(R.string.url) + ":8080/ListTask";
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(Integer.parseInt("0"), group.toJSON());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<JSONObject> jsonObjects = new ArrayList<>();
                try {
                    for (int i=0 ; i < response.length() ; i++) {
                        jsonObjects.add((JSONObject) response.get(i));
                    }
                    for(JSONObject j : jsonObjects){
                        Task task = ConverTask(j);
                        tasks.add(new Task(task.getId(), task.getNameTask(), task.getDateCreate()));
                    }
                    taskAdapter = new TaskAdapter(Task_list_Layout.this, R.layout.grow_task, tasks);
                    listView.setAdapter(taskAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(Task_list_Layout.this, Task_list_show_Layout.class);
                            intent.putExtra("data_task", jsonObjects.get(position).toString());
                            intent.putExtra("data_group", jsonObject.toString());
                            intent.putExtra("id_user",id_user);
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Task_list_Layout.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public Task ConverTask(JSONObject j) throws JSONException {
        Task task = new Task();
        task.setId(j.optInt("id"));
        task.setNameTask(j.getString("nameTask"));
        task.setDateCreate(j.getString("dateCreate"));
        return task;
    }
}