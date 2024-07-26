package com.example.hotels.mvp.presenter;

import com.example.domain.interactor.CheckIsUserSignedInUseCase;
import com.example.hotels.mvp.view.ILoginView;
import javax.inject.Inject;

public class LoginPresenter implements IPresenter{
    @Inject
    public LoginPresenter(ILoginView iLoginView, CheckIsUserSignedInUseCase checkIsUserSignedInUseCase){
        this.iLoginView = iLoginView;
        this.checkIsUserSignedInUseCase = checkIsUserSignedInUseCase;
    }
}
