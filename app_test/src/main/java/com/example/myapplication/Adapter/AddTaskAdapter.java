package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Class.Group;
import com.example.myapplication.R;

import java.util.List;

public class AddTaskAdapter extends BaseAdapter {
    private Context context;
    private int layout;

    private List<Group> groups;

    public AddTaskAdapter(Context context, int layout, List<Group> groups) {
        this.context = context;
        this.layout = layout;
        this.groups = groups;
    }

    public AddTaskAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        TextView index = (TextView) convertView.findViewById(R.id.index);
        EditText cv = (EditText) convertView.findViewById(R.id.cv);
        EditText ngLam = (EditText) convertView.findViewById(R.id.ngLam);
        EditText deadline = (EditText) convertView.findViewById(R.id.deadline);
        EditText ghiChu = (EditText) convertView.findViewById(R.id.ghiChu);
        Button bthThem = (Button) convertView.findViewById(R.id.btnThem);
        Group group = groups.get(position);
        return convertView;
    }
}
