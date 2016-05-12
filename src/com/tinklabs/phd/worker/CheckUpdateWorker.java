package com.tinklabs.phd.worker;

import com.tinklabs.phd.listener.CheckUpdateWorkerListener;
import com.tinklabs.phd.model.UpdateInfo;
import javafx.application.Platform;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by root on 5/12/16.
 */
public class CheckUpdateWorker extends SwingWorker<UpdateInfo, Void> {

    CheckUpdateWorkerListener mCheckUpdateWorkerListener;

    public CheckUpdateWorker(CheckUpdateWorkerListener mCheckUpdateWorkerListener) {
        this.mCheckUpdateWorkerListener = mCheckUpdateWorkerListener;
    }

    @Override
    protected UpdateInfo doInBackground() throws Exception {

        UpdateInfo updateState = null;
        try {
            //TODO replace with actual logic
            {
                Thread.sleep(10000);
                updateState = new UpdateInfo();
                updateState.currentUpdates = UpdateInfo.UpdateState.NO_UPDATE;// new Random().nextInt(2) == 1 ? UpdateInfo.UpdateState.HAS_UPDATE : UpdateInfo.UpdateState.NO_UPDATE;
            }
        } catch (Throwable t) {
        }

        return updateState;
    }


    @Override
    protected void done() {
        Platform.runLater(() -> {
            if (mCheckUpdateWorkerListener != null)
                try {
                    mCheckUpdateWorkerListener.onCheckUpdateComplete(get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
        });
    }
}
