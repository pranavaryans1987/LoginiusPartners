package com.loginius.loginiuspartners.Activity.Project;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loginius.loginiuspartners.Model.Adapter.Project.PrjAdpt;
import com.loginius.loginiuspartners.Model.Project.ProjectBody;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectOstActivity extends AppCompatActivity {

    List<ProjectBody> prj;
    RecyclerView recPOA;
    PrjAdpt adpt;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_ost);
        recPOA=findViewById(R.id.prjostrecvw);
        pd = new ProgressDialog(ProjectOstActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
        prj = new ArrayList<>();

        callList();
    }

    private void callList() {
        pd.show();
        Call<List<ProjectBody>> call = ApiClient.getClient().prjOut(new ProjectBody("getT9okE6npr2jLi1st"));
        call.enqueue(new Callback<List<ProjectBody>>() {
            @Override
            public void onResponse(Call<List<ProjectBody>> call, Response<List<ProjectBody>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ProjectOstActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    pd.cancel();
                    return;
                }
                List<ProjectBody> pbody = response.body();
                for (ProjectBody p : pbody) {
                    prj.add(p);
                }
                adpt = new PrjAdpt(ProjectOstActivity.this, prj);
                recPOA.setLayoutManager(new LinearLayoutManager(ProjectOstActivity.this));
                recPOA.setAdapter(adpt);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.cancel();
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<List<ProjectBody>> call, Throwable t) {

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