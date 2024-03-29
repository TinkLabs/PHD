package com.tinklabs.phd.scenes;

import com.tinklabs.phd.Main;
import com.tinklabs.phd.listener.PortDetectionTaskListener;
import com.tinklabs.phd.model.PortDetectionResult;
import com.tinklabs.phd.model.PortsInfo;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.Map;

/**
 * Created by root on 5/16/16.
 */
public class USBDetectionScene extends Scene {
    public USBDetectionScene(Main main) {
        super(init(main),main.getWidth(),main.getHeight());
    }

    private static Parent init(Main main) {
        FXMLLoader loader = null;
        VBox pane = null;
        try {
            loader = new FXMLLoader(main.getClass().getResource("resources/layouts/usb_detection.fxml"));
            pane = loader.load();
            Map<String, Object> fxmlNamespace = loader.getNamespace();
            final VBox ports_list = (VBox) fxmlNamespace.get("port_list");
            final Button flash = (Button) fxmlNamespace.get("flash");
            flash.setVisible(false);
            main.startUSBDetectionTask(new PortDetectionTaskListener() {
                public void onUSBDetectionTaskProgress(PortsInfo.Port port) {
                    Platform.runLater(() -> {
                        if (port != null)
                            ports_list.getChildren().add(port.getNode());
                    });

                }

                @Override
                public void onUSBDetectionTaskComplete(PortDetectionResult result) {
                    Platform.runLater(() -> {
                        flash.setVisible(result != null && result.result == PortDetectionResult.PortDetectionResultEnum.SUCCESS);
                        flash.setOnMouseClicked(event -> Platform.runLater(() -> {
                            main.switchToFlashingScreen(result);
                        }));
                    });
                }
            });
            DoubleProperty wProperty = new SimpleDoubleProperty();
            final ScrollPane scrollPane = (ScrollPane) fxmlNamespace.get("scrollpane");
            wProperty.bind(ports_list.heightProperty()); // bind to Hbox width chnages
            wProperty.addListener((ChangeListener) (ov, t, t1) -> {
                scrollPane.setVvalue(Integer.MAX_VALUE);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pane;
    }
}
