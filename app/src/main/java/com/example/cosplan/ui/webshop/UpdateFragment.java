package com.example.cosplan.ui.webshop;

import android.content.Context;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.cosplan.R;
import com.example.cosplan.data.webshop.Webshop;
import com.example.cosplan.data.webshop.WebshopViewModel;


public class UpdateFragment extends Fragment {

    private WebshopViewModel mWebshopViewModel ;
    TextView mName,mLink;
    public UpdateFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_update, container, false);
        mWebshopViewModel= new ViewModelProvider(this).get(WebshopViewModel.class);
        final Webshop temp=UpdateFragmentArgs.fromBundle(getArguments()).getCurrentWebshop();

        mName=view.findViewById(R.id.UpdateWebsiteName);
        mLink=view.findViewById(R.id.UpdateWebsiteLink);
        mName.setText(temp.mSiteName);
        mLink.setText(temp.mSiteLink);
        Button Update=view.findViewById(R.id.btn_UpdateDb);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUpLink=mLink.getText().toString();
                String mUpName=mName.getText().toString();
                Webshop mUpWebshop=new Webshop(temp.mId,mUpName,mUpLink);
                mWebshopViewModel.update(mUpWebshop);
                Navigation.findNavController(v).navigate(R.id.action_updateFragment_to_nav_webshop);
                closeKeyboard(v);
            }
        });

        return view;
    }
    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}