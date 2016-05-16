package com.tinklabs.phd.worker;

import com.tinklabs.phd.listener.DownloadWorkerListener;
import com.tinklabs.phd.model.DownloadWorkerResult;
import com.tinklabs.phd.model.UpdateInfo;
import com.tinklabs.phd.util.Validations;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by root on 5/16/16.
 */
public class DownloadWorker extends SwingWorker<DownloadWorkerResult, UpdateInfo.UpdateData> {

    DownloadWorkerListener mDownloadWorkerListener;
    UpdateInfo mUpdateInfo;

    public DownloadWorker(DownloadWorkerListener downloadWorkerListener, UpdateInfo updateInfo) {
        mDownloadWorkerListener = downloadWorkerListener;
        mUpdateInfo = updateInfo;
    }

    @Override
    protected void process(List<UpdateInfo.UpdateData> chunks) {
        if (!Validations.isEmptyOrNull(chunks)) {
            UpdateInfo.UpdateData updateData = chunks.get(0);
            if (mDownloadWorkerListener != null)
                mDownloadWorkerListener.onDownloadUpdate(updateData);
        }
    }

    @Override
    protected DownloadWorkerResult doInBackground() throws Exception {
        //TODO: Replace with real logic
        UpdateInfo.UpdateData updateData = mUpdateInfo != null && !Validations.isEmptyOrNull(mUpdateInfo.updateDatas) ? mUpdateInfo.updateDatas.get(0) : null;
        if (updateData != null) {
            while (updateData.done < updateData.file_length) {
                updateData.done += 100;
                publish(updateData);
                Thread.sleep(1000);
            }
        }
        DownloadWorkerResult downloadWorkerResult = new DownloadWorkerResult();
        downloadWorkerResult.result = DownloadWorkerResult.DownloadWorkerResultEnum.DOWNLOADS_SUCCESSFUL;
        return downloadWorkerResult;
    }

    @Override
    protected void done() {
        if (mDownloadWorkerListener != null)
            try {
                mDownloadWorkerListener.onDownloadWorkerFinished(get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
    }
}
