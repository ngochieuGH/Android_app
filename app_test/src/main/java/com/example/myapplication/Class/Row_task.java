package com.example.myapplication.Class;

import org.json.JSONException;
import org.json.JSONObject;

public class Row_task {
    private int id;
    private String name_task, dead_line, ghi_chu, name_fileT, uri_fileT, day_finish;
    private User user;
    private int trang_thai;

    public Row_task() {
    }

    public Row_task(String name_task, String dead_line, User user, String ghi_chu, int trang_thai) {
        this.name_task = name_task;
        this.dead_line = dead_line;
        this.user = user;
        this.ghi_chu = ghi_chu;
        this.trang_thai = trang_thai;
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

    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }

    public String getName_fileT() {
        return name_fileT;
    }

    public void setName_fileT(String name_fileT) {
        this.name_fileT = name_fileT;
    }

    public String getUri_fileT() {
        return uri_fileT;
    }

    public void setUri_fileT(String uri_fileT) {
        this.uri_fileT = uri_fileT;
    }

    public String getDay_finish() {
        return day_finish;
    }

    public void setDay_finish(String day_finish) {
        this.day_finish = day_finish;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("name_task", getName_task());
        jsonObject.put("dead_line", getDead_line());
        jsonObject.put("user", getUser().toJSON());
        jsonObject.put("ghi_chu", getGhi_chu());
        jsonObject.put("trang_thai", getTrang_thai());
        jsonObject.put("name_fileT", getName_fileT());
        jsonObject.put("uri_fileT", getUri_fileT());
        jsonObject.put("day_finish", getDay_finish());
        return jsonObject;
    }
}
