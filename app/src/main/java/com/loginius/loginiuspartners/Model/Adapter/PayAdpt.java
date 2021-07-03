package com.loginius.loginiuspartners.Model.Adapter;

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
import com.loginius.loginiuspartners.Model.Project.Payment;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayAdpt extends RecyclerView.Adapter<PayAdpt.ViewHolder> {
    Context context;
    List<Payment> list;
    ProgressDialog pd;

    public PayAdpt(Context context, List<Payment> list) {
        this.context = context;
        this.list = list;
        pd = new ProgressDialog(context);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
    }

    @NonNull
    @Override
    public PayAdpt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayAdpt.ViewHolder holder, int position) {
        holder.payid.setText(list.get(position).getId());
        holder.prjnm.setText(list.get(position).getPrjnm());
        holder.damt.setText(list.get(position).getDevamt());
        holder.devnm.setText(list.get(position).getDevnm());
        holder.remamt.setText(list.get(position).getPndamt());
        if (list.get(position).getPndamt().equals("0") || list.get(position).getPndamt().equals(0)) {
            holder.paybtn.setEnabled(false);
            holder.paybtn.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_bg_dis));
        } else {
            holder.paybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View prompt = li.inflate(R.layout.pay_dialog_box, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(prompt);
                    final AlertDialog dialog = alertDialogBuilder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    final TextInputEditText amount = (TextInputEditText) prompt.findViewById(R.id.ed_pay_dialg);
                    final Button pay, cnl;
                    pay = (Button) prompt.findViewById(R.id.btn_pay_ok);
                    cnl = (Button) prompt.findViewById(R.id.btn_pay_cnl);
                    pay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String amt = amount.getText().toString();
                            int a = Integer.parseInt(amt);
                            int x = Integer.parseInt(list.get(position).getPndamt());
                            if (amt.equals("") || amt.equals(0) || amt.isEmpty() || a > x) {
                                if (a > x) {
                                    amount.setError("Enter Value is biggest Then Payment.");
                                    amount.setText(list.get(position).getPndamt());
                                    amount.requestFocus();
                                } else {
                                    amount.setError("Please Enter Amount.");
                                    amount.requestFocus();
                                }
                            } else {
                                pd.show();
                                Call<Payment> call = ApiClient.getClient().devPayAmt(new Payment(list.get(position).getId(), amt, "to_4kEn6_Pa2yM1nt"));
                                call.enqueue(new Callback<Payment>() {
                                    @Override
                                    public void onResponse(Call<Payment> call, Response<Payment> response) {
                                        if (!response.isSuccessful()) {
                                            Toast.makeText(context, "Faild", Toast.LENGTH_SHORT).show();
                                            pd.cancel();
                                            return;
                                        }
                                        int x = response.body().getStatus();
                                        if (x == 1) {
                                            Toast.makeText(context, "Payment Done!", Toast.LENGTH_SHORT).show();
                                            holder.remamt.setText(response.body().getPndamt().toString());
                                            if (response.body().getPndamt().equals("0") || response.body().getPndamt().equals(0)) {
                                                holder.paybtn.setEnabled(false);
                                                holder.paybtn.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_bg_dis));
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
                                    public void onFailure(Call<Payment> call, Throwable t) {

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
        Button paybtn;
        TextView prjnm, damt, devnm, remamt, payid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prjnm = itemView.findViewById(R.id.paylst_prjnm);
            damt = itemView.findViewById(R.id.paylst_devamt);
            devnm = itemView.findViewById(R.id.paylst_devnm);
            remamt = itemView.findViewById(R.id.paylst_remamt);
            payid = itemView.findViewById(R.id.paylist_id);
            paybtn = itemView.findViewById(R.id.paylist_btnpay);
        }
    }
}
