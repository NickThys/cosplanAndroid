package com.example.cosplan.data.cosplay.shoppingList;

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

import java.util.List;

public class ShoppingListPartAdapter extends RecyclerView.Adapter<ShoppingListPartAdapter.ShoppingListPartViewHolder> {
    private List<ShoppingListPart> mShoppingListParts;
    private final LayoutInflater mLayoutInflater;
    private final Application mApplication;

    public List<ShoppingListPart> getShoppingListParts() {
        return mShoppingListParts;
    }

    public ShoppingListPartAdapter(Context context, Application application) {
        mLayoutInflater = LayoutInflater.from(context);
        mApplication = application;
    }


    public static class ShoppingListPartViewHolder extends RecyclerView.ViewHolder {
        private final TextView mShoppingListListPartName, mShoppingListListPartShop;
        private final CheckBox mShoppingListPartPacked;

        public ShoppingListPartViewHolder(@NonNull View itemView) {
            super(itemView);
            mShoppingListListPartName = itemView.findViewById(R.id.TextView_ShoppingListPartName);
            mShoppingListListPartShop = itemView.findViewById(R.id.TextView_ShoppingListPartShop);
            mShoppingListPartPacked = itemView.findViewById(R.id.CheckBox_ShoppingListPartChecked);
        }
    }

    public ShoppingListPart getShoppingListPartAtPosition(int position) {
        return mShoppingListParts.get(position);
    }

    public void setShoppingListParts(List<ShoppingListPart> shoppingListParts) {
        mShoppingListParts = shoppingListParts;
    }


    @NonNull
    @Override
    public ShoppingListPartAdapter.ShoppingListPartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.shoppinglist_part_row, parent, false);
        return new ShoppingListPartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingListPartAdapter.ShoppingListPartViewHolder holder, int position) {
        final ShoppingListPart mCurrentShoppingListPart = mShoppingListParts.get(position);
        String mCosplayShoppingListPartName = mCurrentShoppingListPart.mCosplayShoppingListPartName;
        String mCosplayShoppingListPartShop = mCurrentShoppingListPart.mCosplayShoppingListPartShop;
        boolean mCosplayShoppingListPartChecked = mCurrentShoppingListPart.mCosplayShoppingListPartChecked;
        holder.mShoppingListListPartName.setText(mCosplayShoppingListPartName);
        holder.mShoppingListPartPacked.setChecked(mCosplayShoppingListPartChecked);
        holder.mShoppingListListPartShop.setText(mCosplayShoppingListPartShop);
        holder.mShoppingListPartPacked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShoppingListPartViewModel viewModel = new ShoppingListPartViewModel(mApplication);
                ShoppingListPart mTempShoppingListPart = new ShoppingListPart(mCurrentShoppingListPart.mCosplayId, mCurrentShoppingListPart.mCosplayShoppingLIstPartId, mCurrentShoppingListPart.mCosplayShoppingListPartShop, mCurrentShoppingListPart.mCosplayShoppingListPartName, holder.mShoppingListPartPacked.isChecked());

                viewModel.update(mTempShoppingListPart);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mShoppingListParts != null) {
            return mShoppingListParts.size();
        } else {
            return 0;
        }
    }


}
