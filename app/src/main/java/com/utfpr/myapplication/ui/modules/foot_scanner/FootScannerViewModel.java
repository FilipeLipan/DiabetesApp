package com.utfpr.myapplication.ui.modules.foot_scanner;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.WebDetection;
import com.google.api.services.vision.v1.model.WebEntity;
import com.utfpr.myapplication.ui.common.BaseViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lispa on 22/04/2018.
 */

public class FootScannerViewModel extends BaseViewModel {

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

    public void startScanning(Bitmap bitmap){
        showLoading();

        compositeDisposable.clear();
        compositeDisposable.dispose();

            scanBitmap(bitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        hideLoading();
                        scanResult.setValue(aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        scanResult.setValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private Observable<Boolean> scanBitmap(Bitmap bitmap) {
        return Observable.create(emitter -> {

            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bitmap.recycle();

                Image inputImage = new Image();
                inputImage.encodeContent(byteArray);


                Feature desiredFeature = new Feature();
                desiredFeature.setType("WEB_DETECTION");
                desiredFeature.setMaxResults(50);

                AnnotateImageRequest request = new AnnotateImageRequest();
                request.setImage(inputImage);
                request.setFeatures(Collections.singletonList(desiredFeature));

                BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();

                batchRequest.setRequests(Collections.singletonList(request));

                BatchAnnotateImagesResponse batchResponse = vision.images().annotate(batchRequest).execute();

                WebDetection results = batchResponse.getResponses().get(0).getWebDetection();

                Boolean verify = false;
                for (WebEntity webEntity : ((ArrayList<WebEntity>) results.get("webEntities"))) {
                    if ((webEntity.getDescription().contains("Diabetic foot") || webEntity.getDescription().contains("Disease") || webEntity.getDescription().contains("Injury") ||
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
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }


}
