package com.tinklabs.phd;

import com.tinklabs.phd.scenes.WaitingForNetworkScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        setSceneAndShow(new WaitingForNetworkScene(this));
    }

    public void setTitle(String title) {
        if (primaryStage != null) {
            primaryStage.setTitle(title);
        }
    }

    public void setSceneAndShow(Scene scene) {
        if (scene != null && primaryStage != null) {
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public void setSceneAndShow(Parent root, int width, int height) {
        if (root != null && primaryStage != null) {
            setSceneAndShow(new Scene(root, 300, 275));
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
