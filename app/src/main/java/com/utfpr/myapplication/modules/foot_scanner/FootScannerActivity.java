package com.utfpr.myapplication.modules.foot_scanner;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import  android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.utfpr.myapplication.R;
import com.utfpr.myapplication.common.BaseActivity;
import com.utfpr.myapplication.databinding.ActivityFootScannerBinding;
import com.utfpr.myapplication.utils.ImagePicker;

public class FootScannerActivity extends BaseActivity<FootScannerViewModel, ActivityFootScannerBinding> {

    public static void launch(Context context){
        Intent intent = new Intent(context, FootScannerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public FootScannerViewModel getViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(FootScannerViewModel.class);
    }

    @Override
    public Integer getActivityLayout() {
        return R.layout.activity_foot_scanner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataBind().progressbar.setVisibility(View.INVISIBLE);

        getViewModel().getScanResult().observe(this, observer -> {
            if(observer != null){
                if(observer){
                    getDataBind().progressbar.setVisibility(View.INVISIBLE);
                    getDataBind().resultTextview.setText("Ferida detectada");
                }else {
                    getDataBind().progressbar.setVisibility(View.INVISIBLE);
                    getDataBind().resultTextview.setText("Ferida não detectada");
                }
            }
        });

        getDataBind().scanButton.setOnClickListener(view -> {
            getDataBind().resultTextview.setText("");
            startCameraActivity();
        });
    }

    public static final int PICK_USER_PROFILE_IMAGE = 1000;

    public void startCameraActivity() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(cameraIntent, PICK_USER_PROFILE_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_USER_PROFILE_IMAGE) {
                getDataBind().progressbar.setVisibility(View.VISIBLE);
                    getViewModel().startScanning(ImagePicker.getImageFromResult(this, resultCode, data));
            }
        }
    }
}
