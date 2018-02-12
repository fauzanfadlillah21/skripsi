/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.prefmanager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class PrefManager_petugas {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "pref_petugas";

    private static final String IS_FIRST_TIME_LAUNCH = "Launch_petugas";

    public PrefManager_petugas(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}