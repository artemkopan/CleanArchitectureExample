package com.artemkopan.presentation.base;

import android.app.Application;
import android.content.Context;

import com.artemkopan.presentation.BuildConfig;
import com.artemkopan.presentation.dependency.AppInjector;

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

}
