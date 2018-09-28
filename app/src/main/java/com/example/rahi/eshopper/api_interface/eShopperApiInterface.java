package com.example.rahi.eshopper.api_interface;

import com.example.rahi.eshopper.model.CheckOutModel;
import com.example.rahi.eshopper.model.FeaturedProductModel;
import com.example.rahi.eshopper.model.GetOrderIdModel;
import com.example.rahi.eshopper.model.GetShippingIdModel;
import com.example.rahi.eshopper.model.OrderDetailsModel;
import com.example.rahi.eshopper.model.PlaceOrderDetailsModel;
import com.example.rahi.eshopper.model.PlaceOrderModel;
import com.example.rahi.eshopper.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface eShopperApiInterface {
    @GET("Your Login")
    Call<UserModel> getAllLoginDetail();

    @POST("Your Register")
    Call<UserModel> postDataForCreateUserRegister(@Body UserModel result);

    @PUT("Your Register")
    Call<UserModel> putDataForUpdateUserInfo(@Body UserModel result);

    @GET("Your Product_featured")
    Call<FeaturedProductModel> getAllFeaturedProductDetail();

    @GET("Your Product_featured")
    Call<FeaturedProductModel> getDataForProductDetail();

    @POST("Your Checkout")
    Call<CheckOutModel> postDataForShippingDetail(@Body CheckOutModel result);

    @GET("Your Get_shipping_id")
    Call<GetShippingIdModel> getDataForShippingId();

    @POST("Your Place_order")
    Call<PlaceOrderModel> postDataForPlaceOrder(@Body PlaceOrderModel result);

    @GET("Your Get_order_id")
    Call<GetOrderIdModel> getDataForOrderId();

    @POST("Your Place_order_details")
    Call<PlaceOrderDetailsModel> postDataForPlaceOrderDetails(@Body PlaceOrderDetailsModel result);

    @PUT("Your Product_featured")
    Call<FeaturedProductModel> putDataForUpdateConsume(@Body FeaturedProductModel result);

    @GET("Your Order_details")
    Call<OrderDetailsModel> getDataForOrderDetails();
}
