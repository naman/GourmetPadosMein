package com.mc.priveil.gourmetpadosmein.Models;

import java.io.Serializable;

/**
 * Created by Srishti on 25/10/2015.
 */
public class FoodOffering implements Serializable {
    String offeringName;
    String cuisine;


    public FoodOffering(String offeringName, String cuisine) {
        this.offeringName = offeringName;
        this.cuisine = cuisine;

    }

    public String getOfferingName() {
        return offeringName;
    }

    public void setOfferingName(String offeringName) {
        this.offeringName = offeringName;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
