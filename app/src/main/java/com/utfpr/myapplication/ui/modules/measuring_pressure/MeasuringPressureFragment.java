package com.utfpr.myapplication.ui.modules.measuring_pressure;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.FragmentMeasuringPressureBinding;
import com.utfpr.myapplication.ui.common.BaseFragment;
import com.utfpr.myapplication.ui.modules.history.measuring_pressure_detail.MeasurePressureDetailActivity;

public class MeasuringPressureFragment extends BaseFragment<MeasuringPressureViewModel, FragmentMeasuringPressureBinding> {

    private SurfaceHolder previewHolder = null;
    private Camera camera = null;
    private WakeLock wakeLock = null;
    private final int CAMERA_PERMISSION = 1556;

    public static MeasuringPressureFragment newInstance(){
        return new MeasuringPressureFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBaseActivity().setTitle(getString(R.string.measuring_pressure));

        getDataBind().restartButton.setOnClickListener(view1 -> {
            getDataBind().tutorialContainer.setVisibility(View.VISIBLE);
            getViewModel().resetProcessing();
            getDataBind().beatPerMinuteTextview.setText(getString(R.string.empty_beat_per_minute));
            getDataBind().restartButton.setVisibility(View.INVISIBLE);
        });


        checkPermissionAndStartCamera();
        observeLiveData();
    }

    private void initCamera(){
        if(getContext() != null) {
            PowerManager pm = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotDimScreen");
        }
        previewHolder = getDataBind().cameraSurfaceview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        wakeLock.acquire(10*60*1000L /*10 minutes*/);
        camera = Camera.open();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            initCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            wakeLock.release();
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private void observeLiveData() {
        getViewModel().getCreatedHistoryIdLiveData().observe(this, historyId -> {
            if(historyId != null){
                MeasurePressureDetailActivity.launchWithId(getContext(), historyId);
            }
        });

        getViewModel().getBeatPerSecondMutableLivedata().observe(this, beatsAvg ->
                getDataBind().beatPerMinuteTextview.setText(String.valueOf(beatsAvg)));

        getViewModel().isProcessingMutableLivedata().observe(this, isProcessing -> {
            if(isProcessing !=null && isProcessing){
                getDataBind().tutorialContainer.setVisibility(View.GONE);
                getDataBind().restartButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private  SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);
            } catch (Throwable t) {
                Log.e("PreviewDemo-", "Exception in setPreviewDisplay()", t);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Camera.Size size = getSmallestPreviewSize(width, height, parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
                Log.d(getFragmentTag(), "Using width=" + size.width + " height=" + size.height);
            }
            camera.setParameters(parameters);
            camera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // Ignore
        }
    };

    private Camera.PreviewCallback previewCallback = (data, cam) ->
            getViewModel().process(data, cam);

    private Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea < resultArea) result = size;
                }
            }
        }

        return result;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.CAMERA)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initCamera();
            }
        }
    }


    private void checkPermissionAndStartCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);
        } else {
            initCamera();
        }
    }

    @Override
    public MeasuringPressureViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(MeasuringPressureViewModel.class);
    }

    @Override
    public String getFragmentTag() {
        return MeasuringPressureFragment.class.getSimpleName();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_measuring_pressure;
    }
}
