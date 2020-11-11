package com.example.cosplan.ui.cosplay_screen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.cosplan.R;
import com.example.cosplan.data.Coplay.CosplayViewModel;
import com.example.cosplan.data.Cosplay_Part.Part;
import com.example.cosplan.data.Cosplay_Part.PartAdapter;
import com.example.cosplan.data.Cosplay_Part.PartViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class cosplay_parts extends Fragment {
    private PartViewModel partViewModel;
   private AlertDialog.Builder dialogBuilder;

    private AlertDialog dialog;
   private EditText mPartName, mPartLink,mPartCost, mPartEndDate ;
    private Spinner mPartmakeBuy;
    ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.BuyMake, android.R.layout.simple_spinner_item);
    private ImageView mPartImage;
    private Button mPartChooseImage, mPartCancel, mPartAddPart;
    private FloatingActionButton mfabAddPart;

    private DatePickerDialog.OnDateSetListener mEndDateSetListener;
   public static final int GALLERY_REQUEST_CODE_PART = 2;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_cosplay_parts, container, false);
        final PartAdapter partAdapterMake=new PartAdapter(requireContext());
        final PartAdapter partAdapterBuy=new PartAdapter(requireContext());

        //recyclerview Make
        RecyclerView recyclerViewMake=root.findViewById(R.id.RecViewMake);
        recyclerViewMake.setAdapter(partAdapterMake);
        recyclerViewMake.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerViewMake.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper helperMake=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // TODO: 11/11/2020 Add delete on swipe
            }
        });
        helperMake.attachToRecyclerView(recyclerViewMake);

        partViewModel = new ViewModelProvider(this).get(PartViewModel.class);
        partViewModel.getAllPartsToMake().observe(getViewLifecycleOwner(), new Observer<List<Part>>() {
            @Override
            public void onChanged(List<Part> parts) {
                partAdapterMake.setParts(parts);
            }
        });
        //recyclerview buy
        RecyclerView recyclerViewBuy=root.findViewById(R.id.RecViewBuy);
        recyclerViewBuy.setAdapter(partAdapterBuy);
        recyclerViewBuy.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerViewBuy.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper helperBuy=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // TODO: 11/11/2020 Add delete on swipe
            }
        });
        helperBuy.attachToRecyclerView(recyclerViewBuy);

        partViewModel = new ViewModelProvider(this).get(PartViewModel.class);
        partViewModel.getAllPartsToBuy().observe(getViewLifecycleOwner(), new Observer<List<Part>>() {
            @Override
            public void onChanged(List<Part> parts) {
                partAdapterBuy.setParts(parts);
            }
        });
        //fab to add a new cosplay part
        mfabAddPart=root.findViewById(R.id.fabAddPart);
        mfabAddPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewPartDialog();
            }
        });
        return root;
    }

    public void createNewPartDialog() {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View PartPopUpView = getLayoutInflater().inflate(R.layout.add_cosplay_part, null);
        mPartName = PartPopUpView.findViewById(R.id.EditTextCosplayName);
        mPartmakeBuy=PartPopUpView.findViewById(R.id.Spinner_PartBuyMake);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPartmakeBuy.setAdapter(adapter);
        mPartLink=PartPopUpView.findViewById(R.id.EditText_partLink);
        mPartEndDate = PartPopUpView.findViewById(R.id.editTextEndDate);
        mPartCost = PartPopUpView.findViewById(R.id.editTextBudget);
        mPartImage = PartPopUpView.findViewById(R.id.imageViewCosplayImgPreview);
        mPartChooseImage = PartPopUpView.findViewById(R.id.btnChooseCosplayImg);
        mPartCancel = PartPopUpView.findViewById(R.id.buttonCancel);
        mPartAddPart = PartPopUpView.findViewById(R.id.buttonAddCosplay);

        dialogBuilder.setView(PartPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();


        //create dateSelector and add the selected date to the Edit text
        mPartEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int year;
                    int month;
                    int day;
                    String mtemp = mPartEndDate.getText().toString().trim();
                    if (mtemp.matches("")) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        String mDateComlete = mPartEndDate.getText().toString();
                        String[] mDate = mDateComlete.split("/");
                        day = Integer.parseInt(mDate[0].trim());
                        month = Integer.parseInt(mDate[1].trim());
                        year = Integer.parseInt(mDate[2].trim());
                        month = month - 1;
                    }

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mEndDateSetListener, year, month, day);
                    datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            }
        });
        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mPartEndDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        //Cancel. dismiss the popup
        mPartCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Choose the picture from the gallery
        mPartChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.txt_chooseImg_intent)), GALLERY_REQUEST_CODE_PART);

            }
        });
        mPartAddPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Part temp=new Part();
                temp.mCosplayPartId=0;
                temp.mCosplayPartName=mPartName.getText().toString();
                temp.mCosplayPartBuyMake=mPartmakeBuy.getSelectedItem().toString();
                temp.mCosplayPartLink=mPartLink.getText().toString();
                temp.mCosplayPartCost=Double.parseDouble(mPartCost.getText().toString());
                temp.mCosplayPartEndDate=mPartEndDate.getText().toString();
                temp.mCosplayPartImg=((BitmapDrawable)mPartImage.getDrawable()).getBitmap();
                partViewModel.insert(temp);
                dialog.dismiss();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE_PART) {
            if (data == null) {
                return;
            } else {
                Uri imageData = data.getData();
                mPartImage.setImageURI(imageData);
            }
        }
    }
}