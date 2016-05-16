package com.tinklabs.phd.worker;

import com.tinklabs.phd.listener.PortDetectionTaskListener;
import com.tinklabs.phd.model.PortDetectionResult;
import com.tinklabs.phd.model.PortsInfo;
import com.tinklabs.phd.model.USBPort;
import com.tinklabs.phd.util.Validations;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by root on 5/16/16.
 */
public class PortDetectionTask extends SwingWorker<PortDetectionResult, PortsInfo.Port> {
    PortDetectionTaskListener mPortDetectionTaskListener;

    public PortDetectionTask(PortDetectionTaskListener portDetectionTaskListener) {
        mPortDetectionTaskListener = portDetectionTaskListener;
    }

    @Override
    protected void process(List<PortsInfo.Port> chunks) {
        if (mPortDetectionTaskListener != null && !Validations.isEmptyOrNull(chunks))
            mPortDetectionTaskListener.onUSBDetectionTaskProgress(chunks.get(0));
    }

    @Override
    protected PortDetectionResult doInBackground() throws Exception {
        //TODO: replace with real logic
        PortDetectionResult portDetectionResult = new PortDetectionResult();
        for (int i = 0; i < 20; i++) {
            Thread.sleep(1000);
            USBPort usbPort = new USBPort();
            usbPort.id = "USB00" + i;
            usbPort.index = i;
            portDetectionResult.portsInfo.port.add(usbPort);
            publish(usbPort);
        }
        portDetectionResult.result = PortDetectionResult.PortDetectionResultEnum.SUCCESS;
        return portDetectionResult;
    }

    @Override
    protected void done() {
        if(mPortDetectionTaskListener!=null)
            try {
                mPortDetectionTaskListener.onUSBDetectionTaskComplete(get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
    }
}
