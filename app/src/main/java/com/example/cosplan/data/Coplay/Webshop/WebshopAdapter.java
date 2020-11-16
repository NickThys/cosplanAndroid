package com.example.cosplan.data.Coplay.Webshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.ui.webshop.WebshopFragmentDirections;

import java.util.List;

public class WebshopAdapter extends RecyclerView.Adapter<WebshopAdapter.WebshopViewHolder> {
    private List<Webshop> mWebshops;
    private final LayoutInflater mInflater;

    public WebshopAdapter(Context context){mInflater=LayoutInflater.from(context);}

    public class WebshopViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCosplayWebsiteName,mCosplayWebshopLink;
        public WebshopViewHolder(@NonNull View itemView) {
            super(itemView);
            mCosplayWebshopLink=itemView.findViewById(R.id.textviewName);
            mCosplayWebsiteName=itemView.findViewById(R.id.textviewLink);
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
