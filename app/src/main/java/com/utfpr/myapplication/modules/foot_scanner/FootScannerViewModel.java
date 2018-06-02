package com.utfpr.myapplication.modules.foot_scanner;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.WebDetection;
import com.google.api.services.vision.v1.model.WebEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;


/**
 * Created by lispa on 22/04/2018.
 */

public class FootScannerViewModel extends ViewModel {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MutableLiveData<Boolean> scanResult = new MutableLiveData<Boolean>();

    private Vision vision;

    @Inject
    public FootScannerViewModel(Vision vision){
        this.vision = vision;
    }


    public MutableLiveData<Boolean> getScanResult() {
        return scanResult;
    }

    public void startScanning(Bitmap bmp){

        scanBitmap(bmp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        scanResult.setValue(aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO handle error
                        scanResult.setValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private Observable<Boolean> scanBitmap(Bitmap bmp) {
        return Observable.create(emitter -> {

            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bmp.recycle();

                Image inputImage = new Image();
                inputImage.encodeContent(byteArray);


                Feature desiredFeature = new Feature();
                desiredFeature.setType("WEB_DETECTION");

                AnnotateImageRequest request = new AnnotateImageRequest();
                request.setImage(inputImage);
                request.setFeatures(Collections.singletonList(desiredFeature));

                BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();

                batchRequest.setRequests(Collections.singletonList(request));

                BatchAnnotateImagesResponse batchResponse = vision.images().annotate(batchRequest).execute();

                WebDetection results = batchResponse.getResponses().get(0).getWebDetection();

                Boolean verify = false;
                for (WebEntity webEntity : ((ArrayList<WebEntity>) results.get("webEntities"))) {
                    if ((webEntity.getDescription().contains("Diabetic foot") || webEntity.getDescription().contains("Disease") ||
                            webEntity.getDescription().contains("Wound") || webEntity.getDescription().contains("Wound healing"))
                            && Double.valueOf(webEntity.getScore()) > 0.4) {
                        verify = true;
                    }
                }

                if (verify) {
                    emitter.onNext(true);
                } else {
                    emitter.onNext(false);
                }
            } catch (IOException e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
