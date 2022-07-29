package com.example.fileupload;

public class Upload {
    public String name;
    public String url;

    public Upload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Upload() {
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
}
