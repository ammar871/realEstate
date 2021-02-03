package com.ammarreal.realestates.commen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.ammarreal.realestates.sign.LoginActivity;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class SessionManagment {

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;
    private static final String FILE_NAME = "coursatApp";
    public static final String KEY_USER_FNAME = "userfName";
    public static final String KEY_USER_LNAME = "userlName";
    public static final String KEY_USER_EMAIL = "userEmail";
    public static final String KEY_USER_PHONE = "userPhone";
    public static final String KEY_USER_PASS = "userPass";
    public static final String KEY_IS_LOGIN = "isLogin";

    public SessionManagment(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void saveData(String fName, String lName, String email, String phone,
                         String pass, boolean isLogin) {

        mEditor.putString(KEY_USER_FNAME, fName);
        mEditor.putString(KEY_USER_LNAME, lName);
        mEditor.putString(KEY_USER_EMAIL, email);
        mEditor.putString(KEY_USER_PHONE, phone);
        mEditor.putString(KEY_USER_PASS, pass);
        mEditor.putBoolean(KEY_IS_LOGIN, isLogin);
        mEditor.commit();
    }

    public HashMap<String, String> loadData() {
        HashMap<String, String> userData = new HashMap<>();
        userData.put(KEY_USER_FNAME, mSharedPreferences.getString(KEY_USER_FNAME, ""));
        userData.put(KEY_USER_LNAME, mSharedPreferences.getString(KEY_USER_LNAME, ""));
        userData.put(KEY_USER_EMAIL, mSharedPreferences.getString(KEY_USER_EMAIL, ""));
        userData.put(KEY_USER_PHONE, mSharedPreferences.getString(KEY_USER_PHONE, ""));
        userData.put(KEY_USER_PASS, mSharedPreferences.getString(KEY_USER_PASS, ""));
        return userData;
    }


  public   void logOut() {
        mEditor.clear();
        mEditor.commit();
        Intent mIntent = new Intent(mContext, LoginActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mIntent);
    }


    public boolean isLogin() {
        return mSharedPreferences.getBoolean(KEY_IS_LOGIN, false);
    }
}
