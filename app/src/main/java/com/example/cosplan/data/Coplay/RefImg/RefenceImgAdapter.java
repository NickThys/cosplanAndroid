package com.example.cosplan.data.Coplay.RefImg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import java.util.List;

public class RefenceImgAdapter extends RecyclerView.Adapter<RefenceImgAdapter.ReferenceImgViewHolder> {

    List<ReferenceImg> mRefImgs;
    LayoutInflater inflater;

    public RefenceImgAdapter(List<ReferenceImg> mRefImgs, Context context) {
        this.mRefImgs = mRefImgs;
        this.inflater=LayoutInflater.from(context);
    }
    public void setRefImg(List<ReferenceImg> refImgs){mRefImgs=refImgs;notifyDataSetChanged();}
    @NonNull
    @Override
    public ReferenceImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custum_row_ref_img,parent,false);
        return new ReferenceImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferenceImgViewHolder holder, int position) {
        holder.IVrefimg.setImageBitmap(mRefImgs.get(position).mCosplayRefImgImage);
    }

    @Override
    public int getItemCount() {
        return mRefImgs.size();
    }

    public class ReferenceImgViewHolder extends RecyclerView.ViewHolder {
        ImageView IVrefimg;
        public ReferenceImgViewHolder(@NonNull View itemView) {
            super(itemView);
            IVrefimg=itemView.findViewById(R.id.ImgaeViewRefImg);
        }
    }
}
