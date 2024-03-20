package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Class.User;

import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
    EditText name, username, password, rPassword;
    Button btn;
    public void init_Widget(){
        name = (EditText) findViewById(R.id.textName);
        username = (EditText) findViewById(R.id.textUserName);
        password = (EditText) findViewById(R.id.textPass);
        rPassword = (EditText) findViewById(R.id.textRPass);
        btn = (Button) findViewById(R.id.btnSignUp);
    }
    public void DialogSignUp(JSONObject data){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.setCanceledOnTouchOutside(false);
        Button btn = (Button) dialog.findViewById(R.id.btnNext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, homeLayout.class);
                intent.putExtra("data_user", data.toString());
                startActivity(intent);
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    // Main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init_Widget();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // 192.168.1.3 : Local Server
        // 192.168.1.150: Remote Server
        // http://172.20.10.5:8080/SignUp
        String url = "http://192.168.1.150:8080/SignUp";

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = name.getText().toString();
                String s2 = username.getText().toString();
                String s3 = password.getText().toString();
                if(!s1.equals("") && !s2.equals("") && !s3.equals("")){
                    if(s3.equals(rPassword.getText().toString())){
                        User user = new User();
                        user.setName(s1);
                        user.setUsername(s2);
                        user.setPassword(s3);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, user.toJSON(),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
//                                        Toast.makeText(SignUp.this, response.toString(), Toast.LENGTH_SHORT).show();
//                                        Intent scr = new Intent(SignUp.this, Home_Layout.class);
//                                        startActivity(scr);
                                        DialogSignUp(response);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SignUp.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        requestQueue.add(jsonObjectRequest);
                    }
                    else {
                        Toast.makeText(SignUp.this, "Mật khẩu không khóp, vui lòng nhập lại ", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(SignUp.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}