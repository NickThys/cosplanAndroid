package com.example.cosplan.data.webshop;


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


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WebshopAdapter extends RecyclerView.Adapter<WebshopAdapter.WebshopViewHolder> {
    private List<Webshop> mWebshops;
    private final LayoutInflater mLayoutInflater;
    final com.example.cosplan.data.webshop.WebshopViewModel mWebshopViewModel;
    final AlertDialog.Builder dialogBuilder;

    public WebshopAdapter(Context context, Application mApplication) {
        mLayoutInflater = LayoutInflater.from(context);
        mWebshopViewModel = new com.example.cosplan.data.webshop.WebshopViewModel(mApplication);
        dialogBuilder = new AlertDialog.Builder(context);
    }

    static class WebshopViewHolder extends RecyclerView.ViewHolder {
        private final TextView mWebshopName, mWebshopLink;

        public WebshopViewHolder(@NonNull View itemView) {
            super(itemView);
            mWebshopName = itemView.findViewById(R.id.TextView_WebshopName);
            mWebshopLink = itemView.findViewById(R.id.TextView_WebshopLink);
        }
    }

    @NotNull
    @Override
    public WebshopViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custum_webshop_row, parent, false);
        return new WebshopViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if (mWebshops != null) {
            return mWebshops.size();
        } else {
            return 0;
        }
    }

    public void setWebshops(List<Webshop> webshop) {
        mWebshops = webshop;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final WebshopViewHolder holder, int position) {
        final Webshop mCurrentWebshop = mWebshops.get(position);
        String mWebshopName = mCurrentWebshop.mWebshopName;
        String mWebshopLink = mCurrentWebshop.mWebshopLink;

        holder.mWebshopLink.setText(mWebshopLink);
        holder.mWebshopName.setText(mWebshopName);

        View itemView = holder.itemView;
        itemView.findViewById(R.id.ConstraintLayout_Webshop);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog mDialog;
                final View WebshopPopUpView = mLayoutInflater.inflate(R.layout.cosplay_webshop, null);
                final EditText mWebshopName, mWebshopLink;
                final Button mCancel, mAdd;

                mWebshopLink = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteLink);
                mWebshopName = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteName);
                mAdd = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteAdd);
                mCancel = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteCancel);
                TextView mTitle = WebshopPopUpView.findViewById(R.id.txt_cosplayWebshop);

                mAdd.setText(R.string.UpdateDb);
                mTitle.setText(R.string.UpdateWebshop);
                mWebshopLink.setText(mCurrentWebshop.mWebshopLink);
                mWebshopName.setText(mCurrentWebshop.mWebshopName);

                dialogBuilder.setView(WebshopPopUpView);
                mDialog = dialogBuilder.create();
                mDialog.show();

                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Webshop temp = new Webshop(mCurrentWebshop.mWebshopId, mWebshopName.getText().toString(), mWebshopLink.getText().toString());
                        mWebshopViewModel.update(temp);
                        mDialog.dismiss();
                    }
                });
            }
        });

    }

    public Webshop getWebshopAtPosition(int position) {
        return mWebshops.get(position);
    }

}
