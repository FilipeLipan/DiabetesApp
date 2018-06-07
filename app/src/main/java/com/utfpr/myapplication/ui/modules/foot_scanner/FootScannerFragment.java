package com.utfpr.myapplication.ui.modules.foot_scanner;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.FragmentFootScannerBinding;
import com.utfpr.myapplication.ui.common.BaseFragment;


public class FootScannerFragment extends BaseFragment<FootScannerViewModel, FragmentFootScannerBinding> {

    private final int CAMERA_PERMISSION = 1556;
    public static final int PICK_USER_PROFILE_IMAGE = 1000;

    private Bitmap mBitmap;
    private Uri mUri;

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

        getDataBind().progressbar.setVisibility(View.INVISIBLE);

        observeViewModel();
        setUpClickListeners();
    }

    private void observeViewModel(){
        getViewModel().getScanResult().observe(this, observer -> {
            if (observer != null) {
                if (observer) {
                    getDataBind().progressbar.setVisibility(View.INVISIBLE);
                    getDataBind().resultTextview.setText("Ferida detectada");
                } else {
                    getDataBind().progressbar.setVisibility(View.INVISIBLE);
                    getDataBind().resultTextview.setText("Ferida não detectada");
                }
            }
        });
    }

    private void setUpClickListeners(){
        getDataBind().scanButton.setOnClickListener(v -> {
            getDataBind().resultTextview.setText("");
            if(mBitmap != null){
                getDataBind().progressbar.setVisibility(View.VISIBLE);
                getViewModel().startScanning(mBitmap);
            }
        });

        getDataBind().clickToTakePictureInclude.setOnClickListener(view1 -> checkPermissionAndStartPicture());
        getDataBind().cropImageView.setOnCropImageCompleteListener((view1, result) -> mBitmap = result.getBitmap());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                mUri = result.getUri();
                getDataBind().cropImageView.setImageUriAsync(mUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getContext(), R.string.cant_load_photo, Toast.LENGTH_SHORT).show();
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
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);
        } else {
            startCameraActivity();
        }
    }

    private void startCameraActivity() {
        CropImage.activity()
                .start(getContext(), this);
    }


}
