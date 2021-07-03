package com.loginius.loginiuspartners.Activity.Admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loginius.loginiuspartners.Model.Course.CourseBody;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseActivity extends AppCompatActivity {

    ProgressDialog pd;
    EditText ed_nm, ed_fee, ed_dur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        pd = new ProgressDialog(CourseActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
        ed_nm = findViewById(R.id.ed_crsnm);
        ed_dur = findViewById(R.id.ed_crsDur);
        ed_fee = findViewById(R.id.ed_crsFee);


    }

    public void drawerBack(View view) {
        this.finish();
    }

    public void addCrs(View view) {
        //add course fab click
        if (ed_nm.equals("") || ed_dur.equals("") || ed_fee.equals("")) {
            if (ed_nm.equals("")) {
                ed_nm.setError("Please Enter Course Name.");
                ed_nm.requestFocus();
            } else if (ed_dur.equals("")) {
                ed_dur.setError("Plase Enter Duration Of Course.");
                ed_dur.requestFocus();
            } else {
                ed_fee.setError("Please Enter Fees.");
                ed_fee.requestFocus();
            }
        } else {
            pd.show();
            String nm, dur, fee;
            nm = ed_nm.getText().toString();
            dur = ed_dur.getText().toString();
            fee = ed_fee.getText().toString();
            Call<CourseBody> call = ApiClient.getClient().addCourseBodyCall(new CourseBody(nm, dur, fee, "InsCrs"));
            call.enqueue(new Callback<CourseBody>() {
                @Override
                public void onResponse(Call<CourseBody> call, Response<CourseBody> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(CourseActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int x=response.body().getStatus();
                    if(x==1)
                    {
                        Toast.makeText(CourseActivity.this, "Course Added!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                finish();
                            }
                        }, 2000);
                    }
                    else if(x==2)
                    {
                        Toast.makeText(CourseActivity.this, "Course Already Exist!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                finish();
                            }
                        }, 2000);
                    }
                    else
                    {
                        Toast.makeText(CourseActivity.this, "Some Thing Wrong!", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<CourseBody> call, Throwable t) {

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