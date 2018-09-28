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
import com.example.rahi.eshopper.model.UserModel;
import com.example.rahi.eshopper.utility.RetrofitClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    eShopperApiInterface postApiInterface;

    TextView userEmailTV, userIdTV, userNameTV;
    EditText editTextCustomerName, editTextMobileNo, editTextEmail, editTextPassword, editTextConfrimPassword;
    Button btnPostData;
    ListView listViewLoginDetail;
    String getUserEmail, getCusId, getCusName, cusId, cusName, cusUsrName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        listViewLoginDetail = findViewById(R.id.lv_LoginDetail);
        //getLoginServerData();

        editTextCustomerName = findViewById(R.id.et_CustomerName);
        editTextMobileNo = findViewById(R.id.et_MobileNo);
        editTextEmail = findViewById(R.id.et_Email);
        editTextPassword = findViewById(R.id.et_Password);
        editTextConfrimPassword = findViewById(R.id.et_ConfirmPassword);

        btnPostData = findViewById(R.id.btnPostData);

        userEmailTV = findViewById(R.id.userEmailTV);
        userIdTV = findViewById(R.id.userIdTV);
        userNameTV = findViewById(R.id.userNameTV);

        postApiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);

        btnPostData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnPostData) {
            String vName = editTextCustomerName.getText().toString();
            String vConNo = editTextMobileNo.getText().toString();
            String vUserEmail = editTextEmail.getText().toString();

            String vPass = md5(editTextPassword.getText().toString());
            String vConPass = md5(editTextConfrimPassword.getText().toString());

            if ((vName.equals("")) || (vConNo.equals("")) || (vUserEmail.equals("")) || (vPass.equals("")) || (vConPass.equals(""))) {
                Toast.makeText(getApplicationContext(), "Please Give All Data", Toast.LENGTH_SHORT).show();
            } else {
                getLoginServerData(vName, vConNo, vUserEmail, vPass, vConPass);
            }
        }
    }

    private void getLoginServerData(final String vName, final String vConNo, final String vUserEmail, final String vPass, final String vConPass) {
        postApiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);

        Call<UserModel> phpApiCallArrCall = postApiInterface.getAllLoginDetail();
        phpApiCallArrCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel products = response.body();
                List<UserModel> arrayList = products.getResult();
                String s = "";
                ArrayList<String> arrayList1 = new ArrayList<>();
                for (UserModel userModel : arrayList) {
                    if (vUserEmail.trim().equals(userModel.getCustomerEmail().trim())) {
                        s = userModel.getCustomerEmail() + "\n" + userModel.getPassword();
                        arrayList1.add(s);

                        getCusId = userModel.getCustomerId();
                        getCusName = userModel.getCustomerName();
                        getUserEmail = userModel.getCustomerEmail();
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UserRegistrationActivity.this, android.R.layout.simple_list_item_1, arrayList1);
                listViewLoginDetail.setAdapter(arrayAdapter);

                userIdTV.setText(getCusId);
                userNameTV.setText(getCusName);
                userEmailTV.setText(getUserEmail);

                cusId = userIdTV.getText().toString();
                cusName = userNameTV.getText().toString();
                cusUsrName = userEmailTV.getText().toString();

                if (vUserEmail.trim().equals(cusUsrName.trim())) {
                    Toast.makeText(getApplicationContext(), "This Email is Already Exists", Toast.LENGTH_SHORT).show();
                } else {
                    if ((vPass.equals("")) || (vConPass.equals(""))) {
                        Toast.makeText(getApplicationContext(), "Password's are blank", Toast.LENGTH_SHORT).show();
                    } else {
                        UserModel user;
                        // confirm pass
                        if (vPass.trim().equals(vConPass.trim())) {
                            ////Writing Data in Shared Preference
                            //SharedPreferences sharedPreferences = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);
                            //SharedPreferences.Editor editor = sharedPreferences.edit();
                            //editor.putString("CustomerId", cusId);
                            //editor.commit();

                            // create customer id
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                            String vCustomerCode = "ESEC-CC" + simpleDateFormat.format(calendar.getTimeInMillis());

                            user = new UserModel(vCustomerCode, vName, vConNo, vUserEmail, vPass);

                            Call<UserModel> userResponseCall = postApiInterface.postDataForCreateUserRegister(user);
                            userResponseCall.enqueue(new Callback<UserModel>() {
                                @Override
                                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                    UserModel result = response.body();
                                    //Toast.makeText(UserRegistrationActivity.this, "" + result.getCustomerName(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<UserModel> call, Throwable t) {
                                    //textViewError.setText(t.getMessage().toString());
                                }
                            });

                            getLoginData();

                            editTextCustomerName.setText("");
                            editTextMobileNo.setText("");
                            editTextEmail.setText("");
                            editTextPassword.setText("");
                            editTextConfrimPassword.setText("");

                            Intent intent = new Intent(UserRegistrationActivity.this, MainActivity.class);
                            startActivity(intent);

                            Toast.makeText(UserRegistrationActivity.this, "Submitted Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UserRegistrationActivity.this, "Password Not Matched", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(UserRegistrationActivity.this, "Please Connect with Internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getLoginData() {
        final String vUserEmail = editTextEmail.getText().toString();

        postApiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);

        Call<UserModel> phpApiCallArrCall = postApiInterface.getAllLoginDetail();
        phpApiCallArrCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel products = response.body();
                List<UserModel> arrayList = products.getResult();
                String s = "";
                ArrayList<String> arrayList1 = new ArrayList<>();
                for (UserModel userModel : arrayList) {
                    if (vUserEmail.trim().equals(userModel.getCustomerEmail().trim())) {
                        s = userModel.getCustomerEmail() + "\n" + userModel.getPassword();
                        arrayList1.add(s);

                        getCusId = userModel.getCustomerId();
                        getCusName = userModel.getCustomerName();
                        getUserEmail = userModel.getCustomerEmail();
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UserRegistrationActivity.this, android.R.layout.simple_list_item_1, arrayList1);
                listViewLoginDetail.setAdapter(arrayAdapter);

                userIdTV.setText(getCusId);
                userNameTV.setText(getCusName);
                userEmailTV.setText(getUserEmail);

                cusId = userIdTV.getText().toString();
                cusName = userNameTV.getText().toString();
                cusUsrName = userEmailTV.getText().toString();

                //Writing Data in Shared Preference
                SharedPreferences sharedPreferences = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("CustomerId", cusId);
                editor.commit();

                //Writing Data in Shared Preference
                SharedPreferences cusNameSP = getSharedPreferences("CusName", Context.MODE_PRIVATE);
                SharedPreferences.Editor cusNameEditor = cusNameSP.edit();
                cusNameEditor.putString("CusName", cusName);
                cusNameEditor.commit();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(UserRegistrationActivity.this, "Please Connect with Internet", Toast.LENGTH_LONG).show();
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
