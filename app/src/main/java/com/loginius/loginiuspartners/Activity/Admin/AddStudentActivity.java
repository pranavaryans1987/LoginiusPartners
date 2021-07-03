package com.loginius.loginiuspartners.Activity.Admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.loginius.loginiuspartners.Model.Course.CourseNameBody;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudentActivity extends AppCompatActivity {

    ProgressDialog pd;
    TextInputEditText ed_stdnm, ed_cnno, ed_fCno, ed_crsfee, ed_feerv;
    Spinner spn_cnm;
    List<CourseNameBody> clist;
    ArrayList<String> cnm, cf;
    String crs, fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        clist = new ArrayList<>();
        cnm = new ArrayList<>();
        cf = new ArrayList<>();
        cnm.clear();
        cnm.add("SELECT COURSE");

        spn_cnm = findViewById(R.id.spn_stdcrs);
        ed_stdnm = findViewById(R.id.ed_stdnm);
        ed_cnno = findViewById(R.id.ed_stdcnno);
        ed_fCno = findViewById(R.id.ed_stdftcnno);
        ed_crsfee = findViewById(R.id.ed_stdcrsfee);
        ed_feerv = findViewById(R.id.ed_stdrvfee);

        pd = new ProgressDialog(AddStudentActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);

        loadData();
        spn_cnm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                crs = parent.getItemAtPosition(position).toString();
                if (!crs.equals("SELECT COURSE")) {
                    fee = cf.get(position - 1);
                    ed_crsfee.setText(fee);
                } else {
                    ed_crsfee.setText("");
                    ed_crsfee.setHint("COURSE FEE");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadData() {
        pd.show();
        Call<List<CourseNameBody>> call = ApiClient.getClient().getCourse(new CourseNameBody("crsGetList"));
        call.enqueue(new Callback<List<CourseNameBody>>() {
            @Override
            public void onResponse(Call<List<CourseNameBody>> call, Response<List<CourseNameBody>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddStudentActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    return;
                }
                clist = response.body();
                for (CourseNameBody c : clist) {
                    cnm.add(c.getCrsnm());
                    cf.add(c.getCrsfee());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddStudentActivity.this, android.R.layout.simple_list_item_1, cnm);
                spn_cnm.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CourseNameBody>> call, Throwable t) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.cancel();
            }
        }, 2000);
    }

    public void drawerBack(View view) {
        this.finish();
    }

    public void addStd(View view) {
        //add Student fab click
        String snm, cno, fcno, rv;
        snm = ed_stdnm.getText().toString();
        cno = ed_cnno.getText().toString();
        fcno = ed_fCno.getText().toString();
        rv = ed_feerv.getText().toString();
        if (snm.equals("") || cno.equals("") || fcno.equals("") || crs.equals("SELECT COURSE") || fee.equals("")) {
            if (snm.equals("") || snm.isEmpty()) {
                ed_stdnm.setError("Enter Student Name.");
                ed_stdnm.requestFocus();
            } else if (cno.equals("") || cno.isEmpty()) {
                ed_cnno.setError("Enter Contact No.");
                ed_cnno.requestFocus();
            } else if (fcno.equals("") || fcno.isEmpty()) {
                ed_fCno.setError("Enter Father Contact No.");
                ed_fCno.requestFocus();
            } else if (crs.equals("SELECT COURSE") || crs.isEmpty()) {
                Toast.makeText(this, "Please Select Course", Toast.LENGTH_SHORT).show();
                spn_cnm.requestFocus();
            } else if (fee.isEmpty() || fee.equals("")) {
                ed_crsfee.setError("Please Enter Fee.");
                ed_crsfee.requestFocus();
            }
        } else {
            pd.show();
            Call<CourseNameBody> call = ApiClient.getClient().addStudCall(new CourseNameBody(crs, fee, snm, cno, fcno, rv, "TknStdAddApi"));
            call.enqueue(new Callback<CourseNameBody>() {
                @Override
                public void onResponse(Call<CourseNameBody> call, Response<CourseNameBody> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(AddStudentActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int x = response.body().getStatus();
                    if (x == 1) {
                        Toast.makeText(AddStudentActivity.this, "Student Added!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                finish();
                            }
                        }, 2000);
                    } else if (x == 2) {
                        Toast.makeText(AddStudentActivity.this, "Student Already Exitst!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                finish();
                            }
                        }, 2000);
                    } else {
                        Toast.makeText(AddStudentActivity.this, "Some Thing Wrong!", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<CourseNameBody> call, Throwable t) {

                }
            });
        }
    }


}