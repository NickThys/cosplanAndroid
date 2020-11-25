package com.example.cosplan.data.Coplay.Part;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.PartViewHolder> {
    private List<Part> mParts;
    private final LayoutInflater mInflater;
    private final Context mContext;

    public PartAdapter(Context context) {mInflater = LayoutInflater.from(context);this.mContext=context;}

    class PartViewHolder extends RecyclerView.ViewHolder{
        private final ImageView mPartImage;
        private final TextView mPartName;
        private final TextView mPartCost;
        private final TextView mPartStatus;
        private final TextView mPartEndDate;
        public PartViewHolder(@NonNull View itemView) {
            super(itemView);
            mPartImage=itemView.findViewById(R.id.ImageView_PartImage);
            mPartName=itemView.findViewById(R.id.TextView_PartName);
            mPartCost=itemView.findViewById(R.id.TextView_PartCost);
            mPartStatus=itemView.findViewById(R.id.TextView_PartStatus);
            mPartEndDate=itemView.findViewById(R.id.TextView_PartEnddate);
        }
    }

    public void setParts(List<Part> parts){
        mParts=parts;
        notifyDataSetChanged();
    }


    @Override
    public PartAdapter.PartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.custum_cosplay_part_row,parent,false);
        return new PartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PartAdapter.PartViewHolder holder, int position) {
    final Part current=mParts.get(position);
        Bitmap tempPartImage=current.mCosplayPartImg;
        String tempPartName=current.mCosplayPartName;
        String tempPartCost=Double.toString(current.mCosplayPartCost);
        String tempPartStatus=current.mCosplayPartStatus;
        String tempPartEndDate=current.mCosplayPartEndDate;

        holder.mPartImage.setImageBitmap(tempPartImage);
        holder.mPartName.setText(tempPartName);
        holder.mPartCost.setText(tempPartCost);
        holder.mPartStatus.setText(tempPartStatus);
        holder.mPartEndDate.setText(tempPartEndDate);
    }

    @Override
    public int getItemCount() {
        if (mParts != null) {
            return mParts.size();
        } else {
            return 0;
        }
    }
    public void updatePartDialog(final Part tempPart){
        final AlertDialog.Builder mDialogBuilder=new AlertDialog.Builder(mContext);
        final View mPartDialog=mInflater.inflate(R.layout.cosplay_screen_part_update,null);
        final Spinner mPartBuyMake,mPartStatus;
        final ImageView mPartImage;
        final EditText mPartName,mPartLink,mPartCost,mPartDate,mPartNotes;
        final Button mPartChooseImage,mPartCancel,mPartUpdate;

        mPartBuyMake=mPartDialog.findViewById(R.id.Spinner_PartUpdateMakeBuy);
        ArrayAdapter<CharSequence> mPartArrayAdapterMakeBuy=ArrayAdapter.createFromResource(mContext,R.array.BuyMake, android.R.layout.simple_spinner_item);
        mPartArrayAdapterMakeBuy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPartBuyMake.setAdapter(mPartArrayAdapterMakeBuy);

        mPartStatus=mPartDialog.findViewById(R.id.Spinner_PartUpdateStatus);
        ArrayAdapter<CharSequence> mPartArrayAdapterStatus=ArrayAdapter.createFromResource(mContext,R.array.Status, android.R.layout.simple_spinner_item);
        mPartArrayAdapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPartStatus.setAdapter(mPartArrayAdapterStatus);

        mPartImage=mPartDialog.findViewById(R.id.ImageView_PartUpdateImage);
        mPartName=mPartDialog.findViewById(R.id.EditText_PartUpdateName);
        mPartLink=mPartDialog.findViewById(R.id.EditText_PartUpdateLink);
        mPartCost=mPartDialog.findViewById(R.id.EditText_PartUpdateCost);
        mPartDate=mPartDialog.findViewById(R.id.EditText_PartUpdateEnddate);
        mPartNotes=mPartDialog.findViewById(R.id.EditText_PartUpdateNotes);
        mPartChooseImage=mPartDialog.findViewById(R.id.Btn_PartUpdateChooseImage);
        mPartCancel=mPartDialog.findViewById(R.id.Btn_PartUpdateCancel);
        mPartUpdate=mPartDialog.findViewById(R.id.Btn_PartUpdateUpdate);

        
    }
}
