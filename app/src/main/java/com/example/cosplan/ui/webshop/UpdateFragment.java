package com.example.cosplan.ui.webshop;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cosplan.R;
import com.example.cosplan.data.webshop.Webshop;
import com.example.cosplan.data.webshop.WebshopViewModel;


public class UpdateFragment extends Fragment {

    private WebshopViewModel mWebshopViewModel ;

    public UpdateFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_update, container, false);
        mWebshopViewModel= new ViewModelProvider(this).get(WebshopViewModel.class);
        Webshop temp=UpdateFragmentArgs.fromBundle(getArguments()).getCurrentWebshop();
        TextView mName,mLink;
        mName=view.findViewById(R.id.UpdateWebsiteName);
        mLink=view.findViewById(R.id.UpdateWebsiteLink);
        mName.setText(temp.mSiteName);
        mLink.setText(temp.mSiteLink);
        return view;
    }



}