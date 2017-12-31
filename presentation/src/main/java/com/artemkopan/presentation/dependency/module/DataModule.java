package com.artemkopan.presentation.dependency.module;

import com.artemkopan.data.model.RedditDataModel;
import com.artemkopan.domain.model.RedditModel;

import dagger.Binds;
import dagger.Module;

@Module()
public abstract class DataModule {

    @Binds
    abstract RedditModel redditModel(RedditDataModel redditDataModel);

}
