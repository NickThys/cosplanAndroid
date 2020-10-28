package com.example.cosplan.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cosplan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText mCosplayName,mCosplayStartDate,mCosplayEndDate,mCosplayBudget;
    private ImageView mCosplayImage;
    private Button mChoosePicture,mCancel,mAddNewCosplay;
    private FloatingActionButton mfabAddCosplay;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });

        mfabAddCosplay=root.findViewById(R.id.fabAddCosplay);
        mfabAddCosplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewCosplayDialog();
            }
        });

        return root;
    }

    public void createNewCosplayDialog(){
        dialogBuilder=new AlertDialog.Builder(requireContext());
        final View cosplayPopUpView=getLayoutInflater().inflate(R.layout.add_cosplay,null);
        mCosplayName=cosplayPopUpView.findViewById(R.id.EditTextCosplayName);
        mCosplayStartDate=cosplayPopUpView.findViewById(R.id.editTextBeginDate);
        mCosplayEndDate=cosplayPopUpView.findViewById(R.id.editTextEndDate);
        mCosplayBudget=cosplayPopUpView.findViewById(R.id.editTextBudget);
        mCosplayImage=cosplayPopUpView.findViewById(R.id.imageViewCosplayImgPreview);
        mChoosePicture=cosplayPopUpView.findViewById(R.id.btnChooseCosplayImg);
        mCancel=cosplayPopUpView.findViewById(R.id.buttonCancel);
        mAddNewCosplay=cosplayPopUpView.findViewById(R.id.buttonAddCosplay);

        dialogBuilder.setView(cosplayPopUpView);
        dialog=dialogBuilder.create();
        dialog.show();

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}