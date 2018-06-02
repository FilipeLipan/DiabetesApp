package com.utfpr.myapplication.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.utfpr.myapplication.models.User;

/**
 * Created by lispa on 20/05/2018.
 */

public class UserPreferences {

    public static final String USER_NAME = "user-name";
    public static final String USER_PREFERENCES = "user-preferences";
    private Context mContext;
    private SharedPreferences mPreferences;

    public UserPreferences(Context context){
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(USER_PREFERENCES,Context.MODE_PRIVATE);
    }


    public void saveUser(User user){
        mPreferences.edit()
                .putString(USER_NAME, user.name)
                .apply();

    }

    public User getUser(){
        User user = new User();
        user.setName(mPreferences.getString(USER_NAME, ""));

        return user;
    }
}
