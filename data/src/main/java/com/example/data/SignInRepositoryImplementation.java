package com.example.data;

import android.content.Context;

import com.example.data.mapper.FirebaseUserToUser;
import com.example.domain.model.User;
import com.example.domain.repositories.ISignInRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Observable;
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
                    getTokenOnSuccessFUllSignIn(emitter);
                }
            }catch (Exception e){
                emitter.onError(e);
            }
        });
    }

    private void getTokenOnSuccessFUllSignIn(Object emitter){
        User user = FirebaseUserToUser.Create(firebaseAuth.getCurrentUser());
        firebaseAuth.getCurrentUser().getIdToken(false).
                addOnCompleteListener(result -> {
                   user.setAuthToken(result.getResult().getToken());
                   emitter.onNext(user);
                   emitter.onComplete();
                });

    }

}
