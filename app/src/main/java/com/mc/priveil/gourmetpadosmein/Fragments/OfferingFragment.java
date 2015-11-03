package com.mc.priveil.gourmetpadosmein.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mc.priveil.gourmetpadosmein.Adapters.OfferingAdapter;
import com.mc.priveil.gourmetpadosmein.OfferingForm;
import com.mc.priveil.gourmetpadosmein.R;

import java.util.ArrayList;


/**
 * Created by Srishti on 25/10/2015.
 */
public class OfferingFragment extends Fragment {
    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
    public final static String MESSAGE_EMAIL = "com.mc.priveil.gourmetpadosmein.EMAIL";

    public final static String MESSAGE_OBJECTID = "com.mc.priveil.gourmetpadosmein.OBJECTID";

    RecyclerView recyclerView;
    OfferingAdapter offeringAdapter;
    String email;
    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(null);
        View view = inflater.inflate(R.layout.fragment_offerings, container, false);

        if(isConnected() != true){
            Toast.makeText(getActivity(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }

        else{
            recyclerView = (RecyclerView) view.findViewById(R.id.list_offerings);

            ArrayList<String> names;
            ArrayList<ArrayList<String>> cuisines;
            final ArrayList<String> object_ids;
            ArrayList<Double> distances;

            Bundle bundle = getArguments();
            name = (String) bundle.getSerializable(MESSAGE_NAME);
            names = (ArrayList<String>) bundle.getSerializable("names");
            cuisines = (ArrayList<ArrayList<String>>) bundle.getSerializable("cuisines");
            object_ids = (ArrayList<String>) bundle.getSerializable("object_ids");
            distances = (ArrayList<Double>) bundle.getSerializable("distances");

           /* The app will crash on the next line if you've tried to refresh after turning the net on.
            This is because if your net was off, then simply a toast would've been thrown at you,
            but if you turned it on after it was off while the app was running,
            it crashes. It  doesn't receive the intent from which it is supposed to get email.*/

            email  = bundle.getSerializable("email").toString();

//        Log.d("Test", String.format("Proxy object name: %s", itemlist.get(0)));

            offeringAdapter = new OfferingAdapter(name, names, cuisines, object_ids, distances, email);
            recyclerView.setAdapter(offeringAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            FloatingActionButton fab_add_offering = (FloatingActionButton) view.findViewById(R.id.fab_add_offering);
            fab_add_offering.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ui = new Intent(getActivity(), OfferingForm.class);
                    ui.putExtra(MESSAGE_NAME, name);
                    ui.putExtra(MESSAGE_EMAIL, email);
                    ui.putExtra(MESSAGE_OBJECTID, "LOL");
                    startActivity(ui);
//                Toast.makeText(getActivity(), "ss", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }


        return view;
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
