package com.example.myapplication.Class;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private String username, password, name, sdt, email;

    public User() {
    }

    public User(String username, String password, String name, String sdt, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.sdt = sdt;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JSONObject toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("id", getIdProducto());
            jsonObject.put("name", getName());
            jsonObject.put("username", getUsername());
            jsonObject.put("password", getPassword());
            jsonObject.put("sdt", getSdt());
            jsonObject.put("email", getEmail());

            return jsonObject;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}