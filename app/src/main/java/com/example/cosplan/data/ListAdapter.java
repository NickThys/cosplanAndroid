package com.example.cosplan.data;


import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.ui.webshop.WebshopFragment;
import com.example.cosplan.ui.webshop.updateFragment;


import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.WebshopViewHolder> {
    private List<Webshop> mWebshops;
    private final LayoutInflater mInflater;

    public ListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    class WebshopViewHolder extends RecyclerView.ViewHolder {
        private final TextView WebsiteName;
        private final TextView textviewLink;

        public WebshopViewHolder(@NonNull View itemView) {
            super(itemView);
            WebsiteName = itemView.findViewById(R.id.textviewName);
            textviewLink = itemView.findViewById(R.id.textviewLink);
        }
    }

    @Override
    public WebshopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.custum_row, parent, false);
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
    public void onBindViewHolder(@NonNull WebshopViewHolder holder, int position) {


        Webshop current = mWebshops.get(position);
        String tempName = current.mSiteName;
        String tempLink = current.mSiteLink;
        holder.textviewLink.setText(tempLink);

        holder.WebsiteName.setText(tempName);
        holder.itemView.findViewById(R.id.rowLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_webshop_to_updateFragment);
            }
        });

    }

}
