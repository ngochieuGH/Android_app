package com.example.myapplication.Class;

public class putPDF {
    public String nameShare,name, url, title, date;

    public putPDF() {
    }

    public putPDF(String name, String url, String nameShare) {
        this.name = name;
        this.url = url;
        this.nameShare = nameShare;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameShare() {
        return nameShare;
    }

    public void setNameShare(String nameShare) {
        this.nameShare = nameShare;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
