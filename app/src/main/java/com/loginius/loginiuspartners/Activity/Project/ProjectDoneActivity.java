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

import com.loginius.loginiuspartners.Activity.Developer.PandingActivity;
import com.loginius.loginiuspartners.Model.Adapter.Project.PandAmtAdpt;
import com.loginius.loginiuspartners.Model.Project.Project;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectDoneActivity extends AppCompatActivity {
    List<Project> prj;
    RecyclerView doneRec;
    ProgressDialog pd;
    PandAmtAdpt adpt;
    SwipeRefreshLayout ref;
    String chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_done);
        doneRec = findViewById(R.id.doneRec);
        ref = findViewById(R.id.refdn);

        SharedPreferences preferences = getSharedPreferences("Loginius", MODE_PRIVATE);
        chk = preferences.getString("name", "");

        prj = new ArrayList<>();
        pd=new ProgressDialog(ProjectDoneActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
        ref.setColorSchemeColors(getResources().getColor(R.color.indigo_500), getResources().getColor(R.color.pink_300), Color.RED, Color.CYAN);
        ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pd.show();
                prj.clear();
                Call<List<Project>> call= ApiClient.getClient().onGoing(new Project(chk,"dO1nePr1oJe6Ct2lIs1T"));
                call.enqueue(new Callback<List<Project>>() {
                    @Override
                    public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                        if(!response.isSuccessful())
                        {
                            Toast.makeText(ProjectDoneActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                            pd.cancel();
                            return;
                        }
                        List<Project> list=response.body();
                        Toast.makeText(ProjectDoneActivity.this, response.body().size()+"" ,Toast.LENGTH_SHORT).show();
                        for(Project p:list)
                        {
                            prj.add(p);
                        }
                        adpt=new PandAmtAdpt(ProjectDoneActivity.this,prj);
                        doneRec.setLayoutManager(new LinearLayoutManager(ProjectDoneActivity.this));
                        doneRec.setAdapter(adpt);
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
        callPrjDone();
    }

    private void callPrjDone() {
        pd.show();
        prj.clear();
        Call<List<Project>> call= ApiClient.getClient().onGoing(new Project(chk,"dO1nePr1oJe6Ct2lIs1T"));
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(ProjectDoneActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    pd.cancel();
                    return;
                }
                List<Project> list=response.body();
                for(Project p:list)
                {
                    prj.add(p);
                }
                adpt=new PandAmtAdpt(ProjectDoneActivity.this,prj);
                doneRec.setLayoutManager(new LinearLayoutManager(ProjectDoneActivity.this));
                doneRec.setAdapter(adpt);
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