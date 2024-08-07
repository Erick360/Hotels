package com.example.data;

import static com.facebook.AccessTokenManager.TAG;

import android.content.Context;
import android.util.Log;


import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import com.example.data.mapper.FirebaseUserToUser;
import com.example.domain.model.SessionProvider;
import com.example.domain.model.User;
import com.example.domain.repositories.ISignInRepository;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

public class SignInRepositoryImplementation implements ISignInRepository{
    private FirebaseAuth  firebaseAuth;
    private Context context;

    @Inject
    public SignInRepositoryImplementation (Context context, FirebaseAuth firebaseAuth){
        this.context = context;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Observable<User> isSessionOpen(){
        return Observable
                .create(emitter -> {
            try{
                if(firebaseAuth.getCurrentUser()==null){
                    emitter.onError(new Exception(context.getString(R.string.there_is_not_active_user)));
                }else{
                    getTokenOnSuccessFUlSignIn(emitter);
                }
            }catch (Exception e){
                emitter.onError(e);
            }
        });
    }

    @Override
    public Observable<User> signIn(String provider, String accountIdToken) {

        return Observable
                .create(emitter -> {
                    try {
                        AuthCredential credential = null;

                        if (provider.equals(SessionProvider.GOOGLE))
                            credential = GoogleAuthProvider.getCredential(accountIdToken, null);
                        else if (provider.equals(SessionProvider.FACEBOOK))
                            credential = FacebookAuthProvider.getCredential(accountIdToken);


                        firebaseAuth.signInWithCredential(credential)
                                .addOnCompleteListener(
                                        task -> {
                                            if (task.isSuccessful())
                                                getTokenOnSuccessFUlSignIn(emitter);
                                            else {
                                                // If sign in fails, display a message to the user.
                                                Log.w(TAG, "signInWithCredential:failure", task.getException());

                                                emitter.onError(task.getException());
                                            }
                                        });


                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                });

    }

    private void getTokenOnSuccessFUlSignIn(Object emitter){
        User user = FirebaseUserToUser.Create(firebaseAuth.getCurrentUser());
        firebaseAuth.getCurrentUser().getIdToken(false).
                addOnCompleteListener(result -> {
                   user.setAuthToken(result.getResult().getToken());
                   emitter.onNext(user);
                   emitter.onComplete();
                });
    }

}
