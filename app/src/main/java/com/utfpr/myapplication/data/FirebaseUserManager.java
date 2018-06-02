package com.utfpr.myapplication.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.utfpr.myapplication.models.User;
import com.utfpr.myapplication.modules.tutorial.TutorialItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by lispa on 02/04/2018.
 */

public class FirebaseUserManager {

    public static final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static final String TAG = FirebaseUserManager.class.getSimpleName();

    public static final String USER_COLLECTION_NAME = "user";

    public static Observable<User> getUser(String userId){
        return Observable.create(emmiter -> {
            firebaseFirestore.collection(USER_COLLECTION_NAME).document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "success recovering user");
                            if(!task.getResult().exists()) {
                                emmiter.onError(new UserNotFoundException());
                            }else {
                                emmiter.onNext(task.getResult().toObject(User.class));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            emmiter.onError(task.getException());
                        }
                    });
        });
    }

    public static Completable createUser(String userId, User user){
        return Completable.create(emmiter -> {
            firebaseFirestore.collection(USER_COLLECTION_NAME)
                    .document(userId)
                    .set(user)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "success recovering user");
                            emmiter.onComplete();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            emmiter.onError(task.getException());
                        }
                    });
        });
    }


}
