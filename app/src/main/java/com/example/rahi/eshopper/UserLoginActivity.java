package com.example.rahi.eshopper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahi.eshopper.api_interface.eShopperApiInterface;
import com.example.rahi.eshopper.model.UserModel;
import com.example.rahi.eshopper.utility.RetrofitClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView userEmailET, userPasswordET, userRegistration, forgotPass, userEmailTV, userPassTV, userIdTV, userNameTV;
    Button loginBtn;
    ListView listViewLoginDetail;
    eShopperApiInterface apiInterface;
    String getUserEmail, getPassword, getCusId, getCusName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        listViewLoginDetail = findViewById(R.id.listViewLoginDetail);

        userEmailET = findViewById(R.id.userEmailET);
        userPasswordET = findViewById(R.id.userPasswordET);
        userRegistration = findViewById(R.id.userRegistration);
        forgotPass = findViewById(R.id.forgotPass);
        loginBtn = findViewById(R.id.loginBtn);

        userEmailTV = findViewById(R.id.userEmailTV);
        userPassTV = findViewById(R.id.userPassTV);
        userIdTV = findViewById(R.id.userIdTV);
        userNameTV = findViewById(R.id.userNameTV);

        userRegistration.setOnClickListener(this);
        forgotPass.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String usrName = userEmailET.getText().toString();
        String pass = md5(userPasswordET.getText().toString());

        if (view.getId() == R.id.loginBtn) {
            if ((usrName.equals("")) || (pass.equals(""))) {
                Toast.makeText(this, "Please Give Your Email & Password", Toast.LENGTH_SHORT).show();
            } else {
                getLoginServerData(usrName, pass);
            }
        } else if (view.getId() == R.id.userRegistration) {
            Intent intent = new Intent(UserLoginActivity.this, UserRegistrationActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.forgotPass) {
            Intent intent = new Intent(UserLoginActivity.this, UserForgotPasswordActivity.class);
            startActivity(intent);
        }
    }

    private void getLoginServerData(final String usrName, final String pass) {
        apiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);

        Call<UserModel> phpApiCallArrCall = apiInterface.getAllLoginDetail();
        phpApiCallArrCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel products = response.body();
                List<UserModel> arrayList = products.getResult();
                String s = "";
                ArrayList<String> arrayList1 = new ArrayList<>();
                for (UserModel userModel : arrayList) {
                    if (usrName.trim().equals(userModel.getCustomerEmail().trim()) && pass.trim().equals(userModel.getPassword().trim())) {
                        s = userModel.getCustomerEmail() + "\n" +
                                userModel.getPassword();
                        arrayList1.add(s);

                        getCusId = userModel.getCustomerId();
                        getCusName = userModel.getCustomerName();
                        getUserEmail = userModel.getCustomerEmail();
                        getPassword = userModel.getPassword();
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UserLoginActivity.this, android.R.layout.simple_list_item_1, arrayList1);
                listViewLoginDetail.setAdapter(arrayAdapter);

                userIdTV.setText(getCusId);
                userNameTV.setText(getCusName);
                userEmailTV.setText(getUserEmail);
                userPassTV.setText(getPassword);

                String cusId = userIdTV.getText().toString();
                String cusName = userNameTV.getText().toString();
                String cusUsrName = userEmailTV.getText().toString();
                String cusPass = userPassTV.getText().toString();

                if (usrName.trim().equals(cusUsrName.trim()) && (pass.trim().equals(cusPass.trim()))) {
                    //Writing Data in Shared Preference
                    SharedPreferences cusIdSP = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);
                    SharedPreferences.Editor cusIdEditor = cusIdSP.edit();
                    cusIdEditor.putString("CustomerId", cusId);
                    cusIdEditor.commit();

                    //Writing Data in Shared Preference
                    SharedPreferences cusNameSP = getSharedPreferences("CusName", Context.MODE_PRIVATE);
                    SharedPreferences.Editor cusNameEditor = cusNameSP.edit();
                    cusNameEditor.putString("CusName", cusName);
                    cusNameEditor.commit();

                    userEmailET.setText("");
                    userPasswordET.setText("");

                    Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UserLoginActivity.this, "Email & Password not matched", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(UserLoginActivity.this, "Please Connect with Internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private static final String md5(final String password) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public void onBackPressed() {

    }
}
