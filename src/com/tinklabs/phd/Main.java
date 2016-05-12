package com.tinklabs.phd;

import com.tinklabs.phd.listener.NetworkDetectionWorkerListener;
import com.tinklabs.phd.model.NetworkState;
import com.tinklabs.phd.scenes.NetworkInfoAndCheckingForUpdate;
import com.tinklabs.phd.scenes.WaitingForNetworkScene;
import com.tinklabs.phd.worker.NetworkDetectionWorker;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static final double DEFAULT_WIDTH = 300;
    private static final double DEFAULT_HEIGHT = 275;
    Stage primaryStage;
    private double width = DEFAULT_WIDTH;
    private double height = DEFAULT_HEIGHT;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        setSceneAndShow(new WaitingForNetworkScene(this));
        startNetworkDetection();
    }

    private void startNetworkDetection() {
        new NetworkDetectionWorker(state -> {
            if (state != null) {
                switch (state.connectionState) {
                    case CONNECTED: {
                        proceedToNetworkInfoAndCheckingUpdateScreen(state);
                        break;
                    }
                    case DISCONNECTED: {
                        showNoConnectionTryLaterScreen();
                        break;
                    }
                }
            }
        }).execute();
    }

    private void proceedToNetworkInfoAndCheckingUpdateScreen(NetworkState state) {
        setSceneAndShow(new NetworkInfoAndCheckingForUpdate(state,this));

    }

    private void showNoConnectionTryLaterScreen() {

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
            setSceneAndShow(new Scene(root, width, height));
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
