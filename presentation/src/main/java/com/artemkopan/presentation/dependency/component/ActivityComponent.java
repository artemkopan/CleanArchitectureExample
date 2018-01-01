package com.artemkopan.presentation.dependency.component;

import com.artemkopan.presentation.ui.reddit.RedditActivity;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@Subcomponent()
public interface ActivityComponent {

    void inject(@NotNull RedditActivity redditActivity);

}
