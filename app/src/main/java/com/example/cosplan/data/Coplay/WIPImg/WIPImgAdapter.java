package com.example.cosplan.data.Coplay.WIPImg;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import java.util.List;

public class WIPImgAdapter extends RecyclerView.Adapter<WIPImgAdapter.WIPImgViewHolder> {
    List<WIPImg> mWIPImgs;
    LayoutInflater mInflater;
    private WIPImgViewModel mWipImgViewModel;
    private Application mApplication;
    private Context mContext;
    public WIPImgAdapter(List<WIPImg> mWIPImgs, Context context, Application application) {
        this.mWIPImgs = mWIPImgs;
        this.mContext=context;
        this.mInflater = LayoutInflater.from(context);
        this.mApplication=application;
    }
    public void setWIPImgs(List<WIPImg> mWIPImgs){this.mWIPImgs=mWIPImgs;notifyDataSetChanged();}
    @NonNull
    @Override
    public WIPImgAdapter.WIPImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.custum_row_image,parent,false);
        return new WIPImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WIPImgAdapter.WIPImgViewHolder holder, final int position) {
        final WIPImg mCurrent=mWIPImgs.get(position);
        holder.mImageViewWIPImg.setImageBitmap(mCurrent.mCosplayWIPImgImage);
        View itemView=holder.itemView;
        mWipImgViewModel=new WIPImgViewModel(mApplication);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mDialogBuilder=new AlertDialog.Builder(mContext);
                View mImageDialog=mInflater.inflate(R.layout.image_fullscreen,null);
                ImageView mImageView=mImageDialog.findViewById(R.id.ImageView_ImageFullScreen);
                ImageButton mClose=mImageDialog.findViewById(R.id.ImageBtn_ImageClose);
                Button mDelete=mImageDialog.findViewById(R.id.Btn_ImageDelete);
                mImageView.setImageBitmap(mCurrent.mCosplayWIPImgImage);
                mDialogBuilder.setView(mImageDialog);
                final Dialog dialog=mDialogBuilder.create();
                dialog.show();

                mClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mWipImgViewModel.delete(mCurrent);
                        dialog.dismiss();
                    }
                });
            }
        });

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
