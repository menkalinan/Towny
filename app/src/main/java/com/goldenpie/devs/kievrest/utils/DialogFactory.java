package com.goldenpie.devs.kievrest.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.goldenpie.devs.kievrest.R;

public class DialogFactory {
    public static AlertDialog.Builder getLoadingDialog(@NonNull Context context, @NonNull String message) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loader_layout, null, false);
        TextView tv = (TextView) view.findViewById(R.id.dialog_loader_loading_tv);
        tv.setText(message);

        return new AlertDialog.Builder(context)
                .setView(view);
    }

    public static AlertDialog.Builder getLoadingDialog(@NonNull Context context, @StringRes int messageRes) {
        return getLoadingDialog(context, context.getResources().getString(messageRes));
    }

    public static AlertDialog.Builder getInfoDialog(@NonNull Context context, @NonNull String message) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
    }

    public static AlertDialog.Builder getInfoDialog(@NonNull Context context, @StringRes int messageRes) {
        return getInfoDialog(context, context.getResources().getString(messageRes));
    }

    public static void showErrorMessage(@NonNull Context context, String message) {
        getInfoDialog(context, message)
            .setTitle(R.string.error_title)
            .show();
    }

    public static void showErrorMessage(@NonNull Context context, @StringRes int messageRes) {
        showErrorMessage(context, context.getResources().getString(messageRes));
    }
}