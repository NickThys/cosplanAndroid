package com.example.cosplan.ui.webshop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cosplan.R;
import com.example.cosplan.data.webshop.WebshopViewModel;


public class updateFragment extends Fragment {

    private WebshopViewModel mWebshopViewModel ;

    public updateFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_update, container, false);
        mWebshopViewModel= new ViewModelProvider(this).get(WebshopViewModel.class);
        Button btnDelete=view.findViewById(R.id.btn_DeleteFromDb);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteUser(v);
            }
        });

        return view;
    }

    public void DeleteUser(View view) {
    }
}