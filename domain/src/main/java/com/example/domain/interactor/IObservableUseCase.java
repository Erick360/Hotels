package com.example.domain.interactor;

import io.reactivex.observers.DisposableObserver;

public interface IObservableUseCase {
    void subscribe(DisposableObserver observer);

    void cancelSubscription();
}
