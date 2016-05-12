package com.tinklabs.phd.listener;

import javax.swing.*;

/**
 * Created by root on 5/12/16.
 */
public interface NetworkDetectionWorkerListener {
    public void onNetworkDetectionComplete(NetworkState state);

    public enum NetworkState {
        CONNECTED, DISCONNECTED;
    }
}
