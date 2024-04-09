package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.AddTaskAdapter;
import com.example.myapplication.Class.Group;
import com.example.myapplication.Class.Row_task;
import com.example.myapplication.Class.Task;
import com.example.myapplication.Class.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class Task_list_detail_Layout extends AppCompatActivity {
    TextView nameG;
    EditText headingTask;
    private ImageView btnBack;
    JSONObject jsonObject;

    ListView listView;

    AddTaskAdapter adapter;
    Button btnAdd, sendTask;

    List<Row_task> rowTasks;
    ArrayList<User> users;
    public User ConvertUser(JSONObject jsonObject){
        User user = new User();
        user.setId(jsonObject.optInt("id"));
        user.setName(jsonObject.optString("name"));
        return user;
    }
    // ----------------- Get List User in Group --------------
    public void SetContext(Group group){
        RequestQueue requestQueue = Volley.newRequestQueue(Task_list_detail_Layout.this);
        String url = "http://" + getString(R.string.url) + ":8080/ListUserGroup";
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
                for (int i=0 ; i < response.length(); i++){
                    try {
                        users.add(ConvertUser((JSONObject) response.get(i)));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Task_list_detail_Layout.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //// -----------Main---------------
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_detail_layout);
        headingTask = (EditText) findViewById(R.id.HeadingTask);
        sendTask = (Button) findViewById(R.id.sendTask);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        nameG = (TextView) findViewById(R.id.nameGroup);
        listView = (ListView) findViewById(R.id.list_task);
        btnAdd = (Button) findViewById(R.id.btn_add_task);
        rowTasks = new ArrayList<>();
        Intent intent = getIntent();
        Group group = new Group();
        users = new ArrayList<>();
        try {
            jsonObject = new JSONObject(intent.getStringExtra("data_task_details"));
            nameG.setText(jsonObject.optString("nameG"));
            group.setIdgroup(jsonObject.optInt("id"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        SetContext(group);
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
        sendTask.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(!rowTasks.isEmpty()){
                    int id1 = LocalDateTime.now().getHour() + LocalDateTime.now().getMinute() + LocalDateTime.now().getSecond();
                    String date = LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getYear();
                    Task task = new Task(id1,headingTask.getText().toString(), group, rowTasks, date);
                    try {
                        SendTask(task);
                        Log.d("Task value is: ", task.toJson().toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    Toast.makeText(Task_list_detail_Layout.this, "Please make minimum 1 row", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // ---------------------- Send task to server ---------------
    private void SendTask(Task task) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(Task_list_detail_Layout.this);
        String url = "http://" + getString(R.string.url) + ":8080/CreateTask";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, task.toJson()
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                DialogSucess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Task_list_detail_Layout.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    // ------------------ Dialog create Task ---------------
    public void DialogCreateTask(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_task);
        dialog.setCanceledOnTouchOutside(false);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        EditText textNameTask = (EditText) dialog.findViewById(R.id.textNotifi);
        EditText textUser = (EditText) dialog.findViewById(R.id.textUser);
        EditText textDead = (EditText) dialog.findViewById(R.id.textDead);
        EditText textNote = (EditText) dialog.findViewById(R.id.textNote);
        ImageView more = (ImageView) dialog.findViewById(R.id.more);
        ImageView time = (ImageView) dialog.findViewById(R.id.time);
        int[] index = {0};
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(dialog.getContext(), more);
                popupMenu.getMenuInflater().inflate(R.menu.menu_user, popupMenu.getMenu());
                for (int i=0 ; i < users.size() ; i++){
                    popupMenu.getMenu().add(0,users.get(i).getId(),0,users.get(i).getName());
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        textUser.setText(item.getTitle());
                        index[0] = item.getItemId() - 1;
                        Log.d("index value: " , index[0] + "");
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
                rowTasks.add(new Row_task(textNameTask.getText().toString(),textDead.getText().toString(),users.get(index[0]), textNote.getText().toString()));
                adapter = new AddTaskAdapter(Task_list_detail_Layout.this, R.layout.row_task, rowTasks );
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

    // ------------------ Dialog when create success ---------------
    public void DialogSucess(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.setCanceledOnTouchOutside(false);
        Button btn = (Button) dialog.findViewById(R.id.btnNext);
        TextView textView = (TextView) dialog.findViewById(R.id.textChangeNotifi);

        textView.setText("Tạo task thành công !");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Task_list_detail_Layout.this, Task_list_Layout.class);
                setResult(RESULT_OK);
                finish();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    // -----------Dialog edit-----------------
    public void DialogEdit(int position, String nameTask, String nameUser, String deadLine, String note){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_task);
        dialog.setCanceledOnTouchOutside(false);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        EditText textNameTask = (EditText) dialog.findViewById(R.id.textNotifi);
        EditText textUser = (EditText) dialog.findViewById(R.id.textUser);
        EditText textDead = (EditText) dialog.findViewById(R.id.textDead);
        EditText textNote = (EditText) dialog.findViewById(R.id.textNote);
        ImageView more = (ImageView) dialog.findViewById(R.id.more);
        ImageView time = (ImageView) dialog.findViewById(R.id.time);
        int[] index = {0};

        textNameTask.setText(nameTask);
        textUser.setText(nameUser);
        textDead.setText(deadLine);
        textNote.setText(note);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(dialog.getContext(), more);
                popupMenu.getMenuInflater().inflate(R.menu.menu_user, popupMenu.getMenu());
                for (int i=0 ; i < users.size() ; i++){
                    popupMenu.getMenu().add(0,users.get(i).getId(),0,users.get(i).getName());
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        textUser.setText(item.getTitle());
                        index[0] = item.getItemId() - 1;
                        Log.d("index value: " , index[0] + "");
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
                rowTasks.set(position, new Row_task(textNameTask.getText().toString(),textDead.getText().toString(),users.get(index[0]), textNote.getText().toString()));
                adapter.notifyDataSetChanged();
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
    // -----------Dialog delete---------------
    public void DialogDelete(int position){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        //dialog.setCanceledOnTouchOutside(false);
        Button btn = (Button) dialog.findViewById(R.id.btnNext);
        TextView textView = (TextView) dialog.findViewById(R.id.textChangeNotifi);

        textView.setText("Bạn muốn xóa dòng này ?");
        btn.setText("Đồng ý");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowTasks.remove(position);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}