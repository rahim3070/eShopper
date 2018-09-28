package com.example.rahi.eshopper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahi.eshopper.adapter.FeaturedProductDetailsListAdapter;
import com.example.rahi.eshopper.api_interface.eShopperApiInterface;
import com.example.rahi.eshopper.model.FeaturedProductModel;
import com.example.rahi.eshopper.utility.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    eShopperApiInterface apiInterface;
    TextView productIdFromIntent;
    ListView productDetailsLV;

    ImageView imageViewProduct;
    ImageButton imageButtonBuyNow;
    TextView productId, productName, productShortDesc, productCategory, productNewPrice, productOldPrice, productStock, productCondition, productBrand;

    String getProductId, getProductName, getProductShortDesc, getProductCategory, getProductNewPrice, getProductOldPrice, getProductStock, getProductCondition, getProductBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productIdFromIntent = findViewById(R.id.productIdFromIntent);
        productDetailsLV = findViewById(R.id.productDetailsLV);

        //For Intent Value
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String value = bundle.getString("Pro_Id");
            productIdFromIntent.setText(value);
        }

        getProductDetailsServerData();
    }

    private void getProductDetailsServerData() {
        final String vProductId = productIdFromIntent.getText().toString();
        apiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);

        Call<FeaturedProductModel> phpApiCallArrCall = apiInterface.getDataForProductDetail();
        phpApiCallArrCall.enqueue(new Callback<FeaturedProductModel>() {
            @Override
            public void onResponse(Call<FeaturedProductModel> call, Response<FeaturedProductModel> response) {
                if (response != null) {
                    FeaturedProductModel f = response.body();
                    List<FeaturedProductModel> list = f.getResult();

                    for (final FeaturedProductModel featuredProductModel : list) {
                        if (vProductId.trim().equals(String.valueOf(featuredProductModel.getProductId()).trim())) {
                            FeaturedProductDetailsListAdapter productDetailsListAdapter = new FeaturedProductDetailsListAdapter(ProductDetailActivity.this, list, vProductId);
                            productDetailsLV.setAdapter(productDetailsListAdapter);
                        }
                    }
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
            public void onFailure(Call<FeaturedProductModel> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Please Connect with Internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    //    private void loadImageFromUrl(ImageView imageViewPhoto, String url) {
//        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)   // Optional
//                .error(R.mipmap.ic_launcher)  // if Error
//                .into(imageViewPhoto, new com.squareup.picasso.Callback() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });
//    }
//
//    private void getProductDetailsData() {
//        productId.setText(getProductId);
//        productName.setText(getProductName);
//        productShortDesc.setText(getProductShortDesc);
//        productCategory.setText(getProductCategory);
//        productNewPrice.setText(getProductNewPrice);
//        productOldPrice.setText(getProductOldPrice);
//        productStock.setText(getProductStock);
//        productCondition.setText(getProductCondition);
//        productBrand.setText(getProductBrand);
//    }

    @Override
    public void onBackPressed() {

    }
}
