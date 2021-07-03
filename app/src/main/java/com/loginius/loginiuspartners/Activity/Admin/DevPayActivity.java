package com.loginius.loginiuspartners.Activity.Admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loginius.loginiuspartners.Model.Adapter.PayAdpt;
import com.loginius.loginiuspartners.Model.Project.Payment;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DevPayActivity extends AppCompatActivity {

    List<Payment> pay;
    ProgressDialog pd;
    RecyclerView recPay;
    PayAdpt payadpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_pay);

        recPay = findViewById(R.id.devPayrecvw);
        pd = new ProgressDialog(DevPayActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
        pay = new ArrayList<>();

        callPrj();

    }

    private void callPrj() {
        pd.show();
        Call<List<Payment>> call = ApiClient.getClient().getPrjAmt(new Payment("t4Ok6En21Pay"));
        call.enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DevPayActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.cancel();
                            return;
                        }
                    }, 2000);

                }
                List<Payment> res = response.body();
                for (Payment p : res) {
                    pay.add(p);
                }
                payadpt = new PayAdpt(DevPayActivity.this, pay);
                recPay.setLayoutManager(new LinearLayoutManager(DevPayActivity.this));
                recPay.setAdapter(payadpt);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.cancel();
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.cancel();
            }
        }, 2000);
    }
}