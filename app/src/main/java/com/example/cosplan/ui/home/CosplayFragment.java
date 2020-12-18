package com.example.cosplan.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.data.cosplay.Cosplay;
import com.example.cosplan.data.cosplay.CosplayAdapter;
import com.example.cosplan.data.cosplay.CosplayViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CosplayFragment extends Fragment {
    private CosplayViewModel mCosplayViewModel;
    private AlertDialog.Builder mDialogBuilder;
    private AlertDialog mDialog;


    private EditText mCosplayName, mCosplayStartDate, mCosplayEndDate, mCosplayBudget;
    private ImageView mCosplayImage;

    private DatePickerDialog.OnDateSetListener mStartDateSetListener;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;

    public static final int GALLERY_REQUEST_CODE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cosplay, container, false);
        final CosplayAdapter cosplayAdapter = new CosplayAdapter(requireContext());

        RecyclerView mRecyclerView = root.findViewById(R.id.RecView_Cosplay);
        mRecyclerView.setAdapter(cosplayAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position=viewHolder.getAdapterPosition();
                Cosplay myCosplay=cosplayAdapter.getCosplayAtPosition(position);
                DeleteCosplayDialog(myCosplay);
            }
        });

        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mCosplayViewModel = new ViewModelProvider(this).get(CosplayViewModel.class);
        mCosplayViewModel.getAllConventions().observe(getViewLifecycleOwner(), new Observer<List<Cosplay>>() {
            @Override
            public void onChanged(List<Cosplay> cosplays) {
                cosplayAdapter.setCosplays(cosplays);
            }
        });
        //Add the FAB to go to the add cosplay popup
        FloatingActionButton mFabAddCosplay = root.findViewById(R.id.Fab_CosplayAdd);
        mFabAddCosplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewCosplayDialog();
            }
        });

        return root;
    }


    public void DeleteCosplayDialog(final Cosplay cosplay){
        mDialogBuilder =new AlertDialog.Builder(requireContext());
        final View mDeleteCosplayView=getLayoutInflater().inflate(R.layout.delete,null);

        TextView mDeleteText=mDeleteCosplayView.findViewById(R.id.TextView_DeleteTitle);
        mDeleteText.setText(String.format("%s%s", getString(R.string.ConformationDeleteCosplay), cosplay.mCosplayName));

        Button mBtnDelete,mBtnCancel;
        mBtnCancel=mDeleteCosplayView.findViewById(R.id.Btn_DeleteCancel);
        mBtnDelete=mDeleteCosplayView.findViewById(R.id.Btn_DeleteDelete);

        mDialogBuilder.setView(mDeleteCosplayView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        mBtnDelete.setOnClickListener(new View.OnClickListener()        {
            @Override
            public void onClick(View v) {
               mCosplayViewModel.delete(cosplay);
                Toast.makeText(requireContext(), cosplay.mCosplayName+ " deleted",Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                getActivity().recreate();
            }
        });
    }
    public void createNewCosplayDialog() {
        mDialogBuilder = new AlertDialog.Builder(requireContext());
        final View mCosplayPopUpView = getLayoutInflater().inflate(R.layout.add_cosplay, null);

        mCosplayName = mCosplayPopUpView.findViewById(R.id.EditText_NewCosplayName);
        mCosplayStartDate = mCosplayPopUpView.findViewById(R.id.EditText_NewCosplayBeginDate);
        mCosplayEndDate = mCosplayPopUpView.findViewById(R.id.EditText_NewCosplayEndDate);
        mCosplayBudget = mCosplayPopUpView.findViewById(R.id.EditText_NewCosplayBudget);
        mCosplayImage = mCosplayPopUpView.findViewById(R.id.ImageView_NewCosplayImgPreview);

        Button mChoosePicture = mCosplayPopUpView.findViewById(R.id.Btn_NewCosplayChooseImg);
        Button mCancel = mCosplayPopUpView.findViewById(R.id.Btn_NewCosplayCancel);
        Button mAddNewCosplay = mCosplayPopUpView.findViewById(R.id.Btn_NewCosplayAdd);

        mDialogBuilder.setView(mCosplayPopUpView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        //create dateSelector and add the selected date to the Edit text
        mCosplayStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
             ShowDatePickerDialog(hasFocus,true);
            }
        });
        mStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mCosplayStartDate.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
            }
        };

        //create dateSelector and add the selected date to the Edit text
        mCosplayEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ShowDatePickerDialog(hasFocus,false);
            }
        });

        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mCosplayEndDate.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
            }
        };

        //Cancel. dismiss the popup
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        //Choose the picture from the gallery
        mChoosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.txt_chooseImg_intent)), GALLERY_REQUEST_CODE);

            }
        });

        //Add Cosplay to the database
        mAddNewCosplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCosplayName.getText().toString().equals("")){
                    double mCost;
                    if (!mCosplayBudget.getText().toString().equals("")) {
                        mCost = Double.parseDouble(mCosplayBudget.getText().toString());
                    } else {
                        mCost = 0.0;
                    }
                    Cosplay temp = new Cosplay(0,mCosplayName.getText().toString(),mCosplayStartDate.getText().toString(),mCosplayEndDate.getText().toString(),mCost,mCost,((BitmapDrawable)mCosplayImage.getDrawable()).getBitmap());

                    mCosplayViewModel.insert(temp);
                    mDialog.dismiss();
                }
                else{
                    Toast.makeText(requireContext(), getResources().getString(R.string.FillOutAllFields), Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void ShowDatePickerDialog(boolean hasFocus,boolean IsStartDate) {
        EditText mEditTextDate;
        DatePickerDialog.OnDateSetListener mOnDateSetListener;
        if (IsStartDate) {
            mEditTextDate = mCosplayStartDate;
            mOnDateSetListener=mStartDateSetListener;
        }
        else {
            mEditTextDate = mCosplayEndDate;
            mOnDateSetListener=mEndDateSetListener;
        }
            if (hasFocus) {
            int year;
            int month;
            int day;
            String mTempDate = mEditTextDate.getText().toString().trim();
            if (!checkDateFormat(mTempDate)) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            } else {

                String mDateComplete = mEditTextDate.getText().toString();
                String[] mDate = mDateComplete.split("/");
                day = Integer.parseInt(mDate[0].trim());
                month = Integer.parseInt(mDate[1].trim());
                year = Integer.parseInt(mDate[2].trim());
                month = month - 1;
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mOnDateSetListener, year, month, day);
            datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        }
    }

    public Boolean checkDateFormat(String date){
        if (date == null || !date.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))
            return false;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.parse(date);
            return true;
        }catch (ParseException e){
            return false;
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                Uri imageData = data.getData();
                mCosplayImage.setImageURI(imageData);
            }
        }
    }
}