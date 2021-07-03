package com.loginius.loginiuspartners.Activity.Project;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.loginius.loginiuspartners.Model.Adapter.Project.OnGngAdpt;
import com.loginius.loginiuspartners.Model.Project.Project;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnGoingProjectActivity extends AppCompatActivity {

    RecyclerView onprjRec;
    ProgressDialog pd;
    List<Project> op;
    OnGngAdpt adpt;
    String chk;
    SwipeRefreshLayout refong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going_project);

        onprjRec = findViewById(R.id.onprjRec);
        refong = findViewById(R.id.refong);

        pd = new ProgressDialog(OnGoingProjectActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
        op = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("Loginius", MODE_PRIVATE);
        chk = preferences.getString("name", "");
        callOnGoingPrj();
        refong.setColorSchemeColors(getResources().getColor(R.color.indigo_500), getResources().getColor(R.color.pink_300), Color.RED, Color.CYAN);
        refong.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                op.clear();
                pd.show();
                Call<List<Project>> call = ApiClient.getClient().onGoing(new Project(chk, "oNg1oi1Ng6pRj2liSt1"));
                call.enqueue(new Callback<List<Project>>() {
                    @Override
                    public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(OnGoingProjectActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                            pd.cancel();
                            return;
                        }
                        List<Project> projects = response.body();
                        for (Project p : projects) {
                            op.add(p);
                        }
                        adpt = new OnGngAdpt(OnGoingProjectActivity.this, op);
                        onprjRec.setLayoutManager(new LinearLayoutManager(OnGoingProjectActivity.this));
                        onprjRec.setAdapter(adpt);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                refong.setRefreshing(false);
                            }
                        }, 2000);
                    }

                    @Override
                    public void onFailure(Call<List<Project>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void callOnGoingPrj() {
        pd.show();
        Call<List<Project>> call = ApiClient.getClient().onGoing(new Project(chk, "oNg1oi1Ng6pRj2liSt1"));
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(OnGoingProjectActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    pd.cancel();
                    return;
                }
                List<Project> projects = response.body();
                for (Project p : projects) {
                    op.add(p);
                }
                adpt = new OnGngAdpt(OnGoingProjectActivity.this, op);
                onprjRec.setLayoutManager(new LinearLayoutManager(OnGoingProjectActivity.this));
                onprjRec.setAdapter(adpt);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.cancel();
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

            }
        });
    }

    public void drawerBack(View view) {
        this.finish();
    }
}