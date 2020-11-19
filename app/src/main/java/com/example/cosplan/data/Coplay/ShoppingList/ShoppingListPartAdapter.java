package com.example.cosplan.data.Coplay.ShoppingList;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.data.Coplay.CheckList.CheckListPartViewModel;
import com.example.cosplan.data.Coplay.CheckList.ChecklistPart;

import java.util.List;

public class ShoppingListPartAdapter extends RecyclerView.Adapter<ShoppingListPartAdapter.ShoppingListPartViewHolder> {
    private List<ShoppingListPart> mShoppingListParts;
    private List<String> mShoppingListShops;
    private final LayoutInflater mInflater;
    private final Application mApplication;

    public ShoppingListPartAdapter(Context context, Application application) {
        mInflater = LayoutInflater.from(context);
        mApplication = application;
    }


    public class ShoppingListPartViewHolder extends RecyclerView.ViewHolder {
        private final TextView mShoppingListListPartName,mShoppingListListPartShop;
        private final CheckBox mShoppingListPartPacked;

        public ShoppingListPartViewHolder(@NonNull View itemView) {
            super(itemView);
            mShoppingListListPartName = itemView.findViewById(R.id.txtView_ShoppingListPartName);
            mShoppingListListPartShop=itemView.findViewById(R.id.txtView_ShoppingListShop);
            mShoppingListPartPacked = itemView.findViewById(R.id.CheckBox_ShoppingListPart_Checked);
        }
    }

    public ShoppingListPart getShoppingListPartAtPosition(int position) {
        return mShoppingListParts.get(position);
    }

    public void setShoppingListParts(List<ShoppingListPart> shoppingListParts) {
        mShoppingListParts = shoppingListParts;
    }

    public void setShoppingListShops(List<String> shoppingListShops) {
        mShoppingListShops = shoppingListShops;
    }

    @NonNull
    @Override
    public ShoppingListPartAdapter.ShoppingListPartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.shoppinglist_part_row, parent, false);
        return new ShoppingListPartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingListPartAdapter.ShoppingListPartViewHolder holder, int position) {
        final ShoppingListPart current=mShoppingListParts.get(position);
        String tempName=current.mCosplayShoppingListPartName;
        String tempShop=current.mCosplayShoppingListPartShop;
        boolean tempPacked=current.mCosplayShoppingListPartChecked;
        holder.mShoppingListListPartName.setText(tempName);
        holder.mShoppingListPartPacked.setChecked(tempPacked);
        holder.mShoppingListListPartShop.setText(tempShop);
        holder.mShoppingListPartPacked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckListPartViewModel viewModel=new CheckListPartViewModel(mApplication);
                ChecklistPart tempPart=new ChecklistPart(current.mCosplayId,current.mCosplayShoppingLIstPartId,holder.mShoppingListListPartName.getText().toString(),holder.mShoppingListPartPacked.isChecked());
                viewModel.update(tempPart);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mShoppingListShops != null) {
            return mShoppingListParts.size();
        } else {
            return 0;
        }
    }


}
