package com.tinklabs.phd.network;


import com.tinklabs.phd.util.Validations;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;


/**
 * Created by root on 4/29/16.
 */
public class BaseNetworkRequest extends SwingWorker<StringBuffer, Object> {

    private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
    String url;
    BaseNetworkListener callback;
    BaseNetworkBackgroundListener backgroundListener;
    String username = "";
    String password = "";
    private int connect_timeout = DEFAULT_CONNECT_TIMEOUT;

    public BaseNetworkRequest(String url, BaseNetworkListener callback) {
        this.url = url;
        this.callback = callback;
    }

    public BaseNetworkRequest(String url) {
        this(url, null);
        System.out.println("The url is " + url);
    }

    public BaseNetworkRequest(String url, String username, String password) {
        this.url = url;
        this.password = password;
        this.username = username;
        this.callback = null;
        System.out.println("The url is " + url);

    }

    public BaseNetworkRequest(String url, BaseNetworkListener callback, BaseNetworkBackgroundListener backgroundListener, String username, String password) {
        this.url = url;
        this.callback = callback;
        this.backgroundListener = backgroundListener;
        this.username = username;
        this.password = password;

        System.out.println("The url is " + url);

    }

    @Override
    public StringBuffer doInBackground() {
        StringBuffer response = new StringBuffer();
        try {
            URL urlObj = new URL(url);
            URLConnection uc = urlObj.openConnection();
            ((HttpURLConnection) uc).setConnectTimeout(connect_timeout);
            if (!Validations.isEmptyOrNull(username) || !Validations.isEmptyOrNull(password)) {

                String passwdstring = (Validations.isEmptyOrNull(username) ? "" : username) + ":" + (Validations.isEmptyOrNull(password) ? "" : password);
                String encoding = new
                        sun.misc.BASE64Encoder().encode(passwdstring.getBytes());
                uc.setRequestProperty("Authorization", "Basic " + encoding);
            }

            InputStream content = uc.getInputStream();
            if (backgroundListener != null)
                backgroundListener.processInputStream(content);
            else {
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(content));

                String line;
                while ((line = in.readLine()) != null) {
                    response.append("\n" + line);
                }

                in.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return response;
    }

    @Override
    protected void done() {
        if (callback != null)
            try {
                callback.onNetworkRequestComplete(get().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
    }

    public interface BaseNetworkListener {
        void onNetworkRequestComplete(String response);
    }

    public interface BaseNetworkBackgroundListener {
        void processInputStream(InputStream inputStream);
    }
}
