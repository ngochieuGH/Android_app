package com.example.myapplication.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Class.Group;
import com.example.myapplication.Class.Row_task;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Task_list_detail_Layout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddTaskAdapter extends BaseAdapter {
    private Context context;
    private int layout;

    private List<Row_task> rowTasks;

    public AddTaskAdapter(Context context, int layout, List<Row_task> rowTasks) {
        this.context = context;
        this.layout = layout;
        this.rowTasks = rowTasks;
    }

    public AddTaskAdapter(Task_list_detail_Layout context, int layout) {
        this.context = context;
        this.layout = layout;
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
    TextView index,cv,ngLam, deadline, ghiChu;
    Button btnThem, btnXoa;
    void wiget_init(View convertView){
        index = (TextView) convertView.findViewById(R.id.index);
        cv = (TextView) convertView.findViewById(R.id.cv);
        ngLam = (TextView) convertView.findViewById(R.id.ngLam);
        deadline = (TextView) convertView.findViewById(R.id.deadline);
        ghiChu = (TextView) convertView.findViewById(R.id.ghiChu);
        btnThem = (Button) convertView.findViewById(R.id.btnThem);
        btnXoa = (Button) convertView.findViewById(R.id.btnXoa);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        wiget_init(convertView);
        index.setText(position + 1 + "");
        Row_task rowTask = rowTasks.get(position);
        cv.setText(rowTask.getName_task());
        ngLam.setText(rowTask.getUser().getName());
        deadline.setText(rowTask.getDead_line());
        return convertView;
    }


}
