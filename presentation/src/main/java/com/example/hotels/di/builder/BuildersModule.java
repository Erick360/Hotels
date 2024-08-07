package com.example.hotels.di.builder;

import com.example.hotels.di.scope.PerActivity;
import com.example.hotels.mvp.module.LoginModule;
import com.example.hotels.ui.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module  public abstract class BuildersModule {
    @PerActivity
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity contributeLoginActivity();

}
