package com.artemkopan.presentation.dependency;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.artemkopan.presentation.dependency.component.ActivityComponent;
import com.artemkopan.presentation.dependency.component.AppComponent;
import com.artemkopan.presentation.dependency.component.DaggerAppComponent;
import com.artemkopan.presentation.dependency.module.ApiModule;
import com.artemkopan.presentation.dependency.module.AppModule;
import com.artemkopan.presentation.dependency.module.NetworkModule;

import timber.log.Timber;

@SuppressWarnings("WeakerAccess")
public class AppInjector {

    private final Application application;
    private AppComponent appComponent;

    private AppInjector(Application application) {
        this.application = application;
        application
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        handleActivity(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(Activity activity) {

                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }
                });
    }

    public static void init(Application application) {
        new AppInjector(application);
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                                             .appModule(new AppModule(application))
                                             .apiModule(new ApiModule())
                                             .networkModule(new NetworkModule())
                                             .build();
        }
        return appComponent;
    }

    public ActivityComponent getActivityComponent() {
        return getAppComponent().activityComponent();
    }

    private void handleActivity(Activity activity) {
        if (activity instanceof Injectable) {
            ((Injectable) activity).inject(this);
        } else {
            Timber.w("Your activity doesn't injectable %s.\n" +
                     "Implement Injectable interface if you want injectable activity ", activity);
        }
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                                         .registerFragmentLifecycleCallbacks(
                                                 new FragmentManager.FragmentLifecycleCallbacks() {
                                                     @Override
                                                     public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
                                                         if (f instanceof Injectable) {
                                                             ((Injectable) f).inject(AppInjector.this);
                                                         }
                                                     }
                                                 }, true);
        }
    }
}
