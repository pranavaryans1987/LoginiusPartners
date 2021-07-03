package com.loginius.loginiuspartners.Activity.Admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.loginius.loginiuspartners.Model.Develop.DevList;
import com.loginius.loginiuspartners.Model.Project.ProjectBody;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectActivity extends AppCompatActivity {

    Spinner spn_dev;
    List<DevList> lstDev;
    ArrayList<String> dnm;
    String devnm = "Select Developer", pnm = "", cpnm = "", cno = "", prtl = "", pamt = "", damt = "", rvamt = "";
    ProgressDialog pd;
    TextInputEditText ed_prtnm, ed_cnpnm, ed_cntno, ed_prjnm, ed_prjamt, ed_devamt, ed_rvamt;
    TextView txtDate;
    int mYear, mMonth, mDay;
    Button btnPrj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        btnPrj = findViewById(R.id.addPrjbtn);
        spn_dev = findViewById(R.id.spn_devNm);
        txtDate = findViewById(R.id.txt_duedt);
        ed_prtnm = findViewById(R.id.ed_prtnm);
        ed_cnpnm = findViewById(R.id.ed_cntpnm);
        ed_cntno = findViewById(R.id.ed_no);
        ed_prjnm = findViewById(R.id.ed_prjtlt);
        ed_prjamt = findViewById(R.id.ed_prjamt);
        ed_devamt = findViewById(R.id.ed_devAmt);
        ed_rvamt = findViewById(R.id.ed_amtrec);

        dnm = new ArrayList<>();
        lstDev = new ArrayList<>();
        dnm.clear();
        dnm.add("Select Developer");

        pd = new ProgressDialog(AddProjectActivity.this);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);

        loaddev();

        spn_dev.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                devnm = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loaddev() {
        pd.show();
        Call<List<DevList>> call = ApiClient.getClient().getDev(new DevList("getDevLdata"));
        call.enqueue(new Callback<List<DevList>>() {
            @Override
            public void onResponse(Call<List<DevList>> call, Response<List<DevList>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddProjectActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                    return;
                }

                lstDev = response.body();
                for (DevList d : lstDev) {
                    dnm.add(d.getDnm().toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddProjectActivity.this, android.R.layout.simple_list_item_1, dnm);
                spn_dev.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<DevList>> call, Throwable t) {

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

    public void addProject(View view) {
        //add Project FAB CLick
        getStringFrmUser();
        if (pnm.equals("") || cpnm.equals("") || cno.equals("") || prtl.equals("") || pamt.equals("") ||
                txtDate.getText().equals("") || damt.equals("") || (devnm.equals("Select Developer") || devnm.equals(""))) {
            if (pnm.equals("") || pnm.isEmpty()) {
                ed_prtnm.setError("Please Enter Party Name");
                ed_prtnm.requestFocus();
            } else if (cpnm.equals("") || cpnm.isEmpty()) {
                ed_cnpnm.setError("Please Enter Contact Person Name");
                ed_cnpnm.requestFocus();
            } else if (cno.isEmpty() || cno.isEmpty()) {
                ed_cntno.setError("Please Enter Contact No.");
                ed_cntno.requestFocus();
            } else if (prtl.equals("") || prtl.isEmpty()) {
                ed_prjnm.setError("Please Enter Project Title.");
                ed_prjnm.requestFocus();
            } else if (pamt.equals("") || pamt.isEmpty()) {
                ed_prjamt.setError("Please Enter Project Amount");
                ed_prjamt.requestFocus();
            } else if (damt.equals("") || damt.isEmpty()) {
                ed_devamt.setError("Please Enter Dev. Amount.");
                ed_devamt.requestFocus();
            } else if (devnm.equals("Select Developer") || devnm.equals("")) {
                Toast.makeText(this, "Please Select Developer.", Toast.LENGTH_SHORT).show();
                spn_dev.requestFocus();
            } else if (txtDate.getText().equals("")) {
                Toast.makeText(this, "Please Select Due Date.", Toast.LENGTH_SHORT).show();
                btnPrj.requestFocus();
            }

        } else {
            pd.show();
            String date = txtDate.getText().toString();
            Call<ProjectBody> call = ApiClient.getClient().PrjAdd(new ProjectBody(pnm, cpnm, cno, prtl, pamt, damt, date, rvamt, devnm, "Tk_3ad6_DPrO2jeC1T"));
            call.enqueue(new Callback<ProjectBody>() {
                @Override
                public void onResponse(Call<ProjectBody> call, Response<ProjectBody> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(AddProjectActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int x = response.body().getStatus();
                    if (x == 1) {
                        Toast.makeText(AddProjectActivity.this, "Project Added!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddProjectActivity.this, "Project Already Exist!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddProjectActivity.this, "Some Thing Wrong!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                finish();
                            }
                        },2000);
                    }
                }

                @Override
                public void onFailure(Call<ProjectBody> call, Throwable t) {

                }
            });

        }
    }

    private void getStringFrmUser() {
        pnm = ed_prtnm.getText().toString();
        cpnm = ed_cnpnm.getText().toString();
        cno = ed_cntno.getText().toString();
        prtl = ed_prjnm.getText().toString();
        pamt = ed_prjamt.getText().toString();
        damt = ed_devamt.getText().toString();
        rvamt = ed_rvamt.getText().toString();
    }


    public void selectDate(View view) {
        //open calender and display to textview selected date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(AddProjectActivity.this, R.style.DatePickerThemeLight, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                txtDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        }, mYear, mMonth, mDay);
        dialog.show();
//        dialog.setThemeDark(true);
//        dialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
//        dialog.setMinDate(c);
//        dialog.show(getFragmentManager(), "Datepickerdialog");
    }
}