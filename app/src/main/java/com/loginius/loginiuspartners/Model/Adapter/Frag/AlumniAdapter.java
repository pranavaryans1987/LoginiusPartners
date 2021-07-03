package com.loginius.loginiuspartners.Model.Adapter.Frag;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import java.util.List;

public class AlumniAdapter extends RecyclerView.Adapter<AlumniAdapter.ViewHolder> {
    Context context;
    List<CurrentStud> list;
    ProgressDialog pd;
    AlumniAdapter adpt;

    public AlumniAdapter(Context context, List<CurrentStud> list) {
        this.context = context;
        this.list = list;
        pd = new ProgressDialog(context);
        pd.setMessage("please wait ...");
        pd.setCancelable(false);
    }

    @NonNull
    @Override
    public AlumniAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crnt_stud_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumniAdapter.ViewHolder holder, int position) {

        holder.stdid.setText(list.get(position).getId());
        holder.stdcrs.setText(list.get(position).getCrs());
        holder.stdnm.setText(list.get(position).getName());
        holder.done.setVisibility(View.GONE);
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
                if (isWhatsappInstalled) {
                    try {
                        Intent sendIntent = new Intent("android.intent.action.MAIN");
                        sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91" + list.get(position).getMob()) + "@s.whatsapp.net");
                        context.startActivity(sendIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Uri uri = Uri.parse("market://details?id=com.whatsapp");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                    context.startActivity(goToMarket);
                }

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
