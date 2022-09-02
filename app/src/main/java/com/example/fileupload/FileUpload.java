package com.example.fileupload;

public class FileUpload {
    private String name;
    private String description;
    private String url;

    public FileUpload(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }



    public FileUpload() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
