package com.tinklabs.phd.listener;

import com.tinklabs.phd.model.DownloadWorkerResult;
import com.tinklabs.phd.model.UpdateInfo;

/**
 * Created by root on 5/16/16.
 */
public interface DownloadWorkerListener {
    void onDownloadUpdate(UpdateInfo.UpdateData updateData);

    void onDownloadWorkerFinished(DownloadWorkerResult downloadWorkerResult);
}
