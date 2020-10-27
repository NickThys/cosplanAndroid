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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.data.Convention.Convention;
import com.example.cosplan.data.Convention.ConventionAdapter;
import com.example.cosplan.data.Convention.ConventionViewModel;

import java.util.List;

public class CalenderFragment extends Fragment {

    private ConventionViewModel conventionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        conventionViewModel =ViewModelProviders.of(this).get(ConventionViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_calender, container, false);
        final ConventionAdapter conventionAdapterBelgium=new ConventionAdapter(requireContext());
        final ConventionAdapter conventionAdapterNetherland=new ConventionAdapter(requireContext());


        //RecyclerView Belgium
        RecyclerView recyclerViewBelgium=root.findViewById(R.id.recyclerViewBelgium);
        recyclerViewBelgium.setAdapter(conventionAdapterBelgium);
        recyclerViewBelgium.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper helperBelgium=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        helperBelgium.attachToRecyclerView(recyclerViewBelgium);
        conventionViewModel=new ViewModelProvider(this).get(ConventionViewModel.class);
        conventionViewModel.getAllConventionsBelgium().observe(getViewLifecycleOwner(), new Observer<List<Convention>>() {
            @Override
            public void onChanged(List<Convention> conventions) {
                conventionAdapterBelgium.setConventions(conventions);
            }
        });
        //RecyclerView Netherland
        RecyclerView recyclerViewNetherland=root.findViewById(R.id.recyclerViewNetherland);
        recyclerViewNetherland.setAdapter(conventionAdapterNetherland);
        recyclerViewNetherland.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper helperNetherland=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        helperNetherland.attachToRecyclerView(recyclerViewNetherland);
        conventionViewModel.getAllConventionsNetherland().observe(getViewLifecycleOwner(), new Observer<List<Convention>>() {
            @Override
            public void onChanged(List<Convention> conventions) {
                conventionAdapterNetherland.setConventions(conventions);
            }
        });



        return root;
    }
}