package com.example.cosplan.ui.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cosplan.R;


public class testFragment extends Fragment {
    private testViewModel testViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup containder,Bundle savedInstanceState){
        testViewModel= ViewModelProviders.of(this).get(com.example.cosplan.ui.test.testViewModel.class);
        View root=inflater.inflate(R.layout.fragment_test,containder,false);
        final TextView textView=root.findViewById(R.id.text_test);
        testViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}