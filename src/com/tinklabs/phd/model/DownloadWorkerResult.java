package com.tinklabs.phd.model;

/**
 * Created by root on 5/16/16.
 */
public class DownloadWorkerResult {
    public DownloadWorkerResultEnum result;

    public static enum DownloadWorkerResultEnum {
        DOWNLOADS_SUCCESSFUL, DOWNLOADS_UNSUCCESSFUL;
    }
}
