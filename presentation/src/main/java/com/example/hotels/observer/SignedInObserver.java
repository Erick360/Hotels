package com.example.hotels.observer;

import com.example.domain.model.User;
import com.example.hotels.mvp.view.ISessionView;

import io.reactivex.observers.DisposableObserver;

public class SignedInObserver extends DisposableObserver<User>{
    public SignedInObserver(ISessionView iSessionView){
        this.iSessionView = iSessionView;
    }
    @Override
    public void onNext(User value){
        iSessionView.updateUI(value);
        iSessionView.onSignedIn();
    }

    @Override
    public void onError(Throwable e){
        iSessionView.hideLoading();
        iSessionView.onSignedOut();
    }
    @Override
    public void onComplete(){
        iSessionView.hideLoading();
    }
}
