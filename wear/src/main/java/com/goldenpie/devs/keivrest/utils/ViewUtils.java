package com.goldenpie.devs.keivrest.utils;

import android.content.Context;
import android.content.Intent;
import android.support.wearable.activity.ConfirmationActivity;
import android.text.TextUtils;

import com.goldenpie.devs.keivrest.ui.activity.MainActivity;

public class ViewUtils {

    public static void showFailureAnim(Context context){
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Отмена");
        context.startActivity(intent);
    }

    public static void showFailureAnim(Context context, String reason){
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, reason);
        context.startActivity(intent);
    }


    public static void showSuccsessAnim(Context context){
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.OPEN_ON_PHONE_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Готово");
        context.startActivity(intent);
    }

    public static void showSuccsessAnim(Context context, String text) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, text);
        context.startActivity(intent);
    }
}
