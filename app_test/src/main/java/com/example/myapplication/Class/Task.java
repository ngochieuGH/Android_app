package com.example.myapplication.Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Task {
    private int id;
    private String nameTask;
    private Group group;
    private List<Row_task> rowTasks;

    public Task() {
    }

    public Task(int id,String nameTask, Group group, List<Row_task> rowTasks) {
        this.id = id;
        this.nameTask = nameTask;
        this.group = group;
        this.rowTasks = rowTasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Row_task> getRowTasks() {
        return rowTasks;
    }

    public void setRowTasks(List<Row_task> rowTasks) {
        this.rowTasks = rowTasks;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",getId());
        jsonObject.put("nameTask", getNameTask());
        jsonObject.put("group", getGroup().toJSON());
        JSONArray jsonArray = new JSONArray();
        for(int i=0 ; i < getRowTasks().size() ; i++){
            jsonArray.put(i, getRowTasks().get(i).toJSON());
        }
        jsonObject.put("rowtasks", jsonArray);
        return jsonObject;
    }

}
