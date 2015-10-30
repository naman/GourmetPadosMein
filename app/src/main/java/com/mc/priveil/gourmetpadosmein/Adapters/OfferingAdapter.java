package com.mc.priveil.gourmetpadosmein.Adapters;

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

    ArrayList<String> names;
    ArrayList<ArrayList<String>> cuisines;
    ArrayList<String> object_ids;
    ArrayList<Double> distances;
    String email;

    public OfferingAdapter(ArrayList<String> names, ArrayList<ArrayList<String>> cuisines, ArrayList<String> object_ids, ArrayList<Double> distances, String email) {
        this.names = names;
        this.cuisines = cuisines;
        this.object_ids = object_ids;
        this.distances = distances;
        this.email = email;
    }

    @Override
    public OfferingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.list_items_offerings, viewGroup, false);
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
        viewholder.cuisine.setText(cuisines_);
        viewholder.objectid.setText(object_ids.get(i));
        /*
        float distance = Float.parseFloat(distances.get(i).toString())/1000;
        viewholder.distance.setText(String.valueOf(distance));
        */
        viewholder.distance.setText(distances.get(i).toString());

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

            distance = (TextView) itemView.findViewById(R.id.distance);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), OfferingViewActivity.class);
                    intent.putExtra("objectid", objectid.getText().toString());
                    intent.putExtra("email", email);
                    itemView.getContext().startActivity(intent);
                }
            });


        }


    }
}
