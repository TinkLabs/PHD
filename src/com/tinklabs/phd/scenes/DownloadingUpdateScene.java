package com.tinklabs.phd.scenes;

import com.tinklabs.phd.Main;
import com.tinklabs.phd.model.UpdateInfo;
import com.tinklabs.phd.util.Validations;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Created by root on 5/16/16.
 */
public class DownloadingUpdateScene extends Scene {
    public DownloadingUpdateScene(UpdateInfo updateInfo, Main main) {
        super(init(main, updateInfo), main.getWidth(), main.getHeight());
    }

    private static Parent init(Main main, UpdateInfo updateInfo) {
        FXMLLoader loader = null;
        BorderPane pane = null;

        try {
            loader = new FXMLLoader(main.getClass().getResource("resources/layouts/downloading_updates_scene.fxml"));
            pane = loader.load();
            Map<String, Object> fxmlNamespace = loader.getNamespace();

            VBox vbox = (VBox) fxmlNamespace.get("vbox");
            VBox dynamic_info_container = (VBox) fxmlNamespace.get("dynamic_info_container");
            Text download_counter = (Text) fxmlNamespace.get("update_counter");
            Text filename = (Text) fxmlNamespace.get("filename");
            Text url = (Text) fxmlNamespace.get("url");
            Text percentage_done = (Text) fxmlNamespace.get("percentage_done");
            ProgressBar progress_bar = (ProgressBar) fxmlNamespace.get("progress_bar");
            main.startDownloadWorker(main.new MainDownloadListener() {
                @Override
                public void onDownloadUpdate(UpdateInfo.UpdateData updateData) {
                    Platform.runLater(() -> {
                                if (updateData != null) {
                                    download_counter.setText(updateData.current + " of " + updateData.total);
                                    if (!Validations.isEmptyOrNull(updateData.file_name)) {
                                        filename.setVisible(true);
                                        filename.setText("File name: " + updateData.file_name);
                                    } else
                                        filename.setVisible(false);

                                    if (!Validations.isEmptyOrNull(updateData.url)) {
                                        url.setVisible(true);
                                        url.setText("URL : " + updateData.url);
                                    } else
                                        url.setVisible(false);

                                    percentage_done.setText("Done : " + (updateData.done) + " / " + (updateData.file_length));
                                    double progress = (double) updateData.done / (double) updateData.file_length;
                                    progress_bar.setProgress(progress);
                                }
                            }
                    );
                }
            }, updateInfo);


        } catch (Exception e) {
            e.printStackTrace();
        }
        main.setTitle("PHD - Downloading Update");
        return pane;
    }
}
