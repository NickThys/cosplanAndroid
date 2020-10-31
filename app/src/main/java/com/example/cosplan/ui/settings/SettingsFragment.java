package com.example.cosplan.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.cosplan.MainActivity;
import com.example.cosplan.R;
import com.example.cosplan.data.Coplay.Cosplay;
import com.example.cosplan.ui.home.CosplayFragment;


public class SettingsFragment extends Fragment {
    SwitchCompat enableDarkMode, enableCompactMode;
    SharedPreferences sharedPreferences = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);
        enableDarkMode = root.findViewById(R.id.EnableDarkMode);
        enableCompactMode = root.findViewById(R.id.EnableCompactMode);

        sharedPreferences = getContext().getSharedPreferences("night", 0);
        boolean NightmodeEnabled = sharedPreferences.getBoolean("night_mode", false);
        if (NightmodeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            enableDarkMode.setChecked(true);
        }
        enableDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    enableDarkMode.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", true);
                    getActivity().recreate();
                    editor.apply();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    enableDarkMode.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", false);
                    getActivity().recreate();
                    editor.apply();
                }

            }
        });

        // creating the the settings for switching between normal & compact mode
        sharedPreferences=getContext().getSharedPreferences("compact",0);
        boolean CompactModeEnabled=sharedPreferences.getBoolean("compact_mode",false);
        final CosplayFragment temp= new CosplayFragment();
        if (CompactModeEnabled){

            temp.setIsCompactModeEnabled(CompactModeEnabled);
            enableCompactMode.setChecked(true);
        }
        enableCompactMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    temp.setIsCompactModeEnabled(true);
                    enableCompactMode.setChecked(true);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("compact_mode",true);
                    getActivity().recreate();
                    editor.apply();
                }
                else {
                    temp.setIsCompactModeEnabled(false);
                    enableCompactMode.setChecked(false);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("compact_mode",false);
                    getActivity().recreate();
                    editor.apply();
                }
            }
        });

        return root;
    }

}