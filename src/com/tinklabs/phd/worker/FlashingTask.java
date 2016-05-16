package com.tinklabs.phd.worker;

import com.tinklabs.phd.listener.PortFlashingListener;
import com.tinklabs.phd.model.PortsInfo;
import com.tinklabs.phd.util.Validations;
import javafx.application.Platform;

import javax.swing.*;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 5/16/16.
 */
public class FlashingTask extends SwingWorker<PortsInfo.Port, PortsInfo.Port> {
    private final PortsInfo.Port mPort;
    private final PortFlashingListener mPortFlashingListener;

    public FlashingTask(PortsInfo.Port port, PortFlashingListener portFlashingListener) {
        mPort = port;
        mPortFlashingListener = portFlashingListener;
    }

    @Override
    protected void process(List<PortsInfo.Port> chunks) {
        if (!Validations.isEmptyOrNull(chunks))
            Platform.runLater(() -> {
                if (mPortFlashingListener != null)
                    mPortFlashingListener.onPortUpdate(chunks.get(0));
            });
    }

    @Override
    protected PortsInfo.Port doInBackground() throws Exception {
        //TODO Replace with real logic
        if (mPort != null) {
            while (mPort.percent_done < 1)
                mPort.percent_done += 0.05;
            Thread.sleep(2000);
            publish(mPort);
        }
        mPort.status = PortsInfo.Status.SUCESSS;//new Random().nextInt(100) % 3 == 0 ? PortsInfo.Status.FAILURE : PortsInfo.Status.SUCESSS;
        return mPort;
    }

    @Override
    protected void done() {
        try {
            PortsInfo.Port mPort = get();
            if (mPort != null && mPort.status != null && mPortFlashingListener != null) {
                switch (mPort.status) {
                    case SUCESSS:
                        Platform.runLater(() -> mPortFlashingListener.onPortComplete(mPort));
                        break;
                    case FAILURE:
                        Platform.runLater(() -> mPortFlashingListener.onPortFailed(mPort));
                        break;
                }
            }
        } catch (Exception e) {
        }
    }
}
