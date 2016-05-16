package com.tinklabs.phd;

import com.tinklabs.phd.config.Config;
import com.tinklabs.phd.listener.DownloadWorkerListener;
import com.tinklabs.phd.listener.PortDetectionTaskListener;
import com.tinklabs.phd.model.DownloadWorkerResult;
import com.tinklabs.phd.model.NetworkState;
import com.tinklabs.phd.model.UpdateInfo;
import com.tinklabs.phd.scenes.*;
import com.tinklabs.phd.util.RuntimeUtils;
import com.tinklabs.phd.worker.CheckUpdateWorker;
import com.tinklabs.phd.worker.DownloadWorker;
import com.tinklabs.phd.worker.NetworkDetectionWorker;
import com.tinklabs.phd.worker.PortDetectionTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static final double DEFAULT_WIDTH = 300;
    private static final double DEFAULT_HEIGHT = 275;
    Stage primaryStage;
    private double width = DEFAULT_WIDTH;
    private double height = DEFAULT_HEIGHT;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(Config.PREFERRED_WINDOW_DECORATION_STYLE);
        primaryStage.setResizable(false);
        proceedToWaitingForNetwork();
    }

    public void proceedToWaitingForNetwork() {
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

    private void startUpdateCheck() {
        new CheckUpdateWorker(updateInfo -> {
            if (updateInfo != null) {
                switch (updateInfo.currentUpdates) {
                    case NO_UPDATE:
                        proceedToUpdateInfoScreen(updateInfo);
                        break;
                    case HAS_UPDATE:
                        proceedToDownloadingUpdateScreen(updateInfo);
                        break;
                }
            }
        }).execute();
    }

    private void proceedToDownloadingUpdateScreen(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            setSceneAndShow(new DownloadingUpdateScene(updateInfo, this));
        }
    }

    private void proceedToUpdateInfoScreen(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            setSceneAndShow(new UpdateInfoScene(updateInfo, this));
        }
    }

    private void proceedToNetworkInfoAndCheckingUpdateScreen(NetworkState state) {
        if (state != null) {
            setSceneAndShow(new NetworkInfoAndCheckingForUpdateScene(state, this));
            startUpdateCheck();
        }
    }

    public void proceedToBurningScreen(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            setSceneAndShow(new USBDetectionScene(this));
        }
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
            primaryStage.show();
            primaryStage.setScene(scene);
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


    public void startDownloadWorker(MainDownloadListener downloadWorkerListener, UpdateInfo updateInfo) {
        new DownloadWorker(downloadWorkerListener, updateInfo).execute();
    }

    public void restartDevice() {
        try {
            RuntimeUtils.restartApplication(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startUSBDetectionTask(PortDetectionTaskListener portDetectionTaskListener) {
new PortDetectionTask(portDetectionTaskListener).execute();
    }

    public abstract class MainDownloadListener implements DownloadWorkerListener {
        @Override
        public void onDownloadWorkerFinished(DownloadWorkerResult downloadWorkerResult) {
            Platform.runLater(() -> switchToPostUpdateDownloadScreen(downloadWorkerResult));

        }
    }

    private void switchToPostUpdateDownloadScreen(DownloadWorkerResult downloadWorkerResult) {
        if (downloadWorkerResult != null) {
            setSceneAndShow(new PostUpdateDownloadScene(this, downloadWorkerResult));
        }
    }
}
