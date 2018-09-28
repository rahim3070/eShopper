package com.example.rahi.eshopper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahi.eshopper.adapter.OrderDetailsAdapter;
import com.example.rahi.eshopper.api_interface.eShopperApiInterface;
import com.example.rahi.eshopper.model.OrderDetailsModel;
import com.example.rahi.eshopper.utility.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

    eShopperApiInterface apiInterface;

    TextView customerId;
    ListView orderDetailsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderDetailsLV = findViewById(R.id.orderDetailsLV);
        customerId = findViewById(R.id.customerId);

        //        //For Intent Value
        //        Bundle bundle = getIntent().getExtras();
        //        if (bundle != null) {
        //            String value = bundle.getString("OrderId");
        //            orderIdFromIntent.setText(value);
        //        }

        //To Read Data From Shared Preference
        SharedPreferences sharedPreferences = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("CustomerId")) {
            String userId = sharedPreferences.getString("CustomerId", "User Email Not Found");

            customerId.setText(userId);
        } else {
            Toast.makeText(this, "No User Found", Toast.LENGTH_SHORT).show();
        }

        showServerOrderDetailData();
    }

    public void showServerOrderDetailData() {
        final String vCusId = customerId.getText().toString();
        apiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);

        Call<OrderDetailsModel> phpApiCallArrCall = apiInterface.getDataForOrderDetails();
        phpApiCallArrCall.enqueue(new Callback<OrderDetailsModel>() {
            @Override
            public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                if (response != null) {
                    OrderDetailsModel f = response.body();
                    List<OrderDetailsModel> list = f.getResult();

                    for (final OrderDetailsModel orderDetailsModel : list) {
                        if (vCusId.trim().equals(String.valueOf(orderDetailsModel.getCustomerId()).trim())) {
                            OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(OrderDetailsActivity.this, list, vCusId);
                            orderDetailsLV.setAdapter(orderDetailsAdapter);
                        }
                    }

                    //Toast.makeText(OrderDetailsActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                }

                //                ProductDetailsModel products = response.body();
//                List<ProductDetailsModel> arrayList = products.getResult();
//                String s = "";
//                ArrayList<String> arrayList1 = new ArrayList<>();
//                for (final ProductDetailsModel productDetailsModel : arrayList) {
//                    if (vProductId.trim().equals(productDetailsModel.getProductId().trim())) {
//                        s = productDetailsModel.getProductId();
//
//                        arrayList1.add(s);
//
//                        String vPhotoLink = "https://mdabdurrahim.com/laravel/eshopper/" + productDetailsModel.getImageOption();
//                        loadImageFromUrl(imageViewProduct, vPhotoLink);
//
//                        imageButtonBuyNow.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                //Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
//                                //intent.putExtra("product", (Serializable) productDetailsModel);
//                                //startActivity(intent);
//
//                                //Toast.makeText(mContext, "Buy : " + f.getProductName(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        getProductId = productDetailsModel.getProductId();
//                        getProductName = productDetailsModel.getProductName();
//                        getProductShortDesc = productDetailsModel.getShortDescription();
//                        getProductCategory = productDetailsModel.getCategoryName();
//                        getProductNewPrice = productDetailsModel.getNewPrice() + " Tk";
//                        getProductOldPrice = productDetailsModel.getOldPrice() + " Tk";
//                        getProductStock = productDetailsModel.getStock() + " Pcs";
//                        getProductCondition = "New";
//                        getProductBrand = productDetailsModel.getManufacturerName();
//                    }
//                }
//
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ProductDetailActivity.this, android.R.layout.simple_list_item_1, arrayList1);
//                productDetailsListView.setAdapter(arrayAdapter);
//
//                getProductDetailsData();
            }

            @Override
            public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, "Please Connect with Internet", Toast.LENGTH_LONG).show();
                //Toast.makeText(OrderDetailsActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
            }
        });

        //        final Call<OrderDetailsModel> featuredProductModelCall = apiInterface.getDataForOrderDetails();
        //        featuredProductModelCall.enqueue(new Callback<OrderDetailsModel>() {
        //            @Override
        //            public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
        //                if (response != null) {
        //                    OrderDetailsModel f = response.body();
        //                    List<OrderDetailsModel> list = f.getResult();
        //                    List<String> getAllData = new ArrayList<>();
        //
        //                    for (OrderDetailsModel order : list) {
        //                        if (order.getOrderId() == Integer.parseInt(vOrderId)) {
        //                            strCustomerName = order.getCustomerName();
        //                            intOrderQty = order.getSubmitQty() - order.getReturnQty();
        //                       /* for (int i=1; i<=intOrderQty;i++){
        //
        //                        }*/
        //
        //                            strProductName = order.getProductName();
        //                            strOrderDate = order.getOrderDate();
        //                            strOrderStatus = order.getOrderStatus();
        //                            productPrice = order.getMrp();
        //                            totalOrderAmount = order.getTotal();
        //                        }
        //                    }
        //
        //                    String listString = strProductName + " " + productPrice + " " + strOrderStatus + " " + strOrderDate;
        //                    ArrayList<String> stringArrayList = new ArrayList<>();
        //                    stringArrayList.add(listString);
        //                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(OrderDetailsActivity.this, android.R.layout.simple_list_item_1, stringArrayList);
        //                    listViewOrderHistory.setAdapter(arrayAdapter);
        //                }
        //            }
        //
        //            @Override
        //            public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
        //                Toast.makeText(OrderDetailsActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG);
        //
        //            }
        //        });
    }

    @Override
    public void onBackPressed() {

    }
}
