package com.artemkopan.presentation.base;

import android.app.Application;
import android.content.Context;

import com.artemkopan.presentation.dependency.AppInjector;
import com.orhanobut.logger.Logger;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);

        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Logger.e(throwable, throwable.getMessage());
            }
        });
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }


}
