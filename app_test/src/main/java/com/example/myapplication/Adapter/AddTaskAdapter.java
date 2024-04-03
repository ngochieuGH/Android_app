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
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Task_list_detail_Layout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public AddTaskAdapter(Task_list_detail_Layout context, int layout) {
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
        Group group = groups.get(position);
        ngLam.setText(group.getName_group());
        deadline.setText(group.getCode_group());
        return convertView;
    }


}
