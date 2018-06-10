package com.utfpr.myapplication.ui.modules.foot_scanner;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Toast;

import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.FragmentFootScannerBinding;
import com.utfpr.myapplication.ui.common.BaseFragment;
import com.utfpr.myapplication.utils.ImagePicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FootScannerFragment extends BaseFragment<FootScannerViewModel, FragmentFootScannerBinding> {

    private final int CAMERA_PERMISSION = 1556;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    private String mCurrentPhotoPath;

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
            if(mCurrentPhotoPath != null){
                getDataBind().progressbar.setVisibility(View.VISIBLE);
                getViewModel().startScanning(ImagePicker.getImageBitmap(getContext(), mCurrentPhotoPath));
            }
        });

        getDataBind().clickToTakePictureInclude.setOnClickListener(view1 -> checkPermissionAndStartPicture());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            Bundle extras = data.getExtras();
//            mBitmap = (Bitmap) extras.get("data");
            setPic();
        }else {
            Toast.makeText(getActivity(), "Image Capture Failed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_PERMISSION){
            if (permissions[0].equals(Manifest.permission.CAMERA)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        }
    }


    private void checkPermissionAndStartPicture() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.utfpr.myapplication.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic() {
        getDataBind().photoImageView.setImageBitmap(ImagePicker.getImageBitmap(getContext(), mCurrentPhotoPath));
    }

}
