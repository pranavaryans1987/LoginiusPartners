package com.loginius.loginiuspartners.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.loginius.loginiuspartners.Activity.Admin.AddProjectActivity;
import com.loginius.loginiuspartners.Activity.Admin.AddStudentActivity;
import com.loginius.loginiuspartners.Activity.Admin.CourseActivity;
import com.loginius.loginiuspartners.Activity.Admin.DevPayActivity;
import com.loginius.loginiuspartners.Activity.Admin.DeveloperActivity;
import com.loginius.loginiuspartners.Activity.Admin.ViewStudentActivity;
import com.loginius.loginiuspartners.Activity.Developer.PandingActivity;
import com.loginius.loginiuspartners.Activity.Developer.ReceivedActivity;
import com.loginius.loginiuspartners.Activity.Project.OnGoingProjectActivity;
import com.loginius.loginiuspartners.Activity.Project.ProjectActivity;
import com.loginius.loginiuspartners.Activity.Project.ProjectDoneActivity;
import com.loginius.loginiuspartners.Activity.Project.ProjectOstActivity;
import com.loginius.loginiuspartners.Activity.Project.ProjectWorkActivity;
import com.loginius.loginiuspartners.Activity.Stud.StudentActivity;
import com.loginius.loginiuspartners.R;

public class Activity_Dashboard extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout admn_desh, lnr, admLnr;
    CardView crd_cmpprj, crd_pndpay, crd_sprt;
    String chk, adm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        SharedPreferences preferences = getSharedPreferences("Loginius", MODE_PRIVATE);
        chk = preferences.getString("name", "");
        adm = preferences.getString("type", "");

        admn_desh = findViewById(R.id.admn_desh);
        admLnr = findViewById(R.id.admLnr);
        lnr = findViewById(R.id.lay_ld);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome : Mr. " + chk);


        if (adm.equals("0") || adm.equals("") || adm.isEmpty()) {
            admn_desh.setVisibility(View.GONE);
            lnr.setVisibility(View.VISIBLE);
            admLnr.setVisibility(View.GONE);
        }
    }

    public void project(View view) {
        //on Going Project
        if (adm.equals("0") || adm.equals("") || adm.isEmpty()) {
            //developer
            startActivity(new Intent(Activity_Dashboard.this, OnGoingProjectActivity.class));
        } else {
            //admin
            startActivity(new Intent(Activity_Dashboard.this, ProjectWorkActivity.class));
        }
    }

    public void donePrj(View view) {
        //completed Project
        startActivity(new Intent(Activity_Dashboard.this, ProjectDoneActivity.class));
    }

    public void pndPay(View view) {
        //pending payment
        startActivity(new Intent(Activity_Dashboard.this, PandingActivity.class));
    }

    public void rcvPay(View view) {
        //received payment
        if (adm.equals("0") || adm.equals("") || adm.isEmpty()) {
            //developer
            startActivity(new Intent(Activity_Dashboard.this, ReceivedActivity.class));
        } else {
            //admin
            startActivity(new Intent(Activity_Dashboard.this, ProjectActivity.class));
        }
    }

    public void suprt(View view) {
        //Support
        if (ActivityCompat.checkSelfPermission(Activity_Dashboard.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + "+916359906666"));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(Activity_Dashboard.this
                    , new String[]{Manifest.permission.CALL_PHONE}
                    , 100);
        }
    }

    public void plst(View view) {
        // OUR Play Store
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5101918195157571397")));
    }

    /*ADMIN PART START*/
    public void addProject(View view) {
        //add / asign Project
        startActivity(new Intent(Activity_Dashboard.this, AddProjectActivity.class));
    }

    public void PrjOutstnd(View view) {
        //Project Outstanding
        startActivity(new Intent(Activity_Dashboard.this, ProjectOstActivity.class));
    }

    public void devPay(View view) {
        //developer Payment
        startActivity(new Intent(Activity_Dashboard.this, DevPayActivity.class));
    }

    public void crs(View view) {
        //Add CourseBody
        startActivity(new Intent(Activity_Dashboard.this, CourseActivity.class));
    }

    public void addstd(View view) {
        //Add Student
        startActivity(new Intent(Activity_Dashboard.this, AddStudentActivity.class));
    }

    public void stdost(View view) {
        //Student Outstand
        startActivity(new Intent(Activity_Dashboard.this, StudentActivity.class));

    }

    public void vstd(View view) {
        //view Studen [current student / ALUMNIS]
        startActivity(new Intent(Activity_Dashboard.this, ViewStudentActivity.class));
        /*
         * 2 TABS
         * 1) CURRENT STUDENT
         *   -DETAILS
         *   -BTN COURSE COMPLETE
         * 2) ALUMNIS STUDENT
         *   - DETAILS
         */
    }

    public void addDev(View view) {
        startActivity(new Intent(Activity_Dashboard.this, DeveloperActivity.class));
    }

    /*ADMIN PART OVER*/

    public void logOut(MenuItem item) {
        SharedPreferences preferences = getSharedPreferences("Loginius", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        editor.apply();
        finish();
        startActivity(new Intent(Activity_Dashboard.this, MainActivity.class));
    }
}