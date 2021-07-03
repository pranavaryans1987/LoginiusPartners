package com.loginius.loginiuspartners.Activity.Developer;

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

import com.loginius.loginiuspartners.Model.Adapter.Project.RecvAmtAdpt;
import com.loginius.loginiuspartners.Model.Project.Project;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceivedActivity extends AppCompatActivity {

    List<Project> prj;
    RecyclerView prjRec;
    ProgressDialog pd;
    RecvAmtAdpt adpt;
    SwipeRefreshLayout ref;
    String chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);

        prjRec = findViewById(R.id.rcvRec);
        ref = findViewById(R.id.refrcv);

        SharedPreferences preferences = getSharedPreferences("Loginius", MODE_PRIVATE);
        chk = preferences.getString("name", "");

        prj = new ArrayList<>();
        pd = new ProgressDialog(ReceivedActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);

        ref.setColorSchemeColors(getResources().getColor(R.color.indigo_500), getResources().getColor(R.color.pink_300), Color.RED, Color.CYAN);
        ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pd.show();
                prj.clear();
                Call<List<Project>> call = ApiClient.getClient().onGoing(new Project(chk, "re1cEi1Vep6rOj2eCtl1ist"));
                call.enqueue(new Callback<List<Project>>() {
                    @Override
                    public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(ReceivedActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                            pd.cancel();
                            return;
                        }
                        List<Project> list = response.body();
                        for (Project p : list) {
                            prj.add(p);
                        }
                        adpt = new RecvAmtAdpt(ReceivedActivity.this, prj);
                        prjRec.setLayoutManager(new LinearLayoutManager(ReceivedActivity.this));
                        prjRec.setAdapter(adpt);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ref.setRefreshing(false);
                                pd.cancel();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onFailure(Call<List<Project>> call, Throwable t) {

                    }
                });
            }
        });
        callRecv();

    }

    private void callRecv() {
        pd.show();
        prj.clear();
        Call<List<Project>> call = ApiClient.getClient().onGoing(new Project(chk, "re1cEi1Vep6rOj2eCtl1ist"));
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ReceivedActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    pd.cancel();
                    return;
                }
                List<Project> list = response.body();
                for (Project p : list) {
                    prj.add(p);
                }
                adpt = new RecvAmtAdpt(ReceivedActivity.this, prj);
                prjRec.setLayoutManager(new LinearLayoutManager(ReceivedActivity.this));
                prjRec.setAdapter(adpt);
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