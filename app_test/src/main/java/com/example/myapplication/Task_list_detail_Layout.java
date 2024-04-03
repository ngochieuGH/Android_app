package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.AddTaskAdapter;
import com.example.myapplication.Class.Group;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Task_list_detail_Layout extends AppCompatActivity {
    TextView nameG;
    private ImageView btnBack;
    JSONObject jsonObject;

    ListView listView;

    AddTaskAdapter adapter;
    Button btnAdd, sendTask;

    List<Group> groupList;

    String s_name;

    public void SetContext(Group group){
        RequestQueue requestQueue = Volley.newRequestQueue(Task_list_detail_Layout.this);
        String url = "http://" + getString(R.string.url) + ":8080/ListUserGroup";
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(Integer.parseInt("0"), group.toJSON());
            Log.d("2222222222222222", jsonArray.getString(0));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(Task_list_detail_Layout.this, response.toString(), Toast.LENGTH_SHORT).show();
                Log.d("2222222222222222", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Task_list_detail_Layout.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_detail_layout);
        sendTask = (Button) findViewById(R.id.sendTask);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        nameG = (TextView) findViewById(R.id.nameGroup);
        listView = (ListView) findViewById(R.id.list_task);
        btnAdd = (Button) findViewById(R.id.btn_add_task);
        groupList = new ArrayList<>();
        Intent intent = getIntent();
        Group group = new Group();
        try {
            jsonObject = new JSONObject(intent.getStringExtra("data_task_details"));
            nameG.setText(jsonObject.optString("nameG"));
            group.setIdgroup(jsonObject.optInt("id"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //SetContext(group);
        registerForContextMenu(sendTask);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCreateTask();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void DialogCreateTask(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_task);
        dialog.setCanceledOnTouchOutside(false);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        EditText textUser = (EditText) dialog.findViewById(R.id.textUser);
        EditText textDead = (EditText) dialog.findViewById(R.id.textDead);
        EditText textNote = (EditText) dialog.findViewById(R.id.textNote);
        ImageView more = (ImageView) dialog.findViewById(R.id.more);
        ImageView time = (ImageView) dialog.findViewById(R.id.time);
        //registerForContextMenu(more);
        textUser.setText(s_name);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(dialog.getContext(), more);
                popupMenu.getMenuInflater().inflate(R.menu.menu_user, popupMenu.getMenu());
                popupMenu.getMenu().add(0,0,0, "La Ngoc Hieu");
                popupMenu.getMenu().add(0,1,0,"Nguyen Cong Lam");
                popupMenu.getMenu().add(0,2,0,"Truong Thanh Tung");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        textUser.setText(item.getTitle());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(dialog.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        textDead.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },nam,thang,ngay);
                datePickerDialog.show();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupList.add(new Group(1,textUser.getText().toString(),textDead.getText().toString(),null));
                adapter = new AddTaskAdapter(Task_list_detail_Layout.this, R.layout.row_task, groupList );
                listView.setAdapter(adapter);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}