package com.example.cosplan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.cosplan.data.cosplay.Cosplay;
import com.example.cosplan.data.cosplay.CosplayViewModel;
import com.example.cosplan.data.cosplay.part.Part;
import com.example.cosplan.data.cosplay.part.PartViewModel;
import com.example.cosplan.ui.home.CosplayFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CosplayPartUpdate extends AppCompatActivity {
    Part tempPart;
    Cosplay mCosplay;
    CosplayViewModel cosplayViewModel;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;
    private PartViewModel mPartViewModel;
String mPartUri;
  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
    }*/
  ImageView mPartImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosplay_part_update);
        Intent intent=getIntent();
        tempPart=intent.getParcelableExtra("part");
        mCosplay=intent.getParcelableExtra("cosplay");
        mPartViewModel = new PartViewModel(this.getApplication());
cosplayViewModel=new ViewModelProvider(this).get(CosplayViewModel.class);
        Button mPartCancel;
        Button mPartUpdate;
        Button mPartUpdateImage;

        final Spinner mPartBuyMake, mPartStatus;
        final EditText mPartName, mPartLink, mPartCost, mPartDate, mPartNotes;

        mPartBuyMake = findViewById(R.id.Spinner_PartUpdateMakeBuy);
        ArrayAdapter<CharSequence> mPartArrayAdapterMakeBuy = ArrayAdapter.createFromResource(this, R.array.BuyMake, android.R.layout.simple_spinner_item);
        mPartArrayAdapterMakeBuy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPartBuyMake.setAdapter(mPartArrayAdapterMakeBuy);

        mPartStatus = findViewById(R.id.Spinner_PartUpdateStatus);
        ArrayAdapter<CharSequence> mPartArrayAdapterStatus = ArrayAdapter.createFromResource(this, R.array.Status, android.R.layout.simple_spinner_item);
        mPartArrayAdapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPartStatus.setAdapter(mPartArrayAdapterStatus);

        mPartImage = findViewById(R.id.ImageView_PartUpdateImage);
        mPartName = findViewById(R.id.EditText_PartUpdateName);
        mPartLink = findViewById(R.id.EditText_PartUpdateLink);
        mPartCost = findViewById(R.id.EditText_PartUpdateCost);
        mPartDate = findViewById(R.id.EditText_PartUpdateEndDate);
        mPartNotes = findViewById(R.id.EditText_PartUpdateNotes);
        mPartCancel = findViewById(R.id.Btn_PartUpdateCancel);
        mPartUpdate = findViewById(R.id.Btn_PartUpdateUpdate);
        mPartUpdateImage=findViewById(R.id.Btn_PartUpdateImage);
        if(tempPart.mCosplayPartImg!=null)
            SetImageFromUri(mPartImage, tempPart.mCosplayPartImg);
        mPartBuyMake.setSelection(mPartArrayAdapterMakeBuy.getPosition(tempPart.mCosplayPartBuyMake));
        mPartStatus.setSelection(mPartArrayAdapterStatus.getPosition(tempPart.mCosplayPartStatus));
        mPartName.setText(tempPart.mCosplayPartName);
        mPartLink.setText(tempPart.mCosplayPartLink);
        mPartCost.setText(Double.toString(tempPart.mCosplayPartCost));
        mPartDate.setText(tempPart.mCosplayPartEndDate);
        mPartNotes.setText(tempPart.mCosplayPartNote);

        mPartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int year;
                    int month;
                    int day;
                    String mTemp = mPartDate.getText().toString().trim();
                    if (!checkDateFormat(mTemp)) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        String mDateComplete = mPartDate.getText().toString();
                        String[] mDate = mDateComplete.split("/");
                        day = Integer.parseInt(mDate[0].trim());
                        month = Integer.parseInt(mDate[1].trim());
                        year = Integer.parseInt(mDate[2].trim());
                        month = month - 1;
                    }

                    DatePickerDialog mDatePickerDialog = new DatePickerDialog(getApplicationContext(), R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mEndDateSetListener, year, month, day);
                    mDatePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDatePickerDialog.show();
                }
            }
        });
        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mPartDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };
        //endregion
        mPartUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(Intent.createChooser(intent, v.getResources().getString(R.string.txt_chooseImg_intent)), 6);
            }
        });
        mPartCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(getApplicationContext(), CosplayMain.class));
                finish();
            }
        });

        mPartUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double mOldCost = tempPart.mCosplayPartCost;
                Part mTempPart = new Part(tempPart.mCosplayId, tempPart.mCosplayPartId, mPartName.getText().toString(), mPartBuyMake.getSelectedItem().toString(), mPartLink.getText().toString(), Double.parseDouble(mPartCost.getText().toString()), mPartStatus.getSelectedItem().toString(), mPartDate.getText().toString(), mPartUri, mPartNotes.getText().toString());
                mPartViewModel.update(mTempPart);
                Cosplay mTempCosplay = mCosplay;
                mTempCosplay.mCosplayRemainingBudget = Math.round((mTempCosplay.mCosplayRemainingBudget - Double.parseDouble(mPartCost.getText().toString()) + mOldCost) * 100.0) / 100.0;
                cosplayViewModel.update(mTempCosplay);
                //startActivity(new Intent(getApplicationContext(), CosplayMain.class));
                finish();
                //updateCosplayHeaderBudget();
            }
        });

    }
    public void SetImageFromUri(ImageView mImageView, String mImagePath) {
        Uri selectedImageUri = null;
        if (mImagePath != null) {
            File f = new File(mImagePath);
            selectedImageUri = Uri.fromFile(f);
        }
        Bitmap mBitmap = null;
        try {
            mBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mImageView.setImageBitmap(mBitmap);
    }
    public Boolean checkDateFormat(String date) {
        if (date == null || !date.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))
            return false;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public String getPathFromUri(Uri mContentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(mContentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6 && data != null) {
           Uri mImageData = data.getData();
            mPartUri = getPathFromUri(mImageData);
            SetImageFromUri(mPartImage, mPartUri);
        }
    }
}