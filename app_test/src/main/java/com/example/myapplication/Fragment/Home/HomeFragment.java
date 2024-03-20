package com.example.myapplication.Fragment.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.homeLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    private View view;
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = view.findViewById(R.id.nameUser);
        String s = getArguments().getString("dataHome");
        try {
            JSONObject jsonObject = new JSONObject(s);
            textView.setText(jsonObject.opt("name").toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return view;
    }
}
