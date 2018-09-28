
package com.example.rahi.eshopper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceOrderDetailsModel {

    @SerializedName("order_id")
    @Expose
    private int orderId;
    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("submit_qty")
    @Expose
    private String submitQty;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("result")
    @Expose
    private List<PlaceOrderDetailsModel> result = null;

    public PlaceOrderDetailsModel(int orderId, int productId, String productName, String submitQty, String mrp) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.submitQty = submitQty;
        this.mrp = mrp;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSubmitQty() {
        return submitQty;
    }

    public void setSubmitQty(String submitQty) {
        this.submitQty = submitQty;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public List<PlaceOrderDetailsModel> getResult() {
        return result;
    }

    public void setResult(List<PlaceOrderDetailsModel> result) {
        this.result = result;
    }


}
