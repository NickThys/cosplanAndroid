package com.example.cosplan.data.Coplay.WIPImg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import java.util.List;

public class WIPImgAdapter extends RecyclerView.Adapter<WIPImgAdapter.WIPImgViewHolder> {
    List<WIPImg> mWIPImgs;
    LayoutInflater mInflater;

    public WIPImgAdapter(List<WIPImg> mWIPImgs, Context context) {
        this.mWIPImgs = mWIPImgs;
        this.mInflater = LayoutInflater.from(context);
    }
    public void setWIPImgs(List<WIPImg> mWIPImgs){this.mWIPImgs=mWIPImgs;notifyDataSetChanged();}
    @NonNull
    @Override
    public WIPImgAdapter.WIPImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.custum_row_image,parent,false);
        return new WIPImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WIPImgAdapter.WIPImgViewHolder holder, int position) {
        holder.mImageViewWIPImg.setImageBitmap(mWIPImgs.get(position).mCosplayWIPImgImage);
    }

    @Override
    public int getItemCount() {
        return mWIPImgs.size();
    }

    public class WIPImgViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageViewWIPImg;
        public WIPImgViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewWIPImg=itemView.findViewById(R.id.ImageView_RefImage);
        }
    }
}
