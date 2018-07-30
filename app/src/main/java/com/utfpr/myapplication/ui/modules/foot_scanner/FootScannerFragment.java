package com.utfpr.myapplication.ui.modules.foot_scanner;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.FragmentFootScannerBinding;
import com.utfpr.myapplication.ui.common.BaseFragment;
import com.utfpr.myapplication.ui.modules.history.measuring_pressure_detail.MeasurePressureDetailActivity;
import com.utfpr.myapplication.utils.ImagePicker;

public class FootScannerFragment extends BaseFragment<FootScannerViewModel, FragmentFootScannerBinding> {

    private final int CAMERA_PERMISSION = 1556;
    public static final int PICK_USER_PROFILE_IMAGE = 1000;

    public static FootScannerFragment newInstance() {
        return new FootScannerFragment();
    }

    @Override
    public FootScannerViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(FootScannerViewModel.class);
    }

    @Override
    public String getFragmentTag() {
        return "FootScannerFragment";
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_foot_scanner;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBaseActivity().setTitle(getString(R.string.foot_scan));

        observeViewModel();
        setUpClickListeners();
    }

    private void observeViewModel(){
        getViewModel().getScanResult().observe(this, observer -> {
            if (observer != null) {
                if (observer) {
                    getDataBind().resultTextview.setText("Ferida detectada");
                } else {
                    getDataBind().resultTextview.setText("Ferida não detectada");
                }
            }
        });

    }

    private void setUpClickListeners(){
        getDataBind().clickToTakePictureInclude.setOnClickListener(v -> checkPermissionAndStartPicture());
        getDataBind().takePhotoButton.setOnClickListener(v -> checkPermissionAndStartPicture());
        getDataBind().loadingInclude.getRootView().setOnClickListener(view -> {});
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                if (resultCode == Activity.RESULT_OK) {
                        if (requestCode == PICK_USER_PROFILE_IMAGE) {

                            getDataBind().clickToTakePictureInclude.setVisibility(View.INVISIBLE);
                            Bitmap bitmap = ImagePicker.getImageFromResult(getContext(), resultCode, data);
                                    getViewModel().startScanning(bitmap);
                                    getDataBind().photoImageView.setImageBitmap(bitmap);
                          }
                   }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_PERMISSION){
            if (permissions[0].equals(Manifest.permission.CAMERA)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraActivity();
            }
        }
    }


    private void checkPermissionAndStartPicture() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);
        } else {
            startCameraActivity();
        }
    }


    public void startCameraActivity() {
              Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
              if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
                      startActivityForResult(cameraIntent, PICK_USER_PROFILE_IMAGE);
                 }
            }

    @Override
    public void showLoading() {
        getDataBind().loadingInclude.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        getDataBind().loadingInclude.setVisibility(View.GONE);
    }
}
