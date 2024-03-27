package com.example.myapplication.Class;

public class Group {
    //    private int id;
    private int idgroup;
    private String name_group, code_group;
    private User user;

    public Group() {
    }

    public Group(int idgroup, String name_group, String code_group, User user) {
        this.idgroup = idgroup;
        this.name_group = name_group;
        this.code_group = code_group;
        this.user = user;
    }
    public Group(String name_group, User user){
        this.name_group = name_group;
        this.user = user;
    }
    public int getIdgroup() {
        return idgroup;
    }

    public void setIdgroup(int idgroup) {
        this.idgroup = idgroup;
    }

    public String getName_group() {
        return name_group;
    }

    public void setName_group(String name_group) {
        this.name_group = name_group;
    }

    public String getCode_group() {
        return code_group;
    }

    public void setCode_group(String code_group) {
        this.code_group = code_group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
