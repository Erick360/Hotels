package com.example.domain.interactor;

import com.example.domain.model.User;
import com.example.domain.repositories.ISignInRepository;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;


public class CheckIsUserSignedInUseCase extends UseCaseBase<User,Void>{
    private ISignInRepository iSignInRepository;
    private Scheduler schedulerThread;

    @Inject public CheckIsUserSignedInUseCase(
        ISignInRepository iSingInRepository, Scheduler schedulerThread){

        this.iSignInRepository = iSignInRepository;
        this.schedulerThread = schedulerThread;

    }

    @Override public Observable<User> implementUseCase(
        DisposableObserver observer , Void parameters){
        Observable<User> observable = iSignInRepository.isSessionOpen();
        this.createUseCase(observable, observer, schedulerThread);
        return observable;
    }
}
