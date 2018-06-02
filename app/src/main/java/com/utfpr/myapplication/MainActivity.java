package com.utfpr.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.utfpr.myapplication.common.BaseActivity;
import com.utfpr.myapplication.databinding.ActivityMainBinding;
import com.utfpr.myapplication.modules.foot_scanner.FootScannerActivity;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    public static void launch(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void launchAndClearTop(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public MainViewModel getViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(MainViewModel.class);
    }

    @Override
    public Integer getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataBind().scanDemoButton.setOnClickListener(view -> {
            FootScannerActivity.launch(this);});

        getDataBind().logoutButton.setOnClickListener( v ->{
            FirebaseAuth.getInstance().signOut();
            LoginActivity.launch(MainActivity.this);
            MainActivity.this.finish();
        });

    }
}
