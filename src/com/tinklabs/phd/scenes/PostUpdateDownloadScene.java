package com.tinklabs.phd.scenes;

import com.tinklabs.phd.Main;
import com.tinklabs.phd.model.DownloadWorkerResult;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Map;

/**
 * Created by root on 5/16/16.
 */
public class PostUpdateDownloadScene extends Scene {
    public PostUpdateDownloadScene(Main main, DownloadWorkerResult downloadWorkerResult) {
        super(init(main, downloadWorkerResult), main.getWidth(), main.getHeight());
    }

    private static Parent init(Main main, DownloadWorkerResult downloadWorkerResult) {
        FXMLLoader loader = null;
        VBox pane = null;

        try {
            loader = new FXMLLoader(main.getClass().getResource("resources/layouts/downloading_complete.fxml"));
            pane = loader.load();
            Map<String, Object> fxmlNamespace = loader.getNamespace();
            Text downloads_completion_status = (Text) fxmlNamespace.get("downloads_completion_status");
            downloads_completion_status.setTextAlignment(TextAlignment.CENTER);
            Button retry = (Button) fxmlNamespace.get("retry");
            if (downloadWorkerResult != null && downloadWorkerResult.result != null) {
                switch (downloadWorkerResult.result) {
                    case DOWNLOADS_SUCCESSFUL:
                        downloads_completion_status.setText("Please Wait");
                        retry.setVisible(false);
                        main.setTitle("PHD - Downloads Successful");
                        main.restartDevice();
                        break;
                    case DOWNLOADS_UNSUCCESSFUL:
                        downloads_completion_status.setText("Downloading updates \nunsuccessful");
                        main.setTitle("PHD - Downloads Unsuccessful");
                        retry.setVisible(true);
                        retry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                main.proceedToWaitingForNetwork();
                            }
                        });
                        break;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return pane;
    }
}
