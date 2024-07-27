package com.example.tanyai.core.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tanyai.core.models.UserSharedModel;
import com.example.tanyai.util.constants.app.ConstantsApp;

public class UserServices {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public void initilizeUserService(Context context) {
        sharedPreferences = context.getSharedPreferences(ConstantsApp.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public UserSharedModel getUserInfo() {
        UserSharedModel userSharedModel = new UserSharedModel(
                getUserBool(ConstantsApp.IS_FIRST_TIME)
        );
        return userSharedModel;
    }
    public void saveUserInfo(UserSharedModel sharedModel) {
        saveBool(ConstantsApp.IS_FIRST_TIME, sharedModel.isFirstTime());

    }

    private boolean getUserBool(String key) {
        return sharedPreferences.getBoolean(key, true);
    }

    private void saveBool(String key, boolean val) {
        editor.putBoolean(key, val);
        editor.apply();

    }
}
