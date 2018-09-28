package com.example.rahi.eshopper.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahi.eshopper.CartActivity;
import com.example.rahi.eshopper.R;
import com.example.rahi.eshopper.model.FeaturedProductModel;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class FeaturedProductDetailsListAdapter extends BaseAdapter {
    Activity mContext;
    List<FeaturedProductModel> result;
    LayoutInflater mLayoutInflater;
    private final String vProductId;

    public FeaturedProductDetailsListAdapter(Activity mContext, List<FeaturedProductModel> result, String vProductId) {
        this.mContext = mContext;
        this.result = result;
        this.vProductId = vProductId;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        ImageView imageViewProduct;
        ImageButton imageButtonBuyNow;
        //Button imageButtonBuyNow;
        TextView productId, productName, productConsume, productShortDesc, productCategory, productNewPrice, productOldPrice, productStock, productCondition, productBrand;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //final FeaturedProductModel featuredProductModel = result.get(position);
        for (final FeaturedProductModel featuredProductModel : result) {
            if (vProductId.trim().equals(String.valueOf(featuredProductModel.getProductId()).trim())) {
                ViewHolder viewHolder;

                if (view == null) {

                    view = mLayoutInflater.inflate(R.layout.product_details_list, parent, false);
                    viewHolder = new ViewHolder();

                    viewHolder.imageViewProduct = view.findViewById(R.id.imageViewProduct);
                    viewHolder.imageButtonBuyNow = view.findViewById(R.id.imageButtonBuyNow);

                    viewHolder.productId = view.findViewById(R.id.productId);
                    viewHolder.productName = view.findViewById(R.id.productName);
                    viewHolder.productConsume = view.findViewById(R.id.productConsume);
                    viewHolder.productShortDesc = view.findViewById(R.id.productShortDesc);
                    viewHolder.productCategory = view.findViewById(R.id.productCategory);
                    viewHolder.productNewPrice = view.findViewById(R.id.productNewPrice);
                    viewHolder.productOldPrice = view.findViewById(R.id.productOldPrice);
                    viewHolder.productStock = view.findViewById(R.id.productStock);
                    viewHolder.productCondition = view.findViewById(R.id.productCondition);
                    viewHolder.productBrand = view.findViewById(R.id.productBrand);

                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }

                String vPhotoLink = "https://mdabdurrahim.com/laravel/eshopper/" + featuredProductModel.getImageOption();
                loadImageFromUrl(viewHolder.imageViewProduct, vPhotoLink);

                viewHolder.imageButtonBuyNow = view.findViewById(R.id.imageButtonBuyNow);

                viewHolder.productId.setText(String.valueOf(featuredProductModel.getProductId()));
                viewHolder.productName.setText(String.valueOf(featuredProductModel.getProductName()));
                viewHolder.productConsume.setText(String.valueOf(featuredProductModel.getConsume()));
                viewHolder.productShortDesc.setText(String.valueOf(featuredProductModel.getShortDescription()));
                viewHolder.productCategory.setText(String.valueOf(featuredProductModel.getCategoryName()));
                viewHolder.productNewPrice.setText(String.valueOf(featuredProductModel.getNewPrice()));
                viewHolder.productOldPrice.setText(String.valueOf(featuredProductModel.getOldPrice()));
                viewHolder.productStock.setText("In Stock : " + String.valueOf(featuredProductModel.getStock()));
                viewHolder.productCondition.setText(String.valueOf("New"));
                viewHolder.productBrand.setText(String.valueOf(featuredProductModel.getManufacturerName()));

                viewHolder.imageButtonBuyNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, CartActivity.class);
                        intent.putExtra("product", (Serializable) featuredProductModel);
                        mContext.startActivity(intent);

                        //Toast.makeText(mContext, "Buy : " + f.getProductName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        return view;
    }

    private void loadImageFromUrl(ImageView imageViewPhoto, String url) {
        Picasso.with(this.mContext).load(url).placeholder(R.mipmap.ic_launcher)   // Optional
                .error(R.mipmap.ic_launcher)  // if Error
                .into(imageViewPhoto, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
