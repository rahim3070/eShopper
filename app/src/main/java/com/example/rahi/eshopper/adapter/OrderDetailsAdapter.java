package com.example.rahi.eshopper.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rahi.eshopper.R;
import com.example.rahi.eshopper.model.OrderDetailsModel;

import java.util.List;

public class OrderDetailsAdapter extends BaseAdapter {
    Activity mContext;
    List<OrderDetailsModel> result;
    LayoutInflater mLayoutInflater;
    private final String vCusId;

    public OrderDetailsAdapter(Activity mContext, List<OrderDetailsModel> result, String vCusId) {
        this.mContext = mContext;
        this.result = result;
        this.vCusId = vCusId;
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
        TextView customerId, oredrId, invoiceNo, paymentType, orderDate, orderStatus, customerCode, customerName, contactNo, customerEmail, address;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //final OrderDetailsModel orderDetailsModel = result.get(position);
        for (final OrderDetailsModel orderDetailsModel : result) {
            if (vCusId.trim().equals(String.valueOf(orderDetailsModel.getCustomerId()).trim())) {
                ViewHolder viewHolder;

                if (view == null) {

                    view = mLayoutInflater.inflate(R.layout.order_details_list, parent, false);
                    viewHolder = new ViewHolder();

                    viewHolder.customerId = view.findViewById(R.id.customerId);
                    viewHolder.oredrId = view.findViewById(R.id.oredrId);
                    viewHolder.invoiceNo = view.findViewById(R.id.invoiceNo);
                    viewHolder.paymentType = view.findViewById(R.id.paymentType);
                    viewHolder.orderDate = view.findViewById(R.id.orderDate);
                    viewHolder.orderStatus = view.findViewById(R.id.orderStatus);
                    viewHolder.customerCode = view.findViewById(R.id.customerCode);
                    viewHolder.customerName = view.findViewById(R.id.customerName);
                    viewHolder.contactNo = view.findViewById(R.id.contactNo);
                    viewHolder.customerEmail = view.findViewById(R.id.customerEmail);
                    viewHolder.address = view.findViewById(R.id.address);

                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }

                viewHolder.customerId.setText(String.valueOf(orderDetailsModel.getCustomerId()));
                viewHolder.oredrId.setText(String.valueOf(orderDetailsModel.getOrderId()));
                viewHolder.invoiceNo.setText(String.valueOf(orderDetailsModel.getTrxId()));
                viewHolder.paymentType.setText(String.valueOf(orderDetailsModel.getPaymentType()));
                viewHolder.orderDate.setText(String.valueOf(orderDetailsModel.getOrderDate()));
                viewHolder.orderStatus.setText(String.valueOf(orderDetailsModel.getOrderStatus()));
                viewHolder.customerCode.setText(String.valueOf(orderDetailsModel.getCustomerCode()));
                viewHolder.customerName.setText(String.valueOf(orderDetailsModel.getCustomerName()));
                viewHolder.contactNo.setText(String.valueOf(orderDetailsModel.getContactNo()));
                viewHolder.customerEmail.setText(String.valueOf(orderDetailsModel.getCustomerEmail()));
                viewHolder.address.setText(String.valueOf(orderDetailsModel.getAddress()));
            }
        }

        return view;
    }
}
