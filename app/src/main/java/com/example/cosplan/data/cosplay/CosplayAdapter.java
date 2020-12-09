package com.example.cosplan.data.cosplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.ui.home.CosplayFragmentDirections;

import java.text.DecimalFormat;
import java.util.List;

public class CosplayAdapter extends RecyclerView.Adapter<CosplayAdapter.CosplayViewHolder> {
    private List<Cosplay> mCosplays;
    private final LayoutInflater mLayoutInflater;

    public CosplayAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CosplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.cosplay_normalview, parent, false);
        return new CosplayViewHolder(itemView);
    }

    public void setCosplays(List<Cosplay> cosplays) {
        mCosplays = cosplays;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CosplayViewHolder holder, int position) {
        final Cosplay mCurrentCosplay = mCosplays.get(position);

        DecimalFormat form=new DecimalFormat("0.00");
        holder.mCosplayName.setText(mCurrentCosplay.mCosplayName);
        holder.mCosplayEndDate.setText(mCurrentCosplay.mCosplayEndDate);
        holder.mCosplayPercentage.setText(String.format("%s %%", form.format(mCurrentCosplay.mCosplayPercentage)));
        holder.mCosplayImg.setImageBitmap(mCurrentCosplay.mCosplayIMG);

        View itemView=holder.itemView;
        itemView.findViewById(R.id.cosplayRowLayout);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CosplayFragmentDirections.ActionNavHomeToCosplayScreen action=CosplayFragmentDirections.actionNavHomeToCosplayScreen(mCurrentCosplay);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCosplays != null) {
            return mCosplays.size();
        } else {
            return 0;
        }
    }

    public static class CosplayViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCosplayName,mCosplayEndDate,mCosplayPercentage;
        private final ImageView mCosplayImg;

        public CosplayViewHolder(@NonNull View itemView) {
            super(itemView);
            mCosplayName = itemView.findViewById(R.id.TextView_CosplayName);
            mCosplayEndDate = itemView.findViewById(R.id.TextView_CosplayEndDate);
            mCosplayPercentage = itemView.findViewById(R.id.TextView_CosplayPercentage);
            mCosplayImg = itemView.findViewById(R.id.ImageView_CosplayImage);
        }
    }
    public Cosplay getCosplayAtPosition(int position){
        return mCosplays.get(position);
    }
}
