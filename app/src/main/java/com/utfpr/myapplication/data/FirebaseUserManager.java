package com.utfpr.myapplication.data;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.models.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by lispa on 02/04/2018.
 */

public class FirebaseUserManager {

    private static final String TAG = FirebaseUserManager.class.getSimpleName();

    private static final String USER_COLLECTION_NAME = "user";

    private static final String HISTORY_COLLECTION_NAME = "history";

    public static Observable<User> getUser(String userId){
        return Observable.create(emmiter -> {
            FirebaseFirestore.getInstance().collection(USER_COLLECTION_NAME).document(userId)
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
            FirebaseFirestore.getInstance().collection(USER_COLLECTION_NAME)
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

    public static Observable<String> createHistory(String userId, History history){
        return Observable.create(emmiter -> {
            FirebaseFirestore.getInstance().collection(USER_COLLECTION_NAME)
                    .document(userId)
                    .collection(HISTORY_COLLECTION_NAME)
                    .add(history)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "success creating history");
                            emmiter.onNext(task.getResult().getId());
                        } else {
                            Log.d(TAG, "Error creating history", task.getException());
                            emmiter.onError(task.getException());
                        }
                    });
        });
    }

    public static Observable<History> getUserHistory(String userId, String historyId){
        return Observable.create(emmiter -> {
            FirebaseFirestore.getInstance().collection(USER_COLLECTION_NAME).document(userId)
                    .collection(HISTORY_COLLECTION_NAME)
                    .document(historyId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "success recovering one history");
                            if(!task.getResult().exists()) {
                                emmiter.onError(new UserNotFoundException());
                            }else {
                                emmiter.onNext(task.getResult().toObject(History.class));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            emmiter.onError(task.getException());
                        }
                    });
        });
    }

    public static Observable<List<History>> getAllUserHistory(String userId){
        return Observable.create(emmiter -> {
            FirebaseFirestore.getInstance().collection(USER_COLLECTION_NAME).document(userId)
                    .collection(HISTORY_COLLECTION_NAME)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "success recovering all history");
                            emmiter.onNext(task.getResult().toObjects(History.class));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            emmiter.onError(task.getException());
                        }
                    });
        });
    }

}
