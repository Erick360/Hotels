package com.example.hotels.mvp.module;

import com.example.domain.interactor.CheckIsUserSignedInUseCase;
import com.example.hotels.mvp.presenter.LoginPresenter;
import com.example.hotels.mvp.view.ILoginView;
import com.example.hotels.ui.LoginActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class LoginModule {
    @Provides static LoginPresenter provideLoginPresenter(
      ILoginView iLoginView,
      CheckIsUserSignedInUseCase checkIsUserSignedInUseCase){
       return new LoginPresenter(iLoginView,
               checkIsUserSignedInUseCase);

    }

    @Binds abstract ILoginView provideLoginView(LoginActivity loginActivity);
}
