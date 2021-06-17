package com.ivanboyukliev.plantsirrigationsystem.navmenu.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.plantapi.PlantApiRequest;
import com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.model.PlantFromApi;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static String plantName;

    private TextView plantScientificName;
    private ImageView plantImageView;

    private static PlantApiRequest plantApiRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Intent parentIntent = getActivity().getIntent();

        plantName = parentIntent.getExtras().getString("PlantName");

        plantApiRequest = new PlantApiRequest(plantName, getContext());

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        plantScientificName = root.findViewById(R.id.plantImageHomeMenu);

        plantImageView = root.findViewById(R.id.plantImageHomeMenu);

        plantScientificName = root.findViewById(R.id.plantScientificName);

        homeViewModel.getCurrentPlant().observe(getViewLifecycleOwner(), plantFromApi -> {
            plantScientificName.setText(plantFromApi.getScientificName());
            Picasso.get().load(plantFromApi.getImageUrl())
                    .resize(160, 174)
                    .into(plantImageView);
        });

        return root;
    }

    public static String getPlantName() {
        return plantName;
    }

    public static PlantApiRequest getPlantApiRequest() {
        return plantApiRequest;
    }
}