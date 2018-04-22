package com.utfpr.myapplication.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.utfpr.myapplication.modules.tutorial.TutorialItem;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by lispa on 02/04/2018.
 */

public class FirebaseTutorialManager {

    public static final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static final String TAG = FirebaseTutorialManager.class.getSimpleName();

    public static final String TUTORIAL_COLLECTION_NAME = "tutorial";

    public static Observable<List<TutorialItem>> getAll(){
        return Observable.create(emmiter -> {
            firebaseFirestore.collection(TUTORIAL_COLLECTION_NAME)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "success recovering tutorial");
                                emmiter.onNext(task.getResult().toObjects(TutorialItem.class));
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                emmiter.onError(task.getException());
                            }
                        }
                    });

        });
    }

}
