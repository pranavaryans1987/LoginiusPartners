package com.loginius.loginiuspartners.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.loginius.loginiuspartners.Model.Login.Login;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputEditText unm, pwd;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.CALL_PHONE }, 100);
        }
        SharedPreferences preferences = getSharedPreferences("Loginius", MODE_PRIVATE);
        String chk = preferences.getString("name", "");
        if (!chk.isEmpty()) {
            finish();
            startActivity(new Intent(MainActivity.this, Activity_Dashboard.class));
        }

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
        unm = findViewById(R.id.ed_lgpUnm);
        pwd = findViewById(R.id.ed_lgpPwd);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void signin(View view) {
        String client, psd;
        client = unm.getText().toString();
        psd = pwd.getText().toString();

        if (client.equals("") || client.isEmpty() || psd.equals("") || psd.isEmpty()) {
            if (client.equals("") || client.isEmpty()) {
                unm.setError("Please Enter Username.");
                unm.requestFocus();
            } else {
                pwd.setError("Please Enter Password.");
                pwd.requestFocus();
            }
        } else {
            pd.show();
            Call<Login> call= ApiClient.getClient().userChk(new Login(client,psd,"lgL7og6i_2niu1sPart"));
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int x=response.body().getStatus();
                    if(x==1) {
                        SharedPreferences preferences = getSharedPreferences("Loginius", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("id", response.body().getId());
                        editor.putString("name", client);
                        editor.putString("status", String.valueOf(response.body().getStatus()));
                        editor.putString("type", response.body().getType());
                        editor.apply();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                finish();
                                startActivity(new Intent(MainActivity.this, Activity_Dashboard.class));
                            }
                        },2000);

                    }
                    else if(x==2)
                    {
                        Toast.makeText(MainActivity.this, "User Not Found.", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                            }
                        },2000);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Some Thing Wrong.", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                            }
                        }, 2000);                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {

                }
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pd.cancel();
    }
}