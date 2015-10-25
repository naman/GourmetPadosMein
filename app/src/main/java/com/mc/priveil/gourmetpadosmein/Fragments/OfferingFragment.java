package com.mc.priveil.gourmetpadosmein.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mc.priveil.gourmetpadosmein.Adapters.OfferingAdapter;
import com.mc.priveil.gourmetpadosmein.Models.FoodOffering;
import com.mc.priveil.gourmetpadosmein.OfferingListActivity;
import com.mc.priveil.gourmetpadosmein.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Srishti on 25/10/2015.
 */
public class OfferingFragment extends Fragment {
    RecyclerView recyclerView;
    OfferingAdapter offeringAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(null);
        View view = inflater.inflate(R.layout.fragment_offerings, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_offerings);

        List<FoodOffering> offerings = new ArrayList<>();

        Bundle bundle = getArguments();
        FoodOffering food_item = (FoodOffering) bundle.getSerializable("offerings");

        offerings.add(food_item);

        offeringAdapter = new OfferingAdapter(offerings);
        recyclerView.setAdapter(offeringAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FloatingActionButton fab_add_post = (FloatingActionButton) view.findViewById(R.id.fab_add_offering);
        fab_add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(view.getContext(), "FAB pressed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), OfferingListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
