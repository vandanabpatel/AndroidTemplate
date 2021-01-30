package com.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.model.object.UserDataModel;
import com.android.ui.App;

public class PreferenceUtils {
    private static String TAG = "PreferenceUtils";
    private static PreferenceUtils instance;

    private String PREFERENCE_APP = Utility.getPackageName(App.getAppContext()) + "_app";
    private String PREFERENCE_LOCALE = Utility.getPackageName(App.getAppContext());

    // for app
    private String preference_isTutorial = "isTutorial";
    private String preference_isLoggedIn = "isLoggedIn";
    private String preference_authToken = "authToken";
    private String preference_username = "username";
    private String preference_password = "password";
    private String preference_urlETag = "urlEtag";
    private String preference_badge = "badge";

    private String preference_schoolModel = "schoolModel ";
    private String preference_addressModel = "addressModel ";
    private String preference_currentAddressModel = "currentAddressModel";
    private String preference_userModel = "userModel";

    // shared preferences
    private SharedPreferences sp_app;
    private SharedPreferences sp_locale;

    private PreferenceUtils() {
        sp_app = App.getAppContext().getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE);
        sp_locale = App.getAppContext().getSharedPreferences(PREFERENCE_LOCALE, Context.MODE_PRIVATE);
    }

    public static PreferenceUtils getInstance() {
        if (instance == null) {
            instance = new PreferenceUtils();
        }
        return instance;
    }

    /**
     * clear local storage
     */
    public void clearData() {
        Debugger.logE(TAG, "clearData");
        sp_locale.edit().clear().commit();
    }

    /**
     * store data into local
     */
    // store - session token
    public String getAuthToken() {
        return sp_locale.getString(preference_authToken, "");
    }

    public void setAuthToken(String token) {
        sp_locale.edit().putString(preference_authToken, token).commit();
    }

    // store - is user logged in or not
    public boolean isLoggedIn() {
        return sp_locale.getBoolean(preference_isLoggedIn, false);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        sp_locale.edit().putBoolean(preference_isLoggedIn, isLoggedIn).commit();
    }

    // store - user credential
    public String getUsername() {
        return sp_locale.getString(preference_username, "");
    }

    public String getPassword() {
        return sp_locale.getString(preference_password, "");
    }

    public void setUserCredential(String username, String password) {
        sp_locale.edit().putString(preference_username, username)
                .putString(preference_password, password).commit();
    }

    // store - logged in user's data
    public UserDataModel getUser() {
        return (UserDataModel) Utility.jsonToPojo(sp_locale.getString(preference_userModel, null), UserDataModel.class);
    }

    public void setUser(UserDataModel object) {
        sp_locale.edit().putString(preference_userModel, Utility.pojoToJson(object)).commit();
    }

    // school selection
    /*public SchoolModel getSchool() {
        return (SchoolModel) Utility.jsonToPojo(sp_locale.getString(preference_schoolModel, null), SchoolModel.class);
    }

    public void setSchool(SchoolModel object) {
        sp_locale.edit().putString(preference_schoolModel, Utility.pojoToJson(object)).commit();
    }*/
}