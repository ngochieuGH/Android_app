package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.FShareAdapter;
import com.example.myapplication.Class.putPDF;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GroupLayout extends AppCompatActivity {
    TextView textView,txt, messs;
    EditText editText;

    ImageView btnTaskList, btnOptions, file, calendar, send_file, btnBack;
    public static final int REQUEST_PDF_FILES = 1;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    Query query;
    ProgressBar progressBar;
    String user_name, idGroup;
    int id_user;
    JSONObject jsonObject;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_layout);
        btnTaskList = (ImageView) findViewById(R.id.newTaskList);
        btnOptions = (ImageView) findViewById(R.id.btnOption);
        file = (ImageView) findViewById(R.id.file);
        calendar = (ImageView) findViewById(R.id.Calendar);
        txt = (TextView) findViewById(R.id.textContent);
        send_file = (ImageView) findViewById(R.id.btnArrow);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        textView = (TextView) findViewById(R.id.name_group2);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        editText = (EditText) findViewById(R.id.textTitle);
        messs = (TextView) findViewById(R.id.mess_null);
        Intent intent = getIntent();
        try {
            jsonObject = new JSONObject(intent.getStringExtra("dataGroup"));
            textView.setText(jsonObject.optString("nameG"));
            user_name = intent.getStringExtra("user_name");
            id_user = intent.getIntExtra("id_user",0);
            //Toast.makeText(GroupLayout.this, user_name, Toast.LENGTH_SHORT).show();
            idGroup = jsonObject.optInt("id") + "";
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Group id" + idGroup);
        retrieveFile();
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
                intent.putExtra("data_task", jsonObject.toString());
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });

    }
    private void retrieveFile(){
        recyclerView = findViewById(R.id.recycler_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        progressBar.setVisibility(View.VISIBLE);
        query = databaseReference.orderByChild("name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    progressBar.setVisibility(View.GONE);
                    showFile();
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showFile() {
        FirebaseRecyclerOptions<putPDF> options = new FirebaseRecyclerOptions.Builder<putPDF>().setQuery(query, putPDF.class).build();
        FirebaseRecyclerAdapter<putPDF, FShareAdapter> adapter = new FirebaseRecyclerAdapter<putPDF, FShareAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FShareAdapter holder, int position, @NonNull putPDF model) {
                progressBar.setVisibility(View.GONE);
                messs.setVisibility(View.GONE);
                holder.file_title.setText(model.getName());
                holder.file_title.setPaintFlags(holder.file_title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.nameShare.setText(model.getNameShare());
                holder.title.setText(model.getTitle());
                holder.file_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setType("*/*");
                        intent.setData(Uri.parse(model.getUrl()));
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public FShareAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fshare_item,parent, false);
                FShareAdapter holder = new FShareAdapter(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void uploadFile(Uri data) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading .....");
        progressDialog.show();

        StorageReference reference = storageReference.child("uploadPDF" + System.currentTimeMillis() + ".pdf");

        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getMetadata() != null){
                    if(taskSnapshot.getMetadata().getReference() != null){
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                putPDF putPDF = new putPDF(txt.getText().toString(), uri.toString(), user_name);
                                if (editText.getText().toString().equals("")){
                                    putPDF.setTitle("# Tai Lieu");
                                }
                                else {
                                    putPDF.setTitle("# " + editText.getText().toString());
                                }
                                putPDF.setDate(date);
                                databaseReference.child(databaseReference.push().getKey()).setValue(putPDF);
                                progressDialog.dismiss();
                                DialogSendFile();
                            }
                        });
                    }
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File is Uploaded.. " + (int)progress + "%");
            }
        });
    }

    @SuppressLint("Range")
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
                    //----- new 25/3/2024
                    String uriString = pdfUri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;
                    if(uriString.startsWith("content://")){
                        Cursor cursor = null;
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                cursor = this.getContentResolver().query(pdfUri,null,null,null);
                                if (cursor != null && cursor.moveToFirst()){
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                }
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }
                    //-----------//
                    txt.setVisibility(View.VISIBLE);
                    txt.setText("#" + displayName);
//                    Toast.makeText(GroupLayout.this,pdfUri.getPath(),Toast.LENGTH_SHORT).show();
//                    Log.d("file_uri", pdfUri.toString());
//                    Log.d("file_path", pdfUri.getPath().substring(pdfUri.getPath().lastIndexOf("/") + 1));
                    send_file.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!txt.getText().toString().equals("")){
                                uploadFile(pdfUri);
                            }
                        }
                    });
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
                    send_file.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!txt.getText().toString().equals("")){
                                //uploadFile();
                                //DialogSendFile();
                            }
                        }
                    });
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
                editText.getText().clear();
                txt.setText(null);
                txt.setVisibility(View.GONE);
                btnOptions.setVisibility(View.VISIBLE);
                file.setVisibility(View.INVISIBLE);
                calendar.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                showFile();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}