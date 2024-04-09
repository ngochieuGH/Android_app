package com.example.myapplication.Class;

import org.json.JSONException;
import org.json.JSONObject;

public class Row_task {
    private int id;
    private String name_task, dead_line, ghi_chu;
    private User user;

    public Row_task() {
    }

    public Row_task(String name_task, String dead_line, User user, String ghi_chu) {
        this.name_task = name_task;
        this.dead_line = dead_line;
        this.user = user;
        this.ghi_chu = ghi_chu;
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

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
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
        jsonObject.put("ghi_chu", getGhi_chu());
        return jsonObject;
    }
}
