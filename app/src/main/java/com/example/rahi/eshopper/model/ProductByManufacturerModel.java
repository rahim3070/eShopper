
package com.example.rahi.eshopper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductByManufacturerModel {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("manufacturer_id")
    @Expose
    private String manufacturerId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("new_price")
    @Expose
    private String newPrice;
    @SerializedName("image_option")
    @Expose
    private String imageOption;
    @SerializedName("manufacturer_name")
    @Expose
    private String manufacturerName;
    @SerializedName("result")
    @Expose
    private List<ProductByManufacturerModel> result = null;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getImageOption() {
        return imageOption;
    }

    public void setImageOption(String imageOption) {
        this.imageOption = imageOption;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public List<ProductByManufacturerModel> getResult() {
        return result;
    }

    public void setResult(List<ProductByManufacturerModel> result) {
        this.result = result;
    }

}
