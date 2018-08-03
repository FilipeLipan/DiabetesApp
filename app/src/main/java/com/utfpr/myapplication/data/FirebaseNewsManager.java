package com.utfpr.myapplication.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.utfpr.myapplication.models.News;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by lispa on 02/04/2018.
 */

public class FirebaseNewsManager {

    private final FirebaseDatabase firebaseDatabase;
    private static final String TAG = FirebaseNewsManager.class.getSimpleName();
    private static final String NEWS_COLLECTION_NAME = "news";

    public FirebaseNewsManager(FirebaseDatabase firebaseDatabase){
        this.firebaseDatabase = firebaseDatabase;
    }


    public Observable<List<News>> getAll(){
        return Observable.create(emmiter -> {

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<News> list = new ArrayList<>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    list.add(messageSnapshot.getValue(News.class));
                }
                Log.d(TAG, "success recovering news");
                emmiter.onNext(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                emmiter.onError(new Exception(databaseError.getMessage()));
            }
        };

        firebaseDatabase.getReference().child(NEWS_COLLECTION_NAME).addListenerForSingleValueEvent(eventListener);
        });
    }

}
