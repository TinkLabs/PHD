package com.tinklabs.phd.scenes;

import com.tinklabs.phd.Main;
import com.tinklabs.phd.listener.PortFlashingListener;
import com.tinklabs.phd.model.PortDetectionResult;
import com.tinklabs.phd.model.PortsInfo;
import com.tinklabs.phd.util.Validations;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by root on 5/16/16.
 */
public class FlashingScene extends Scene {
    public FlashingScene(Main main, PortDetectionResult result) {
        super(init(main, result), main.getWidth(), main.getHeight());
    }

    private static Parent init(Main main, PortDetectionResult result) {
        FXMLLoader loader = null;
        VBox pane = null;
        try {
            loader = new FXMLLoader(main.getClass().getResource("resources/layouts/flashing_screen.fxml"));
            pane = loader.load();
            Map<String, Object> fxmlNamespace = loader.getNamespace();
            VBox port_list = (VBox) fxmlNamespace.get("port_list");
            final Button next_step = (Button) fxmlNamespace.get("next_step");
            AtomicBoolean success = new AtomicBoolean(true);
            AtomicInteger complete_count = new AtomicInteger(0);
            if (result != null && !Validations.isEmptyOrNull(result.portsInfo.port)) {
                for (PortsInfo.Port port : result.portsInfo.port) {
                    loader = new FXMLLoader(main.getClass().getResource("resources/layouts/flashing_row.fxml"));
                    GridPane list_item = loader.load();
                    Map<String, Object> list_item_namespace = loader.getNamespace();
                    Text list_item_port = (Text) list_item_namespace.get("port_id");
                    list_item_port.setText(port.getDescription());
                    ProgressBar list_item_progress = (ProgressBar) list_item_namespace.get("progress");
                    ImageView imageView = (ImageView) list_item_namespace.get("status_image");
                    main.startNewPortFlashingTask(port, new PortFlashingListener() {

                        @Override
                        public void onPortUpdate(PortsInfo.Port port) {
                            Platform.runLater(() -> {
                                imageView.setVisible(false);
                                list_item_progress.setVisible(true);
                                list_item_progress.setProgress(port.percent_done);
                            });
                        }

                        @Override
                        public void onPortComplete(PortsInfo.Port port) {
                            Platform.runLater(() -> {
                                imageView.setVisible(true);
                                imageView.setImage(new Image("com/tinklabs/phd/resources/drawables/check.png"));
                                list_item_progress.setVisible(false);
                            });
                            int completedCount = complete_count.incrementAndGet();
                            checkCompleteCountAndNextStep(main, complete_count, result, next_step, success);
                        }

                        @Override
                        public void onPortFailed(PortsInfo.Port port) {
                            Platform.runLater(() -> {
                                imageView.setVisible(true);
                                imageView.setImage(new Image("com/tinklabs/phd/resources/drawables/cross.png"));
                                list_item_progress.setVisible(false);
                            });
                            success.set(false);
                            int completedCount = complete_count.incrementAndGet();
                            checkCompleteCountAndNextStep(main, complete_count, result, next_step, success);

                        }
                    });
                    port_list.getChildren().add(list_item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pane;
    }

    private static void checkCompleteCountAndNextStep(Main main, AtomicInteger complete_count, PortDetectionResult result, Button next_step, AtomicBoolean success) {
        int completed = complete_count == null ? 0 : complete_count.get();
        if (result != null && result.portsInfo != null && !Validations.isEmptyOrNull(result.portsInfo.port) && completed == result.portsInfo.port.size()) {
            next_step.setVisible(true);
            if (success.get()) {
                next_step.setText("Next");
                next_step.setOnMouseClicked(event -> {
                    main.proceedToBurningScreen(null);
                });
            } else {
                next_step.setText("Reburn");
                next_step.setOnMouseClicked(event -> {
                    main.switchToFlashingScreen(result);
                });
            }

        }
    }
}
