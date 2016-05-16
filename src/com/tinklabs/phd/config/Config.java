package com.tinklabs.phd.config;

import javafx.stage.StageStyle;

import java.net.Inet4Address;

/**
 * Created by root on 5/12/16.
 */
public class Config {
    public static final StageStyle PREFERRED_WINDOW_DECORATION_STYLE = StageStyle.UNIFIED;

    public static class Network {
        public static Class LOCAL_NETWORK_ADDRESS_TYPE = Inet4Address.class;

        public static Class getLocalNetworkAddressClassType() {
            return LOCAL_NETWORK_ADDRESS_TYPE;
        }
    }
}
