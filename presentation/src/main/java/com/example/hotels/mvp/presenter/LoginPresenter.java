package com.example.hotels.mvp.presenter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.domain.model.SessionProvider;
import com.example.hotels.observer.SignInObserver;
import com.example.hotels.observer.SignedInObserver;
import com.example.domain.interactor.parameters.SignInParameters;
import com.example.hotels.observer.SignedInObserver;
import com.example.domain.interactor.CheckIsUserSignedInUseCase;
import com.example.domain.interactor.SignInUseCase;
import com.example.hotels.mvp.view.ILoginView;
import com.example.domain.interactor.SignOutUseCase;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

public class LoginPresenter implements IPresenter{
    ILoginView iLoginView;
    private static final String TAG = "LOGIN";
    private Context context;
    private CheckIsUserSignedInUseCase checkIsUserSignedInUseCase;
    private SignInUseCase signInUseCase;
    private SignOutUseCase signOutUseCase;

    @Inject
    public LoginPresenter(ILoginView iLoginView, CheckIsUserSignedInUseCase checkIsUserSignedInUseCase
    , SignInUseCase signInUseCase, SignOutUseCase signOutUseCase){
        this.iLoginView = iLoginView;
        this.checkIsUserSignedInUseCase = checkIsUserSignedInUseCase;
        this.signInUseCase = signInUseCase;
        this.signOutUseCase = signOutUseCase;
    }

    public LoginPresenter(ILoginView iLoginView, SignInUseCase signInUseCase, SignOutUseCase signOutUseCase, CheckIsUserSignedInUseCase checkIsUserSignedInUseCase, GoogleApiClient googleApiClient) {
    }

    public void initialize(Context context){
        this.context = context;
        isSignedIn();
    }

    public void isSignedIn(){
        checkIsUserSignedInUseCase.implementUseCase(new SignedInObserver(iLoginView),null);
    }

    //Hacer login con Google, obteniendo el intent con la api del cliente de google.
    public Intent googleSignIn() {
        iLoginView.showLoading();
        return Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    }

    //Manejar el resultado del intent de google para obtener los credenciales de acceso.
    public void handleGoogleResult(Intent data) {

        GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        //Si hemos tenido éxito al loguearnos en Google
        if (googleSignInResult.isSuccess()) {
            //Obtenemos la cuenta de google.
            GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

            //Autenticar usuario en Firebase.
            Log.d(TAG, "FirebaseAuthWithGoogle Id:" + googleSignInAccount.getId());
            Log.d(TAG, "IdToken:" + googleSignInAccount.getIdToken());

            //Obtenemos el IdToken de Google que nos servirá para el SignIn de Firebase.
            String accountIdToken = googleSignInAccount.getIdToken();
            signInUseCase.implementUseCase(
                    new SignInObserver(iLoginView, context),
                    SignInParameters.Parameters.Create(SessionProvider.GOOGLE, accountIdToken));
        }
    }

    //Manejamos el resultado del callbackManager de Facebook.
    public void handleFacebookResult(LoginResult loginResult) {
        iLoginView.showLoading();
        //Obtenemos el Token de acceso de Facebook y lo usaremos en firebase en capas superiores.
        String accessToken = loginResult.getAccessToken().getToken();
        signInUseCase.implementUseCase(
                new SignInObserver(iLoginView, context),
                SignInParameters.Parameters.Create(SessionProvider.FACEBOOK, accessToken));
    }

    @Override
    public void destroy() {

    }
}
