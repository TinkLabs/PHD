package com.tinklabs.phd.scenes;

import com.tinklabs.phd.Main;
import com.tinklabs.phd.model.UpdateInfo;
import com.tinklabs.phd.util.Validations;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Created by root on 5/11/16.
 */
public class UpdateInfoScene extends Scene {

    public UpdateInfoScene(UpdateInfo state, Main main) {
        super(init(state, main), main.getWidth(), main.getHeight());
    }

    private static Parent init(final UpdateInfo updateInfo, final Main main) {
        FXMLLoader loader = null;
        BorderPane pane = null;

        try {
            loader = new FXMLLoader(main.getClass().getResource("resources/layouts/update_info_scene.fxml"));
            pane = loader.load();
            Map<String, Object> fxmlNamespace = loader.getNamespace();

            VBox vbox = (VBox) fxmlNamespace.get("vbox");
            VBox dynamic_info_container = (VBox) fxmlNamespace.get("dynamic_info_container");
            if (!Validations.isEmptyOrNull(updateInfo.model))
                dynamic_info_container.getChildren().add(new Text("Model : " + updateInfo.model));
            if (!Validations.isEmptyOrNull(updateInfo.version))
                dynamic_info_container.getChildren().add(new Text("Version : " + updateInfo.version));
            if (!Validations.isEmptyOrNull(updateInfo.date))
                dynamic_info_container.getChildren().add(new Text("Date : " + updateInfo.date));

            Button next = (Button) fxmlNamespace.get("next");
            next.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    main.proceedToButningScreen(updateInfo);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        main.setTitle("PHD - Checking For Update");
        return pane;
    }
}
