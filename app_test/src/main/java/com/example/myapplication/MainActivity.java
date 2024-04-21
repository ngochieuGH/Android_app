package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {
    EditText editText;
    EditText editPass;
    Button btn;
    Button btn2, btnSignUp, btnDialog;
    public void init_Widget(){
        editText = (EditText) findViewById(R.id.editTextText);
        editPass = (EditText) findViewById(R.id.editTextTextPassword);
        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnDialog = (Button) findViewById(R.id.btndialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_Widget();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // 192.168.1.3 : Local Server
        // 192.168.1.150: Remote Server
        String url = "http://" + getString(R.string.url) +":8080/login";
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = editText.getText().toString();
                String s2 = editPass.getText().toString();
                if(!s1.equals("") && !s2.equals("") ){
                    User user = new User();
//                    Toast.makeText(MainActivity.this, s1 + " " + s2, Toast.LENGTH_SHORT).show();
                    user.setUsername(s1);
                    user.setPassword(s2);

//                JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, user.names(),
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                        Intent scr = new Intent(MainActivity.this, FragementMain.class);
////                        scr.putExtra("data", "Success");
//                        startActivity(scr);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        Intent scr = new Intent(MainActivity.this, HomeLayout.class);
////                        startActivity(scr);
//                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                        Log.d("Text", error.toString());
//                    }
//                });

                    Log.d("datasend",user.toJSON().toString());
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, user.toJSON(),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("Res value is: ", response.toString());
                                    if(response.toString().equals("{}")){
                                        Toast.makeText(MainActivity.this, "Accout not find, please check again", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Intent scr = new Intent(MainActivity.this, homeLayout.class);
                                        Log.d("State_Home","Off1");
                                        scr.putExtra("data_user", response.toString());
                                        Log.d("State_Home","Off2");
                                        startActivity(scr);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                        Intent scr = new Intent(MainActivity.this, HomeLayout.class);
//                        startActivity(scr);
                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("Text_err", error.toString());
                        }
                    });
                    requestQueue.add(request);
                }
                else{
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(MainActivity.this, "Click on", Toast.LENGTH_SHORT).show();

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_notification);
                dialog.setCanceledOnTouchOutside(false);
                Button btn = (Button) dialog.findViewById(R.id.btnNext);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
    }
}