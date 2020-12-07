package com.example.cosplan.ui.calender;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.data.convention.Convention;
import com.example.cosplan.data.convention.ConventionAdapter;
import com.example.cosplan.data.convention.ConventionViewModel;

import java.util.List;

public class CalenderFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ConventionViewModel mConventionViewModel ;

        final View root = inflater.inflate(R.layout.fragment_calender, container, false);
        final ConventionAdapter mConventionAdapterBelgium=new ConventionAdapter(requireContext());
        final ConventionAdapter mConventionAdapterNetherland=new ConventionAdapter(requireContext());

        //region RecyclerView Belgium
        RecyclerView mRecyclerViewBelgium=root.findViewById(R.id.RecView_CalenderBelguim);
        mRecyclerViewBelgium.setAdapter(mConventionAdapterBelgium);
        mRecyclerViewBelgium.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));
        mRecyclerViewBelgium.setLayoutManager(new LinearLayoutManager(requireContext()));

        ItemTouchHelper mItemTouchHelperBelgium=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        mItemTouchHelperBelgium.attachToRecyclerView(mRecyclerViewBelgium);

        mConventionViewModel =new ViewModelProvider(this).get(ConventionViewModel.class);
        mConventionViewModel.getAllConventionsBelgium().observe(getViewLifecycleOwner(), new Observer<List<Convention>>() {
            @Override
            public void onChanged(List<Convention> conventions) {
                mConventionAdapterBelgium.setConventions(conventions);
            }
        });
        //endregion

        //region RecyclerView Netherland
        RecyclerView mRecyclerViewNetherland=root.findViewById(R.id.RecView_CalenderNetherland);
        mRecyclerViewNetherland.setAdapter(mConventionAdapterNetherland);
        mRecyclerViewNetherland.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));
        mRecyclerViewNetherland.setLayoutManager(new LinearLayoutManager(requireContext()));

        ItemTouchHelper mItemTouchHelperNetherland=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        mItemTouchHelperNetherland.attachToRecyclerView(mRecyclerViewNetherland);
        mConventionViewModel.getAllConventionsNetherland().observe(getViewLifecycleOwner(), new Observer<List<Convention>>() {
            @Override
            public void onChanged(List<Convention> conventions) {
                mConventionAdapterNetherland.setConventions(conventions);
            }
        });
        //endregion

        return root;
    }
}