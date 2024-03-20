package com.example.myapplication.Class;

public class Group {
    //    private int id;
    private String name, nameLeader;

    public Group(String name, String nameLeader) {
        this.name = name;
        this.nameLeader = nameLeader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLeader() {
        return nameLeader;
    }

    public void setNameLeader(String nameLeader) {
        this.nameLeader = nameLeader;
    }
}
