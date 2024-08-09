package com.example.data;

import android.content.Context;

import android.util.Log;

import com.example.data.mapper.FirebaseUserToUser;
import com.example.domain.model.SessionProvider;
import com.example.domain.model.User;
import com.example.domain.repositories.ISignInRepository;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

import javax.inject.Inject;


public class SignInRepositoryImplementation implements ISignInRepository{

    private static final String TAG = "SIGNIN";

    //Factoría del almacén de datos para seleccionar entre Local o Remoto.
    private SessionDataStoreFactory sessionDataStoreFactory;

    private GoogleApiClient googleApiClient;
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
                    getTokenOnSuccessFulSignIn(emitter);
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
                                                getTokenOnSuccessFulSignIn(emitter);
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

    //Registro de usuario mediante email y contraseña
    @Override
    public Observable<User> signUpAccountWithEmail(String email, String password) {
        return Observable
                .create(emitter -> {
                    try {
                        //Creamos usuario nuevo mediante email y contraseña
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(
                                        task -> {
                                            if (task.isSuccessful())
                                                getTokenOnSuccessFulSignIn(emitter);
                                            else emitter.onError(task.getException());
                                        });
                    } catch (Exception e) {
                        //Indicamos que se ha producido un error.
                        emitter.onError(e);
                    }
                });
    }

        //Retornar el usuario con el token de autenticación
        private void getTokenOnSuccessFulSignIn(ObservableEmitter<User> emitter) {
            // Sign in success, Actualizar interfaz con información del usuario logueado.
            User user = FirebaseUserToUser.Create(firebaseAuth.getCurrentUser());

            //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            //Obtenemos el usuario de la sesión de firebase y obtenemos su token, sin la necesidad de forzar el refresco de token.
            firebaseAuth.getCurrentUser().getIdToken(false)
                    .addOnCompleteListener(result -> {
                        //Una vez obtenido el token de usuario, lo asignamos a la instancia usuario.
                        user.setAuthToken(result.getResult().getToken());

                        //Si se ha guardado correctamente localmente el usuario, lo retornamos.
                        if (sessionDataStoreFactory.Local().saveSession(user)) {
                            emitter.onNext(user);
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Exception(context.getString(R.string.error_save_user_local)));
                        }

                        Log.d(TAG, "Token Firebase User:" + result.getResult().getToken());
                    });
        }
}

