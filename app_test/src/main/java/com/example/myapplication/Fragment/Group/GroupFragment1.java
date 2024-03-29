package com.example.myapplication.Fragment.Group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.example.myapplication.Adapter.GroupAdapter;
import com.example.myapplication.Class.Group;
import com.example.myapplication.Class.User;
import com.example.myapplication.GroupLayout;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupFragment1 extends Fragment {

    private View view;
    ListView listGroup;
    ArrayList<Group> groupArrayList;
    GroupAdapter groupAdapter;
    private Group ConvertGroup(JSONObject jsonObject) throws JSONException {
        Group group = new Group();
        User user = new User();
        group.setIdgroup(jsonObject.optInt("id"));
        group.setName_group(jsonObject.optString("nameG"));
        user.setId(jsonObject.getJSONObject("leader").optInt("id"));
        user.setName(jsonObject.getJSONObject("leader").optString("name"));
        group.setUser(user);
        return group;
    }
    private void init(View view, User user) {
        //Log.d("Group_state", String.valueOf(user.getId()));
        listGroup = (ListView) view.findViewById(R.id.listGroup);
        groupArrayList = new ArrayList<>();
//        groupArrayList.add(new Group());
//        groupArrayList.add(new Group());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.150:8080/getListGroup";
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(Integer.parseInt("0"),user.toJSON());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //Toast.makeText(getActivity(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Group_state", response.toString());
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                ArrayList<JSONObject> list = new ArrayList<>();
                try {
                    for(int i=0 ; i < response.length() ; i++){
                        list.add((JSONObject) response.get(i));

                    }
                    for(JSONObject j : list){
                        Group group = ConvertGroup(j);
                        groupArrayList.add(new Group(group.getName_group(), group.getUser()));
                    }
                    groupAdapter = new GroupAdapter(getActivity(), R.layout.group_row, groupArrayList);

                    listGroup.setAdapter(groupAdapter);
                    listGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(getActivity(), list.get(position).toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), GroupLayout.class);
                            intent.putExtra("dataGroup", list.get(position).toString());
                            intent.putExtra("user_name", user.getName());
                            //Log.d("data_sendg", user.getName());
                            startActivity(intent);
                        }
                    });
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String s = getArguments().getString("dataHome");
        //Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject = new JSONObject(s);
            User user = new User();
            user.setId(jsonObject.optInt("id"));
            user.setName(jsonObject.opt("name").toString());
            init(view, user);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
//        groupAdapter = new GroupAdapter(getActivity(), R.layout.group_row, groupArrayList);
//        listGroup.setAdapter(groupAdapter);
    }
}
