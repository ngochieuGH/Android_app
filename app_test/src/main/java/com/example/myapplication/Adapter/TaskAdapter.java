package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Class.Task;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

public class TaskAdapter  extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Task> tasks;

    public TaskAdapter(Context context, int layout, List<Task> tasks) {
        this.context = context;
        this.layout = layout;
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
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
        TextView nameTask = (TextView) convertView.findViewById(R.id.nameTask);
        TextView dateCreate = (TextView) convertView.findViewById(R.id.dateCreate);

        Task task = tasks.get(position);
        nameTask.setText(task.getNameTask());
        dateCreate.setText("Ngày tạo: " + task.getDateCreate());
        return convertView;
    }
}
