package com.example.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Class.Group;
import com.example.myapplication.R;

import java.util.List;

public class GroupAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Group> groupList;

    public GroupAdapter(Context context, int layout, List<Group> groupList) {
        this.context = context;
        this.layout = layout;
        this.groupList = groupList;
    }

    @Override
    public int getCount() {
        return groupList.size();
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
        TextView nameGroup = (TextView) convertView.findViewById(R.id.nameGroup);
        TextView nameLeader = (TextView) convertView.findViewById(R.id.nameLeader);

        Group group = groupList.get(position);
        nameGroup.setText(group.getName_group());
        nameLeader.setText(group.getUser().getName());
        Log.d("Adapter1111111", "Yesssss");
        return convertView;
    }
}
