package com.mc.priveil.gourmetpadosmein.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mc.priveil.gourmetpadosmein.Adapters.OfferingAdapter;
import com.mc.priveil.gourmetpadosmein.R;

import java.util.ArrayList;


/**
 * Created by Srishti on 25/10/2015.
 */
public class OfferingFragment extends Fragment {
    RecyclerView recyclerView;
    OfferingAdapter offeringAdapter;
String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(null);
        View view = inflater.inflate(R.layout.fragment_offerings, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_offerings);

        ArrayList<String> names;
        ArrayList<ArrayList<String>> cuisines;
        final ArrayList<String> object_ids;
        ArrayList<Double> distances;

        Bundle bundle = getArguments();
        names = (ArrayList<String>) bundle.getSerializable("names");
        cuisines = (ArrayList<ArrayList<String>>) bundle.getSerializable("cuisines");
        object_ids = (ArrayList<String>) bundle.getSerializable("object_ids");
        distances = (ArrayList<Double>) bundle.getSerializable("distances");
        email  = bundle.getSerializable("email").toString();

//        Log.d("Test", String.format("Proxy object name: %s", itemlist.get(0)));

        offeringAdapter = new OfferingAdapter(names, cuisines, object_ids, distances, email);
        recyclerView.setAdapter(offeringAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FloatingActionButton fab_add_offering = (FloatingActionButton) view.findViewById(R.id.fab_add_offering);
        fab_add_offering.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Intent ui = new Intent(getActivity(), OfferingForm.class);
//                ui.putExtra(MESSAGE_NAME, name);
//                ui.putExtra(MESSAGE_EMAIL, email);
                Toast.makeText(getActivity(), "ss", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        return view;
    }
}
