package com.example.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Class.Row_task;
import com.example.myapplication.R;
import com.example.myapplication.Task_list_show_Layout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Row_taskAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Row_task> rowTasks;

    private int id_user;

    public Row_taskAdapter(Context context, int layout, List<Row_task> rowTasks, int id_user) {
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
        LinearLayout bg = (LinearLayout) convertView.findViewById(R.id.bg);
        TextView index = (TextView) convertView.findViewById(R.id.index);
        TextView cv = (TextView) convertView.findViewById(R.id.cv);
        TextView ngLam = (TextView) convertView.findViewById(R.id.ngLam);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        index.setText(position + 1 + ". ");
        Row_task rowTask = rowTasks.get(position);
        cv.setText("Công việc: " + rowTask.getName_task());
        ngLam.setText("Người làm: " + rowTask.getUser().getName());
        Log.d("1444444444444444", "finisgh");
        if (id_user != rowTask.getUser().getId()){
            bg.setBackgroundResource(R.drawable.bg_disable);
        }
        if(rowTask.getTrang_thai() == 1){
            status.setText("Trạng thái: Đã Hoàn Thành");
        } else{
            status.setText("Trạng thái: Chưa Hoàn Thành");
        }
        if(rowTask.getTrang_thai() == 1 && id_user == rowTask.getUser().getId()){
            bg.setBackgroundResource(R.drawable.bg_finishtask);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();
            String date1 = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate date1Parsed = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate date2Parsed = LocalDate.parse(rowTask.getDead_line(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (date1Parsed.isAfter(date2Parsed) && rowTask.getTrang_thai() == 0 && id_user == rowTask.getUser().getId()) {
                Log.d("1444444444444444", "lateeeeeeeeeeeeee");
                bg.setBackgroundResource(R.drawable.bg_latetask);
            }
        }
        return convertView;
    }
}
