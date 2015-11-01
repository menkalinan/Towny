package com.goldenpie.devs.kievrest.utils;

import com.goldenpie.devs.kievrest.event.ErrorEvent;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BaseCallback<T> implements Callback<T> {
    public static String getErrorHeaderMessage(RetrofitError error) {
        String message = null;
        Response response = error.getResponse();
        if (response != null && response.getStatus() == HttpURLConnection.HTTP_INTERNAL_ERROR) {

        }
        if (message == null) {
            message = error.getMessage();
        }
        if (error.getCause() != null && error.getCause() instanceof UnknownHostException) {
            message += "\nPlease check your network connection and try again";
        } else if (error.getCause() instanceof SocketTimeoutException) {
            message = "Server temporary unavailable";
        }
        return message;
    }

    @Override
    public void success(T t, Response response) {
        EventBus.getDefault().post(t);
    }

    @Override
    public void failure(RetrofitError error) {
        handleError(error);
    }

    protected void handleError(RetrofitError error) {
        String message = getErrorHeaderMessage(error);
        if (!message.contains("Please check your network connection and try again")) {
            EventBus.getDefault().post(new ErrorEvent(message));
        } else
            EventBus.getDefault().post(new NetworkErrorEvent());
    }

}
