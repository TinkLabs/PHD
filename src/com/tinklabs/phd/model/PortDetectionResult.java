package com.tinklabs.phd.model;

/**
 * Created by root on 5/16/16.
 */
public class PortDetectionResult {
    public PortDetectionResultEnum result;
    public PortsInfo portsInfo= new PortsInfo();

    public enum PortDetectionResultEnum {
        SUCCESS, FAILURE;
    }
}
