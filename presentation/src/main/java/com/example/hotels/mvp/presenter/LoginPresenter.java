package com.example.hotels.mvp.presenter;

import android.content.Context;

import com.example.domain.model.SessionProvider;
import com.example.hotels.observer.SignInObserver;
import com.example.hotels.observer.SignedInObserver;
import com.example.domain.interactor.parameters.SignInParameters;
import com.example.hotels.observer.SignedInObserver;
import com.example.domain.interactor.CheckIsUserSignedInUseCase;
import com.example.domain.interactor.SignInUseCase;
import com.example.hotels.mvp.view.ILoginView;
import com.example.domain.interactor.SignOutUseCase;
import com.facebook.login.LoginResult;

import javax.inject.Inject;

public class LoginPresenter implements IPresenter{
    ILoginView iLoginView;
    private Context context;
    private CheckIsUserSignedInUseCase checkIsUserSignedInUseCase;
    private SignInUseCase signInUseCase;
    private SignOutUseCase signOutUseCase;

    @Inject
    public LoginPresenter(ILoginView iLoginView, CheckIsUserSignedInUseCase checkIsUserSignedInUseCase
    , SignInUseCase signInUseCase, SignOutUseCase signOutUseCase){
        this.iLoginView = iLoginView;
        this.checkIsUserSignedInUseCase = checkIsUserSignedInUseCase;
        this.signInUseCase = signInUseCase;
        this.signOutUseCase = signOutUseCase;
    }

    public void initialize(Context context){
        this.context = context;
        isSignedIn();
    }

    public void isSignedIn(){
        checkIsUserSignedInUseCase.implementUseCase(new SignedInObserver(iLoginView),null);
    }

    //Manejamos el resultado del callbackManager de Facebook.
    public void handleFacebookResult(LoginResult loginResult) {
        iLoginView.showLoading();
        //Obtenemos el Token de acceso de Facebook y lo usaremos en firebase en capas superiores.
        String accessToken = loginResult.getAccessToken().getToken();
        signInUseCase.implementUseCase(
                new SignInObserver(iLoginView, context),
                SignInParameters.Parameters.Create(SessionProvider.FACEBOOK, accessToken));
    }

    @Override
    public void destroy() {

    }
}
