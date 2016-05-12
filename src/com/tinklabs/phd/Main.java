package com.tinklabs.phd;

import com.tinklabs.phd.listener.NetworkDetectionWorkerListener;
import com.tinklabs.phd.scenes.WaitingForNetworkScene;
import com.tinklabs.phd.worker.NetworkDetectionWorker;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        setSceneAndShow(new WaitingForNetworkScene(this));
        startNetworkDetection();
    }

    private void startNetworkDetection() {
        new NetworkDetectionWorker(new NetworkDetectionWorkerListener() {
            @Override
            public void onNetworkDetectionComplete(NetworkState state) {
                if (state != null && state == NetworkState.CONNECTED){

                }else{
                    //RETRY (Since it is only a check, not a listner)
                }
            }
        }).execute();
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
