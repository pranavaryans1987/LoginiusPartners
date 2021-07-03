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
import com.loginius.loginiuspartners.Model.Stud.Student;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudAdpt extends RecyclerView.Adapter<StudAdpt.ViewHolder> {
    Context context;
    List<Student> list;
    ProgressDialog pd;

    public StudAdpt(Context context, List<Student> list) {
        this.context = context;
        this.list = list;
        pd = new ProgressDialog(context);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
    }

    @NonNull
    @Override
    public StudAdpt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stud_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudAdpt.ViewHolder holder, int position) {
        holder.stdid.setText(list.get(position).getId());
        holder.stdnm.setText(list.get(position).getNm());
        holder.stdpnd.setText(list.get(position).getPnd());
        holder.stdcrs.setText(list.get(position).getCrs());
        if (list.get(position).getPnd().equals("0") || list.get(position).getPnd().equals(0)) {
            holder.stdbtn.setEnabled(false);
            holder.stdbtn.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_bg_dis));
        } else {
            holder.stdbtn.setOnClickListener(new View.OnClickListener() {
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
                                Call<Student> call = ApiClient.getClient().stdRecPay(new Student(list.get(position).getId(), amt, "to_9kEn6_Re2ce1iVe_d"));
                                call.enqueue(new Callback<Student>() {
                                    @Override
                                    public void onResponse(Call<Student> call, Response<Student> response) {
                                        if (!response.isSuccessful()) {
                                            Toast.makeText(context, "Faild", Toast.LENGTH_SHORT).show();
                                            pd.cancel();
                                            return;
                                        }
                                        int x = response.body().getStatus();
                                        if (x == 1) {
                                            Toast.makeText(context, "Payment Done!", Toast.LENGTH_SHORT).show();
                                            holder.stdpnd.setText(response.body().getPnd().toString());
                                            if (response.body().getPnd().equals("0") || response.body().getPnd().equals(0)) {
                                                holder.stdbtn.setEnabled(false);
                                                holder.stdbtn.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_bg_dis));
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
                                    public void onFailure(Call<Student> call, Throwable t) {

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
        Button stdbtn;
        TextView stdid, stdnm, stdcrs, stdpnd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stdid = itemView.findViewById(R.id.std_id);
            stdbtn = itemView.findViewById(R.id.std_pay);
            stdcrs = itemView.findViewById(R.id.std_crs);
            stdnm = itemView.findViewById(R.id.std_nm);
            stdpnd = itemView.findViewById(R.id.std_pnd);
        }
    }
}
