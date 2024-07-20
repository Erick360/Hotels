package com.example.domain.repositories;

import android.database.Observable;

import com.example.domain.model.User;

public interface ISignInRepository {
    Observable<User> isSessionOpen();
}
