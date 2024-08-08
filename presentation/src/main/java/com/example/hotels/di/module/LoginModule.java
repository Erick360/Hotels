package com.example.hotels.di.module;

import com.example.domain.interactor.CheckIsUserSignedInUseCase;
import com.example.domain.interactor.SignInUseCase;
import com.example.domain.interactor.SignOutUseCase;
import com.example.hotels.mvp.presenter.LoginPresenter;
import com.example.hotels.mvp.view.ILoginView;
import com.example.hotels.ui.LoginActivity;
import com.google.android.gms.common.api.GoogleApiClient;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class LoginModule{
    @Provides
    static LoginPresenter provideLoginPresenter(
        ILoginView iLoginView, SignInUseCase signInUseCase, SignOutUseCase signOutUseCase, CheckIsUserSignedInUseCase checkIsUserSignedInUseCase, GoogleApiClient googleApiClient
    ) {
        return new LoginPresenter(iLoginView, signInUseCase, signOutUseCase, checkIsUserSignedInUseCase, googleApiClient);
    }

    @Binds
    abstract ILoginView provideLoginView(LoginActivity loginActivity);
    }
}