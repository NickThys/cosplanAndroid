package com.example.cosplan.ui.webshop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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

    private WebshopViewModel webshopViewModel;
    WebshopAdapter mWebshopAdapter;
    AlertDialog.Builder dialogBuilder;
    Dialog dialog;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_webshop, container, false);

        mWebshopAdapter = new WebshopAdapter(requireContext(),getActivity().getApplication());


        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mWebshopAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        //Delete webshop
        ItemTouchHelper helper = new ItemTouchHelper(
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

        helper.attachToRecyclerView(recyclerView);
        webshopViewModel = new ViewModelProvider(this).get(WebshopViewModel.class);
        webshopViewModel.getAllWebshops().observe(getViewLifecycleOwner(), new Observer<List<Webshop>>() {
            @Override
            public void onChanged(List<Webshop> webshops) {
                mWebshopAdapter.setWebshops(webshops);
            }
        });

        //add new webshop
        FloatingActionButton fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWebshopDialog();
            }
        });

        return root;
    }
    public void deleteDialog(final Webshop mWebshop) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View deleteCosplayView = getLayoutInflater().inflate(R.layout.delete, null);
        TextView mDeleteText = deleteCosplayView.findViewById(R.id.TextView_DeleteTitle);
        mDeleteText.setText(getString(R.string.ConformationDeleteCheckListPart) + mWebshop.mSiteName);
        final Button yes, no;
        no = deleteCosplayView.findViewById(R.id.Btn_DeleteNo);
        yes = deleteCosplayView.findViewById(R.id.Btn_DeleteYes);
        dialogBuilder.setView(deleteCosplayView);
         dialog = dialogBuilder.create();
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webshopViewModel.delete(mWebshop);
                Toast.makeText(requireContext(), mWebshop.mSiteName + " deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                mWebshopAdapter.notifyDataSetChanged();
            }

        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mWebshopAdapter.notifyDataSetChanged();

            }
        });

    }
    private void AddWebshopDialog(){
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View WebshopPopUpView = getLayoutInflater().inflate(R.layout.cosplay_webshop, null);
        final EditText mSiteName, mSiteLink;
        Button mCancel, mAdd;
        mSiteLink = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteLink);
        mSiteName = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteName);
        mAdd = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteAdd);
        mCancel = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteCancel);
        dialogBuilder.setView(WebshopPopUpView);
        dialog = dialogBuilder.create();
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
                if (inputCheck( mSiteName.getText().toString(),mSiteLink.getText().toString())){
                    Webshop temp = new Webshop ( 0, mSiteName.getText().toString(), mSiteLink.getText().toString());
                    webshopViewModel.insert(temp);
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(requireContext(), R.string.FillOutAllFields,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean inputCheck(String name,String link){
        return !(name==null||link==null);
    }
}