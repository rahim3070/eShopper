
package com.example.rahi.eshopper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductDetailsModel implements Serializable {

    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("manufacturer_id")
    @Expose
    private String manufacturerId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("long_description")
    @Expose
    private String longDescription;
    @SerializedName("new_price")
    @Expose
    private int newPrice;
    @SerializedName("old_price")
    @Expose
    private String oldPrice;
    @SerializedName("image_option")
    @Expose
    private String imageOption;
    @SerializedName("manufacturer_name")
    @Expose
    private String manufacturerName;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("stock")
    @Expose
    private int stock;
    @SerializedName("consume")
    @Expose
    private int consume;
    @SerializedName("result")
    @Expose
    private List<ProductDetailsModel> result = null;

    public ProductDetailsModel(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(int newPrice) {
        this.newPrice = newPrice;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public List<ProductDetailsModel> getResult() {
        return result;
    }

    public void setResult(List<ProductDetailsModel> result) {
        this.result = result;
    }

}
