package com.tinklabs.phd.scenes;

import com.tinklabs.phd.Main;
import com.tinklabs.phd.model.NetworkState;
import com.tinklabs.phd.util.Validations;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
            close.setOnMouseClicked(event -> Platform.exit());
            VBox vbox = (VBox) fxmlNamespace.get("vbox");
            VBox dynamic_info_container = (VBox) fxmlNamespace.get("dynamic_info_container");
            if (!Validations.isEmptyOrNull(state.ipAddress))
                dynamic_info_container.getChildren().add(new Text("IP Address : " + state.ipAddress));
            if (!Validations.isEmptyOrNull(state.macAddress))
                dynamic_info_container.getChildren().add(new Text("MAC Address : " + state.macAddress));
            if (!Validations.isEmptyOrNull(state.serial))
                dynamic_info_container.getChildren().add(new Text("Serial : " + state.serial));
            if (!Validations.isEmptyOrNull(state.extraInfo)) {
                Set<String> keys = state.extraInfo.keySet();
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    dynamic_info_container.getChildren().add(new Text(key + " : " + state.extraInfo.get(key)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        main.setTitle("PHD - Checking For Update");
        return pane;
    }
}
