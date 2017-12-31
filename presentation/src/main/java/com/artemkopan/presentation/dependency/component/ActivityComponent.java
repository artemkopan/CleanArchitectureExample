package com.artemkopan.presentation.dependency.component;

import com.artemkopan.presentation.ui.home.HomeActivity;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@Subcomponent()
public interface ActivityComponent {

    void inject(@NotNull HomeActivity homeActivity);

}
