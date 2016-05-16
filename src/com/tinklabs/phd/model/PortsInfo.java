package com.tinklabs.phd.model;

import javafx.scene.Node;

import java.util.ArrayList;

/**
 * Created by root on 5/16/16.
 */
public class PortsInfo {
    public ArrayList<Port> port = new ArrayList<>();

    public enum Status {
        INPROGRESS, SUCESSS, FAILURE, IDLE;
    }

    public static abstract class Port {
        public String id;
        public int index;
        public transient Status status;
        public transient double percent_done;

        public abstract String getType();

        public abstract Node getNode();

        public abstract String getDescription();
    }
}
