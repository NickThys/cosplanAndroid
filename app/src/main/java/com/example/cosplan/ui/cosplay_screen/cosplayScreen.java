package com.example.cosplan.ui.cosplay_screen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cosplan.R;

import com.example.cosplan.data.Coplay.Cosplay;
import com.example.cosplan.data.Coplay.CosplayAdapter;
import com.example.cosplan.data.Coplay.CosplayViewModel;

import java.util.Calendar;

public class cosplayScreen extends Fragment {
    private CosplayViewModel cosplayViewModel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private CosplayAdapter cosplayAdapter;

    TextView mName,mStartDate,mEndDate,mPercentage,mBudget;
    ImageView mImage;
    ImageButton mUpdateCosplay;
    private EditText mCosplayName, mCosplayStartDate, mCosplayEndDate, mCosplayBudget;

    private ImageView mCosplayImage;
    private Button mChoosePicture, mCancel, mUpdateCosplays;

    private DatePickerDialog.OnDateSetListener mStartDateSetListener;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;
    public static final int GALLERY_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.cosplay_header, container, false);
        cosplayViewModel=new ViewModelProvider(this).get(CosplayViewModel.class);
        final Cosplay tempCosplay= cosplayScreenArgs.fromBundle(getArguments()).getCurrentCosplay();
        cosplayAdapter=new CosplayAdapter(requireContext());
        mName=v.findViewById(R.id.CosScreenName);
        mEndDate=v.findViewById(R.id.CosScreenEndDate);
        mPercentage=v.findViewById(R.id.CosScreenPercentage);
        mBudget=v.findViewById(R.id.CosScreenBudget);
        mImage=v.findViewById(R.id.CosScreenImg);
        mUpdateCosplay=v.findViewById(R.id.CosScreenUpdate);

        mName.setText(tempCosplay.mCosplayName);
        mEndDate.setText(tempCosplay.mCosplayEndDate);
        mBudget.setText(Double.toString(tempCosplay.mCosplayBudget));
        mImage.setImageBitmap(tempCosplay.mCosplayIMG);
        mPercentage.setText("% complete");

        mUpdateCosplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCosplayDialog(tempCosplay);
            }
        });

        return v;
    }
    public void UpdateCosplayDialog(final Cosplay cosplay){
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View cosplayPopUpView = getLayoutInflater().inflate(R.layout.add_cosplay, null);
        mCosplayName = cosplayPopUpView.findViewById(R.id.EditTextCosplayName);
        mCosplayStartDate = cosplayPopUpView.findViewById(R.id.editTextBeginDate);
        mCosplayEndDate = cosplayPopUpView.findViewById(R.id.editTextEndDate);
        mCosplayBudget = cosplayPopUpView.findViewById(R.id.editTextBudget);
        mCosplayImage = cosplayPopUpView.findViewById(R.id.imageViewCosplayImgPreview);
        mChoosePicture = cosplayPopUpView.findViewById(R.id.btnChooseCosplayImg);
        mCancel = cosplayPopUpView.findViewById(R.id.buttonCancel);
        mUpdateCosplays = cosplayPopUpView.findViewById(R.id.buttonAddCosplay);
        dialogBuilder.setView(cosplayPopUpView);

        mUpdateCosplays.setText("Update Cosplay");

        mCosplayName.setText(cosplay.mCosplayName);
        mCosplayStartDate.setText(cosplay.mCosplayStartDate);
        mCosplayEndDate.setText(cosplay.mCosplayEndDate);
        mCosplayBudget.setText(Double.toString(cosplay.mCosplayBudget));
        mCosplayImage.setImageBitmap(cosplay.mCosplayIMG);

        dialog = dialogBuilder.create();
        dialog.show();

        //create dateSelector and add the selected date to the Edit text
        mCosplayStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    int year;
                    int month;
                    int day;
                    String mtemp = mCosplayStartDate.getText().toString().trim();
                    if (mtemp.matches("")) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        String mDateComlete = mCosplayStartDate.getText().toString();
                        String[] mDate = mDateComlete.split("/");
                        day = Integer.parseInt(mDate[0].trim());
                        month = Integer.parseInt(mDate[1].trim());
                        year = Integer.parseInt(mDate[2].trim());
                        month = month - 1;
                    }

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mStartDateSetListener, year, month, day);
                    datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            }
        });
        mStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mCosplayStartDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        //create dateSelector and add the selected date to the Edit text
        mCosplayEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int year;
                    int month;
                    int day;
                    String mtemp = mCosplayEndDate.getText().toString().trim();
                    if (mtemp.matches("")) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        String mDateComlete = mCosplayEndDate.getText().toString();
                        String[] mDate = mDateComlete.split("/");
                        day = Integer.parseInt(mDate[0].trim());
                        month = Integer.parseInt(mDate[1].trim());
                        year = Integer.parseInt(mDate[2].trim());
                        month = month - 1;
                    }

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mEndDateSetListener, year, month, day);
                    datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            }
        });
        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mCosplayEndDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        //Cancel. dismiss the popup
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

        //Add Copslay to the database
        mUpdateCosplays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cosplay CosUP = new Cosplay(cosplay.mCosplayId,mCosplayName.getText().toString(),mCosplayStartDate.getText().toString(),mCosplayEndDate.getText().toString(),Double.parseDouble(mCosplayBudget.getText().toString()),((BitmapDrawable)mCosplayImage.getDrawable()).getBitmap());

                cosplayViewModel.update(CosUP);
                dialog.dismiss();

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data == null) {
                return;
            } else {
                Uri imageData = data.getData();
                mImage.setImageURI(imageData);
            }
        }
    }
}