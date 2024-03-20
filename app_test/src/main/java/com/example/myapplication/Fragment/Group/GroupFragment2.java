package com.example.myapplication.Fragment.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class GroupFragment2 extends Fragment {
    private View view;
    private ImageView btnTaskList, btnOptions, file, calendar;
    private EditText txt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnTaskList = (ImageView) view.findViewById(R.id.newTaskList);
        btnOptions = (ImageView) view.findViewById(R.id.btnOption);
        file = (ImageView) view.findViewById(R.id.file);
        calendar = (ImageView) view.findViewById(R.id.Calendar);
        txt = (EditText) view.findViewById(R.id.textContent);
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOptions.setVisibility(View.INVISIBLE);
                file.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.VISIBLE);
            }
        });

        btnTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Group_details_Layout.this, Task_list_Layout.class);
//                startActivity(intent);
                Fragment fragment = new TaskFragment1();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.replace_Fragment, fragment).commit();
            }
        });
    }

    }

