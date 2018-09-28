package com.example.rahi.eshopper.utility;

import com.example.rahi.eshopper.model.CartItemModel;
import com.example.rahi.eshopper.model.FeaturedProductModel;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static List<CartItemModel> items = new ArrayList<CartItemModel>();

    public static void insert(CartItemModel cartItemModel) {
        items.add(cartItemModel);
    }

    public static void update(FeaturedProductModel featuredProductModel){
        int index = getIndex(featuredProductModel);
        int qty = items.get(index).getQuantity() + 1;
        items.get(index).setQuantity(qty);
    }

    public static void remove(FeaturedProductModel featuredProductModel) {
        int index = getIndex(featuredProductModel);
        items.remove(index);
    }

    public static void removeAll() {
        items.clear();
    }

    public static List<CartItemModel> contents() {
        return items;
    }

    public static double total() {
        double s = 0;
        for (CartItemModel cartItemModel : items) {
            s += cartItemModel.getFeaturedProductModel().getNewPrice() * cartItemModel.getQuantity();
        }

        return s;
    }

    public static boolean isExists(FeaturedProductModel featuredProductModel) {
        return getIndex(featuredProductModel) != -1;
    }

    private static int getIndex(FeaturedProductModel featuredProductModel) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getFeaturedProductModel().getProductId() == featuredProductModel.getProductId()) {
                return i;
            }
        }

        return -1;
    }
}
