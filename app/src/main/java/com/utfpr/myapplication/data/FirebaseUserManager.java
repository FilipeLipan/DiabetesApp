package com.utfpr.myapplication.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.utfpr.myapplication.data.local.LocalPreferences;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.models.User;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by lispa on 02/04/2018.
 */

public class FirebaseUserManager {

    private static final String TAG = FirebaseUserManager.class.getSimpleName();
    private static final String USER_COLLECTION_NAME = "user";
    private static final String HISTORY_COLLECTION_NAME = "history";
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseDatabase firebaseDatabase;
    private final LocalPreferences localPreferences;

    public FirebaseUserManager(FirebaseFirestore firebaseFirestore, FirebaseDatabase firebaseDatabase, LocalPreferences localPreferences){
        this.firebaseFirestore = firebaseFirestore;
        this.localPreferences = localPreferences;
        this.firebaseDatabase = firebaseDatabase;
    }


    public Observable<User> getUser(String userId){
        return Observable.create(emmiter -> {
            firebaseDatabase.getReference().child(USER_COLLECTION_NAME).child(userId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(TAG, "success recovering user");
                            if(!dataSnapshot.exists()){
                                emmiter.onError(new UserNotFoundException());
                            }else {
                                emmiter.onNext(dataSnapshot.getValue(User.class));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "Error getting documents: ", new Exception(databaseError.getMessage()));
                            emmiter.onError(new Exception(databaseError.getMessage()));
                        }
                    });

//            firebaseFirestore.collection(USER_COLLECTION_NAME).document(userId)
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "success recovering user");
//                            if(!task.getResult().exists()) {
//                                emmiter.onError(new UserNotFoundException());
//                            }else {
//                                emmiter.onNext(task.getResult().toObject(User.class));
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                            emmiter.onError(task.getException());
//                        }
//                    });
        });
    }

    public Completable createUser(String userId, User user){
        return Completable.create(emmiter -> {
            firebaseDatabase.getReference().child(USER_COLLECTION_NAME).child(userId).setValue("sdsdsafds")
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "success creating user");
                            emmiter.onComplete();
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                            emmiter.onError(task.getException());
                        }
                }
            });



//            firebaseFirestore.collection(USER_COLLECTION_NAME)
//                    .document(userId)
//                    .set(user)
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "success recovering user");
//                            emmiter.onComplete();
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                            emmiter.onError(task.getException());
//                        }
//                    });
        });
    }

    public Observable<String> createHistory(String userId, History history){
        localPreferences.saveLastExamDate(Calendar.getInstance().getTimeInMillis());

        return Observable.create(emmiter -> {
            firebaseFirestore.collection(USER_COLLECTION_NAME)
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

    public Observable<History> getUserHistory(String userId, String historyId){
        return Observable.create(emmiter -> {
            firebaseFirestore.collection(USER_COLLECTION_NAME).document(userId)
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

    public Observable<List<History>> getAllUserHistory(String userId){
        return Observable.create(emmiter -> {
            firebaseFirestore.collection(USER_COLLECTION_NAME).document(userId)
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
