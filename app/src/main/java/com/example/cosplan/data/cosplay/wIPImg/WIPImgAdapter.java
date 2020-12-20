package com.example.cosplan.data.cosplay.wIPImg;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class WIPImgAdapter extends RecyclerView.Adapter<WIPImgAdapter.WIPImgViewHolder> {
    List<WIPImg> mWIPImgs;
    final LayoutInflater mLayoutInflater;
    private WIPImgViewModel mWipImgViewModel;
    private final Application mApplication;
    private final Context mContext;
    AlertDialog.Builder mDialogBuilder;
    Dialog mDialog;

    public WIPImgAdapter(List<WIPImg> mWIPImgs, Context context, Application application) {
        this.mWIPImgs = mWIPImgs;
        this.mContext=context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mApplication=application;
    }
    public void setWIPImgs(List<WIPImg> mWIPImgs){this.mWIPImgs=mWIPImgs;notifyDataSetChanged();}
    @NonNull
    @Override
    public WIPImgAdapter.WIPImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= mLayoutInflater.inflate(R.layout.custum_row_image,parent,false);
        return new WIPImgViewHolder(view);
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
    @Override
    public void onBindViewHolder(@NonNull WIPImgAdapter.WIPImgViewHolder holder, final int position) {
        final WIPImg mCurrent=mWIPImgs.get(position);

      SetImageFromUri(holder.mImageViewWIPImg,mCurrent.mCosplayWIPImgImage);

        View itemView=holder.itemView;
        mWipImgViewModel=new WIPImgViewModel(mApplication);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogBuilder=new AlertDialog.Builder(mContext);
                View mImageDialog= mLayoutInflater.inflate(R.layout.image_fullscreen,null);
                ImageView mImageView=mImageDialog.findViewById(R.id.ImageView_ImageFullScreen);
                ImageButton mClose=mImageDialog.findViewById(R.id.ImageBtn_ImageClose);
                Button mDelete=mImageDialog.findViewById(R.id.Btn_ImageDelete);

                SetImageFromUri(mImageView,mCurrent.mCosplayWIPImgImage);

                mDialogBuilder.setView(mImageDialog);
                mDialog=mDialogBuilder.create();
                mDialog.show();

                mClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog(mCurrent);
                        mDialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mWIPImgs.size();
    }

    public static class WIPImgViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImageViewWIPImg;
        public WIPImgViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewWIPImg=itemView.findViewById(R.id.ImageView_RefImage);

        }
    }
    public void deleteDialog(final WIPImg mCurrent) {
        mDialogBuilder = new AlertDialog.Builder(mContext);
        final View deleteCosplayView = mLayoutInflater.inflate(R.layout.delete, null);
        TextView mDeleteText = deleteCosplayView.findViewById(R.id.TextView_DeleteTitle);
        mDeleteText.setText( R.string.DeleteImage);
        final Button mBtnDelete, mBtnCancel;
        mBtnCancel = deleteCosplayView.findViewById(R.id.Btn_DeleteCancel);
        mBtnDelete = deleteCosplayView.findViewById(R.id.Btn_DeleteDelete);
        mDialogBuilder.setView(deleteCosplayView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWipImgViewModel.delete(mCurrent);
                Toast.makeText(mContext, "Image deleted", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }

        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }
}
