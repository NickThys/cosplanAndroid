package com.example.cosplan.data.Coplay.Webshop;

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
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.data.Coplay.Part.PartViewModel;
import com.example.cosplan.ui.webshop.WebshopFragmentDirections;

import java.util.List;

public class WebshopAdapter extends RecyclerView.Adapter<WebshopAdapter.WebshopViewHolder> {
    private List<Webshop> mWebshops;
    private final LayoutInflater mInflater;
    private final Context context;
    private WebshopViewModel mWebshopViewModel;
    private Application mApplication;
    public WebshopAdapter(Context context,Application mApplication){mInflater=LayoutInflater.from(context);this.context=context;this.mApplication=mApplication;}

    public class WebshopViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCosplayWebsiteName,mCosplayWebshopLink;
        public WebshopViewHolder(@NonNull View itemView) {
            super(itemView);
            mCosplayWebsiteName=itemView.findViewById(R.id.textviewName);
            mCosplayWebshopLink=itemView.findViewById(R.id.textviewLink);
        }
    }

    @NonNull
    @Override
    public WebshopAdapter.WebshopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.custum_webshop_row,parent,false);
        return new WebshopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WebshopAdapter.WebshopViewHolder holder, int position) {
        final Webshop current=mWebshops.get(position);
        String tempName = current.mCosplayWebshopName;
        String tempLink = current.mCosplayWebshopLink;
        holder.mCosplayWebshopLink.setText(tempLink);
        holder.mCosplayWebsiteName.setText(tempName);
        View itemView = holder.itemView;
        itemView.findViewById(R.id.rowLayout);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mWebshopViewModel=new WebshopViewModel(mApplication);

                final Dialog dialog;
                AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
                final View WebshopPopUpView=mInflater.inflate(R.layout.cosplay_webshop,null);
                final EditText mSiteName,mSiteLink;
                Button mCancel,mAdd;
                mSiteLink=WebshopPopUpView.findViewById(R.id.EditText_WebsiteLink);
                mSiteName=WebshopPopUpView.findViewById(R.id.EditText_WebsiteName);
                mAdd=WebshopPopUpView.findViewById(R.id.btn_addCosplayWebshop);
                mCancel=WebshopPopUpView.findViewById(R.id.btn_CancelCosplayWebshop);
                TextView mTitle=WebshopPopUpView.findViewById(R.id.txt_cosplayWebshop);

                mAdd.setText("Update");
                mTitle.setText("Update webshop");
                mSiteLink.setText(current.mCosplayWebshopLink);
                mSiteName.setText(current.mCosplayWebshopName);

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
                        Webshop temp=new Webshop(current.mCosplayId,current.mCosplayWebshopId,mSiteName.getText().toString(),mSiteLink.getText().toString());


                        mWebshopViewModel.update(temp);
                        dialog.dismiss();
                    }
                });
            }
        });
    }
    public Webshop getWebshopAtPosition(int position){return mWebshops.get(position);}
    @Override
    public int getItemCount() {
        if (mWebshops!=null){
            return mWebshops.size();
        }
        else{
            return 0;
        }
    }
    public void setWebshops(List<Webshop> webshops){
        mWebshops=webshops;
        notifyDataSetChanged();
    }


}
