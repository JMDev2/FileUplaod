package com.uploads.fileupload.models;

public class FileDownload {
    private String fileName;
    private String fileDescription;

    public FileDownload(String fileName, String fileDescription) {
        this.fileName = fileName;
        this.fileDescription = fileDescription;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }
}
