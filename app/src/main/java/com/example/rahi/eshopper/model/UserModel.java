
package com.example.rahi.eshopper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserModel {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
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
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("result")
    @Expose
    private List<UserModel> result = null;

    public UserModel(String customerCode, String customerName, String contactNo, String customerEmail, String password) {
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.contactNo = contactNo;
        this.customerEmail = customerEmail;
        this.password = password;
    }

    public UserModel(String customerEmail, String password) {
        this.customerEmail = customerEmail;
        this.password = password;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserModel> getResult() {
        return result;
    }

    public void setResult(List<UserModel> result) {
        this.result = result;
    }
}
