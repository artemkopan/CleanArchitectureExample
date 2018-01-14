package com.artemkopan.presentation.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.artemkopan.domain.Logger;
import com.artemkopan.domain.Logger.Printer;
import com.artemkopan.domain.Logger.Priority;
import com.artemkopan.presentation.BuildConfig;
import com.artemkopan.presentation.dependency.AppInjector;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
            Logger.addPrinter(new LoggerTimberPrinter());
        }

        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Timber.e(throwable);
            }
        });
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    private static class LoggerTimberPrinter implements Printer {

        @Override
        public void log(@Priority int priority, String tag, @NotNull String message, @Nullable Throwable t) {
            switch (priority) {
                case Logger.DEBUG:
                    Timber.tag(tag).log(Log.DEBUG, t, message);
                    break;
                case Logger.ERROR:
                    Timber.tag(tag).log(Log.ERROR, t, message);
                    break;
                case Logger.INFO:
                    Timber.tag(tag).log(Log.INFO, t, message);
                    break;
                case Logger.WARN:
                    Timber.tag(tag).log(Log.WARN, t, message);
                    break;
                default:
                    Timber.tag(tag).log(priority, t, message);
            }
        }
    }

}
