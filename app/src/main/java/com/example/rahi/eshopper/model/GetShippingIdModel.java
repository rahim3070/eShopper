
package com.example.rahi.eshopper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShippingIdModel {

    @SerializedName("shipping_id")
    @Expose
    private int shippingId;
    @SerializedName("customer_id")
    @Expose
    private int customerId;
    @SerializedName("result")
    @Expose
    private List<GetShippingIdModel> result = null;

    public int getShippingId() {
        return shippingId;
    }

    public void setShippingId(int shippingId) {
        this.shippingId = shippingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<GetShippingIdModel> getResult() {
        return result;
    }

    public void setResult(List<GetShippingIdModel> result) {
        this.result = result;
    }

}
