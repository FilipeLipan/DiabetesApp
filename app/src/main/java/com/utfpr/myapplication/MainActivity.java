package com.utfpr.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.utfpr.myapplication.common.BaseActivity;
import com.utfpr.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    public static void launch(Context context){
        Intent intent = new Intent(context, MainActivity.class);
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

        getDataBind().logoutButton.setOnClickListener( v ->{
            FirebaseAuth.getInstance().signOut();
            LoginActivity.launch(MainActivity.this);
            MainActivity.this.finish();
        });
    }
}
