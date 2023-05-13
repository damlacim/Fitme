
package com.damlacim.fitme.data.local.pref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Damla Cim on 24.04.2023
 */
public class UserPreferences {

    private static final String PREF_NAME = "UserPref";
    private static final String KEY_IS_USER_LOGGED_IN = "isUserLoggedIn";

    private final SharedPreferences mPrefs;

    public UserPreferences(Context context) {
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isUserLoggedIn() {
        return mPrefs.getBoolean(KEY_IS_USER_LOGGED_IN, false);
    }

    public void setUserLoggedIn(boolean isUserLoggedIn) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(KEY_IS_USER_LOGGED_IN, isUserLoggedIn);
        editor.apply();
    }
}