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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.data.Coplay.Webshop.WebshopViewModel;
import com.example.cosplan.ui.webshop.WebshopFragmentDirections;


import java.util.List;

public class WebshopAdapter extends RecyclerView.Adapter<WebshopAdapter.WebshopViewHolder> {
    private List<Webshop> mWebshops;
    private final LayoutInflater mInflater;
    final com.example.cosplan.data.webshop.WebshopViewModel mWebshopViewModel;
    AlertDialog.Builder dialogBuilder;
    public WebshopAdapter(Context context, Application mAppcation) {
        mInflater = LayoutInflater.from(context);
        mWebshopViewModel=new com.example.cosplan.data.webshop.WebshopViewModel(mAppcation);
        dialogBuilder=new AlertDialog.Builder(context);
    }


    class WebshopViewHolder extends RecyclerView.ViewHolder {
        private final TextView WebsiteName;
        private final TextView textviewLink;

        public WebshopViewHolder(@NonNull View itemView) {
            super(itemView);
            WebsiteName = itemView.findViewById(R.id.TextView_WebshopName);
            textviewLink = itemView.findViewById(R.id.TextView_WebshopLink);
        }
    }

    @Override
    public WebshopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.custum_webshop_row, parent, false);
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


        final Webshop current = mWebshops.get(position);
        String tempName = current.mSiteName;
        String tempLink = current.mSiteLink;
        holder.textviewLink.setText(tempLink);

        holder.WebsiteName.setText(tempName);
        View itemView = holder.itemView;
        itemView.findViewById(R.id.ConstraintLayout_Webshop);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog;

                final View WebshopPopUpView=mInflater.inflate(R.layout.cosplay_webshop,null);
                final EditText mSiteName,mSiteLink;
                Button mCancel,mAdd;
                mSiteLink=WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteLink);
                mSiteName=WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteName);
                mAdd=WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteAdd);
                mCancel=WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteCancel);
                TextView mTitle=WebshopPopUpView.findViewById(R.id.txt_cosplayWebshop);

                mAdd.setText("Update");
                mTitle.setText("Update webshop");
                mSiteLink.setText(current.mSiteLink);
                mSiteName.setText(current.mSiteName);
                dialogBuilder.setView(WebshopPopUpView);
                dialog=dialogBuilder.create();
                dialog.show();
                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Webshop temp=new Webshop(current.mId,mSiteName.getText().toString(),mSiteLink.getText().toString());


                        mWebshopViewModel.update(temp);
                        dialog.dismiss();
                    }
                });
            }
        });

    }
    public Webshop getWebshopAtPosition(int position){
        return mWebshops.get(position);
    }

}
