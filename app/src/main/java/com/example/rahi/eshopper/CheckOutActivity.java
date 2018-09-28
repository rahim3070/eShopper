package com.example.rahi.eshopper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahi.eshopper.api_interface.eShopperApiInterface;
import com.example.rahi.eshopper.model.CheckOutModel;
import com.example.rahi.eshopper.model.GetShippingIdModel;
import com.example.rahi.eshopper.utility.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {

    eShopperApiInterface postApiInterface;

    EditText customerName, companyName, contactNo, email, address, zipCode, country;
    Button btnPostShippingData, buttonContinueShopping;
    int vCustomerId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        customerName = findViewById(R.id.customerName);
        companyName = findViewById(R.id.companyName);
        contactNo = findViewById(R.id.contactNo);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        zipCode = findViewById(R.id.zipCode);
        country = findViewById(R.id.country);

        btnPostShippingData = findViewById(R.id.btnPostShippingData);
        buttonContinueShopping = findViewById(R.id.buttonContinueShopping);

        btnPostShippingData.setOnClickListener(this);
        buttonContinueShopping.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnPostShippingData) {
            postShippingServerData();
        } else if (view.getId() == R.id.buttonContinueShopping) {
            Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
            intent.putExtra("isExists", "2");
            intent.putExtra("cCount", "0");
            startActivity(intent);
        }
    }

    private void postShippingServerData() {

        String vCustomerName = customerName.getText().toString();
        String vCompanyName = companyName.getText().toString();
        String vContactNo = contactNo.getText().toString();
        String vEmail = email.getText().toString();
        String vAddress = address.getText().toString();
        String vZipCode = zipCode.getText().toString();
        String vCountry = country.getText().toString();

        CheckOutModel user;

        if ((vCustomerName.equals("")) || (vContactNo.equals("")) || (vEmail.equals("")) || (vAddress.equals("")) || (vZipCode.equals("")) || (vCountry.equals(""))) {
            Toast.makeText(getApplicationContext(), "Please Give All Data", Toast.LENGTH_SHORT).show();
        } else {
            //To Read Data From Shared Preference
            SharedPreferences sharedPreferences = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);

            if (sharedPreferences.contains("CustomerId")) {
                vCustomerId = Integer.parseInt(sharedPreferences.getString("CustomerId", "User Email Not Found"));
            }

            if (vCustomerId == 0) {
                Toast.makeText(CheckOutActivity.this, "No User Found", Toast.LENGTH_LONG).show();
            } else {
                postApiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);
                user = new CheckOutModel(vCustomerId, vCustomerName, vCompanyName, vEmail, vContactNo, vAddress, vZipCode, vCountry);

                Call<CheckOutModel> userResponseCall = postApiInterface.postDataForShippingDetail(user);
                userResponseCall.enqueue(new Callback<CheckOutModel>() {
                    @Override
                    public void onResponse(Call<CheckOutModel> call, Response<CheckOutModel> response) {
                        CheckOutModel result = response.body();
                        //Toast.makeText(UserRegistrationActivity.this, "" + result.getCustomerName(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<CheckOutModel> call, Throwable t) {
                        //Toast.makeText(getApplicationContext(), "Please Connect with Internet", Toast.LENGTH_SHORT).show();
                    }
                });

                customerName.setText("");
                companyName.setText("");
                contactNo.setText("");
                email.setText("");
                address.setText("");
                zipCode.setText("");
                country.setText("");

                //Writing Data in Shared Preference
                String vValue = "PaymentOption";
                SharedPreferences sharedPreferencesPO = getSharedPreferences("PaymentOption", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferencesPO.edit();
                editor1.putString("PaymentOption", vValue);
                editor1.commit();

                Intent intent = new Intent(CheckOutActivity.this, CartActivity.class);
                //intent.putExtra("Tag","PaymentOption");
                startActivity(intent);

                Toast.makeText(CheckOutActivity.this, "Submitted Successfully", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {

    }
}
