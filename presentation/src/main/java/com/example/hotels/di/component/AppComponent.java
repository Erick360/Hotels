package com.example.hotels.di.component;

import com.example.hotels.App;
import com.example.hotels.mvp.module.AppModule;
import com.example.hotels.di.builder.BuildersModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        BuildersModule.class
})

public interface AppComponent {
    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(App application);

        AppComponent build();

    }

    void inject(App app);
}
