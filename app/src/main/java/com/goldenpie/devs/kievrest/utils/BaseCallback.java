package com.goldenpie.devs.kievrest.utils;

import android.util.Log;

import com.goldenpie.devs.kievrest.event.ErrorEvent;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;

import java.net.HttpURLConnection;
import java.net.UnknownHostException;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BaseCallback<T> implements Callback<T> {
    @Override
    public void success(T t, Response response) {
        EventBus.getDefault().post(t);
    }

    @Override
    public void failure(RetrofitError error) {
        handleError(error);
        handleGlobalError(error);
    }

    protected void handleError(RetrofitError error) {
        String message = getErrorHeaderMessage(error);
        Log.e("BaseCallback", message, error);
        EventBus.getDefault().post(new ErrorEvent<T>(message));
    }

    public static String getErrorHeaderMessage(RetrofitError error) {
        String message = null;
        Response response = error.getResponse();
        if (response != null && response.getStatus() == HttpURLConnection.HTTP_INTERNAL_ERROR) {

        }
        if (message == null) {
            message = error.getMessage();
        }
        if(error.getCause() != null && error.getCause() instanceof UnknownHostException) {
            message += "\nPlease check your network connection and try again";
        }
        return message;
    }

    /**
     * Handle all non HTTP errors
     * @param error current error
     */
    public void handleGlobalError(RetrofitError error){
        if(error.getKind() == RetrofitError.Kind.NETWORK){
            EventBus.getDefault().post(new NetworkErrorEvent());
        }
    }
}
