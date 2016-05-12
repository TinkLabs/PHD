package com.tinklabs.phd.worker;

import com.tinklabs.phd.config.Config;
import com.tinklabs.phd.listener.NetworkDetectionWorkerListener;
import com.tinklabs.phd.model.NetworkState;
import com.tinklabs.phd.network.BaseNetworkRequest;
import com.tinklabs.phd.util.Validations;
import javafx.application.Platform;

import javax.swing.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by root on 5/12/16.
 */
public class NetworkDetectionWorker extends SwingWorker<NetworkState, Void> {
    NetworkDetectionWorkerListener mNetworkDetectionWorkerListener;
    public static final String SECURE_PING_LINK = "https://api3.handy.travel/apis/ping";
    public static final int RETRY_GAP = 5000;
    public static final int RETRY_TIMES = 5;

    public NetworkDetectionWorker(NetworkDetectionWorkerListener mNetworkDetectionWorkerListener) {
        this.mNetworkDetectionWorkerListener = mNetworkDetectionWorkerListener;
    }

    @Override
    protected NetworkState doInBackground() throws Exception {
        NetworkState networkState = new NetworkState();
        String response = null;
        int cumulated_waiting_time = 0;
        while (cumulated_waiting_time < (RETRY_TIMES * RETRY_GAP)) {
            response = new BaseNetworkRequest(SECURE_PING_LINK).doInBackground().toString();
            if (!Validations.isEmptyOrNull(response)) {
                networkState.connectionState = NetworkState.ConnectionState.CONNECTED;
                InetAddress IP = null;
                NetworkInterface network = null;
                Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
                if (n != null && n.hasMoreElements()) {
                    network = n.nextElement();
                    Enumeration<InetAddress> a = network.getInetAddresses();
                    while (a != null && a.hasMoreElements()) {
                        IP = a.nextElement();
                        if (Config.Network.getLocalNetworkAddressClassType().isInstance(IP))break;
                    }
                }

                if (IP != null) {
                    networkState.ipAddress = IP.getHostAddress();
                }

                if (network != null) {
                    byte[] mac = network.getHardwareAddress();
                    if (!Validations.isEmptyOrNull(mac)) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        networkState.macAddress = sb.toString();
                    }
                }

                //TODO Add Serial and any other info

                break;
            }
            try {
                Thread.sleep(RETRY_GAP);
                cumulated_waiting_time += RETRY_GAP;
            } catch (Throwable t) {
            }
        }
        return networkState;
    }

    @Override
    protected void done() {
        super.done();
        if (mNetworkDetectionWorkerListener != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        mNetworkDetectionWorkerListener.onNetworkDetectionComplete(get());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}
