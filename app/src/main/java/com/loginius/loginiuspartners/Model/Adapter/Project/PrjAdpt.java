package com.loginius.loginiuspartners.Model.Adapter.Project;

import android.app.AlertDialog;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.loginius.loginiuspartners.Model.Project.ProjectBody;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrjAdpt extends RecyclerView.Adapter<PrjAdpt.ViewHolder> {
    Context context;
    List<ProjectBody> list;
    ProgressDialog pd;

    public PrjAdpt(Context context, List<ProjectBody> list) {
        this.context = context;
        this.list = list;
        pd = new ProgressDialog(context);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
    }

    @NonNull
    @Override
    public PrjAdpt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrjAdpt.ViewHolder holder, int position) {
        holder.prjid.setText(list.get(position).getId());
        holder.prjnm.setText(list.get(position).getPrjnm());
        holder.prjpnd.setText(list.get(position).getPnd());
        holder.date.setText(list.get(position).getDate());
        if (list.get(position).getPnd().equals("0") || list.get(position).getPnd().equals(0)) {
            holder.prjbtn.setEnabled(false);
            holder.prjbtn.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_bg_dis));
        } else {
            holder.prjbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View prompt = li.inflate(R.layout.pay_dialog_box, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(prompt);
                    final AlertDialog dialog = alertDialogBuilder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    final TextInputEditText amount = (TextInputEditText) prompt.findViewById(R.id.ed_pay_dialg);
                    amount.setHint("Enter Receive Amount");
                    final Button pay, cnl;
                    pay = (Button) prompt.findViewById(R.id.btn_pay_ok);
                    cnl = (Button) prompt.findViewById(R.id.btn_pay_cnl);
                    pay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String amt = amount.getText().toString();
                            int a = Integer.parseInt(amt);
                            int x = Integer.parseInt(list.get(position).getPnd());
                            if (amt.equals("") || amt.equals(0) || amt.isEmpty() || a > x) {
                                if (a > x) {
                                    amount.setError("Enter Value is biggest Then Receive.");
                                    amount.setText(list.get(position).getPnd());
                                    amount.requestFocus();
                                } else {
                                    amount.setError("Please Enter Amount.");
                                    amount.requestFocus();
                                }
                            } else {
                                pd.show();
                                Call<ProjectBody> call = ApiClient.getClient().prjRec(new ProjectBody(list.get(position).getId(), amt, "to_9kEnP6_Re2ceR1iVe_dJ"));
                                call.enqueue(new Callback<ProjectBody>() {
                                    @Override
                                    public void onResponse(Call<ProjectBody> call, Response<ProjectBody> response) {
                                        if (!response.isSuccessful()) {
                                            Toast.makeText(context, "Faild", Toast.LENGTH_SHORT).show();
                                            pd.cancel();
                                            return;
                                        }
                                        int x = response.body().getStatus();
                                        if (x == 1) {
                                            Toast.makeText(context, "Payment Done!", Toast.LENGTH_SHORT).show();
                                            holder.prjpnd.setText(response.body().getPnd().toString());
                                            if (response.body().getPnd().equals("0") || response.body().getPnd().equals(0)) {
                                                holder.prjbtn.setEnabled(false);
                                                holder.prjbtn.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_bg_dis));
                                            }
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    pd.cancel();
                                                }
                                            }, 2000);
                                        } else {
                                            Toast.makeText(context, "Some Thing Wrong!", Toast.LENGTH_SHORT).show();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    pd.cancel();
                                                }
                                            }, 2000);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ProjectBody> call, Throwable t) {

                                    }
                                });
                                dialog.dismiss();
                            }
                        }
                    });
                    cnl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
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
        TextView prjnm, prjpnd, prjid, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.post_date);
            prjnm = itemView.findViewById(R.id.post_pnm);
            prjpnd = itemView.findViewById(R.id.post_amt);
            prjid = itemView.findViewById(R.id.post_id);
            prjbtn = itemView.findViewById(R.id.post_pay);
        }
    }
}
