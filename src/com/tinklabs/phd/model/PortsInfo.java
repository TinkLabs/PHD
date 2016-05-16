package com.tinklabs.phd.model;

import javafx.scene.Node;

import java.util.ArrayList;

/**
 * Created by root on 5/16/16.
 */
public class PortsInfo {
    public ArrayList<Port> port = new ArrayList<>();

    public static abstract class Port {
        public String id;
        public int index;

        public abstract String getType();

        public abstract Node getNode();
    }
}
