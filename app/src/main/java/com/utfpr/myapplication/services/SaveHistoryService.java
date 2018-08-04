package com.utfpr.myapplication.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.utfpr.myapplication.data.FirebaseUserManager;
import com.utfpr.myapplication.models.History;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SaveHistoryService extends IntentService {

    private static final String TAG = "SaveHistoryService:";
    public static final String KEY_HISTORY = "key-history";

    public SaveHistoryService() {
        super("SaveHistoryService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null && intent.hasExtra(KEY_HISTORY)){
            saveHistory(intent.getParcelableExtra(KEY_HISTORY));
        }
    }

    public void saveHistory(History history) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String id = firebaseDatabase.getReference().child(FirebaseUserManager.HISTORY_COLLECTION_NAME).child(FirebaseAuth.getInstance().getUid()).push().getKey();
        firebaseDatabase.getReference().child(FirebaseUserManager.HISTORY_COLLECTION_NAME).child(FirebaseAuth.getInstance().getUid()).child(id).setValue(history)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "success creating history");
                        } else {
                            Log.e(TAG, "Error creating history", task.getException());
                        }
                    }
                });
    }

}
