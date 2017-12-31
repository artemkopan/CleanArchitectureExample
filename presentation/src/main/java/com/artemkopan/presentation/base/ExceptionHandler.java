package com.artemkopan.presentation.base;

import android.content.Context;
import android.text.TextUtils;

import com.artemkopan.presentation.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import timber.log.Timber;

public class ExceptionHandler {

    public static String handleException(Context context, Throwable throwable) {
        Timber.e(throwable);

        if (context == null) return "";

        String message;

        if (throwable instanceof UnknownHostException ||
            throwable instanceof ConnectException) {
            return context.getString(R.string.error_internet_connection);
        } else if (throwable instanceof SocketTimeoutException) {
            return context.getString(R.string.error_internet_timeout);
        } else {
            message = throwable.getMessage();
        }

        if (TextUtils.isEmpty(message)) {
            message = context.getString(R.string.error_undefined);
        }

        return message;
    }

}
