package com.tinklabs.phd.scenes;

import com.tinklabs.phd.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.Map;

/**
 * Created by root on 5/16/16.
 */
public class USBDetectionScene extends Scene {
    public USBDetectionScene(Main main) {
        super(init(main));
    }

    private static Parent init(Main main) {
        FXMLLoader loader = null;
        VBox pane = null;

        try {
            loader = new FXMLLoader(main.getClass().getResource("resources/layouts/usb_detection.fxml"));
            pane = loader.load();
            Map<String, Object> fxmlNamespace = loader.getNamespace();



        } catch (Exception e) {
            e.printStackTrace();
        }
        return pane;
    }
}
