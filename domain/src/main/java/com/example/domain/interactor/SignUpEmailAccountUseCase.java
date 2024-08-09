package com.example.domain.interactor;

import com.example.domain.interactor.parameters.EmailParameters;
import com.example.domain.model.User;
import com.example.domain.repositories.ISignInRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class SignUpEmailAccountUseCase  extends  UseCaseBase<User, EmailParameters>{
    private ISignInRepository iSignInRepository;
    private Scheduler schedulerThread;

    @Inject
    public SignUpEmailAccountUseCase(ISignInRepository iSignInRepository, Scheduler schedulerThread) {
        this.iSignInRepository = iSignInRepository;
        this.schedulerThread = schedulerThread;
    }

    @Override
    public Observable<User> implementUseCase(DisposableObserver observer, EmailParameters.Parameters parameters) {
        //Observable para loguearse con la cuenta de email
        Observable<User> observable = iSignInRepository.signUpAccountWithEmail(parameters.getEmail(), parameters.getPassword());

        //Con este observable, construimos el caso de uso, al que subscribimos el observador recibido.
        this.createUseCase(observable, observer, schedulerThread);

        return observable;
    }

    @Override
    public Observable<User> implementUseCase(DisposableObserver observer, EmailParameters object) {
        return null;
    }
}
