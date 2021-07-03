package com.loginius.loginiuspartners.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loginius.loginiuspartners.Model.Develop.DevBody;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeveloperActivity extends AppCompatActivity {

    ProgressDialog pd;
    TextInputEditText ed_devnm, ed_cnno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        ed_cnno = findViewById(R.id.ed_devcnno);
        ed_devnm = findViewById(R.id.ed_devnm);

        pd = new ProgressDialog(DeveloperActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);

    }

    public void drawerBack(View view) {
        this.finish();
    }

    public void addDev(View view) {
        String devnm,cno;
        devnm=ed_devnm.getText().toString();
        cno=ed_cnno.getText().toString();
        if(devnm.equals("") || cno.equals(""))
        {
            if(devnm.equals("") || devnm.isEmpty())
            {
                ed_devnm.setError("Please Enter Developer Name");
                ed_devnm.requestFocus();
            }
            else
            {
                ed_cnno.setError("Please Enter Contact No.");
                ed_cnno.requestFocus();
            }
        }
        else
        {
            pd.show();
            Call<DevBody> call= ApiClient.getClient().Develop(new DevBody(devnm,cno,"devListAdd"));
            call.enqueue(new Callback<DevBody>() {
                @Override
                public void onResponse(Call<DevBody> call, Response<DevBody> response) {
                    if (!response.isSuccessful())
                    {
                        Toast.makeText(DeveloperActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    }
                    int x=response.body().getStatus();
                    if(x==1)
                    {
                        Toast.makeText(DeveloperActivity.this, "Developer Added!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                finish();
                            }
                        },2000);
                    }
                    else if(x==2)
                    {
                        Toast.makeText(DeveloperActivity.this, "Developer Already Exist!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                finish();
                            }
                        },2000);
                    }
                    else
                    {
                        Toast.makeText(DeveloperActivity.this, "Some Thing Wrong!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                finish();
                            }
                        }, 2000);
                    }
                }

                @Override
                public void onFailure(Call<DevBody> call, Throwable t) {

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
}