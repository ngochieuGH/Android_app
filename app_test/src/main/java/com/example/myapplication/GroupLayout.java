package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class GroupLayout extends AppCompatActivity {

    ImageView btnTaskList, btnOptions, file, calendar, send_file, btnBack;
    EditText txt;
    public static final int REQUEST_PDF_FILES = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_layout);
        btnTaskList = (ImageView) findViewById(R.id.newTaskList);
        btnOptions = (ImageView) findViewById(R.id.btnOption);
        file = (ImageView) findViewById(R.id.file);
        calendar = (ImageView) findViewById(R.id.Calendar);
        txt = (EditText) findViewById(R.id.textContent);
        send_file = (ImageView) findViewById(R.id.btnArrow);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOptions.setVisibility(View.INVISIBLE);
                file.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.VISIBLE);
            }
        });

        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                startActivityForResult(Intent.createChooser(intent,"Choose files"),REQUEST_PDF_FILES);
                startActivityForResult(intent, REQUEST_PDF_FILES);
            }
        });

        btnTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupLayout.this, Task_list_Layout.class);
                startActivity(intent);
            }
        });

        send_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt.getText().toString().equals("")){
                    DialogSendFile();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("file_status", "yes");
        // Kiểm tra xem kết quả trả về có phải từ yêu cầu chọn tệp không
        if (requestCode == REQUEST_PDF_FILES && resultCode == RESULT_OK) {
            if (data != null) {
                // Kiểm tra xem người dùng đã chọn một hoặc nhiều tệp
                if (data.getData() != null) {
                    // Trường hợp người dùng chỉ chọn một tệp
                    Uri pdfUri = data.getData();
                    // Xử lý tệp PDF ở đây
                    String name_file = pdfUri.getPath().substring(pdfUri.getPath().lastIndexOf("/") + 1);
                    txt.setText("#" + name_file + "\n");
                    Toast.makeText(GroupLayout.this,pdfUri.getPath(),Toast.LENGTH_SHORT).show();
                    Log.d("file_uri", pdfUri.toString());
                    Log.d("file_path", pdfUri.getPath().substring(pdfUri.getPath().lastIndexOf("/") + 1));
                } else {
                    // Trường hợp người dùng chọn nhiều tệp
                    ClipData clipData = data.getClipData();
                    String file_name = "";
                    if (clipData != null) {
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri pdfUri = clipData.getItemAt(i).getUri();
                            // Xử lý từng tệp PDF ở đây
                            file_name += "#" + pdfUri.getPath().substring(pdfUri.getPath().lastIndexOf("/") + 1) + "\n";
                            Toast.makeText(GroupLayout.this,pdfUri.getPath(),Toast.LENGTH_SHORT).show();
                            Log.d("file_uri", pdfUri.toString());
                            Log.d("file_path", pdfUri.getPath().substring(pdfUri.getPath().lastIndexOf("/") + 1));
                        }
                    }
                    txt.setText(file_name);
                }
            }
        }
        else {
            Log.d("file_status", "no");
        }
    }
    public void DialogSendFile(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.setCanceledOnTouchOutside(false);
        Button btn = (Button) dialog.findViewById(R.id.btnNext);
        TextView textView = (TextView) dialog.findViewById(R.id.textChangeNotifi);

        textView.setText("Chia sẻ thành công");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}