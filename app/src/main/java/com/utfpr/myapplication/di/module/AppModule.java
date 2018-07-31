package com.utfpr.myapplication.di.module;

import android.app.Application;
import android.content.Context;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.utfpr.myapplication.BuildConfig;
import com.utfpr.myapplication.data.FirebaseNewsManager;
import com.utfpr.myapplication.data.FirebaseTutorialManager;
import com.utfpr.myapplication.data.FirebaseUserManager;
import com.utfpr.myapplication.data.local.LocalPreferences;
import com.utfpr.myapplication.data.local.UserPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

/**
 * Created by lispa on 31/03/2018.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }


    @Reusable
    @Provides
    Vision provideGoogleVisionApi() {
        Vision.Builder visionBuilder = new Vision.Builder(
                new NetHttpTransport(),
                new AndroidJsonFactory(),
                null);

        visionBuilder.setVisionRequestInitializer(new VisionRequestInitializer(BuildConfig.VISION_API_KEY));
        return visionBuilder.build();
    }


    @Singleton
    @Provides
    UserPreferences provideUserPreferences(Context context) {
        return new UserPreferences(context);
    }

    @Singleton
    @Provides
    LocalPreferences provideLocalPreferences(Context context) {
        return new LocalPreferences(context);
    }

    @Reusable
    @Provides
    FirebaseFirestore provideFirebase(){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);
        return firebaseFirestore;
    }

    @Singleton
    @Provides
    FirebaseNewsManager provideFirebaseNewsManager(FirebaseFirestore firebaseFirestore){
        return new FirebaseNewsManager(firebaseFirestore);
    }

    @Singleton
    @Provides
    FirebaseTutorialManager provideFirebaseTutorialManager(FirebaseFirestore firebaseFirestore){
        return new FirebaseTutorialManager(firebaseFirestore);
    }

    @Singleton
    @Provides
    FirebaseUserManager provideFirebaseUserManager(FirebaseFirestore firebaseFirestore, LocalPreferences localPreferences){
        return new FirebaseUserManager(firebaseFirestore, localPreferences);
    }
}
