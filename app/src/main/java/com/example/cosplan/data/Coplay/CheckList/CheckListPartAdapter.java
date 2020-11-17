package com.example.cosplan.data.Coplay.CheckList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import java.util.List;

public class CheckListPartAdapter extends RecyclerView.Adapter<CheckListPartAdapter.CheckListPartViewHolder> {
    private List<ChecklistPart> mCheckListParts;
    private final LayoutInflater mInflater;

    public CheckListPartAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public class CheckListPartViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCheckListPartName;
        private final CheckBox mCheckListPartPacked;

        public CheckListPartViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckListPartName = itemView.findViewById(R.id.txtView_CheckListPartName);
            mCheckListPartPacked = itemView.findViewById(R.id.CheckBox_CheckListPartPacked);
        }
    }
    public ChecklistPart getChecklistPartAtPosition(int position){return mCheckListParts.get(position);}
    public void setCheckListParts(List<ChecklistPart>checkListParts){mCheckListParts=checkListParts;notifyDataSetChanged();}
    @NonNull
    @Override
    public CheckListPartAdapter.CheckListPartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.checklist_part_row, parent, false);
        return new CheckListPartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListPartAdapter.CheckListPartViewHolder holder, int position) {
        final ChecklistPart current=mCheckListParts.get(position);
        String tempName=current.mCosplayCheckListPartName;
        boolean tempPacked=current.mCosplayCheckListPartChecked;
        holder.mCheckListPartName.setText(tempName);
        holder.mCheckListPartPacked.setEnabled(tempPacked);
    }

    @Override
    public int getItemCount() {
        if (mCheckListParts != null) {
            return mCheckListParts.size();
        } else {
            return 0;
        }
    }
}
