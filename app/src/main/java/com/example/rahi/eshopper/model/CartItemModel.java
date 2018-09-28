package com.example.rahi.eshopper.model;

public class CartItemModel {
    private FeaturedProductModel featuredProductModel;
    private int quantity;

    public CartItemModel(FeaturedProductModel featuredProductModel, int quantity) {
        this.featuredProductModel = featuredProductModel;
        this.quantity = quantity;
    }

    public FeaturedProductModel getFeaturedProductModel() {
        return featuredProductModel;
    }

    public void setFeaturedProductModel(FeaturedProductModel featuredProductModel) {
        this.featuredProductModel = featuredProductModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
