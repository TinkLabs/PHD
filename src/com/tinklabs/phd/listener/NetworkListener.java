package com.tinklabs.phd.listener;

/**
 * Created by root on 5/12/16.
 */
public interface NetworkListener {
    public void onNetworkStateChanged(NetworkState state);

    public enum NetworkState {
        CONNECTED, DISCONNECTED;
    }
}
