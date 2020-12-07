package com.example.cosplan.data.convention;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConventionAdapter extends RecyclerView.Adapter<ConventionAdapter.ConventionViewHolder> {
    private List<Convention> mConventions;
    private final LayoutInflater mLayoutInflater;

    public ConventionAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    static class ConventionViewHolder extends RecyclerView.ViewHolder {
        private final TextView mConventionName, mConventionPlace, mConventionDate;

        public ConventionViewHolder(@NonNull View itemView) {
            super(itemView);
            mConventionName = itemView.findViewById(R.id.TextView_ConventionName);
            mConventionPlace = itemView.findViewById(R.id.TextView_ConventionPlace);
            mConventionDate = itemView.findViewById(R.id.TextView_ConventionDate);
        }
    }

    public void setConventions(List<Convention> conventions) {
        mConventions = conventions;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public ConventionAdapter.ConventionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_calender_row, parent, false);
        return new ConventionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConventionAdapter.ConventionViewHolder holder, int position) {
        final Convention mCurrentConvention = mConventions.get(position);
        String mConventionName = mCurrentConvention.mConventionName;
        String mConventionPlace = mCurrentConvention.mConventionPlace;
        String mConventionBeginDate = mCurrentConvention.mConventionBeginDate;
        String mConventionEndDate = mCurrentConvention.mConventionEndDate;
        holder.mConventionName.setText(mConventionName);
        holder.mConventionPlace.setText(mConventionPlace);
        holder.mConventionDate.setText(String.format("from %s to %s", mConventionBeginDate, mConventionEndDate));
    }

    @Override
    public int getItemCount() {
        if (mConventions != null) {
            return mConventions.size();
        } else {
            return 0;
        }
    }
}
