package com.example.cosplan.data.Coplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.PartViewHolder> {
    private List<Part> mParts;
    private final LayoutInflater mInflater;

    public PartAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    class PartViewHolder extends RecyclerView.ViewHolder{
        private final ImageView mPartImage;
        private final TextView mPartName;
        private final TextView mPartCost;
        private final TextView mPartStatus;
        private final TextView mPartEndDate;
        public PartViewHolder(@NonNull View itemView) {
            super(itemView);
            mPartImage=itemView.findViewById(R.id.img_PartImage);
            mPartName=itemView.findViewById(R.id.txt_PartName);
            mPartCost=itemView.findViewById(R.id.txt_PartCost);
            mPartStatus=itemView.findViewById(R.id.txt_PartStatus);
            mPartEndDate=itemView.findViewById(R.id.txt_PartEnddate);
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
}
