package com.loginius.loginiuspartners.Activity.Stud;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loginius.loginiuspartners.Model.Adapter.StudAdpt;
import com.loginius.loginiuspartners.Model.Stud.Student;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentActivity extends AppCompatActivity {

    List<Student> std;
    RecyclerView stRec;
    StudAdpt stdAdpt;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        stRec = findViewById(R.id.studrecv);
        pd = new ProgressDialog(StudentActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
        std = new ArrayList<>();

        callStd();
    }

    private void callStd() {
        pd.show();
        Call<List<Student>> call = ApiClient.getClient().student(new Student("to8_kE6n_S3td_1"));
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(StudentActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.cancel();
                            return;
                        }
                    }, 2000);
                }
                List<Student> lst = response.body();
                for (Student s : lst) {
                    std.add(s);
                }
                stdAdpt = new StudAdpt(StudentActivity.this, std);
                stRec.setLayoutManager(new LinearLayoutManager(StudentActivity.this));
                stRec.setAdapter(stdAdpt);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.cancel();
                    }
                }, 2000);

            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {

            }
        });
    }
}