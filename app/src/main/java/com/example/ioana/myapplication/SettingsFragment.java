package com.example.ioana.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by Ioana on 11/17/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_visualiser);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();


    }
}
