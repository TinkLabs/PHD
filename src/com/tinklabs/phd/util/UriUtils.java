package com.tinklabs.phd.util;

import org.omg.CORBA.NameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by root on 4/29/16.
 */
public class UriUtils {
    public static String nameValuePairsToQuery(NameValuePair[] nameValuePairs) {
        if (nameValuePairs != null) {
            String result = "";
            for (int i = 0; i < nameValuePairs.length; i++) {
                NameValuePair nameValuePair = nameValuePairs[i];
                if (nameValuePair != null) {
                    if (i > 0) result = result + "&";
                    try {
                        result = result + URLEncoder.encode(nameValuePair.id, "UTF-8") + "=" + URLEncoder.encode(nameValuePair.value.extract_string(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {

                    }
                }
            }
            return result;
        }
        return null;
    }
}
