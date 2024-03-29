package com.tinklabs.phd.scenes;

import com.tinklabs.phd.Main;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Created by root on 5/11/16.
 */
public class WaitingForNetworkScene extends Scene {


    public WaitingForNetworkScene(Main main) {
        super(init(main), main.getWidth(), main.getHeight());
    }

    private static Parent init(Main main) {
        FXMLLoader loader = null;
        BorderPane pane = null;

        try {
            loader = new FXMLLoader(main.getClass().getResource("resources/layouts/waiting_for_network.fxml"));
            pane = loader.load();
            Map<String, Object> fxmlNamespace = loader.getNamespace();


        } catch (Exception e) {
            e.printStackTrace();
        }
        main.setTitle("PHD - Waiting for network");
        return pane;
    }
}
