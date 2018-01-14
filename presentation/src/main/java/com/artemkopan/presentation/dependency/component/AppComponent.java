package com.artemkopan.presentation.dependency.component;

import com.artemkopan.presentation.dependency.UserScope;
import com.artemkopan.presentation.dependency.module.ApiModule;
import com.artemkopan.presentation.dependency.module.AppModule;
import com.artemkopan.presentation.dependency.module.DataModule;
import com.artemkopan.presentation.dependency.module.NetworkModule;
import com.artemkopan.presentation.dependency.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@UserScope

@Component(modules = {
        AppModule.class,
        ApiModule.class,
        NetworkModule.class,
        DataModule.class,
        ViewModelModule.class})
public interface AppComponent {

    ActivityComponent activityComponent();

}
