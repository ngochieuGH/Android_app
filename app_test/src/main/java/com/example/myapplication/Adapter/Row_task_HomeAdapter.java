package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Class.Row_task;
import com.example.myapplication.R;

import java.util.List;

public class Row_task_HomeAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Row_task> rowTasks;

    public Row_task_HomeAdapter(Context context, int layout, List<Row_task> rowTasks) {
        this.context = context;
        this.layout = layout;
        this.rowTasks = rowTasks;
    }

    @Override
    public int getCount() {
        return rowTasks.size();
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
        TextView nameTask = convertView.findViewById(R.id.nameTask);
        TextView deadLine = convertView.findViewById(R.id.deadline);
        Row_task rowTask = rowTasks.get(position);
        nameTask.setText("# " + rowTask.getName_task());
        deadLine.setText("DeadLine: " + rowTask.getDead_line());
        return convertView;
    }
}
