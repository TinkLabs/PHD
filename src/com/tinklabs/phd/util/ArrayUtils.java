package com.tinklabs.phd.util;

import java.util.ArrayList;

/**
 * Created by root on 4/28/16.
 */
public class ArrayUtils {
    public static <T> void safelyAppendToArrayList(ArrayList<T> arrayList, T obj) {
        if(arrayList!=null) arrayList.add(obj);
    }

    public static void logArrayList(ArrayList<String> arrayList) {
        if(arrayList!=null){
            for(int i=0;i<arrayList.size();i++)
                System.out.println(arrayList.get(i));
        }
    }
}
