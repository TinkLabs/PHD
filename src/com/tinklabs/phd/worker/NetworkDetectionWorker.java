package com.tinklabs.phd.worker;

import com.tinklabs.phd.listener.NetworkDetectionWorkerListener;
import com.tinklabs.phd.listener.NetworkDetectionWorkerListener.NetworkState;

import javax.swing.*;

/**
 * Created by root on 5/12/16.
 */
public class NetworkDetectionWorker extends SwingWorker<NetworkState, Void> {
    NetworkDetectionWorkerListener mNetworkDetectionWorkerListener;

    public NetworkDetectionWorker(NetworkDetectionWorkerListener mNetworkDetectionWorkerListener) {
        this.mNetworkDetectionWorkerListener = mNetworkDetectionWorkerListener;
    }

    @Override
    protected NetworkState doInBackground() throws Exception {
        return null;
    }

    @Override
    protected void done() {
        super.done();
    }
}
