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
import com.google.firebase.firestore.QuerySnapshot;
import com.utfpr.myapplication.ui.modules.tutorial.TutorialItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by lispa on 02/04/2018.
 */

public class FirebaseTutorialManager {

    private final FirebaseDatabase firebaseDatabase;
    private static final String TAG = FirebaseTutorialManager.class.getSimpleName();
    private static final String TUTORIAL_COLLECTION_NAME = "tutorial";

    public FirebaseTutorialManager(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public Observable<List<TutorialItem>> getAll() {
        return Observable.create(emmiter -> {
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List<TutorialItem> list = new ArrayList<>();
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        list.add(messageSnapshot.getValue(TutorialItem.class));
                    }
                    Log.d(TAG, "success recovering tutorial");
                    emmiter.onNext(list);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    emmiter.onError(new Exception(databaseError.getMessage()));
                    Log.e(TAG, "Error getting documents: ", new Exception(databaseError.getMessage()));

                }
            };

            firebaseDatabase.getReference().child(TUTORIAL_COLLECTION_NAME).addListenerForSingleValueEvent(eventListener);

        });
    }

}
