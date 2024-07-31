package com.example.hotels.mvp.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module  public abstract class BuildersModule {
    @PerActivity
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity contributeLoginActivity();

}
