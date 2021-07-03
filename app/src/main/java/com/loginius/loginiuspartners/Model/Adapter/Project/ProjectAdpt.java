package com.loginius.loginiuspartners.Model.Adapter.Project;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loginius.loginiuspartners.Model.Project.Project;
import com.loginius.loginiuspartners.R;

import java.util.List;

public class ProjectAdpt extends RecyclerView.Adapter<ProjectAdpt.ViewHolder> {
    Context context;
    List<Project> list;
    ProgressDialog pd;

    public ProjectAdpt(Context context, List<Project> list) {
        this.context = context;
        this.list = list;
        pd = new ProgressDialog(context);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
    }

    @NonNull
    @Override
    public ProjectAdpt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pay_project_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdpt.ViewHolder holder, int position) {
        holder.prjprt.setText(list.get(position).getParty());
        holder.prjnm.setText(list.get(position).getPrj());
        holder.prjpnd.setText(list.get(position).getAmt());
        holder.date.setText(list.get(position).getDate());
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
        TextView prjnm, prjpnd, prjprt, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.pay_date);
            prjnm = itemView.findViewById(R.id.pay_pnm);
            prjpnd = itemView.findViewById(R.id.pay_amt);
            prjprt = itemView.findViewById(R.id.pay_prt);

        }
    }
}
