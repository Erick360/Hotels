package com.example.hotels.mvp.presenter;

import android.content.Context;

import com.example.domain.interactor.CheckIsUserSignedInUseCase;
import com.example.hotels.mvp.view.ILoginView;
import com.example.hotels.observer.SignedInObserver;

import javax.inject.Inject;

public class LoginPresenter implements IPresenter{
    ILoginView iLoginView;
    private Context context;
    private CheckIsUserSignedInUseCase checkIsUserSignedInUseCase;

    @Inject
    public LoginPresenter(ILoginView iLoginView, CheckIsUserSignedInUseCase checkIsUserSignedInUseCase){
        this.iLoginView = iLoginView;
        this.checkIsUserSignedInUseCase = checkIsUserSignedInUseCase;
    }

    public void initialize(Context context){
        this.context = context;
        isSignedIn();
    }

    public void isSignedIn(){
        checkIsUserSignedInUseCase.implementUseCase(new SignedInObserver(iLoginView),null);
    }

    @Override
    public void destroy() {

    }
}
