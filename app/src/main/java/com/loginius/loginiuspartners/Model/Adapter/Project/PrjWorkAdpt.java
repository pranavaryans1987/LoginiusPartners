package com.loginius.loginiuspartners.Model.Adapter.Project;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loginius.loginiuspartners.Model.Project.ProjectBody;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrjWorkAdpt extends RecyclerView.Adapter<PrjWorkAdpt.ViewHolder> {
    Context context;
    List<ProjectBody> list;
    ProgressDialog pd;

    public PrjWorkAdpt(Context context, List<ProjectBody> list) {
        this.context = context;
        this.list = list;
        pd = new ProgressDialog(context);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
    }

    @NonNull
    @Override
    public PrjWorkAdpt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_work_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrjWorkAdpt.ViewHolder holder, int position) {
        holder.prjid.setText(list.get(position).getId());
        holder.prjnm.setText(list.get(position).getPnm());
        holder.prjpnm.setText(list.get(position).getCpnm());
        holder.date.setText(list.get(position).getDate());
        holder.prjbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                Call<ProjectBody> call=ApiClient.getClient().prjRec(new ProjectBody(list.get(position).getId(),"to1Ke0nP6rj2Wrk1"));
                call.enqueue(new Callback<ProjectBody>() {
                    @Override
                    public void onResponse(Call<ProjectBody> call, Response<ProjectBody> response) {
                        if(!response.isSuccessful())
                        {
                            Toast.makeText(context, "Faild", Toast.LENGTH_SHORT).show();
                            pd.cancel();
                            return;
                        }
                        int x=response.body().getStatus();
                        if(x==1)
                        {
                            Toast.makeText(context, "Added To Completed Project!", Toast.LENGTH_SHORT).show();
                            list.remove(position);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();
                                    pd.cancel();
                                }
                            }, 2000);
                        }
                    }

                    @Override
                    public void onFailure(Call<ProjectBody> call, Throwable t) {

                    }
                });
             }
        });

    }


    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button prjbtn;
        TextView prjnm, prjpnm, prjid, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.prjwrkdate);
            prjnm = itemView.findViewById(R.id.prjwrknm);
            prjpnm = itemView.findViewById(R.id.prjwrkcpmn);
            prjid = itemView.findViewById(R.id.prjwrkid);
            prjbtn = itemView.findViewById(R.id.prjwrkdone);
        }
    }
}
