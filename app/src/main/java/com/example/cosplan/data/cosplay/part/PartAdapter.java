package com.example.cosplan.data.cosplay.part;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.data.cosplay.Cosplay;
import com.example.cosplan.data.cosplay.CosplayViewModel;
import com.example.cosplan.data.cosplay.wIPImg.WIPImg;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.PartViewHolder> implements PreferenceManager.OnActivityResultListener {
    private static final int GALLERY_REQUEST_CODE_PART_IMAGE =50 ;
    private List<Part> mParts;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;
    private PartViewModel mPartViewModel;
    private final Application mApplication;
    private Cosplay mCosplay;
    private CosplayViewModel mCosplayViewModel;
    private View v;
    private String mPath;
    private ImageView mPartImage;

    public void setCosplay(Cosplay tempCosplay, CosplayViewModel cosplayViewModel, View v) {
        mCosplay = tempCosplay;
        mCosplayViewModel = cosplayViewModel;
        this.v = v;
    }


    public PartAdapter(Context context, Application mApplication) {
        this.mApplication = mApplication;
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }



    static class PartViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mPartImage;
        private final TextView mPartName;
        private final TextView mPartCost;
        private final TextView mPartStatus;
        private final TextView mPartEndDate;

        public PartViewHolder(@NonNull View itemView) {
            super(itemView);
            mPartImage = itemView.findViewById(R.id.ImageView_PartImage);
            mPartName = itemView.findViewById(R.id.TextView_PartName);
            mPartCost = itemView.findViewById(R.id.TextView_PartCost);
            mPartStatus = itemView.findViewById(R.id.TextView_PartStatus);
            mPartEndDate = itemView.findViewById(R.id.TextView_PartEndDate);
        }
    }

    public Part getPartAtPosition(int mPosition) {
        return mParts.get(mPosition);
    }

    public void setParts(List<Part> parts) {
        mParts = parts;
        notifyDataSetChanged();
    }


    @NotNull
    @Override
    public PartAdapter.PartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.custum_cosplay_part_row, parent, false);
        return new PartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PartAdapter.PartViewHolder holder, int position) {
        final Part current = mParts.get(position);
        String mCosplayPartImg = current.mCosplayPartImg;
        String mCosplayPartName = current.mCosplayPartName;
        String mCosplayPartCost = Double.toString(current.mCosplayPartCost);
        String mCosplayPartStatus = current.mCosplayPartStatus;
        String mCosplayPartEndDate = current.mCosplayPartEndDate;

        SetImageFromUri(holder.mPartImage,mCosplayPartImg);

        holder.mPartName.setText(mCosplayPartName);
        holder.mPartCost.setText(mCosplayPartCost);
        holder.mPartStatus.setText(mCosplayPartStatus);
        holder.mPartEndDate.setText(mCosplayPartEndDate);
        View itemView = holder.itemView;
        mPartViewModel = new PartViewModel(mApplication);
        switch (current.mCosplayPartBuyMake) {
            case "Buy":
                itemView.findViewById(R.id.RecView_PartsToBuy);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updatePartDialog(current);
                    }
                });
                break;
            case "Make":
                itemView.findViewById(R.id.RecView_PartsToMake);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updatePartDialog(current);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mParts != null) {
            return mParts.size();
        } else {
            return 0;
        }
    }

    @SuppressLint("SetTextI18n")
    public void updatePartDialog(final Part tempPart) {
        final AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(mContext);
        final View mPartDialog = mInflater.inflate(R.layout.cosplay_screen_part_update, null);
        final Spinner mPartBuyMake, mPartStatus;
   //     final ImageView mPartImage;
        final EditText mPartName, mPartLink, mPartCost, mPartDate, mPartNotes;
        final Button mPartCancel, mPartUpdate, mPartImageUpdate;
        mPath=tempPart.mCosplayPartImg;

        mPartBuyMake = mPartDialog.findViewById(R.id.Spinner_PartUpdateMakeBuy);
        ArrayAdapter<CharSequence> mPartArrayAdapterMakeBuy = ArrayAdapter.createFromResource(mContext, R.array.BuyMake, android.R.layout.simple_spinner_item);
        mPartArrayAdapterMakeBuy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPartBuyMake.setAdapter(mPartArrayAdapterMakeBuy);

        mPartStatus = mPartDialog.findViewById(R.id.Spinner_PartUpdateStatus);
        ArrayAdapter<CharSequence> mPartArrayAdapterStatus = ArrayAdapter.createFromResource(mContext, R.array.Status, android.R.layout.simple_spinner_item);
        mPartArrayAdapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPartStatus.setAdapter(mPartArrayAdapterStatus);

        mPartImage = mPartDialog.findViewById(R.id.ImageView_PartUpdateImage);
        mPartName = mPartDialog.findViewById(R.id.EditText_PartUpdateName);
        mPartLink = mPartDialog.findViewById(R.id.EditText_PartUpdateLink);
        mPartCost = mPartDialog.findViewById(R.id.EditText_PartUpdateCost);
        mPartDate = mPartDialog.findViewById(R.id.EditText_PartUpdateEndDate);
        mPartNotes = mPartDialog.findViewById(R.id.EditText_PartUpdateNotes);
        mPartCancel = mPartDialog.findViewById(R.id.Btn_PartUpdateCancel);
        mPartUpdate = mPartDialog.findViewById(R.id.Btn_PartUpdateUpdate);
        mPartImageUpdate=mPartDialog.findViewById(R.id.Btn_PartUpdateImage);

        SetImageFromUri(mPartImage,tempPart.mCosplayPartImg);
        mPartBuyMake.setSelection(mPartArrayAdapterMakeBuy.getPosition(tempPart.mCosplayPartBuyMake));
        mPartStatus.setSelection(mPartArrayAdapterStatus.getPosition(tempPart.mCosplayPartStatus));
        mPartName.setText(tempPart.mCosplayPartName);
        mPartLink.setText(tempPart.mCosplayPartLink);
        mPartCost.setText(Double.toString(tempPart.mCosplayPartCost));
        mPartDate.setText(tempPart.mCosplayPartEndDate);
        mPartNotes.setText(tempPart.mCosplayPartNote);
        mDialogBuilder.setView(mPartDialog);
        final Dialog mDialog = mDialogBuilder.create();
        mDialog.show();

        //region DateListener
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

                    DatePickerDialog mDatePickerDialog = new DatePickerDialog(mContext, R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mEndDateSetListener, year, month, day);
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

        mPartCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
        mPath="";
            }
        });
        mPartUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double mOldCost = tempPart.mCosplayPartCost;
                Part mTempPart = new Part(tempPart.mCosplayId, tempPart.mCosplayPartId, mPartName.getText().toString(), mPartBuyMake.getSelectedItem().toString(), mPartLink.getText().toString(), Double.parseDouble(mPartCost.getText().toString()), mPartStatus.getSelectedItem().toString(), mPartDate.getText().toString(), mPath, mPartNotes.getText().toString());
                mPartViewModel.update(mTempPart);
                Cosplay mTempCosplay = mCosplay;
                mTempCosplay.mCosplayRemainingBudget =Math.round((mTempCosplay.mCosplayRemainingBudget - Double.parseDouble(mPartCost.getText().toString()) + mOldCost)*100.0)/100.0;
                mCosplayViewModel.update(mTempCosplay);
                mDialog.dismiss();
                updateCosplayHeaderBudget();
            }
        });
        mPartImageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {

                    // Requesting the permission
                    ActivityCompat.requestPermissions((Activity)mContext,
                            new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},
                            101);
                }
                else {
                    Intent mIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    ((Activity)mContext).startActivityForResult(Intent.createChooser(mIntent, mContext.getString(R.string.txt_chooseImg_intent)), GALLERY_REQUEST_CODE_PART_IMAGE);
                }


            }
        });
    }

    public Boolean checkDateFormat(String date) {
        if (date == null || !date.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))
            return false;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public void SetImageFromUri(ImageView mImageView,String mImagePath){
        Uri selectedImageUri=null;
        if (mImagePath != null) {
            File f = new File(mImagePath);
            selectedImageUri = Uri.fromFile(f);
        }
        Bitmap mBitmap=null;
        try {
            mBitmap= BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(selectedImageUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mImageView.setImageBitmap(mBitmap);
    }
    public void updateCosplayHeaderBudget() {
        TextView mBudget = v.findViewById(R.id.TextView_CosplayHeaderBudget);
        double percentage = mCosplay.mCosplayRemainingBudget / mCosplay.mCosplayBudget * 100;
        if (percentage < 25 && percentage > 0) {
            mBudget.setTextColor(Color.YELLOW);
        } else if (percentage <= 0) {
            mBudget.setTextColor(Color.RED);
        } else {
            mBudget.setTextColor(Color.GREEN);

        }
        mBudget.setText(Double.toString(mCosplay.mCosplayRemainingBudget));
    }
    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri mImageData;

        if (requestCode == GALLERY_REQUEST_CODE_PART_IMAGE && data != null) {
            mImageData = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = v.getContext().getContentResolver().openInputStream(mImageData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        mPath=getPathFromUri(mImageData);
        SetImageFromUri(mPartImage,mPath);
        }
        return false;
    }

    public String getPathFromUri(Uri mContentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = v.getContext().getContentResolver().query(mContentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}

