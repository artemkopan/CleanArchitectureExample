package com.artemkopan.presentation.dependency.module;

import com.artemkopan.data.api.RedditService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Singleton
    @Provides
    RedditService provideRedditService(Retrofit retrofit) {
        return retrofit.create(RedditService.class);
    }

}
