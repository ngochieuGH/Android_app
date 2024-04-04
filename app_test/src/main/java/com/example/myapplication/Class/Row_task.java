package com.example.myapplication.Class;

import org.json.JSONException;
import org.json.JSONObject;

public class Row_task {
    private int id;
    private String name_task, dead_line;
    private User user;

    public Row_task() {
    }

    public Row_task(String name_task, String dead_line, User user) {
        this.name_task = name_task;
        this.dead_line = dead_line;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_task() {
        return name_task;
    }

    public void setName_task(String name_task) {
        this.name_task = name_task;
    }

    public String getDead_line() {
        return dead_line;
    }

    public void setDead_line(String dead_line) {
        this.dead_line = dead_line;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("name_task", getName_task());
        jsonObject.put("dead_line", getDead_line());
        jsonObject.put("user", getUser().toJSON());
        return jsonObject;
    }
}
