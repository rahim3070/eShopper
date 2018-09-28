
package com.example.rahi.eshopper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckOutModel {

    @SerializedName("customer_id")
    @Expose
    private int customerId;
    @SerializedName("f_name")
    @Expose
    private String fName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("email_address")
    @Expose
    private String emailAddress;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("result")
    @Expose
    private List<CheckOutModel> result = null;

    public CheckOutModel(int customerId, String fName, String companyName, String emailAddress, String mobile, String address, String zipCode, String country) {
        this.customerId = customerId;
        this.fName = fName;
        this.companyName = companyName;
        this.emailAddress = emailAddress;
        this.mobile = mobile;
        this.address = address;
        this.zipCode = zipCode;
        this.country = country;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<CheckOutModel> getResult() {
        return result;
    }

    public void setResult(List<CheckOutModel> result) {
        this.result = result;
    }
}
