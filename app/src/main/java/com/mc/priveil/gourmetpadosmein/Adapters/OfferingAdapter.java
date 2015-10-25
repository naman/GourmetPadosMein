package com.mc.priveil.gourmetpadosmein.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mc.priveil.gourmetpadosmein.R;

import java.util.ArrayList;

/**
 * Created by Srishti on 25/10/2015.
 */
public class OfferingAdapter extends RecyclerView.Adapter<OfferingAdapter.OfferingViewHolder> {

    ArrayList<String> names;
    ArrayList<ArrayList<String>> cuisines;


    public OfferingAdapter(ArrayList<String> names, ArrayList<ArrayList<String>> cuisines) {
        this.names = names;
        this.cuisines = cuisines;
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
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class OfferingViewHolder extends RecyclerView.ViewHolder {
        TextView offeringName;
        TextView cuisine;

        public OfferingViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            offeringName = (TextView) itemView.findViewById(R.id.food_offering);
            cuisine = (TextView) itemView.findViewById(R.id.cuisine);
        }
    }
}
