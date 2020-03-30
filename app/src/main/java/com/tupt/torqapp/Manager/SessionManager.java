package com.tupt.torqapp.Manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.tupt.torqapp.Activity.LoginActivity;
import com.tupt.torqapp.Activity.MainActivity;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String TITLE = "TITLE";
    public static final String F_NAME = "F_NAME";
    public static final String L_NAME = "L_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String ID = "ID";
    public static final String C_ID = "C_ID";
    public static final String R_ID = "R_ID";
    public static final String R_NAME = "R_NAME";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String title, String first_name ,String last_name, String email, String id, String course_id, String role_id, String role_name, String access_token) {

        editor.putBoolean(LOGIN, true);
        editor.putString(TITLE, title);
        editor.putString(F_NAME, first_name);
        editor.putString(L_NAME, last_name);
        editor.putString(EMAIL, email);
        editor.putString(ID,id);
        editor.putString(C_ID, course_id);
        editor.putString(R_ID, role_id);
        editor.putString(R_NAME, role_name);
        editor.putString(ACCESS_TOKEN, access_token);
        editor.apply();
    }
    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }
    public void checkLogin() {

        if (!this.isLogin()) {
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(TITLE, sharedPreferences.getString(TITLE, null));
        user.put(F_NAME, sharedPreferences.getString(F_NAME, null));
        user.put(L_NAME, sharedPreferences.getString(L_NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(C_ID, sharedPreferences.getString(C_ID, null));
        user.put(R_ID, sharedPreferences.getString(R_ID, null));
        user.put(R_NAME, sharedPreferences.getString(R_NAME, null));
        user.put(ACCESS_TOKEN, sharedPreferences.getString(ACCESS_TOKEN, null));
        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }

}
