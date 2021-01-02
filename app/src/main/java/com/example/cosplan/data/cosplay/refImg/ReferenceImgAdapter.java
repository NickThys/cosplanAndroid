package com.example.cosplan.data.cosplay.refImg;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ReferenceImgAdapter extends RecyclerView.Adapter<ReferenceImgAdapter.ReferenceImgViewHolder> {

    List<ReferenceImg> mRefImgs;
    final LayoutInflater mLayoutInflater;
    final Context mContext;
    final Application mApplication;
    ReferenceImgViewModel mRefImgViewModel;

    public ReferenceImgAdapter(List<ReferenceImg> mRefImgs, Context context, Application application) {
        this.mRefImgs = mRefImgs;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mApplication = application;
    }

    public void setRefImg(List<ReferenceImg> refImgs) {
        mRefImgs = refImgs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReferenceImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.custum_row_image, parent, false);
        return new ReferenceImgViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ReferenceImgViewHolder holder, int position) {
        final ReferenceImg mCurrentReferenceImg = mRefImgs.get(position);

        SetImageFromUri(holder.ImageViewRefImg,mCurrentReferenceImg.mCosplayRefImgImage);

        View itemView = holder.itemView;
        mRefImgViewModel = new ReferenceImgViewModel(mApplication);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(mContext);
                View mImageDialog = mLayoutInflater.inflate(R.layout.image_fullscreen, null);
                ImageView mImageView = mImageDialog.findViewById(R.id.ImageView_ImageFullScreen);
                ImageButton mCloseView = mImageDialog.findViewById(R.id.ImageBtn_ImageClose);
                ImageButton mShareImage=mImageDialog.findViewById(R.id.ImageBtn_ImageShare);
                Button mDeleteImage = mImageDialog.findViewById(R.id.Btn_ImageDelete);
                SetImageFromUri(mImageView,mCurrentReferenceImg.mCosplayRefImgImage);

                mDialogBuilder.setView(mImageDialog);
                final Dialog mDialog = mDialogBuilder.create();
                mDialog.show();

                mShareImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareImage(mCurrentReferenceImg.mCosplayRefImgImage,v);
                    }
                });
                mCloseView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDeleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog(mCurrentReferenceImg);
                        mDialog.dismiss();
                    }
                });
            }
        });
    }

    private void shareImage(String mCosplayRefImgImage,View v) {
        Uri uri= Uri.parse(mCosplayRefImgImage);

        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
        shareIntent.setType("image/*");
        v.getContext().startActivity(Intent.createChooser(shareIntent,"Share image to"));

    }

    @Override
    public int getItemCount() {
        return mRefImgs.size();
    }

    public static class ReferenceImgViewHolder extends RecyclerView.ViewHolder {
        final ImageView ImageViewRefImg;

        public ReferenceImgViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageViewRefImg = itemView.findViewById(R.id.ImageView_RefImage);
        }
    }

    private void deleteDialog(final ReferenceImg mCurrent) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        final View deleteCosplayView = mLayoutInflater.inflate(R.layout.delete, null);
        TextView mDeleteText = deleteCosplayView.findViewById(R.id.TextView_DeleteTitle);
        mDeleteText.setText(R.string.DeleteImage);
        final Button mBtnDelete, mBtnCancel;
        mBtnCancel = deleteCosplayView.findViewById(R.id.Btn_DeleteCancel);
        mBtnDelete = deleteCosplayView.findViewById(R.id.Btn_DeleteDelete);
        dialogBuilder.setView(deleteCosplayView);
        final Dialog mDialog = dialogBuilder.create();
        mDialog.show();

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefImgViewModel.delete(mCurrent);
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
    public Uri uriToImage(String mImagePath){
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
        return selectedImageUri;
    }
}
