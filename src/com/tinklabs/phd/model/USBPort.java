package com.tinklabs.phd.model;

import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 * Created by root on 5/16/16.
 */
public class USBPort extends PortsInfo.Port {
    public static final String TYPE = "USB";

    @Override
    public String getType() {
        return TYPE;
    }


    @Override
    public Node getNode() {
        Text text = new Text("USB " + (index + 1));
        text.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
        return text;
    }
}
