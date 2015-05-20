package me.toufu.appsdklib.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by toufu on 15-5-18.
 */
public class WidgetHelper {
    public static void showMessageDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).create().show();
    }
}
