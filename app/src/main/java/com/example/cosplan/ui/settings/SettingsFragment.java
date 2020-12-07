package com.example.cosplan.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.cosplan.R;


public class SettingsFragment extends Fragment {
    SwitchCompat mEnableDarkMode;
    SharedPreferences mSharedPreferences = null;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);
        mEnableDarkMode = root.findViewById(R.id.Switch_SettingsDarkMode);

        mSharedPreferences = getContext().getSharedPreferences("night", 0);
        boolean NightmodeEnabled = mSharedPreferences.getBoolean("night_mode", false);
        if (NightmodeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            mEnableDarkMode.setChecked(true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        mEnableDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mEnableDarkMode.setChecked(true);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putBoolean("night_mode", true);
                    editor.apply();

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    mEnableDarkMode.setChecked(false);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putBoolean("night_mode", false);
                    editor.apply();
                }
                getActivity().recreate();
            }
        });
        return root;
    }

}