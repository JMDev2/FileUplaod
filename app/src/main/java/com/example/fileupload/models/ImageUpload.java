package com.example.fileupload.models;

public class ImageUpload {
    private String name;
    private String url;
    private String ref;
    private String description;

    public ImageUpload(String name, String url, String description) {
        this.name = name;
        this.url = url;
        this.description = description;
    }

    public ImageUpload(String name, String url, String description, String ref) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.ref = ref;
    }

    public ImageUpload() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

