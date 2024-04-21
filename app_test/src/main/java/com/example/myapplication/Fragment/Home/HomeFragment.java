package com.example.myapplication.Fragment.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.Row_taskAdapter;
import com.example.myapplication.Adapter.Row_task_HomeAdapter;
import com.example.myapplication.Class.Row_task;
import com.example.myapplication.Class.Task;
import com.example.myapplication.Class.User;
import com.example.myapplication.R;
import com.example.myapplication.Task_list_show_Layout;
import com.example.myapplication.homeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {
    private View view;
    private TextView textView, messNull;
    private ListView listView;
    private List<Row_task> rowTasks;
    private Row_task_HomeAdapter rowTaskHomeAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = view.findViewById(R.id.nameUser);
        messNull = view.findViewById(R.id.mess_null);
        listView = view.findViewById(R.id.list_task_user);
        rowTasks = new ArrayList<>();
        String s = getArguments().getString("dataHome");
        try {
            JSONObject jsonObject = new JSONObject(s);
            textView.setText(jsonObject.opt("name").toString());
            GetRowTaskHome(jsonObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return view;
    }
    private void GetRowTaskHome(JSONObject jsonObject) {
        User user = new User();
        user.setId(jsonObject.optInt("id"));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://" + getString(R.string.url) + ":8080/ListRowTaskHome";
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(Integer.parseInt("0"), user.toJSON());
            Log.d("home data", jsonArray.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("home data", response.toString());
                List<JSONObject> jsonObjects = new ArrayList<>();
                try {
                    for (int i=0 ; i < response.length() ; i++) {
                        jsonObjects.add((JSONObject) response.get(i));
                    }
                    for(JSONObject j : jsonObjects){
                        rowTasks.add(ConverRowTask(j));
                    }
                    Collections.sort(rowTasks, new DeadlineComparator());
                    rowTaskHomeAdapter = new Row_task_HomeAdapter(getActivity(), R.layout.row_task_home, rowTasks);
                    listView.setAdapter(rowTaskHomeAdapter);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private Row_task ConverRowTask(JSONObject j) {
        Row_task rowTask = new Row_task();
        rowTask.setName_task(j.optString("name_task"));
        rowTask.setDead_line(j.optString("dead_line"));
        return rowTask;
    }
    public static class DeadlineComparator implements Comparator<Row_task> {
        @Override
        public int compare(Row_task r1, Row_task r2) {
            String[] parts1 = r1.getDead_line().split("/");
            String[] parts2 = r2.getDead_line().split("/");

            for (int i = 2; i >= 0; i--) {
                int comparison = parts1[i].compareTo(parts2[i]);
                if (comparison != 0) {
                    return comparison;
                }
            }

            return 0;
        }
    }
}
