package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.myapplication.Adapter.Row_taskAdapter;
import com.example.myapplication.Adapter.TaskAdapter;
import com.example.myapplication.Class.Group;
import com.example.myapplication.Class.Row_task;
import com.example.myapplication.Class.Task;
import com.example.myapplication.Class.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Task_list_show_Layout extends AppCompatActivity {
    TextView textUser;
    public static final int REQUEST_PDF_FILES = 1;
    ListView listView;
    Row_taskAdapter rowTaskAdapter;
    List<Row_task> rowTasks;
    ImageView btnBack;
    TextView nameGroup, nameTaskHeading;
    JSONObject data_task, data_group;
    int id_user;

    public void widget_init() {
        listView = (ListView) findViewById(R.id.list_task);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        nameGroup = (TextView) findViewById(R.id.nameGroup);
        nameTaskHeading = (TextView) findViewById(R.id.HeadingTask);
        rowTasks = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_show_layout);
        widget_init();
        Intent intent = getIntent();
        try {
            data_task = new JSONObject(intent.getStringExtra("data_task"));
            Log.d("Data_send_task", data_task.toString());
            data_group = new JSONObject(intent.getStringExtra("data_group"));
            nameGroup.setText(data_group.optString("nameG"));
            nameTaskHeading.setText(data_task.optString("nameTask"));
            id_user = intent.getIntExtra("id_user", 0);
            ConvertRowTask(data_task);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void ConvertRowTask(JSONObject dataTask) throws JSONException {
        Task task = new Task();
        task.setId(dataTask.optInt("id"));
        RequestQueue requestQueue = Volley.newRequestQueue(Task_list_show_Layout.this);
        String url = "http://" + getString(R.string.url) + ":8080/ListRowTask";
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(Integer.parseInt("0"), task.toJSON());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<JSONObject> jsonObjects = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        jsonObjects.add((JSONObject) response.get(i));
                        Log.d("row_task value: ", jsonObjects.get(i).toString());
                    }
                    for (JSONObject j : jsonObjects) {
                        rowTasks.add(ConverTask(j));
                        Log.d("row_task value: ", j.toString());
                    }
                    rowTaskAdapter = new Row_taskAdapter(Task_list_show_Layout.this, R.layout.row_task_show, rowTasks, id_user);
                    listView.setAdapter(rowTaskAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Task_list_show_Layout.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public Row_task ConverTask(JSONObject j) throws JSONException {
        Row_task rowTask = new Row_task();
        rowTask.setId(j.optInt("id"));
        rowTask.setName_task(j.optString("name_task"));
        rowTask.setDead_line(j.optString("dead_line"));
        User user = new User();
        user.setId(j.getJSONObject("user").optInt("id"));
        user.setName(j.getJSONObject("user").optString("name"));
        rowTask.setUser(user);
        rowTask.setGhi_chu(j.optString("ghi_chu"));
        return rowTask;
    }

    public Boolean DialogSendFileTask() {
        final Boolean[] check_staus = {false};
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_send_file_task);
        dialog.setCanceledOnTouchOutside(false);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        textUser = (TextView) dialog.findViewById(R.id.textUser);
        ImageView more = (ImageView) dialog.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, REQUEST_PDF_FILES);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textUser.getText().equals("")){
                    Toast.makeText(Task_list_show_Layout.this, "Please choose file", Toast.LENGTH_SHORT).show();
                } else{
                    check_staus[0] = true;
                    GetFileTask();
                    dialog.dismiss();
                }
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

        return check_staus[0];
    }

    public String GetFileTask() {
        return textUser.getText().toString();
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PDF_FILES && resultCode == RESULT_OK) {
            if (data != null) {
                // Kiểm tra xem người dùng đã chọn một hoặc nhiều tệp
                if (data.getData() != null) {
                    // Trường hợp người dùng chỉ chọn một tệp
                    Uri pdfUri = data.getData();
                    // Xử lý tệp PDF ở đây
                    String name_file = pdfUri.getPath().substring(pdfUri.getPath().lastIndexOf("/") + 1);
                    //----- new 25/3/2024
                    String uriString = pdfUri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;
                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                cursor = this.getContentResolver().query(pdfUri, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                }
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }
                    textUser.setText(displayName);
                    //-----------//
//                    Toast.makeText(GroupLayout.this,pdfUri.getPath(),Toast.LENGTH_SHORT).show();
//                    Log.d("file_uri", pdfUri.toString());
//                    Log.d("file_path", pdfUri.getPath().substring(pdfUri.getPath().lastIndexOf("/") + 1));
                }
            }
        }
    }


}