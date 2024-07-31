package com.example.hotels.mvp.module;

import com.example.domain.repositories.ISignInRepository;
import com.google.firebase.auth.FirebaseAuth;
import javax.inject.Singleton;

import dagger.Provides;

public class AppModule {
    @Provides @Singleton static ISignInRepository providesSignInRepository(
            SignInRepositoryImplementation signInRepositoryImplementation){
        return signInRepositoryImplementation;
    }

    @Provides @Singleton static FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }
}
