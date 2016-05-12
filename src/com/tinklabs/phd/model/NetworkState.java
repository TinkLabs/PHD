package com.tinklabs.phd.model;

import java.util.HashMap;

/**
 * Created by root on 5/12/16.
 */
public class NetworkState {

    public String ipAddress;
    public String macAddress;
    public String serial;
    public HashMap<String, String> extraInfo = new HashMap<>();
    public ConnectionState connectionState = ConnectionState.DISCONNECTED;

    public enum ConnectionState {
        CONNECTED, DISCONNECTED;
    }
}
