package com.tinklabs.phd.scenes;

import com.tinklabs.phd.Main;
import com.tinklabs.phd.model.NetworkState;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Created by root on 5/11/16.
 */
public class NetworkInfoAndCheckingForUpdate extends Scene {

    public NetworkInfoAndCheckingForUpdate(NetworkState state, Main main) {
        super(init(state, main), main.getWidth(), main.getHeight());
    }

    private static Parent init(NetworkState state, Main main) {
        FXMLLoader loader = null;
        BorderPane pane = null;

        try {
            loader = new FXMLLoader(main.getClass().getResource("resources/layouts/network_info_and_checking_for_update.fxml"));
            pane = loader.load();
            Map<String, Object> fxmlNamespace = loader.getNamespace();
            Text close = (Text) fxmlNamespace.get("close");
            close.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Platform.exit();
                }
            });
            VBox vbox = (VBox) fxmlNamespace.get("vbox");
            VBox dynamic_info_container = (VBox) fxmlNamespace.get("dynamic_info_container");
            dynamic_info_container.getChildren().add(new Text("IP Address : " + state.ipAddress));
            dynamic_info_container.getChildren().add(new Text("MAC Address : " + state.macAddress));

        } catch (Exception e) {
            e.printStackTrace();
        }
        main.setTitle("PHD - Checking For Update");
        return pane;
    }
}
