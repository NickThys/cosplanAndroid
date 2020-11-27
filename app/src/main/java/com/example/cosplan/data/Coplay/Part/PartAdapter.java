package com.example.cosplan.data.Coplay.Part;

import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.PartViewHolder> {
    private List<Part> mParts;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;
    private PartViewModel mPartViewModel;
    private final Application mApplication;

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
            mPartEndDate = itemView.findViewById(R.id.TextView_PartEnddate);
        }
    }
    public Part getPartAtPosition(int mPosition){
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
        Bitmap tempPartImage = current.mCosplayPartImg;
        String tempPartName = current.mCosplayPartName;
        String tempPartCost = Double.toString(current.mCosplayPartCost);
        String tempPartStatus = current.mCosplayPartStatus;
        String tempPartEndDate = current.mCosplayPartEndDate;

        holder.mPartImage.setImageBitmap(tempPartImage);
        holder.mPartName.setText(tempPartName);
        holder.mPartCost.setText(tempPartCost);
        holder.mPartStatus.setText(tempPartStatus);
        holder.mPartEndDate.setText(tempPartEndDate);
        View itemView = holder.itemView;
        mPartViewModel = new PartViewModel(mApplication);
        switch (current.mCosplayPartBuyMake){
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

    public void updatePartDialog(final Part tempPart) {
        final AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(mContext);
        final View mPartDialog = mInflater.inflate(R.layout.cosplay_screen_part_update, null);
        final Spinner mPartBuyMake, mPartStatus;
        final ImageView mPartImage;
        final EditText mPartName, mPartLink, mPartCost, mPartDate, mPartNotes;
        final Button mPartCancel, mPartUpdate;

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
        mPartDate = mPartDialog.findViewById(R.id.EditText_PartUpdateEnddate);
        mPartNotes = mPartDialog.findViewById(R.id.EditText_PartUpdateNotes);
        mPartCancel = mPartDialog.findViewById(R.id.Btn_PartUpdateCancel);
        mPartUpdate = mPartDialog.findViewById(R.id.Btn_PartUpdateUpdate);

        mPartImage.setImageBitmap(tempPart.mCosplayPartImg);
        mPartBuyMake.setSelection(mPartArrayAdapterMakeBuy.getPosition(tempPart.mCosplayPartBuyMake));
        mPartStatus.setSelection(mPartArrayAdapterStatus.getPosition(tempPart.mCosplayPartStatus));
        mPartName.setText(tempPart.mCosplayPartName);
        mPartLink.setText(tempPart.mCosplayPartLink);
        mPartCost.setText(Double.toString(tempPart.mCosplayPartCost));
        mPartDate.setText(tempPart.mCosplayPartEndDate);
        mPartNotes.setText(tempPart.mCosplaypartNote);
        mDialogBuilder.setView(mPartDialog);
        final Dialog dialog = mDialogBuilder.create();
        dialog.show();

        //region DateListener
        mPartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int year;
                    int month;
                    int day;
                    String mtemp = mPartDate.getText().toString().trim();
                    if (mtemp.matches("")) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        String mDateComlete = mPartDate.getText().toString();
                        String[] mDate = mDateComlete.split("/");
                        day = Integer.parseInt(mDate[0].trim());
                        month = Integer.parseInt(mDate[1].trim());
                        year = Integer.parseInt(mDate[2].trim());
                        month = month - 1;
                    }

                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mEndDateSetListener, year, month, day);
                    datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
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
                dialog.dismiss();

            }
        });
        mPartUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Part mTempPart =new Part(tempPart.mCosplayId,tempPart.mCosplayPartId,mPartName.getText().toString(),mPartBuyMake.getSelectedItem().toString(),mPartLink.getText().toString(),Double.parseDouble(mPartCost.getText().toString()),mPartStatus.getSelectedItem().toString(),mPartDate.getText().toString(),((BitmapDrawable)mPartImage.getDrawable()).getBitmap(),mPartNotes.getText().toString());
                mPartViewModel.update(mTempPart);
                dialog.dismiss();
            }
        });
    }
}

