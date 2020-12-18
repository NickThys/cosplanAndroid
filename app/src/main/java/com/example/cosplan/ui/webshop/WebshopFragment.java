package com.example.cosplan.ui.webshop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.data.webshop.WebshopAdapter;
import com.example.cosplan.data.webshop.Webshop;
import com.example.cosplan.data.webshop.WebshopViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class WebshopFragment extends Fragment {

    private WebshopViewModel mWebshopViewModel;
    private WebshopAdapter mWebshopAdapter;
    private AlertDialog.Builder mDialogBuilder;
    private Dialog mDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_webshop, container, false);

        mWebshopAdapter = new WebshopAdapter(requireContext(), getActivity().getApplication());


        RecyclerView mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mWebshopAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        //Delete webshop
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Webshop myWebshop = mWebshopAdapter.getWebshopAtPosition(position);
                        deleteDialog(myWebshop);
                    }
                }
        );
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mWebshopViewModel = new ViewModelProvider(this).get(WebshopViewModel.class);
        mWebshopViewModel.getAllWebshops().observe(getViewLifecycleOwner(), new Observer<List<Webshop>>() {
            @Override
            public void onChanged(List<Webshop> webshops) {
                mWebshopAdapter.setWebshops(webshops);
            }
        });

        //add new webshop
        FloatingActionButton mFabAddWebshop = root.findViewById(R.id.Fab_WebshopAdd);
        mFabAddWebshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWebshopDialog();
            }
        });

        return root;
    }

    public void deleteDialog(final Webshop mWebshop) {
        mDialogBuilder = new AlertDialog.Builder(requireContext());
        final View mDeleteCosplayView = getLayoutInflater().inflate(R.layout.delete, null);
        TextView mDeleteText = mDeleteCosplayView.findViewById(R.id.TextView_DeleteTitle);

        mDeleteText.setText(String.format("%s%s", getString(R.string.ConformationDeleteCheckListPart), mWebshop.mWebshopName));
        final Button mBtnDelete, mBtnCancel;
        mBtnCancel = mDeleteCosplayView.findViewById(R.id.Btn_DeleteCancel);
        mBtnDelete = mDeleteCosplayView.findViewById(R.id.Btn_DeleteDelete);

        mDialogBuilder.setView(mDeleteCosplayView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebshopViewModel.delete(mWebshop);
                Toast.makeText(requireContext(), mWebshop.mWebshopName + " deleted", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                mWebshopAdapter.notifyDataSetChanged();
            }

        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mWebshopAdapter.notifyDataSetChanged();

            }
        });
    }

    private void AddWebshopDialog() {
        mDialogBuilder = new AlertDialog.Builder(requireContext());

        final View WebshopPopUpView = getLayoutInflater().inflate(R.layout.cosplay_webshop, null);
        final EditText mSiteName, mSiteLink;
        Button mCancel, mAdd;

        mSiteLink = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteLink);
        mSiteName = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteName);
        mAdd = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteAdd);
        mCancel = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteCancel);
        mDialogBuilder.setView(WebshopPopUpView);
        mDialog = mDialogBuilder.create();
        mDialog.show();
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(v);
                mDialog.dismiss();
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mSiteName.getText().toString())|| TextUtils.isEmpty(mSiteLink.getText().toString())) {

                    Toast.makeText(requireContext(), R.string.FillOutAllFields, Toast.LENGTH_SHORT).show();

                } else {
                    Webshop temp = new Webshop(0, mSiteName.getText().toString(), mSiteLink.getText().toString());
                    mWebshopViewModel.insert(temp);
                    closeKeyboard(v);
                    mDialog.dismiss();
                }
            }
        });
    }

    private void closeKeyboard(View view) {InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}