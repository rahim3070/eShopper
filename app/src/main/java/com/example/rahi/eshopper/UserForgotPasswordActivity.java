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

public class UserForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    eShopperApiInterface postApiInterface;

    TextView tryAgain, userEmailTV, userIdTV;
    EditText editTextEmail, editTextPassword, editTextConfrimPassword;
    ListView listViewLoginDetail;
    Button updateBtn;
    String getUserEmail, getCusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_password);

        listViewLoginDetail = findViewById(R.id.lv_LoginDetail);

        editTextEmail = findViewById(R.id.et_Email);
        editTextPassword = findViewById(R.id.et_Password);
        editTextConfrimPassword = findViewById(R.id.et_ConfirmPassword);
        tryAgain = findViewById(R.id.tryAgain);
        updateBtn = findViewById(R.id.updateBtn);

        userEmailTV = findViewById(R.id.userEmailTV);
        userIdTV = findViewById(R.id.userIdTV);

        tryAgain.setOnClickListener(this);
        updateBtn.setOnClickListener(this);

        postApiInterface = RetrofitClient.getRetrofitClient().create(eShopperApiInterface.class);
    }

    @Override
    public void onClick(View view) {
        String vUserEmail = editTextEmail.getText().toString();

        String vPass = md5(editTextPassword.getText().toString());
        String vConPass = md5(editTextConfrimPassword.getText().toString());

        if (view.getId() == R.id.updateBtn) {
            if ((vUserEmail.equals("")) || (vPass.equals("")) || (vConPass.equals(""))) {
                Toast.makeText(this, "Please Give Your Email & Password", Toast.LENGTH_SHORT).show();
            } else {
                getLoginServerData(vUserEmail, vPass, vConPass);
            }
        } else if (view.getId() == R.id.tryAgain) {
            Intent intent = new Intent(UserForgotPasswordActivity.this, UserLoginActivity.class);
            startActivity(intent);
        }
    }

    private void getLoginServerData(final String vUserEmail, final String vPass, final String vConPass) {
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
                        getUserEmail = userModel.getCustomerEmail();
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UserForgotPasswordActivity.this, android.R.layout.simple_list_item_1, arrayList1);
                listViewLoginDetail.setAdapter(arrayAdapter);

                userIdTV.setText(getCusId);
                userEmailTV.setText(getUserEmail);

                String cusId = userIdTV.getText().toString();
                String cusUsrName = userEmailTV.getText().toString();

                if (vUserEmail.trim().equals(cusUsrName.trim())) {
                    if ((vPass.equals("")) || (vConPass.equals(""))) {
                        Toast.makeText(UserForgotPasswordActivity.this, "Password's are blank", Toast.LENGTH_SHORT).show();
                    } else {
                        UserModel user;
                        // confirm pass
                        if (vPass.trim().equals(vConPass.trim())) {
                            //Writing Data in Shared Preference
                            SharedPreferences sharedPreferences = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("CustomerId", cusId);
                            editor.commit();

                            user = new UserModel(vUserEmail, vPass);

                            Call<UserModel> userResponseCall = postApiInterface.putDataForUpdateUserInfo(user);
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

                            editTextEmail.setText("");
                            editTextPassword.setText("");
                            editTextConfrimPassword.setText("");

                            Intent intent = new Intent(UserForgotPasswordActivity.this, MainActivity.class);
                            startActivity(intent);

                            Toast.makeText(UserForgotPasswordActivity.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UserForgotPasswordActivity.this, "Password Not Matched", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "This Email is not Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(UserForgotPasswordActivity.this, "Please Connect with Internet", Toast.LENGTH_LONG).show();
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
