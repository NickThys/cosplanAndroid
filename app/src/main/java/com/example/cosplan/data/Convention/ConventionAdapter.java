package com.example.cosplan.data.Convention;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import java.util.List;

public class ConventionAdapter extends RecyclerView.Adapter<ConventionAdapter.ConventionViewHolder> {
    private List<Convention> mConventions;
    private final LayoutInflater mInflater;
    public ConventionAdapter(Context context){
        mInflater=LayoutInflater.from(context);
    }

    class ConventionViewHolder extends RecyclerView.ViewHolder{
        private final TextView ConventionName;
        private final TextView ConventionPlace;
        private final TextView ConventionDate;
        public ConventionViewHolder(@NonNull View itemView) {
            super(itemView);
            ConventionName=itemView.findViewById(R.id.convention_name);
            ConventionPlace=itemView.findViewById(R.id.Convention_place);
            ConventionDate=itemView.findViewById(R.id.convention_date);
        }
    }

    public void setConventions(List<Convention> conventions){
        mConventions=conventions;
        notifyDataSetChanged();
}

    @Override
    public ConventionAdapter.ConventionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.custom_calender_row,parent,false);
        return new ConventionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConventionAdapter.ConventionViewHolder holder, int position) {
        final Convention current=mConventions.get(position);
        String tempConName=current.mConName;
        String tempConPlace=current.mConPlace;
        String tempConBeginDate=current.mConBeginDate.toString();
        String tempConEndDate=current.mConEndDate.toString();
        holder.ConventionName.setText(tempConName);
        holder.ConventionPlace.setText(tempConPlace);
        holder.ConventionDate.setText("from " + tempConBeginDate +" to "+tempConEndDate);
    }

    @Override
    public int getItemCount() {
        if (mConventions!=null){
            return mConventions.size();
        }
        else {
        return 0;}
    }
}
