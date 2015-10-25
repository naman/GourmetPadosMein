package com.mc.priveil.gourmetpadosmein.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mc.priveil.gourmetpadosmein.Models.FoodOffering;
import com.mc.priveil.gourmetpadosmein.R;

import java.util.List;

/**
 * Created by Srishti on 25/10/2015.
 */
public class OfferingAdapter extends RecyclerView.Adapter<OfferingAdapter.OfferingViewHolder> {
/*

    public static class OfferingViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView foodOffering;
        TextView cuisine;
        ImageView foodPhoto;

        OfferingViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            foodOffering = (TextView)itemView.findViewById(R.id.food_offering);
            cuisine = (TextView)itemView.findViewById(R.id.cuisine);
            foodPhoto = (ImageView)itemView.findViewById(R.id.food_photo);
        }
    }

    List<FoodOffering> offerings;
    public OfferingAdapter(FoodOffering food_item){
        this.food_item = food_item;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public OfferingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        OfferingViewHolder offeringViewHolder = new OfferingViewHolder(v);
        return offeringViewHolder;
    }

    @Override
    public void onBindViewHolder(OfferingViewHolder offeringViewHolder, int i) {
        offeringViewHolder.foodOffering.setText(offerings.get(i).food);
        offeringViewHolder.cuisine.setText(offerings.get(i).cuisine);
        offeringViewHolder.foodPhoto.setImageResource(offerings.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return offerings.size();
    }
*/


    List<FoodOffering> items;

    public OfferingAdapter(List<FoodOffering> items) {
        this.items = items;
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
        FoodOffering current = items.get(i);
        viewholder.offeringName.setText(current.getOfferingName());
        viewholder.cuisine.setText(current.getCuisine());
//        viewholder.date.setText(current.getPost_date().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
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
