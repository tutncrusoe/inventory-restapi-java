package com.lam.jira.payload.response;

public class UploadImageResponse {
    private String fileName;
    private String downloadUri;

    private String fileCode;
    private long size;

    public UploadImageResponse() {};

    public UploadImageResponse(String fileName, String downloadUri, long size) {
        this.fileName = fileName;
        this.downloadUri = downloadUri;
        this.size = size;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
