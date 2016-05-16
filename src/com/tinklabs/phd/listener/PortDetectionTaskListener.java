package com.tinklabs.phd.listener;

import com.tinklabs.phd.model.PortDetectionResult;
import com.tinklabs.phd.model.PortsInfo;

/**
 * Created by root on 5/16/16.
 */
public interface PortDetectionTaskListener {
    public void onUSBDetectionTaskProgress(PortsInfo.Port port);
    public void onUSBDetectionTaskComplete(PortDetectionResult result);
}
