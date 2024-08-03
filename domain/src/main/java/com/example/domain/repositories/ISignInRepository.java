package com.example.domain.repositories;

import com.example.domain.model.User;

import io.reactivex.Observable;

public interface ISignInRepository {
    Observable<User> isSessionOpen();
    Observable<User> signIn(String provider, String accountIdToken);

}
