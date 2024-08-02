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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.View;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class LoginActivity extends AppCompatActivity implements ILoginView {

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


    @Inject
    LoginPresenter loginPresenter;

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
    public void hideLoading() {

    }

    @Override
    public void showMessages(String message) {

    }
}
