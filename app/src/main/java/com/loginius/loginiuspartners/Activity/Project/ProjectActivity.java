package com.loginius.loginiuspartners.Activity.Project;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loginius.loginiuspartners.Model.Adapter.Project.ProjectAdpt;
import com.loginius.loginiuspartners.Model.Project.Project;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectActivity extends AppCompatActivity {

    List<Project> prj;
    RecyclerView rec;
    ProjectAdpt adpt;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        rec = findViewById(R.id.prjPayRec);
        pd = new ProgressDialog(ProjectActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
        prj = new ArrayList<>();
        callPAY();
    }

    private void callPAY() {
        pd.show();
        Call<List<Project>> call= ApiClient.getClient().getPrj(new Project("pRj1lI0stP2ayLi1st"));
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(ProjectActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    pd.cancel();
                    return;
                }
                List<Project> list=response.body();
                for(Project project:list)
                {
                    prj.add(project);
                }
                adpt=new ProjectAdpt(ProjectActivity.this,prj);
                rec.setLayoutManager(new LinearLayoutManager(ProjectActivity.this));
                rec.setAdapter(adpt);
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
}