package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Class.Row_task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task_list_show_detail_Layout extends AppCompatActivity {
    JSONObject detail_task;
    ImageView btnBack,ic_check;
    TextView HeadingTask, deadLine, status, notes, fileTask, dayFinish;
    TextView textUser;
    int id_user;
    Uri urlFile;
    Boolean check, check_late = false;
    public void widget_init(){
        btnBack = (ImageView) findViewById(R.id.btnBack);
        ic_check = (ImageView) findViewById(R.id.ic_check);
        HeadingTask = (TextView) findViewById(R.id.HeadingTask);
        deadLine = (TextView) findViewById(R.id.deadLine);
        status = (TextView) findViewById(R.id.status);
        notes = (TextView) findViewById(R.id.notes);
        fileTask = (TextView) findViewById(R.id.fileTask);
        dayFinish = (TextView) findViewById(R.id.dayFinish);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_show_detail_layout);
        Intent intent = getIntent();
        try {
            id_user = intent.getIntExtra("id_user", 0);
            detail_task = new JSONObject(intent.getStringExtra("detail_task"));
            Log.d("detail_task", "onCreate: " + detail_task.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        widget_init();
        HeadingTask.setText("Tên công việc: " + detail_task.optString("name_task"));
        deadLine.setText("DeadLine: " +  detail_task.optString("dead_line"));
        try {
            if(id_user != detail_task.getJSONObject("user").optInt("id")){
                ic_check.setEnabled(false);
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate today = LocalDate.now();
                String date1 = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate date1Parsed = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate date2Parsed = LocalDate.parse(detail_task.optString("dead_line"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (date1Parsed.isAfter(date2Parsed)) {
                    check_late = true;
                }
            }
            if(detail_task.optString("ghi_chu").equals("null")){
                notes.setText("# Không có ghi chú");
            }
            else{
                notes.setText( "# Ghi chú: " + detail_task.optString("ghi_chu"));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        if(detail_task.optInt("trang_thai") == 1){
            dayFinish.setVisibility(View.VISIBLE);
            dayFinish.setText("Ngày hoàn thành: " + detail_task.optString("day_finish"));
            check = true;
            urlFile = Uri.parse(detail_task.optString("uri_fileT"));
            Log.d("sendUrl222", urlFile.toString());
            fileTask.setText(detail_task.optString("name_fileT"));
            fileTask.setVisibility(View.VISIBLE);
            ic_check.setImageResource(R.drawable.check_finish);
            ic_check.setEnabled(false);
            status.setText("Trạng thái: Đã Hoàn Thành");
        } else {
            status.setText("Trạng thái: Chưa Hoàn Thành");
        }
        ic_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_late){
                    DialogLate();
                }
                else{
                    DialogSendFileTask();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Task_list_show_detail_Layout.this, Task_list_show_Layout.class);
                setResult(RESULT_OK);
                finish();
            }
        });
        fileTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_VIEW);
                it.setType("*/*");
                it.setData(urlFile);
                if(!check){
                    it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startActivity(it);
            }
        });
    }
    public void DialogSendFileTask() {
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
                startActivityForResult(intent, 1000);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(textUser.getText().equals("")){
                    Toast.makeText(Task_list_show_detail_Layout.this, "Please choose file", Toast.LENGTH_SHORT).show();
                } else{
                    try {
                        UpdateStatus(detail_task);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    dayFinish.setVisibility(View.VISIBLE);
                    dayFinish.setText("Ngày hoàn thành: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    fileTask.setVisibility(View.VISIBLE);
                    fileTask.setText(textUser.getText());
                    ic_check.setEnabled(false);
                    ic_check.setImageResource(R.drawable.check_finish);
                    status.setText("Trạng thái: Đã hoàn thành");
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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void UpdateStatus(JSONObject j) throws JSONException {
//        Row_task rowTask = new Row_task();
//        rowTask.setId(j.optInt("id"));
//        rowTask.setName_fileT(textUser.getText().toString());
//        rowTask.setUri_fileT(urlFile.toString());
        j.put("name_fileT", textUser.getText().toString());
        j.put("uri_fileT", urlFile.toString());
        j.put("day_finish", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Log.d("updateTask", j.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(Task_list_show_detail_Layout.this);
        String url = "http://" + getString(R.string.url) + ":8080/UpdateStatusTask";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, j,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            if (data != null) {
                // Kiểm tra xem người dùng đã chọn một hoặc nhiều tệp
                if (data.getData() != null) {
                    // Trường hợp người dùng chỉ chọn một tệp
                    Uri pdfUri = data.getData();
                    urlFile = pdfUri;
                    Log.d("sendUrl111", urlFile.toString());
                    // Xử lý tệp PDF ở đây
                    String name_file = pdfUri.getPath().substring(pdfUri.getPath().lastIndexOf("/") + 1);
                    //----- new 25/3/2024
                    String uriString = pdfUri.toString();
                    //urlFile = uriString;
                    File myFile = new File(uriString);
                    Log.d("file111", myFile.toString());
                    String path = myFile.getAbsolutePath();
                    Log.d("file111", path);
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
                    check = false;
                    textUser.setText(displayName);
                    //-----------//
//                    Toast.makeText(GroupLayout.this,pdfUri.getPath(),Toast.LENGTH_SHORT).show();
//                    Log.d("file_uri", pdfUri.toString());
//                    Log.d("file_path", pdfUri.getPath().substring(pdfUri.getPath().lastIndexOf("/") + 1));
                }
            }
        }
    }
    public void DialogLate(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.setCanceledOnTouchOutside(false);
        Button btn = (Button) dialog.findViewById(R.id.btnNext);
        TextView textView = (TextView) dialog.findViewById(R.id.textChangeNotifi);

        textView.setText("Đã qua deadline, không thể nộp !");
        btn.setOnClickListener(new View.OnClickListener() {
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