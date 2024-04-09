package com.example.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Class.Row_task;
import com.example.myapplication.R;
import com.example.myapplication.Task_list_show_Layout;

import java.util.List;

public class Row_taskAdapter extends BaseAdapter {
    private Task_list_show_Layout context;
    private int layout;
    private List<Row_task> rowTasks;

    private int id_user;

    public Row_taskAdapter(Task_list_show_Layout context, int layout, List<Row_task> rowTasks, int id_user) {
        this.context = context;
        this.layout = layout;
        this.rowTasks = rowTasks;
        this.id_user  = id_user;
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
        RelativeLayout bg = (RelativeLayout) convertView.findViewById(R.id.bg);
        TextView index = (TextView) convertView.findViewById(R.id.index);
        TextView cv = (TextView) convertView.findViewById(R.id.cv);
        TextView ngLam = (TextView) convertView.findViewById(R.id.ngLam);
        TextView deadline = (TextView) convertView.findViewById(R.id.deadline);
        TextView ghiChu = (TextView) convertView.findViewById(R.id.note);
        TextView fileTask = (TextView) convertView.findViewById(R.id.fileTask);
        ImageView check = (ImageView) convertView.findViewById(R.id.ic_check);
        index.setText(position + 1 + "");
        Row_task rowTask = rowTasks.get(position);
        cv.setText(rowTask.getName_task());
        ngLam.setText(rowTask.getUser().getName());
        deadline.setText(rowTask.getDead_line());
        if(rowTask.getGhi_chu() != "null" && !rowTask.getGhi_chu().equals("")){
            ghiChu.setText("# " + rowTask.getGhi_chu());
            Log.d("ghi_chu: " , "true");
        }
        else {
            ghiChu.setText("# Khong co ghi chu");
            Log.d("ghi_chu: " , "false");
        }
        if (id_user != rowTask.getUser().getId()){
            bg.setBackgroundResource(R.drawable.bg_disable);
            check.setEnabled(false);
        }
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checks = context.DialogSendFileTask();
                if(checks){
                    check.setImageResource(R.drawable.check_finish);
                    fileTask.setVisibility(View.VISIBLE);
                    fileTask.setText(context.GetFileTask());
                }
                else {
                    fileTask.setVisibility(View.GONE);
                }
            }
        });
        return convertView;
    }
}
