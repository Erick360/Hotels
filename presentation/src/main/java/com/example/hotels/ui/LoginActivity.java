package com.example.hotels.ui;

import com.example.domain.model.User;
import com.example.hotels.R;
import com.example.hotels.mvp.presenter.LoginPresenter;
import com.example.hotels.mvp.view.ILoginView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;


import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private static final String TAG = "LOGIN";
    @BindView(R.id.login_progress)
    ProgressBar login_progress;
    @BindView(R.id.login_signInFacebook_btn)
    Button login_signInFacebook_btn;
    @BindView(R.id.login_signInGoogle_btn)
    Button login_signInGoogle_btn;
    @BindView(R.id.login_email_btn)
    Button login_email_btn;
    @BindView(R.id.login_register_btn)
    Button login_register_btn;
    @BindView(R.id.login_signOut_btn)
    Button login_signOut_btn;

    @OnClick(R.id.login_signInFacebook_btn)
    protected void doFacebookSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        loginPresenter.handleFacebookResult(loginResult);
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, error.toString());
                    }
                });
    }

    //Capturamos el click del boton de Login con Google, para hacer login a través de Google.
    @OnClick(R.id.login_signInGoogle_btn)
    protected void doGoogleSignIn() {
        Intent intentGoogle = loginPresenter.googleSignIn();
        startActivityForResult(intentGoogle, Flags.GOOGLE_SIGN_IN);
    }

    @Inject
    LoginPresenter loginPresenter;

    @Inject
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        loginPresenter.initialize(this);
    }

    @Override
    public void updateUI(User user) {

    }

    @Override
    public void onSignedIn() {

    }

    @Override
    public void onSignedOut() {

    }

    @Override
    public void ShowLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessages(String message) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //Capturamos el Intent
        if (requestCode == Flags.GOOGLE_SIGN_IN)//Si recibimos el intent de Google, manejaremos la respuesta a través de Google
            loginPresenter.handleGoogleResult(data);
        else if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode())//Si recibimos el intent de Facebook, manejaremos la respuesta a través de Facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
        else if (requestCode == Flags.EMAIL_LOGIN || requestCode == Flags.EMAIL_SIGN_IN)//Si recibimos el intent de login con email, miramos si tenemos sesión iniciada.
            loginPresenter.isSignedIn();
    }

    @Provides
    @Singleton
    static CallbackManager provideCallbackManager(){
        return CallbackManager.Factory.create();
    }
}
