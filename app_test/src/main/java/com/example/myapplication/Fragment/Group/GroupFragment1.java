package com.example.myapplication.Fragment.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Adapter.GroupAdapter;
import com.example.myapplication.Class.Group;
import com.example.myapplication.GroupLayout;
import com.example.myapplication.R;

import java.util.ArrayList;

public class GroupFragment1 extends Fragment {

    private View view;
    ListView listGroup;
    ArrayList<Group> groupArrayList;
    GroupAdapter groupAdapter;
    private void init(View view){
        listGroup = (ListView) view.findViewById(R.id.listGroup);
        groupArrayList = new ArrayList<>();
        groupArrayList.add(new Group("Nhóm 5 Mobile", "Trương Thanh Tùng"));
        groupArrayList.add(new Group("Nhóm 11 KTTK", "Trương Thanh Tùng"));
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        groupAdapter = new GroupAdapter(getActivity(), R.layout.group_row, groupArrayList);
        listGroup.setAdapter(groupAdapter);

        listGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GroupLayout.class);
                startActivity(intent);
//                Fragment fragment = new GroupFragment2();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.replace_Fragment, fragment).commit();
            }
        });
    }
}
