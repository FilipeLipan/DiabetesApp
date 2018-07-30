package com.utfpr.myapplication.data;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.utfpr.myapplication.models.News;

import java.util.List;
import io.reactivex.Observable;

/**
 * Created by lispa on 02/04/2018.
 */

public class FirebaseNewsManager {

    private final FirebaseFirestore firebaseFirestore;
    private static final String TAG = FirebaseNewsManager.class.getSimpleName();
    private static final String NEWS_COLLECTION_NAME = "news";

    public FirebaseNewsManager(FirebaseFirestore firebaseFirestore){
        this.firebaseFirestore = firebaseFirestore;
    }

    public Observable<List<News>> getAll(){
        return Observable.create(emmiter ->
                firebaseFirestore.collection(NEWS_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "success recovering news");
                        emmiter.onNext(task.getResult().toObjects(News.class));
                    } else {
                        Log.d(TAG, "Error getting the news collection: ", task.getException());
                        emmiter.onError(task.getException());
                    }
                }));
    }

}
