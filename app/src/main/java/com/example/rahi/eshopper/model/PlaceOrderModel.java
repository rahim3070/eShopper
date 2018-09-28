
package com.example.rahi.eshopper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceOrderModel {
    //For Payment & Order
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("trx_id")
    @Expose
    private String trxId;
    @SerializedName("customer_id")
    @Expose
    private int customerId;
    @SerializedName("shipping_id")
    @Expose
    private int shippingId;
    @SerializedName("payment_id")
    @Expose
    private int paymentId;
    @SerializedName("order_total")
    @Expose
    private String orderTotal;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("entry_by")
    @Expose
    private String entryBy;

    //For Order Details
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
    private List<PlaceOrderModel> result = null;

    public PlaceOrderModel(String paymentType, String trxId, int customerId, int shippingId, String orderTotal, String orderDate, String deliveryDate, String entryBy) {
        this.paymentType = paymentType;
        this.trxId = trxId;
        this.customerId = customerId;
        this.shippingId = shippingId;
        this.orderTotal = orderTotal;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.entryBy = entryBy;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getShippingId() {
        return shippingId;
    }

    public void setShippingId(int shippingId) {
        this.shippingId = shippingId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getEntryBy() {
        return entryBy;
    }

    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
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


    public List<PlaceOrderModel> getResult() {
        return result;
    }

    public void setResult(List<PlaceOrderModel> result) {
        this.result = result;
    }
}
