package com.loginius.loginiuspartners.Model.Adapter.Frag;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.loginius.loginiuspartners.Model.Stud.CurrentStud;
import com.loginius.loginiuspartners.R;
import com.loginius.loginiuspartners.Service.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrntAdapter extends RecyclerView.Adapter<CrntAdapter.ViewHolder> {
    Context context;
    List<CurrentStud> list;


    public CrntAdapter(Context context, List<CurrentStud> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public CrntAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crnt_stud_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrntAdapter.ViewHolder holder, int position) {

        holder.stdid.setText(list.get(position).getId());
        holder.stdcrs.setText(list.get(position).getCrs());
        holder.stdnm.setText(list.get(position).getName());
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
                //if (isWhatsappInstalled) {
                    try {
//                        Intent sendIntent = new Intent("android.intent.action.MAIN");
//                        sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
//                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91" + list.get(position).getMob()) + "@s.whatsapp.net");
//                        context.startActivity(sendIntent);
                        String url = "https://api.whatsapp.com/send?phone=91"+list.get(position).getMob();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
               // } else {
                    Uri uri = Uri.parse("market://details?id=com.whatsapp");
               /*     Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                    context.startActivity(goToMarket);
                }*/

            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "+91" + list.get(position).getMob()));
                context.startActivity(intent);
            }
        });

        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<CurrentStud> call = ApiClient.getClient().updCrs(new CurrentStud(list.get(position).getId(), "c1rnt0Tok6en2Stu1d"));
                call.enqueue(new Callback<CurrentStud>() {
                    @Override
                    public void onResponse(Call<CurrentStud> call, Response<CurrentStud> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(context, "Faild", Toast.LENGTH_SHORT).show();

                            return;
                        }
                        int x = response.body().getStatus();
                        if (x == 1) {
                            list.remove(position);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();

                                }
                            }, 2000);
                        } else {
                            Toast.makeText(context, "Some Thing Wrong", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentStud> call, Throwable t) {

                    }
                });

            }
        });
    }

    private boolean whatsappInstalledOrNot(String s) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
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
        AppCompatImageButton call, chat, done;
        TextView stdid, stdnm, stdcrs;
        CardView main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main = itemView.findViewById(R.id.crnlimain);
            call = itemView.findViewById(R.id.crnlicall);
            chat = itemView.findViewById(R.id.crnlichat);
            done = itemView.findViewById(R.id.crnlidone);
            stdid = itemView.findViewById(R.id.crnliid);
            stdnm = itemView.findViewById(R.id.crnlinm);
            stdcrs = itemView.findViewById(R.id.crnlicrs);
        }
    }
}
