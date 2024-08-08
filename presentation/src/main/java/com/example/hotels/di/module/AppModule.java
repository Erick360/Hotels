package com.example.hotels.di.module;

import com.example.data.SignInRepositoryImplementation;
import com.example.domain.repositories.ISignInRepository;
import com.example.hotels.App;
import com.example.hotels.R;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import javax.inject.Singleton;
import com.google.android.gms.auth.api.Auth;
import dagger.Provides;

public class AppModule {

    @Provides
    @Singleton
    static GoogleApiClient provideGoogleApiClient(App application) {

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleApiClient googleApiClient = new GoogleApiClient
                .Builder(application)
                //.enableAutoManage(application,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        return googleApiClient;
    }


    @Provides @Singleton static ISignInRepository providesSignInRepository(
            SignInRepositoryImplementation signInRepositoryImplementation){
        return signInRepositoryImplementation;
    }

    @Provides @Singleton static FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }
}
