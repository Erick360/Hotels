package com.example.domain.interactor;

import com.example.domain.interactor.parameters.SignInParameters;
import com.example.domain.model.User;
import com.example.domain.repositories.ISignInRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class SignInUseCase extends UseCaseBase<User, SignInParameters.Parameters>{
    private ISignInRepository iSignInRepository;
    private Scheduler schedulerThread;

    @Inject
    public SignInUseCase(ISignInRepository iSignInRepository, Scheduler schedulerThread){
        this.iSignInRepository = iSignInRepository;
        this.schedulerThread = schedulerThread;
    }

    @Override
    public Observable<User> implementUseCase(DisposableObserver observer, SignInParameters.Parameters parameters){
        Observable<User> observable = iSignInRepository.signIn(parameters.getSessionProvider(), parameters.getAccountIdToken());
        this.createUseCase(observable, observer, schedulerThread);
        return observable;
    }
}
