package com.acgsocial.utils.minio.domain;

public record FileMetaData(String fileName, String bucket, String region, long size) {
    public String getFileExtension() {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return ""; // No extension found
        }
        return fileName.substring(lastIndexOfDot + 1);
    }

}
