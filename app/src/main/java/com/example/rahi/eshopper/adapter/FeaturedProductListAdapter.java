package com.example.rahi.eshopper.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahi.eshopper.CartActivity;
import com.example.rahi.eshopper.MainActivity;
import com.example.rahi.eshopper.ProductDetailActivity;
import com.example.rahi.eshopper.R;
import com.example.rahi.eshopper.model.FeaturedProductModel;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class FeaturedProductListAdapter extends BaseAdapter {
    Activity mContext;
    List<FeaturedProductModel> result;
    LayoutInflater mLayoutInflater;

    public FeaturedProductListAdapter(Activity mContext, List<FeaturedProductModel> result) {
        this.mContext = mContext;
        this.result = result;
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
        ImageView imageViewPhoto;
        //ImageButton imageButtonBuyNow,buttonDetails;
        Button imageButtonBuyNow, buttonDetails;
        TextView txtViewId, txtViewName, txtViewConsume, txtViewPrice, txtViewStockQty;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if (view == null) {

            view = mLayoutInflater.inflate(R.layout.featured_product_list, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.txtViewId = view.findViewById(R.id.txtViewId);
            viewHolder.txtViewName = view.findViewById(R.id.txtViewName);
            viewHolder.txtViewConsume = view.findViewById(R.id.txtViewConsume);
            //viewHolder.textViewDescription = view.findViewById(R.id.tv_ProductDescription);
            //viewHolder.textViewProductStatus = view.findViewById(R.id.tv_ProductStatus);
            viewHolder.txtViewPrice = view.findViewById(R.id.txtViewPrice);
            viewHolder.txtViewStockQty = view.findViewById(R.id.txtViewStockQty);
            viewHolder.imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
            viewHolder.imageButtonBuyNow = view.findViewById(R.id.imageButtonBuyNow);
            viewHolder.buttonDetails = view.findViewById(R.id.buttonDetails);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //getProductId = viewHolder.textViewPId.getText().toString();
        final FeaturedProductModel featuredProductModel = result.get(position);

        viewHolder.txtViewId.setText(String.valueOf(featuredProductModel.getProductId()));
        viewHolder.txtViewName.setText(String.valueOf(featuredProductModel.getProductName()));
        viewHolder.txtViewConsume.setText(String.valueOf(featuredProductModel.getConsume()));
        //viewHolder.textViewDescription.setText("N/A");
        //viewHolder.textViewProductStatus.setText("N/A");
        viewHolder.txtViewPrice.setText(featuredProductModel.getNewPrice() + " Tk");
        viewHolder.txtViewStockQty.setText("In Stock : " + featuredProductModel.getStock());
        String vPhotoLink = "https://mdabdurrahim.com/laravel/eshopper/" + featuredProductModel.getImageOption();
        loadImageFromUrl(viewHolder.imageViewPhoto, vPhotoLink);

        final String vProductId = viewHolder.txtViewId.getText().toString();

        viewHolder.imageButtonBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CartActivity.class);
                intent.putExtra("product", (Serializable) featuredProductModel);
                mContext.startActivity(intent);

                //doCartIncremet();

                //Toast.makeText(mContext, "Buy : " + f.getProductName(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("product", (Serializable) featuredProductModel);
                intent.putExtra("Pro_Id", vProductId);
                mContext.startActivity(intent);

                //Toast.makeText(mContext, "Details : " + featuredProductModel.getManufacturerName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    //    int mCount=0;
    //
    //    private void doCartIncremet() {
    //        mCount++;
    //        //mCountTv.setText(String.valueOf(mCount));
    //
    //        //Writing Data in Shared Preference
    //        SharedPreferences mCountSP =mContext.getSharedPreferences("mCount", Context.MODE_PRIVATE);
    //        SharedPreferences.Editor mCountEditor = mCountSP.edit();
    //        mCountEditor.putString("mCount", String.valueOf(mCount));
    //        mCountEditor.commit();
    //
    //        //(MainActivity)mContext.findViewById(R.id.count_tv_layout).setTe;
    //    }

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
