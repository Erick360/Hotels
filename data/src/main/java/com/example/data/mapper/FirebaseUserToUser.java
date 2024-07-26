package com.example.data.mapper;

import com.google.firebase.auth.FirebaseUser;

public class FirebaseUserToUser     {
    String provider = firebaseUser.getProviders().get(0);
    String uid = firebaseUser.getUid();
    String email = firebaseUser.getEmail();
    String name = firebaseUser.getDisplayName();
    String photoUrl = firebaseUser.getPhotoUrl().toString();

}
