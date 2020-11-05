package com.example.cosplan.ui.cosplay_screen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cosplan.R;

import com.example.cosplan.data.Coplay.Cosplay;

public class cosplayScreen extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.cosplay_header, container, false);
        final Cosplay temp= cosplayScreenArgs.fromBundle(getArguments()).getCurrentCosplay();
        TextView mName,mEndDate,mPercentage,mBudget;
        ImageView mImage;
        mName=v.findViewById(R.id.CosScreenName);
        mEndDate=v.findViewById(R.id.CosScreenEndDate);
        mPercentage=v.findViewById(R.id.CosScreenPercentage);
        mBudget=v.findViewById(R.id.CosScreenBudget);
        mImage=v.findViewById(R.id.CosScreenImg);
        mName.setText(temp.mCosplayName);
        mEndDate.setText(temp.mCosplayEndDate);
        mBudget.setText(Double.toString(temp.mCosplayBudget));
        mImage.setImageBitmap(temp.mCosplayIMG);
        mPercentage.setText("% complete");

        return v;
    }
}