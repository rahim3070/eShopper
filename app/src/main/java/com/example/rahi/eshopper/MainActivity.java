package com.example.rahi.eshopper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahi.eshopper.adapter.FeaturedProductListAdapter;
import com.example.rahi.eshopper.api_interface.eShopperApiInterface;
import com.example.rahi.eshopper.model.FeaturedProductModel;
import com.example.rahi.eshopper.model.UserModel;
import com.example.rahi.eshopper.utility.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView lv_FeaturedProduct;
    TextView userEmailId, userHeading, count_tv_layout;
    MenuItem UserCartMenu;
    ImageButton image_btn_layout;
    int mCount = 0;
    eShopperApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmailId = findViewById(R.id.userEmailId);
        userHeading = findViewById(R.id.userHeading);

        lv_FeaturedProduct = findViewById(R.id.lv_FeaturedProduct);
        getFeaturedProductServerData();

        //To Read Data From Shared Preference
        SharedPreferences sharedPreferences = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("CustomerId")) {
            String userId = sharedPreferences.getString("CustomerId", "User Email Not Found");

            userEmailId.setText(userId);
        } else {
            Toast.makeText(this, "No User Found", Toast.LENGTH_SHORT).show();
        }

        //To Read Data From Shared Preference
        SharedPreferences cusNameSP = getSharedPreferences("CusName", Context.MODE_PRIVATE);
        if (cusNameSP.contains("CusName")) {
            String userName = cusNameSP.getString("CusName", "User Name Not Found");

            userHeading.setText("Product List for " + userName);
        } else {
            userHeading.setText("Product List");
        }
    }

    private void getFeaturedProductServerData() {
        apiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);
        final Call<FeaturedProductModel> featuredProductModelCall = apiInterface.getAllFeaturedProductDetail();
        featuredProductModelCall.enqueue(new Callback<FeaturedProductModel>() {
            @Override
            public void onResponse(Call<FeaturedProductModel> call, Response<FeaturedProductModel> response) {
                if (response != null) {
                    FeaturedProductModel f = response.body();
                    List<FeaturedProductModel> list = f.getResult();
                    FeaturedProductListAdapter productListCustomAdapter = new FeaturedProductListAdapter(MainActivity.this, list);
                    lv_FeaturedProduct.setAdapter(productListCustomAdapter);
                }
            }

            @Override
            public void onFailure(Call<FeaturedProductModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Please Connect with Internet", Toast.LENGTH_LONG);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //        UserCartMenu = menu.findItem(R.id.UserCartMenu);
        //        View actionView = UserCartMenu.getActionView();
        //
        //        if (actionView != null) {
        //            count_tv_layout = actionView.findViewById(R.id.count_tv_layout);
        //            image_btn_layout = actionView.findViewById(R.id.image_btn_layout);
        //        }
        //
        //        image_btn_layout.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                //Explicit Intent
        //                Intent intent = new Intent(MainActivity.this, CartActivity.class);
        //                startActivity(intent);
        //            }
        //        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.UserLoginMenu) {
            if (item.getTitle() == "Login") {
                //Explicit Intent
                Intent intent = new Intent(this, UserLoginActivity.class);
                startActivity(intent);
            } else {
                // For Remove Shared Preference
                SharedPreferences sharedPreferences = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("CustomerId");
                editor.commit();

                // For Remove Shared Preference
                SharedPreferences cusNameSP = getSharedPreferences("CusName", Context.MODE_PRIVATE);
                SharedPreferences.Editor cusNameEditor = cusNameSP.edit();
                cusNameEditor.remove("CusName");
                cusNameEditor.commit();

                // For Remove Shared Preference
                SharedPreferences sharedPreferencesPO = getSharedPreferences("PaymentOption", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferencesPO.edit();
                editor1.remove("PaymentOption");
                editor1.commit();

                userEmailId.setText("");

                //Explicit Intent
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } else if (item.getItemId() == R.id.UserRegMenu) {
            //Explicit Intent
            Intent intent = new Intent(this, UserRegistrationActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.UserCartMenu) {
            //Explicit Intent
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.OrderDetailsMenu) {
            //Explicit Intent
            Intent intent = new Intent(this, OrderDetailsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.AdminLoginMenu) {
            //Explicit Intent
            Intent intent = new Intent(this, AdminLoginActivity.class);
            startActivity(intent);
        }

        //        else if (item.getItemId() == R.id.UserCheckOutMenu) {
        //            //To Read Dta From Shared Preference
        //            SharedPreferences sharedPreferences = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);
        //
        //            if (sharedPreferences.contains("CustomerId")) {
        //                //Explicit Intent
        //                Intent intent = new Intent(this, CheckOutActivity.class);
        //                startActivity(intent);
        //            } else {
        //                //Explicit Intent
        //                Intent intent = new Intent(this, UserLoginActivity.class);
        //                startActivity(intent);
        //            }
        //        } else if (item.getItemId() == R.id.PlaceOrderMenu) {
        //            //Explicit Intent
        //            Intent intent = new Intent(this, PlaceOrderActivity.class);
        //            startActivity(intent);
        //        }

        return super.onOptionsItemSelected(item);
    }

    // For Menu Title in Runtime
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();

            //To Read Dta From Shared Preference
            SharedPreferences sharedPreferences = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);

            if (sharedPreferences.contains("CustomerId")) {
                menu.findItem(R.id.UserLoginMenu).setTitle("Logout");
                menu.findItem(R.id.UserRegMenu).setVisible(false);
                menu.findItem(R.id.OrderDetailsMenu).setVisible(true);
                menu.findItem(R.id.AdminLoginMenu).setVisible(false);
            } else {
                menu.findItem(R.id.UserLoginMenu).setTitle("Login");
                menu.findItem(R.id.UserRegMenu).setVisible(true);
                menu.findItem(R.id.OrderDetailsMenu).setVisible(false);
                menu.findItem(R.id.AdminLoginMenu).setVisible(true);
            }

            //For Intent Value
            String value = "0";
            String vCartCount = "0";
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                value = bundle.getString("isExists");
                vCartCount = bundle.getString("cCount");
            }

            if (!value.equals("0")) {
                //                if (vCartCount.equals("1")) {
                //                    doCartIncrement();
                //                }

                menu.findItem(R.id.UserCartMenu).setVisible(true);
            } else {
                menu.findItem(R.id.UserCartMenu).setVisible(false);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }


    private void doCartIncrement() {
        int vValue = Integer.parseInt(count_tv_layout.getText().toString());
        int vPreValue;

        if (vValue > 0) {
            vPreValue = Integer.parseInt(count_tv_layout.getText().toString());
        } else {
            vPreValue = 0;
        }

        int vIncValue = Integer.parseInt("1");

        int vCurValue = vPreValue + vIncValue;

        count_tv_layout.setText(String.valueOf(vCurValue));

        //        mCount++;
        //        count_tv_layout.setText(String.valueOf(mCount));
    }

    @Override
    public void onBackPressed() {
        //        AlertDialog.Builder alertDialogBuilder;
        //        alertDialogBuilder = new AlertDialog.Builder(this);
        //
        //        alertDialogBuilder.setIcon(R.drawable.logout);
        //        alertDialogBuilder.setTitle("Warning");
        //        alertDialogBuilder.setMessage("Are you sure, you want to exit ?");
        //        alertDialogBuilder.setCancelable(false);
        //
        //        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        //            @Override
        //            public void onClick(DialogInterface dialog, int which) {
        //                finish();
        //            }
        //        });
        //
        //        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        //            @Override
        //            public void onClick(DialogInterface dialog, int which) {
        //                dialog.cancel();
        //            }
        //        });
        //
        //        AlertDialog alertDialog = alertDialogBuilder.create();
        //        alertDialog.show();
    }
}
