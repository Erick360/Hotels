package com.example.domain.interactor;

import com.example.domain.interactor.parameters.EmailParameters;
import com.example.domain.model.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCaseBase<Type, Object> implements IObservableUseCase{
    ArrayList<DisposableObserver<Type>> observers = new ArrayList<>();

    public void createUseCase(Observable observable, DisposableObserver<Type> observer,
                              Scheduler schedulerThread){
        observable.subscribeOn(Schedulers.io())
                .observeOn(schedulerThread)
                .subscribeWith(observer);
        subscribe(observer);
    }

    public abstract Observable<User> implementUseCase(DisposableObserver observer, EmailParameters.Parameters parameters);

    public abstract Observable<Type> implementUseCase(DisposableObserver observer, Object object);

    @Override
    public void subscribe(DisposableObserver observer){
        if(!observers.contains(observer)) observers.add(observer);
    }

    @Override
    public void cancelSubscription(){
        ArrayList<DisposableObserver<Type>> observersAux = (ArrayList<DisposableObserver<Type>>) observers.clone();
        for(DisposableObserver observer : observersAux){
            if(observer!=null && observersAux.contains(observer)){
                observer.dispose();
                observers.remove(observer);
            }
        }
    }
}
