package com.tinklabs.phd.listener;

import com.tinklabs.phd.model.PortsInfo;

/**
 * Created by root on 5/16/16.
 */
public interface PortFlashingListener {
    void onPortUpdate(PortsInfo.Port port);

    void onPortComplete(PortsInfo.Port port);

    void onPortFailed(PortsInfo.Port port);
}
