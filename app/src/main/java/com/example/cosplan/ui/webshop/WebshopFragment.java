package com.example.cosplan.ui.webshop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_webshop, container, false);

        final WebshopAdapter webshopAdapter = new WebshopAdapter(requireContext());


        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(webshopAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Webshop myWebshop = webshopAdapter.getWebshopAtPosition(position);
                        Toast.makeText(getContext(), "Deleting " + myWebshop.getWebsiteName(), Toast.LENGTH_SHORT).show();
                        webshopViewModel.delete(myWebshop);
                    }
                }
        );
        helper.attachToRecyclerView(recyclerView);
        webshopViewModel = new ViewModelProvider(this).get(WebshopViewModel.class);
        webshopViewModel.getAllWebshops().observe(getViewLifecycleOwner(), new Observer<List<Webshop>>() {
            @Override
            public void onChanged(List<Webshop> webshops) {
                webshopAdapter.setWebshops(webshops);
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(root).navigate(R.id.action_nav_webshop_to_addFragment);
            }
        });
        return root;
    }

}