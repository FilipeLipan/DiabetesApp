package com.utfpr.myapplication.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.utfpr.myapplication.models.User;

/**
 * Created by lispa on 20/05/2018.
 */

public class UserPreferences {

    private static final String USER_NAME = "user-name";
    private static final String USER_ID = "user-id";
    private static final String USER_PREFERENCES = "user-preferences";
    private Context mContext;
    private SharedPreferences mPreferences;

    public UserPreferences(Context context) {
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }


    public void saveUser(String userId, User user) {
        mPreferences.edit()
                .putString(USER_ID, userId)
                .putString(USER_NAME, user.getName())
                .apply();

    }

    public User getUser() {
        return new User()
                .setName(mPreferences.getString(USER_NAME, ""))
                .setId(mPreferences.getString(USER_ID, ""));

    }
}
