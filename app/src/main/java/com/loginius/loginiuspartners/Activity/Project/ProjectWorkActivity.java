package com.loginius.loginiuspartners.Activity.Project;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loginius.loginiuspartners.Model.Adapter.Project.PrjWorkAdpt;
import com.loginius.loginiuspartners.Model.Project.ProjectBody;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectWorkActivity extends AppCompatActivity {

    RecyclerView prjRec;
    List<ProjectBody> prj;
    ProgressDialog pd;
    PrjWorkAdpt adpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_work);
        prjRec = findViewById(R.id.prjRec);
        pd = new ProgressDialog(ProjectWorkActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
        prj = new ArrayList<>();

        pd.show();
        Call<List<ProjectBody>> call = ApiClient.getClient().listPrj(new ProjectBody("getT10okE6npr2jLi1st"));
        call.enqueue(new Callback<List<ProjectBody>>() {
            @Override
            public void onResponse(Call<List<ProjectBody>> call, Response<List<ProjectBody>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ProjectWorkActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    pd.cancel();
                    return;
                }
                List<ProjectBody> list = response.body();
                for (ProjectBody p : list) {
                    prj.add(p);
                }
                adpt = new PrjWorkAdpt(ProjectWorkActivity.this, prj);
                prjRec.setLayoutManager(new LinearLayoutManager(ProjectWorkActivity.this));
                prjRec.setAdapter(adpt);
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


    }
}