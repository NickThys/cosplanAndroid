package com.example.cosplan.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cosplan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import static java.lang.Integer.getInteger;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;


    private EditText mCosplayName,mCosplayStartDate,mCosplayEndDate,mCosplayBudget;
    private ImageView mCosplayImage;
    private Button mChoosePicture,mCancel,mAddNewCosplay;
    private FloatingActionButton mfabAddCosplay;

    private DatePickerDialog.OnDateSetListener mStartDateSetListener;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;
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
        mCosplayStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year;
                int month;
                int day;
                String mtemp =mCosplayStartDate.getText().toString().trim();
                if (mtemp.matches("")){
                Calendar calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);}
                else {
                String mDateComlete=mCosplayStartDate.getText().toString();
                String [] mDate=mDateComlete.split("/");
                day= Integer.parseInt( mDate[0].trim());
                month=Integer.parseInt(mDate[1].trim());
                year=Integer.parseInt(mDate[2].trim());
                month=month-1;
                }

                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),R.style.Theme_MaterialComponents_Light_Dialog_MinWidth,mStartDateSetListener,year,month,day);
                datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        mStartDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month=month+1;
                mCosplayStartDate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
        mCosplayEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year;
                int month;
                int day;
                String mtemp =mCosplayEndDate.getText().toString().trim();
                if (mtemp.matches("")){
                    Calendar calendar=Calendar.getInstance();
                    year=calendar.get(Calendar.YEAR);
                    month=calendar.get(Calendar.MONTH);
                    day=calendar.get(Calendar.DAY_OF_MONTH);}
                else {
                    String mDateComlete=mCosplayEndDate.getText().toString();
                    String [] mDate=mDateComlete.split("/");
                    day= Integer.parseInt( mDate[0].trim());
                    month=Integer.parseInt(mDate[1].trim());
                    year=Integer.parseInt(mDate[2].trim());
                    month=month-1;
                }

                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),R.style.Theme_MaterialComponents_Light_Dialog_MinWidth,mEndDateSetListener,year,month,day);
                datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        mEndDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month=month+1;
                mCosplayEndDate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}