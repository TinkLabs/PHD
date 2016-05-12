package com.tinklabs.phd.listener;

import com.tinklabs.phd.model.UpdateInfo;

/**
 * Created by root on 5/12/16.
 */
public interface CheckUpdateWorkerListener {
    public void onCheckUpdateComplete(UpdateInfo updateInfo);

}
