package com.example.cosplan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.cosplan.data.Coplay.Part;
import com.example.cosplan.data.Coplay.PartViewModel;

public class CosplayScreenPartUpdateFragment extends Fragment {

    private PartViewModel mPartViewModel;
    private EditText mPartName,mPartLink,mPartCost,mPartEndDate,mPartNotes;
    private ImageView mPartImage;
    private Spinner mPartMakeBuy,mPartStatus ;
    private Button mPartCancel,mPartUpdate,mPartChooseImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_cosplay_screen_part_update, container, false);
        mPartViewModel=new ViewModelProvider(this).get(PartViewModel.class);
  //    final Part tempPart=CosplayScreenPartUpdateArgs.fromBundle(getArguments()).getCurrentPart();

        mPartName=v.findViewById(R.id.EditText_PartUpdate_Name);
        mPartLink=v.findViewById(R.id.EditText_PartUpdate_Link);
        mPartCost=v.findViewById(R.id.EditText_PartUpdate_Cost);
        mPartEndDate=v.findViewById(R.id.EditText_PartUpdate_Enddate);
        mPartNotes=v.findViewById(R.id.EditText_PartUpdate_Notes);
        mPartImage=v.findViewById(R.id.imageView_PartUpdate_Img);
        mPartMakeBuy=v.findViewById(R.id.spinner_PartUpdate_MakeBuy);
        mPartStatus=v.findViewById(R.id.spinner_PartUpdate_Status);
        mPartCancel=v.findViewById(R.id.btn_PartUpdate_cancel);
        mPartUpdate=v.findViewById(R.id.btn_PartUpdate_update);
        mPartChooseImage=v.findViewById(R.id.btn_PartUpdate_ChooseImg);


   /*  mPartName.setText(tempPart.mCosplayPartName);
        mPartLink.setText(tempPart.mCosplayPartLink);
        mPartCost.setText(Double.toString(tempPart.mCosplayPartCost));
        mPartEndDate.setText(tempPart.mCosplayPartEndDate);
        mPartImage.setImageBitmap(tempPart.mCosplayPartImg);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.BuyMake, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPartMakeBuy.setAdapter(adapter);
        if (tempPart.mCosplayPartBuyMake=="Buy") {
            mPartMakeBuy.setSelection(0);
        }else {
            mPartMakeBuy.setSelection(1);
        }
*/
        return v;
    }
}