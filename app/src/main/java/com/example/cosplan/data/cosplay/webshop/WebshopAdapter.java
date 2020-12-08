package com.example.cosplan.data.cosplay.webshop;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import java.util.List;

public class WebshopAdapter extends RecyclerView.Adapter<WebshopAdapter.WebshopViewHolder> {
    private List<Webshop> mWebshops;
    private final LayoutInflater mLayoutInflater;
    private final Context context;
    private WebshopViewModel mWebshopViewModel;
    private final Application mApplication;

    public WebshopAdapter(Context context, Application mApplication) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.mApplication = mApplication;
    }

    public static class WebshopViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCosplayWebsiteName, mCosplayWebshopLink;

        public WebshopViewHolder(@NonNull View itemView) {
            super(itemView);
            mCosplayWebsiteName = itemView.findViewById(R.id.TextView_WebshopName);
            mCosplayWebshopLink = itemView.findViewById(R.id.TextView_WebshopLink);
        }
    }

    @NonNull
    @Override
    public WebshopAdapter.WebshopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custum_webshop_row, parent, false);
        return new WebshopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WebshopAdapter.WebshopViewHolder holder, int position) {
        final Webshop mCurrentWebshop = mWebshops.get(position);
        String mCosplayWebshopName = mCurrentWebshop.mCosplayWebshopName;
        String mCosplayWebshopLink = mCurrentWebshop.mCosplayWebshopLink;
        holder.mCosplayWebshopLink.setText(mCosplayWebshopLink);
        holder.mCosplayWebsiteName.setText(mCosplayWebshopName);
        View itemView = holder.itemView;
        itemView.findViewById(R.id.ConstraintLayout_Webshop);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebshopViewModel = new WebshopViewModel(mApplication);

                final Dialog mDialog;
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                final View WebshopPopUpView = mLayoutInflater.inflate(R.layout.cosplay_webshop, null);
                final EditText mSiteName, mSiteLink;
                Button mBtnCancel, mBtnUpdate;
                mSiteLink = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteLink);
                mSiteName = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteName);
                mBtnUpdate = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteAdd);
                mBtnCancel = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteCancel);
                TextView mUpdateTitle = WebshopPopUpView.findViewById(R.id.txt_cosplayWebshop);

                mBtnUpdate.setText(R.string.UpdateDb);
                mUpdateTitle.setText(R.string.UpdateWebshop);
                mSiteLink.setText(mCurrentWebshop.mCosplayWebshopLink);
                mSiteName.setText(mCurrentWebshop.mCosplayWebshopName);
                mDialogBuilder.setView(WebshopPopUpView);
                mDialog = mDialogBuilder.create();
                mDialog.show();
                mBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mBtnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Webshop mTempWebshop = new Webshop(mCurrentWebshop.mCosplayId, mCurrentWebshop.mCosplayWebshopId, mSiteName.getText().toString(), mSiteLink.getText().toString());
                        mWebshopViewModel.update(mTempWebshop);
                        mDialog.dismiss();
                    }
                });
            }
        });
    }

    public Webshop getWebshopAtPosition(int position) {
        return mWebshops.get(position);
    }

    @Override
    public int getItemCount() {
        if (mWebshops != null) {
            return mWebshops.size();
        } else {
            return 0;
        }
    }

    public void setWebshops(List<Webshop> webshops) {
        mWebshops = webshops;
        notifyDataSetChanged();
    }


}
