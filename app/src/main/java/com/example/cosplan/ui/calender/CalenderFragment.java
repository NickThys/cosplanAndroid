package com.example.cosplan.ui.calender;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cosplan.R;
import com.example.cosplan.data.Convention.ConventionViewModel;

public class CalenderFragment extends Fragment {

    private ConventionViewModel conventionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        conventionViewModel =
                ViewModelProviders.of(this).get(ConventionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calender, container, false);
        final TextView textView = root.findViewById(R.id.text_calender);

        return root;
    }
}