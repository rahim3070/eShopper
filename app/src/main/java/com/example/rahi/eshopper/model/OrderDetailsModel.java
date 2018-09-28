
package com.example.rahi.eshopper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsModel {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("trx_id")
    @Expose
    private String trxId;
    @SerializedName("customer_id")
    @Expose
    private int customerId;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("customer_code")
    @Expose
    private String customerCode;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("submit_qty")
    @Expose
    private String submitQty;
    @SerializedName("return_qty")
    @Expose
    private String returnQty;
    @SerializedName("tp")
    @Expose
    private String tp;
    @SerializedName("vat")
    @Expose
    private String vat;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("result")
    @Expose
    private List<OrderDetailsModel> result = null;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(String returnQty) {
        this.returnQty = returnQty;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<OrderDetailsModel> getResult() {
        return result;
    }

    public void setResult(List<OrderDetailsModel> result) {
        this.result = result;
    }

}
