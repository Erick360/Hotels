package com.example.domain.interactor;

import com.example.domain.repositories.ISignInRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class SignOutUseCase extends UseCaseBase<Void, Void>{

    private ISignInRepository iSignInRepository;
    private Scheduler schedulerThread;

    @Inject
    public SignOutUseCase(ISignInRepository iSignInRepository, Scheduler schedulerThread){
        this.iSignInRepository = iSignInRepository;
        this.schedulerThread = schedulerThread;
    }

    @Override
    public Observable implementUseCase(DisposableObserver observer, Void parameters){

        Observable observable = iSignInRepository.signOut();

        this.createUseCase(observable, observer, schedulerThread);
        return observable;
    }
}
