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
        final ConventionAdapter conventionAdapter=new ConventionAdapter(requireContext());
        RecyclerView recyclerView=root.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(conventionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper helper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        helper.attachToRecyclerView(recyclerView);
        conventionViewModel=new ViewModelProvider(this).get(ConventionViewModel.class);
        conventionViewModel.getAllConventionsBelgium().observe(getViewLifecycleOwner(), new Observer<List<Convention>>() {
            @Override
            public void onChanged(List<Convention> conventions) {
                conventionAdapter.setConventions(conventions);
            }
        });

        return root;
    }
}