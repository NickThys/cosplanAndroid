package com.example.cosplan.data.Coplay.RefImg;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.data.Coplay.WIPImg.WIPImg;
import com.example.cosplan.data.Coplay.WIPImg.WIPImgViewModel;

import java.util.List;

public class RefenceImgAdapter extends RecyclerView.Adapter<RefenceImgAdapter.ReferenceImgViewHolder> {

    List<ReferenceImg> mRefImgs;
    LayoutInflater mInflater;
    final Context mContext;
    final Application mApplication;
    ReferenceImgViewModel mRefImgViewModel;

    public RefenceImgAdapter(List<ReferenceImg> mRefImgs, Context context, Application application) {
        this.mRefImgs = mRefImgs;
        this.mInflater =LayoutInflater.from(context);
        this.mContext=context;
        this.mApplication=application;
    }
    public void setRefImg(List<ReferenceImg> refImgs){mRefImgs=refImgs;notifyDataSetChanged();}
    @NonNull
    @Override
    public ReferenceImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.custum_row_image,parent,false);
        return new ReferenceImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferenceImgViewHolder holder, int position) {
        final ReferenceImg mCurrent=mRefImgs.get(position);
        holder.IVrefimg.setImageBitmap(mCurrent.mCosplayRefImgImage);
        View itemView=holder.itemView;
        mRefImgViewModel=new ReferenceImgViewModel(mApplication);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mDialogBuilder=new AlertDialog.Builder(mContext);
                View mImageDialog=mInflater.inflate(R.layout.image_fullscreen,null);
                ImageView mImageView=mImageDialog.findViewById(R.id.ImageView_ImageFullScreen);
                ImageButton mClose=mImageDialog.findViewById(R.id.ImageBtn_ImageClose);
                Button mDelete=mImageDialog.findViewById(R.id.Btn_ImageDelete);
                mImageView.setImageBitmap(mCurrent.mCosplayRefImgImage);
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
                        deleteDialog(mCurrent);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRefImgs.size();
    }

    public class ReferenceImgViewHolder extends RecyclerView.ViewHolder {
        ImageView IVrefimg;
        public ReferenceImgViewHolder(@NonNull View itemView) {
            super(itemView);
            IVrefimg=itemView.findViewById(R.id.ImageView_RefImage);
        }
    }
    public void deleteDialog(final ReferenceImg mCurrent) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        final View deleteCosplayView = mInflater.inflate(R.layout.delete, null);
        TextView mDeleteText = deleteCosplayView.findViewById(R.id.TextView_DeleteTitle);
        mDeleteText.setText( "Do you want to delete this image");
        final Button yes, no;
        no = deleteCosplayView.findViewById(R.id.Btn_DeleteNo);
        yes = deleteCosplayView.findViewById(R.id.Btn_DeleteYes);
        dialogBuilder.setView(deleteCosplayView);
        final Dialog dialog = dialogBuilder.create();
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefImgViewModel.delete(mCurrent);
                Toast.makeText(mContext, "Image deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
