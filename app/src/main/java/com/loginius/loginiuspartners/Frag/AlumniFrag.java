package com.loginius.loginiuspartners.Frag;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.loginius.loginiuspartners.Model.Adapter.Frag.AlumniAdapter;
import com.loginius.loginiuspartners.Model.Stud.CurrentStud;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumniFrag extends Fragment {
    RecyclerView almnRec;
    Context context;
    List<CurrentStud> crntStd;
    AlumniAdapter adpt;
    ProgressDialog pd;
    SwipeRefreshLayout ref;

    public AlumniFrag(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_alumni, container, false);
        almnRec = view.findViewById(R.id.almn_rec);
        crntStd = new ArrayList<>();
        pd = new ProgressDialog(context);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
        pd.show();
        ref = view.findViewById(R.id.refresh);

        ref.setColorSchemeColors(getResources().getColor(R.color.indigo_500), getResources().getColor(R.color.pink_300), Color.RED, Color.CYAN);
        ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                crntStd.clear();
                pd.show();
                Call<List<CurrentStud>> call = ApiClient.getClient().almnStud(new CurrentStud("tok1EnS0td6Alm2niLis1t"));
                call.enqueue(new Callback<List<CurrentStud>>() {
                    @Override
                    public void onResponse(Call<List<CurrentStud>> call, Response<List<CurrentStud>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(context, "Faild", Toast.LENGTH_SHORT).show();
                            pd.cancel();
                            return;
                        }
                        List<CurrentStud> list = response.body();
                        for (CurrentStud c : list) {
                            crntStd.add(c);
                        }
                        adpt = new AlumniAdapter(context, crntStd);
                        almnRec.setLayoutManager(new LinearLayoutManager(context));
                        almnRec.setAdapter(adpt);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.cancel();
                                ref.setRefreshing(false);
                            }
                        }, 2000);
                    }

                    @Override
                    public void onFailure(Call<List<CurrentStud>> call, Throwable t) {

                    }
                });

            }
        });

        Call<List<CurrentStud>> call = ApiClient.getClient().almnStud(new CurrentStud("tok1EnS0td6Alm2niLis1t"));
        call.enqueue(new Callback<List<CurrentStud>>() {
            @Override
            public void onResponse(Call<List<CurrentStud>> call, Response<List<CurrentStud>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Faild", Toast.LENGTH_SHORT).show();
                    pd.cancel();
                    return;
                }
                List<CurrentStud> list = response.body();
                for (CurrentStud c : list) {
                    crntStd.add(c);
                }
                adpt = new AlumniAdapter(context, crntStd);
                almnRec.setLayoutManager(new LinearLayoutManager(context));
                almnRec.setAdapter(adpt);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.cancel();
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<List<CurrentStud>> call, Throwable t) {

            }
        });

        return view;
    }


}
