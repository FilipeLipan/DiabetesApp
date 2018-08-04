package com.utfpr.myapplication.ui.modules.measuring_pressure;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.hardware.Camera;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.utfpr.myapplication.data.FirebaseUserManager;
import com.utfpr.myapplication.livedata_resources.SingleLiveEvent;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.models.HistoryChartEntry;
import com.utfpr.myapplication.ui.common.BaseViewModel;
import com.utfpr.myapplication.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MeasuringPressureViewModel extends BaseViewModel {
    
    private static final String TAG = "MeasuringPressureVM";

    private SingleLiveEvent<String> createdHistoryIdLiveData = new SingleLiveEvent<>();
    private MutableLiveData<String> beatPerSecondMutableLivedata = new MutableLiveData<>();
    private MutableLiveData<Boolean> isProcessingMutableLivedata = new MutableLiveData<>();

    private final AtomicBoolean processing = new AtomicBoolean(false);
    private final int AVERAGE_ARRAY_SIZE = 4;
    private final int BEATS_ARRAY_SIZE = 3;
    private final int BEAT_LOG_MAXIMUM_ARRAY_SIZE = 60;

    private int averageIndex = 0;
    private int[] averageArray = new int[AVERAGE_ARRAY_SIZE];

    private int beatsIndex = 0;
    private int[] beatsArray = new int[BEATS_ARRAY_SIZE];
    private double beats = 0;
    private long startTime = 0;
    private List<Long> beatLog = new ArrayList<>();
    private final FirebaseUserManager firebaseUserManager;

    @Inject
    public MeasuringPressureViewModel(FirebaseUserManager firebaseUserManager){
        this.firebaseUserManager = firebaseUserManager;
        isProcessingMutableLivedata.setValue(false);
    }

    public SingleLiveEvent<String> getCreatedHistoryIdLiveData() {
        return createdHistoryIdLiveData;
    }

    public LiveData<String> getBeatPerSecondMutableLivedata() {
        return beatPerSecondMutableLivedata;
    }

    public LiveData<Boolean> isProcessingMutableLivedata() {
        return isProcessingMutableLivedata;
    }

    public void resetProcessing(){
        startTime = System.currentTimeMillis();
        averageIndex = 0;
        int[] averageArray = new int[AVERAGE_ARRAY_SIZE];
        beatsIndex = 0;
        beatsArray = new int[BEATS_ARRAY_SIZE];
        beats = 0;
        startTime = 0;
        beatLog = new ArrayList<>();
    }
    
    public void process(byte[] data, Camera cam){
        if(startTime == 0) {
            startTime = System.currentTimeMillis();
        }

        if (data == null) throw new NullPointerException();
        Camera.Size size = cam.getParameters().getPreviewSize();
        if (size == null) throw new NullPointerException();

        if (!processing.compareAndSet(false, true)) return;

        int width = size.width;
        int height = size.height;

        int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), width, height);
        Log.i(TAG, "imgAvg="+imgAvg);
        if (imgAvg == 0 || imgAvg == 255) {
            processing.set(false);
            return;
        }

        int averageArrayAvg = 0;
        int averageArrayCnt = 0;
        for (int anAverageArray : averageArray) {
            if (anAverageArray > 0) {
                averageArrayAvg += anAverageArray;
                averageArrayCnt++;
            }
        }

        int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
        if (imgAvg < rollingAverage) {
            beats++;
            beatLog.add(System.currentTimeMillis());
            Log.d(TAG, "BEAT!! beats="+beats);
            if(beatLog.size() >= BEAT_LOG_MAXIMUM_ARRAY_SIZE){
                isProcessingMutableLivedata.setValue(false);
            }
        }

        if (averageIndex == AVERAGE_ARRAY_SIZE) averageIndex = 0;
        averageArray[averageIndex] = imgAvg;
        averageIndex++;

        long endTime = System.currentTimeMillis();
        double totalTimeInSecs = (endTime - startTime) / 1000d;
        if (totalTimeInSecs >= 10) {
            isProcessingMutableLivedata.setValue(true);
            double bps = (beats / totalTimeInSecs);
            int dpm = (int) (bps * 60d);
            if (dpm < 30 || dpm > 180) {
                startTime = System.currentTimeMillis();
                beats = 0;
                processing.set(false);
                return;
            }

            Log.d(TAG, "totalTimeInSecs="+totalTimeInSecs+" beats="+beats);

            if (beatsIndex == BEATS_ARRAY_SIZE) beatsIndex = 0;
            beatsArray[beatsIndex] = dpm;
            beatsIndex++;

            int beatsArrayAvg = 0;
            int beatsArrayCnt = 0;
            for (int aBeatsArray : beatsArray) {
                if (aBeatsArray > 0) {
                    beatsArrayAvg += aBeatsArray;
                    beatsArrayCnt++;
                }
            }
            int beatsAvg = (beatsArrayAvg / beatsArrayCnt);
            beatPerSecondMutableLivedata.setValue(String.valueOf(beatsAvg));
            startTime = System.currentTimeMillis();
            beats = 0;
        }



        processing.set(false);
    }

    private boolean isAtRisk(){
        //TODO make logic to check if user is at risk
        return true;
    }

    private void createHistory(String date){

        ArrayList<HistoryChartEntry> entries = new ArrayList<>();

        int size = beatLog.size();
        for (int i = 0; i < size; i++) {
            if(i != 0){
                entries.add(new HistoryChartEntry().setX((Long) (beatLog.get(i) - beatLog.get(i - 1))).setY((long)i));
            }else {
                entries.add(new HistoryChartEntry().setX(beatLog.get(i)).setY((long) i));
            }
        }

        History history = new History()
                .setCreatedAt(date)
                .setResult(isAtRisk() ? StringUtils.RESULT_DETECTED_TYPE : StringUtils.RESULT_NORMAL_DETECTED_TYPE)
                .setType(StringUtils.HEART_BEAT_TYPE)
                .setEntries(entries);

        addDisposable(firebaseUserManager.createHistory(FirebaseAuth.getInstance().getUid(), history)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String historyId) {
                        createdHistoryIdLiveData.setValue(historyId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e);
                    }

                    @Override
                    public void onComplete() {
                        //ignore
                    }
                }));
    }
}
