package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Class.User;
import com.example.myapplication.Fragment.Group.GroupFragment1;
import com.example.myapplication.Fragment.Home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

public class homeLayout extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment homeFragment, groupFragment;
    private FragmentManager fragmentManager;
    //    public void WidgetMapping(){
//        textView = (TextView) findViewById(R.id.textChange);
//    }
    public void GetDataUser(Intent intent){
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("data_user"));
            Log.d("State_Home",jsonObject.toString());
            User user = new User();
            user.setName(jsonObject.opt("name").toString());
            String s = user.getName();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);

        homeFragment = new HomeFragment();
        groupFragment = new GroupFragment1();

        bottomNavigationView = findViewById(R.id.bottom_navi);

        Intent intent = getIntent();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("data_user"));
            replaceFragment(homeFragment, jsonObject);
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int selectItem = item.getItemId();
                    if(selectItem == R.id.home){
                        replaceFragment(homeFragment, jsonObject);
                    }
                    if(selectItem == R.id.group){
                        replaceFragment(groupFragment);
                    }
                    if(selectItem == R.id.addG){
                        Toast.makeText(homeLayout.this, "Add group on", Toast.LENGTH_SHORT).show();
                    }
                    if(selectItem == R.id.note){

                    }
                    return true;
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    void replaceFragment(Fragment fragment, JSONObject data){
        Bundle bundle = new Bundle();
        bundle.putString("dataHome", data.toString());
        fragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.replace_Fragment, fragment).commit();

    }
    void replaceFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.replace_Fragment, fragment).commit();

    }
}