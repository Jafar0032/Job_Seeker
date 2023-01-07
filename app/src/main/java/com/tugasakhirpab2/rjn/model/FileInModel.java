package com.tugasakhirpab2.rjn.model;

public class FileInModel {

    private String userId, fileName, fileUrl;

    public FileInModel() {
    }

    public FileInModel(String userId, String fileName, String fileUrl) {
        this.userId = userId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public String getUserId() {return userId;}

    public void setUserId(String userId) {this.fileName = fileName;}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
