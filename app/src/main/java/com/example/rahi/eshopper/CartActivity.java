package com.example.rahi.eshopper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahi.eshopper.api_interface.eShopperApiInterface;
import com.example.rahi.eshopper.model.CartItemModel;
import com.example.rahi.eshopper.model.FeaturedProductModel;
import com.example.rahi.eshopper.model.GetOrderIdModel;
import com.example.rahi.eshopper.model.GetShippingIdModel;
import com.example.rahi.eshopper.model.PlaceOrderDetailsModel;
import com.example.rahi.eshopper.model.PlaceOrderModel;
import com.example.rahi.eshopper.utility.Cart;
import com.example.rahi.eshopper.utility.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    eShopperApiInterface postApiInterface;

    ProgressBar loading;

    LinearLayout button, paymentPanel, paymentButtonPanel;
    TextView choiceValue, checkOutInfo, customerIdTV, shippingId, orderId, payment_type, trx_id, customer_id, shipping_id, order_total, order_date, delivery_date, entry_by, product_id, consume, product_name, submit_qty, mrp;
    Button buttonContinueShopping, buttonCancelOrder, buttonCheckout, placeOrderBtn, cancelOrderBtn;
    RadioGroup radioGroup;
    RadioButton CashonDelivery, bKashPayment, DBBLPayment;
    ListView shippingIdLV, orderIdLV;

    TableLayout tableLayoutProduct;

    String getCustomerIdId, getShippingIdId, vCustomerId, getOrderIdId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checkOutInfo = findViewById(R.id.checkOutInfo);
        customerIdTV = findViewById(R.id.customerIdTV);
        shippingId = findViewById(R.id.shippingId);
        orderId = findViewById(R.id.orderId);
        tableLayoutProduct = findViewById(R.id.tableLayoutProduct);
        buttonContinueShopping = findViewById(R.id.buttonContinueShopping);
        buttonCancelOrder = findViewById(R.id.buttonCancelOrder);
        buttonCheckout = findViewById(R.id.buttonCheckout);

        buttonContinueShopping.setOnClickListener(this);
        buttonCancelOrder.setOnClickListener(this);
        buttonCheckout.setOnClickListener(this);

        button = findViewById(R.id.button);
        paymentPanel = findViewById(R.id.paymentPanel);
        paymentButtonPanel = findViewById(R.id.paymentButtonPanel);
        radioGroup = findViewById(R.id.radioGroup);
        CashonDelivery = findViewById(R.id.CashonDelivery);
        bKashPayment = findViewById(R.id.bKashPayment);
        DBBLPayment = findViewById(R.id.DBBLPayment);

        choiceValue = findViewById(R.id.choiceValue);

        placeOrderBtn = findViewById(R.id.placeOrderBtn);
        cancelOrderBtn = findViewById(R.id.cancelOrderBtn);

        shippingIdLV = findViewById(R.id.shippingIdLV);
        orderIdLV = findViewById(R.id.orderIdLV);

        payment_type = findViewById(R.id.payment_type);
        trx_id = findViewById(R.id.trx_id);
        customer_id = findViewById(R.id.customer_id);
        shipping_id = findViewById(R.id.shipping_id);
        order_total = findViewById(R.id.order_total);
        order_date = findViewById(R.id.order_date);
        delivery_date = findViewById(R.id.delivery_date);
        entry_by = findViewById(R.id.entry_by);
        product_id = findViewById(R.id.product_id);
        consume = findViewById(R.id.consume);
        product_name = findViewById(R.id.product_name);
        submit_qty = findViewById(R.id.submit_qty);
        mrp = findViewById(R.id.mrp);

        //loading = findViewById(R.id.loading);

        //radioRG.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) this);
        CashonDelivery.setOnClickListener(this);
        bKashPayment.setOnClickListener(this);
        DBBLPayment.setOnClickListener(this);
        placeOrderBtn.setOnClickListener(this);
        cancelOrderBtn.setOnClickListener(this);

        if (Cart.total() > 0) {
            buttonCheckout.setVisibility(View.VISIBLE);
        } else {
            buttonCheckout.setVisibility(View.GONE);
        }

        //To Read Data From Shared Preference
        SharedPreferences sharedPreferences = getSharedPreferences("PaymentOption", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("PaymentOption")) {
            String vPaymentOption = sharedPreferences.getString("PaymentOption", "Payment Option Not Found");

            checkOutInfo.setText(vPaymentOption);
        } else {
            //Toast.makeText(this, "No Payment Option Found", Toast.LENGTH_SHORT).show();
        }

        String vValue = checkOutInfo.getText().toString();

        if ((vValue.equals("PaymentOption"))) {
            button.setVisibility(View.GONE);
            paymentPanel.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.VISIBLE);
            paymentPanel.setVisibility(View.GONE);
        }

        addCart();
        createColumns();
        fillData();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonContinueShopping) {
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            intent.putExtra("isExists", "2");
            intent.putExtra("cCount", "0");
            startActivity(intent);
        } else if (view.getId() == R.id.buttonCheckout) {
            //To Read Dta From Shared Preference
            SharedPreferences sharedPreferences = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);

            if (sharedPreferences.contains("CustomerId")) {
                //Explicit Intent
                Intent intent = new Intent(CartActivity.this, CheckOutActivity.class);
                startActivity(intent);
            } else {
                //Explicit Intent
                Intent intent = new Intent(CartActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        } else if (view.getId() == R.id.buttonCancelOrder) {
            // For Remove Shared Preference
            SharedPreferences sharedPreferencesPO = getSharedPreferences("PaymentOption", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferencesPO.edit();
            editor1.remove("PaymentOption");
            editor1.commit();

            Cart.removeAll();

            //Explicit Intent
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            intent.putExtra("isExists", "0");
            intent.putExtra("cCount", "0");
            startActivity(intent);
        } else if (view.getId() == R.id.CashonDelivery) {
            getShippingId();
            choiceValue.setText("Pay with cash upon delivery.");
            paymentButtonPanel.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.bKashPayment) {
            getShippingId();
            choiceValue.setText("Send your payment directly to 01675204103 (Personal) via bKash. Please use your Order ID as the payment reference. Your order won’t be shipped until the funds have cleared in our account.");
            paymentButtonPanel.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.DBBLPayment) {
            getShippingId();
            choiceValue.setText("Send your payment directly to 01675204103 (Personal) via DBBL Mobile Banking. Please use your Order ID as the payment reference. Your order won’t be shipped until the funds have cleared in our account.");
            paymentButtonPanel.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.placeOrderBtn) {
            int getSelectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(getSelectedId);
            if (radioButton != null) {
                if (Cart.total() > 0) {
                    // create invoice No
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    String vInvoiceNo = simpleDateFormat.format(calendar.getTimeInMillis());

                    payment_type.setText(radioButton.getText().toString());
                    trx_id.setText(vInvoiceNo);
                    customer_id.setText(customerIdTV.getText().toString());
                    shipping_id.setText(shippingId.getText().toString());
                    order_total.setText(String.valueOf(Cart.total()));
                    order_date.setText(DateFormat.format(calendar.getTimeInMillis()));
                    delivery_date.setText(DateFormat.format(calendar.getTimeInMillis()));
                    entry_by.setText(customerIdTV.getText().toString());

                    String vPayment_Type = payment_type.getText().toString();
                    String vTrx_Id = trx_id.getText().toString();
                    String vCustomer_Id = customer_id.getText().toString();
                    String vShipping_Id = shipping_id.getText().toString();
                    String vOrder_Total = order_total.getText().toString();
                    String vOrder_Date = order_date.getText().toString();
                    String vDelivery_Date = delivery_date.getText().toString();
                    String vEntry_By = entry_by.getText().toString();

                    //For Insert Place Order
                    PlaceOrderModel placeOrderModel = new PlaceOrderModel(vPayment_Type, vTrx_Id, Integer.parseInt(vCustomer_Id), Integer.parseInt(vShipping_Id), vOrder_Total, vOrder_Date, vDelivery_Date, vEntry_By);

                    Call<PlaceOrderModel> userResponseCall = postApiInterface.postDataForPlaceOrder(placeOrderModel);
                    userResponseCall.enqueue(new Callback<PlaceOrderModel>() {
                        @Override
                        public void onResponse(Call<PlaceOrderModel> call, Response<PlaceOrderModel> response) {
                            PlaceOrderModel result = response.body();
                            //Toast.makeText(UserRegistrationActivity.this, "" + result.getCustomerName(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<PlaceOrderModel> call, Throwable t) {
                            //textViewError.setText(t.getMessage().toString());
                        }
                    });

                    getOrderId();
                } else {
                    Toast.makeText(this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                //payment_type.setText("Please Select One");
                Toast.makeText(this, "Please Select a Payment Option", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.cancelOrderBtn) {
            // For Remove Shared Preference
            SharedPreferences sharedPreferencesPO = getSharedPreferences("PaymentOption", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferencesPO.edit();
            editor1.remove("PaymentOption");
            editor1.commit();

            Cart.removeAll();

            //Explicit Intent
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            intent.putExtra("isExists", "0");
            intent.putExtra("cCount", "0");
            startActivity(intent);
        }
    }

    private void getShippingId() {

        //loading.setVisibility(View.VISIBLE);

        //To Read Data From Shared Preference
        SharedPreferences CustomerIdSp = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);

        if (CustomerIdSp.contains("CustomerId")) {
            String username = CustomerIdSp.getString("CustomerId", "Customer Id Not Found");

            vCustomerId = username;
        } else {
            Toast.makeText(this, "No Customer Id Found", Toast.LENGTH_SHORT).show();
        }

        postApiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);

        Call<GetShippingIdModel> phpApiCallArrCall = postApiInterface.getDataForShippingId();
        phpApiCallArrCall.enqueue(new Callback<GetShippingIdModel>() {
            @Override
            public void onResponse(Call<GetShippingIdModel> call, Response<GetShippingIdModel> response) {
                GetShippingIdModel products = response.body();
                List<GetShippingIdModel> arrayList = products.getResult();
                String s = "";
                ArrayList<String> arrayList1 = new ArrayList<>();
                for (GetShippingIdModel getShippingIdModel : arrayList) {
                    if (vCustomerId.trim().equals(String.valueOf(getShippingIdModel.getCustomerId()).trim())) {
                        s = String.valueOf(getShippingIdModel.getShippingId());
                        arrayList1.add(s);

                        getCustomerIdId = String.valueOf(getShippingIdModel.getCustomerId());
                        getShippingIdId = String.valueOf(getShippingIdModel.getShippingId());
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CartActivity.this, android.R.layout.simple_list_item_1, arrayList1);
                shippingIdLV.setAdapter(arrayAdapter);

                customerIdTV.setText(getCustomerIdId);
                shippingId.setText(getShippingIdId);
            }

            @Override
            public void onFailure(Call<GetShippingIdModel> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Please Connect with Internet", Toast.LENGTH_LONG).show();
            }
        });

        Toast.makeText(this, "Place Order Now Please", Toast.LENGTH_SHORT).show();
        //loading.setVisibility(View.GONE);
    }

    private void getOrderId() {
        final String vCustomerId = customerIdTV.getText().toString();

        postApiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);

        Call<GetOrderIdModel> phpApiCallArrCall = postApiInterface.getDataForOrderId();
        phpApiCallArrCall.enqueue(new Callback<GetOrderIdModel>() {
            @Override
            public void onResponse(Call<GetOrderIdModel> call, Response<GetOrderIdModel> response) {
                GetOrderIdModel products = response.body();
                List<GetOrderIdModel> arrayList = products.getResult();
                String s = "";
                ArrayList<String> arrayList1 = new ArrayList<>();
                for (GetOrderIdModel getOrderIdModel : arrayList) {
                    if (vCustomerId.trim().equals(String.valueOf(getOrderIdModel.getCustomerId()).trim())) {
                        s = String.valueOf(getOrderIdModel.getOrderId());
                        arrayList1.add(s);

                        //getCustomerIdId = String.valueOf(getOrderIdModel.getCustomerId());
                        getOrderIdId = String.valueOf(getOrderIdModel.getOrderId());
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CartActivity.this, android.R.layout.simple_list_item_1, arrayList1);
                orderIdLV.setAdapter(arrayAdapter);

                //customerIdTV.setText(getCustomerIdId);
                orderId.setText(getOrderIdId);

                String vOrderId = orderId.getText().toString();

                if (vOrderId.equals("OrderId")) {
                    Toast.makeText(CartActivity.this, "Order Id Not Found", Toast.LENGTH_SHORT).show();
                } else {

                    String vProduct_Id = "";
                    String vConsume = "";
                    String vProduct_Name = "";
                    String vSubmitQty = "";
                    String vMrp = "";

                    for (final CartItemModel cartItemModel : Cart.contents()) {
                        product_id.setText(String.valueOf(cartItemModel.getFeaturedProductModel().getProductId()));
                        consume.setText(String.valueOf(cartItemModel.getFeaturedProductModel().getConsume() + cartItemModel.getQuantity()));
                        product_name.setText(String.valueOf(cartItemModel.getFeaturedProductModel().getProductName()));
                        submit_qty.setText(String.valueOf(cartItemModel.getQuantity()));
                        mrp.setText(String.valueOf(cartItemModel.getFeaturedProductModel().getNewPrice()));

                        //vOrderId = orderId.getText().toString();
                        vProduct_Id = product_id.getText().toString();
                        vConsume = consume.getText().toString();
                        vProduct_Name = product_name.getText().toString();
                        vSubmitQty = submit_qty.getText().toString();
                        vMrp = mrp.getText().toString();

                        //For Insert Place Order Details
                        PlaceOrderDetailsModel placeOrderDetailsModel = new PlaceOrderDetailsModel(Integer.parseInt(vOrderId), Integer.parseInt(vProduct_Id), vProduct_Name, vSubmitQty, vMrp);

                        Call<PlaceOrderDetailsModel> userResponse = postApiInterface.postDataForPlaceOrderDetails(placeOrderDetailsModel);
                        userResponse.enqueue(new Callback<PlaceOrderDetailsModel>() {
                            @Override
                            public void onResponse(Call<PlaceOrderDetailsModel> call, Response<PlaceOrderDetailsModel> response) {
                                PlaceOrderDetailsModel result = response.body();
                                //Toast.makeText(UserRegistrationActivity.this, "" + result.getCustomerName(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<PlaceOrderDetailsModel> call, Throwable t) {
                                //textViewError.setText(t.getMessage().toString());
                            }
                        });

                        //For Update Product Consume
                        FeaturedProductModel featuredProductModel = new FeaturedProductModel(Integer.parseInt(vProduct_Id), Integer.parseInt(vConsume));

                        Call<FeaturedProductModel> userResponseCall = postApiInterface.putDataForUpdateConsume(featuredProductModel);
                        userResponseCall.enqueue(new Callback<FeaturedProductModel>() {
                            @Override
                            public void onResponse(Call<FeaturedProductModel> call, Response<FeaturedProductModel> response) {
                                FeaturedProductModel result = response.body();
                                //Toast.makeText(UserRegistrationActivity.this, "" + result.getCustomerName(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<FeaturedProductModel> call, Throwable t) {
                                //textViewError.setText(t.getMessage().toString());
                            }
                        });
                    }

                    Toast.makeText(CartActivity.this, "Order Completed", Toast.LENGTH_SHORT).show();

                    // For Remove Shared Preference
                    SharedPreferences sharedPreferencesPO = getSharedPreferences("PaymentOption", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferencesPO.edit();
                    editor1.remove("PaymentOption");
                    editor1.commit();

                    Cart.removeAll();

                    customerIdTV.setText("");
                    shippingId.setText("");

                    payment_type.setText("");
                    trx_id.setText("");
                    customer_id.setText("");
                    shipping_id.setText("");
                    order_total.setText("");
                    order_date.setText("");
                    delivery_date.setText("");
                    entry_by.setText("");
                    product_id.setText("");
                    consume.setText("");
                    product_name.setText("");
                    submit_qty.setText("");
                    mrp.setText("");

                    //Explicit Intent
                    Intent intent = new Intent(CartActivity.this, SuccessfulOrderActivity.class);
                    startActivity(intent);

                    //                    // for success order go to history list
                    //                    Intent intent = new Intent(CartActivity.this, OrderDetailsActivity.class);
                    //                    intent.putExtra("OrderId", vOrderId);
                    //                    startActivity(intent);

                    orderId.setText("");
                }
            }

            @Override
            public void onFailure(Call<GetOrderIdModel> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Please Connect with Internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addCart() {
        try {
            Intent intent = getIntent();
            FeaturedProductModel featuredProductModel = (FeaturedProductModel) intent.getSerializableExtra("product");

            if (!Cart.isExists(featuredProductModel)) {
                Cart.insert(new CartItemModel(featuredProductModel, 1));
            } else {
                Cart.update(featuredProductModel);
            }

            Intent intent1 = new Intent(this, MainActivity.class);
            intent1.putExtra("isExists", "1");
            intent1.putExtra("cCount", "1");
            startActivity(intent1);

            if (Cart.total() > 0) {
                buttonCheckout.setVisibility(View.VISIBLE);
            } else {
                buttonCheckout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

//        int mCount=0;
//
//        private void doCartIncremet() {
//            mCount++;
//            //mCountTv.setText(String.valueOf(mCount));
//
//            //Writing Data in Shared Preference
//            SharedPreferences mCountSP =mContext.getSharedPreferences("mCount", Context.MODE_PRIVATE);
//            SharedPreferences.Editor mCountEditor = mCountSP.edit();
//            mCountEditor.putString("mCount", String.valueOf(mCount));
//            mCountEditor.commit();
//
//            //(MainActivity)mContext.findViewById(R.id.count_tv_layout).setTe;
//        }

    private void createColumns() {
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        //Option Column
        TextView textViewOption = new TextView(this);
        textViewOption.setText("Option");
        textViewOption.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewOption.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewOption.setPadding(5, 20, 5, 0);
        tableRow.addView(textViewOption);

        //ID Column
        TextView textViewId = new TextView(this);
        textViewId.setText("ID");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewId.setPadding(5, 20, 5, 0);
        textViewId.setVisibility(View.GONE);
        tableRow.addView(textViewId);

        //Consume Column
        TextView textConsume = new TextView(this);
        textConsume.setText("Consume");
        textConsume.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textConsume.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textConsume.setPadding(5, 20, 5, 0);
        textConsume.setVisibility(View.GONE);
        tableRow.addView(textConsume);

        //Name Column
        TextView textViewName = new TextView(this);
        textViewName.setText("Name");
        textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewName.setPadding(5, 20, 5, 0);
        tableRow.addView(textViewName);

        //Photo Column
        TextView textViewPhoto = new TextView(this);
        textViewPhoto.setText("Photo");
        textViewPhoto.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPhoto.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewPhoto.setPadding(5, 20, 5, 0);
        tableRow.addView(textViewPhoto);

        //Price Column
        TextView textViewPrice = new TextView(this);
        textViewPrice.setText("Price");
        textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPrice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewPrice.setPadding(5, 20, 5, 0);
        tableRow.addView(textViewPrice);

        //Quantity Column
        TextView textViewQuantity = new TextView(this);
        textViewQuantity.setText("Qty");
        textViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewQuantity.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewQuantity.setPadding(5, 20, 5, 0);
        tableRow.addView(textViewQuantity);

        //Sub Total Column
        TextView textViewSubTotal = new TextView(this);
        textViewSubTotal.setText("Sub Total");
        textViewSubTotal.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewSubTotal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewSubTotal.setPadding(5, 20, 5, 0);
        tableRow.addView(textViewSubTotal);

        tableLayoutProduct.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        //Add Divider
        tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        //Option Column
        textViewOption = new TextView(this);
        textViewOption.setText("-----");
        textViewOption.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewOption.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewOption.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewOption);

        //ID Column
        textViewId = new TextView(this);
        textViewId.setText("-----");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewId.setPadding(5, 5, 5, 0);
        textViewId.setVisibility(View.GONE);
        tableRow.addView(textViewId);

        //Consume Column
        textConsume = new TextView(this);
        textConsume.setText("-----");
        textConsume.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textConsume.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textConsume.setPadding(5, 20, 5, 0);
        textConsume.setVisibility(View.GONE);
        tableRow.addView(textConsume);

        //Name Column
        textViewName = new TextView(this);
        textViewName.setText("-----");
        textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewName.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewName);

        //Photo Column
        textViewPhoto = new TextView(this);
        textViewPhoto.setText("-----");
        textViewPhoto.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPhoto.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewPhoto.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewPhoto);

        //Price Column
        textViewPrice = new TextView(this);
        textViewPrice.setText("-----");
        textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPrice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewPrice.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewPrice);

        //Quantity Column
        textViewQuantity = new TextView(this);
        textViewQuantity.setText("-----");
        textViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewQuantity.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewQuantity.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewQuantity);

        //Sub Total Column
        textViewSubTotal = new TextView(this);
        textViewSubTotal.setText("-----");
        textViewSubTotal.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewSubTotal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewSubTotal.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewSubTotal);

        tableLayoutProduct.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
    }

    private void fillData() {
        try {
            for (final CartItemModel cartItemModel : Cart.contents()) {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));

                //Option Column
                ImageButton imageButtonOption = new ImageButton(this);
                imageButtonOption.setImageResource(R.mipmap.trash);
                imageButtonOption.setBackgroundColor(View.GONE);
                imageButtonOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Warning");
                        builder.setMessage("Are you sure ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Cart.remove(cartItemModel.getFeaturedProductModel());
                                tableLayoutProduct.removeAllViews();
                                createColumns();
                                fillData();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.cancel();
                            }
                        });

                        builder.show();
                    }
                });
                tableRow.addView(imageButtonOption);

                //ID Column
                TextView textViewId = new TextView(this);
                textViewId.setText(String.valueOf(cartItemModel.getFeaturedProductModel().getProductId()));
                textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewId.setPadding(5, 25, 5, 0);
                textViewId.setVisibility(View.GONE);
                tableRow.addView(textViewId);

                //Consume Column
                TextView textConsume = new TextView(this);
                textConsume.setText(String.valueOf(cartItemModel.getFeaturedProductModel().getConsume() + cartItemModel.getQuantity()));
                textConsume.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textConsume.setPadding(5, 25, 5, 0);
                textConsume.setVisibility(View.GONE);
                tableRow.addView(textConsume);

                //Name Column
                TextView textViewName = new TextView(this);
                textViewName.setText(String.valueOf(cartItemModel.getFeaturedProductModel().getProductName()));
                textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewName.setPadding(5, 25, 5, 0);
                tableRow.addView(textViewName);

                //Photo Column
                ImageView imageViewPhoto = new ImageView(this);
                String vPhotoLink = "https://mdabdurrahim.com/laravel/eshopper/" + cartItemModel.getFeaturedProductModel().getImageOption();
                loadImageFromUrl(imageViewPhoto, vPhotoLink);
                imageViewPhoto.setPadding(5, 25, 5, 0);
                imageViewPhoto.setScaleType(ImageView.ScaleType.CENTER);
                tableRow.addView(imageViewPhoto);

                //Price Column
                TextView textViewPrice = new TextView(this);
                textViewPrice.setText(String.valueOf(cartItemModel.getFeaturedProductModel().getNewPrice()));
                textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewPrice.setPadding(5, 25, 5, 0);
                tableRow.addView(textViewPrice);

                //Quantity Column
                TextView textViewQuantity = new TextView(this);
                textViewQuantity.setText(String.valueOf(cartItemModel.getQuantity()));
                textViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewQuantity.setPadding(5, 25, 5, 0);

                tableRow.addView(textViewQuantity);

                //Sub Total Column
                TextView textViewSubTotal = new TextView(this);
                textViewSubTotal.setText(String.valueOf(cartItemModel.getFeaturedProductModel().getNewPrice() * cartItemModel.getQuantity()));
                textViewSubTotal.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewSubTotal.setPadding(5, 25, 5, 0);
                textViewSubTotal.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                tableRow.addView(textViewSubTotal);

                tableLayoutProduct.addView(tableRow, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
            }

            // Add Total Row
            TableRow tableRowTotal = new TableRow(this);
            tableRowTotal.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            //Total Column
            TextView textViewTotal = new TextView(this);
            textViewTotal.setText("Total");
            textViewTotal.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewTotal.setPadding(5, 5, 5, 0);
            tableRowTotal.addView(textViewTotal);

            //Total Column Value
            TextView textViewTotalValue = new TextView(this);
            textViewTotalValue.setText(String.valueOf(Cart.total()));
            textViewTotalValue.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewTotalValue.setPadding(5, 5, 5, 0);
            tableRowTotal.addView(textViewTotalValue);

            tableLayoutProduct.addView(tableRowTotal, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));
        } catch (Exception e2) {
            //Toast.makeText(getApplicationContext(), "" + e2.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImageFromUrl(ImageView imageViewPhoto, String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)   // Optional
                .error(R.mipmap.ic_launcher)  // if Error
                .resize(75, 75)
                .centerCrop()
                .into(imageViewPhoto, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public void onBackPressed() {

    }
}
