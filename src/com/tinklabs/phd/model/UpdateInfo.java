package com.tinklabs.phd.model;

import java.util.ArrayList;

/**
 * Created by root on 5/12/16.
 */
public class UpdateInfo {

    //TODO Remove hardcoded class default values;

    public UpdateState currentUpdates;
    public String model = "M370";
    public String version = "6.045";
    public String date = "2016-04-27";
    public ArrayList<UpdateData> updateDatas = new ArrayList<UpdateData>() {
        {
            add(new UpdateData("Handgouts", 2300, "axsdxnkl", "http://www.google.com/"));
        }
    };

    public enum UpdateState {
        HAS_UPDATE, NO_UPDATE;
    }

    public static class UpdateData {
        public String file_name;
        public long file_length;
        public String md5sum;
        public String url;
        public transient long done;
        public transient int current = 1;
        public transient int total = 1;

        public UpdateData() {
        }

        public UpdateData(String file_name, long file_length, String md5sum, String url) {
            this.file_name = file_name;
            this.file_length = file_length;
            this.md5sum = md5sum;
            this.url = url;
        }
    }
}
