package com.mc.priveil.gourmetpadosmein.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mc.priveil.gourmetpadosmein.OfferingViewActivity;
import com.mc.priveil.gourmetpadosmein.R;

import java.util.ArrayList;

/**
 * Created by Srishti on 25/10/2015.
 */
public class OfferingAdapter extends RecyclerView.Adapter<OfferingAdapter.OfferingViewHolder> {
    public final static String MESSAGE_NAME = "com.mc.priveil.gourmetpadosmein.NAME";
    ArrayList<String> names;
    ArrayList<ArrayList<String>> cuisines;
    ArrayList<String> object_ids;
    ArrayList<Double> distances;
    //String email;
    String name;

    public OfferingAdapter(String name, ArrayList<String> names, ArrayList<ArrayList<String>> cuisines, ArrayList<String> object_ids, ArrayList<Double> distances) {
        this.name = name;
        this.names = names;
        this.cuisines = cuisines;
        this.object_ids = object_ids;
        this.distances = distances;
        //this.email = email;
    }



    @Override
    public OfferingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.list_items, viewGroup, false);
        OfferingViewHolder viewholder = new OfferingViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(OfferingViewHolder viewholder, int i) {
        viewholder.offeringName.setText(names.get(i));
        String cuisines_ = "";
        for (String s : cuisines.get(i)) {
            cuisines_ += s + ",";
        }
        cuisines_ = cuisines_.substring(0, cuisines_.length() - 1);
        viewholder.cuisine.setText(cuisines_);
        viewholder.objectid.setText(object_ids.get(i));

        Float distance = Float.parseFloat(distances.get(i).toString()) / 1000;
        String dist = String.format("%.2f", distance);
        viewholder.distance.setText(dist + "km");

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class OfferingViewHolder extends RecyclerView.ViewHolder {
        TextView offeringName;
        TextView cuisine;
        TextView objectid;
        TextView distance;

        public OfferingViewHolder(final View itemView) {
            super(itemView);
            itemView.setClickable(true);
            offeringName = (TextView) itemView.findViewById(R.id.food_offering);
            cuisine = (TextView) itemView.findViewById(R.id.cuisine);
            objectid = (TextView) itemView.findViewById(R.id.objectid);
            objectid.setVisibility(View.GONE);
//            String fromWhereItCame = "myOffering";
            distance = (TextView) itemView.findViewById(R.id.distance);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context c = itemView.getContext();
                    Intent intent = new Intent(c, OfferingViewActivity.class);
                    intent.putExtra("objectid", objectid.getText().toString());
//                    intent.putExtra("activity","myOffering");
                 //   intent.putExtra("email", email);
                    intent.putExtra(MESSAGE_NAME, name);
                    c.startActivity(intent);

//                    Toast.makeText(itemView.getContext(), "HOLA!", Toast.LENGTH_SHORT).show();
                }
            });
/*
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    Intent intent = new Intent(itemView.getContext(), OfferingViewActivity.class);
                    intent.putExtra("objectid", objectid.getText().toString());
                    intent.putExtra("email", email);
                    intent.putExtra(MESSAGE_NAME, name);
                    itemView.getContext().startActivity(intent);

                    return false;
                }
            });*/


        }


    }
}
