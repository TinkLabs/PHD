package com.tinklabs.phd.listener;

import com.tinklabs.phd.model.NetworkState;

/**
 * Created by root on 5/12/16.
 */
public interface NetworkDetectionWorkerListener {
    public void onNetworkDetectionComplete(NetworkState state);

}
