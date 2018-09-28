
package com.example.rahi.eshopper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderIdModel {

    @SerializedName("order_id")
    @Expose
    private int orderId;
    @SerializedName("customer_id")
    @Expose
    private int customerId;
    @SerializedName("result")
    @Expose
    private List<GetOrderIdModel> result = null;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<GetOrderIdModel> getResult() {
        return result;
    }

    public void setResult(List<GetOrderIdModel> result) {
        this.result = result;
    }
}
