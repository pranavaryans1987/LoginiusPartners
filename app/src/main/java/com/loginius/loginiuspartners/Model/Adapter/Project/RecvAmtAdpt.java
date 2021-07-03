package com.loginius.loginiuspartners.Model.Adapter.Project;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loginius.loginiuspartners.Model.Project.Project;
import com.loginius.loginiuspartners.R;

import java.util.List;

public class RecvAmtAdpt extends RecyclerView.Adapter<RecvAmtAdpt.ViewHolder> {
    Context context;
    List<Project> list;

    public RecvAmtAdpt(Context context, List<Project> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecvAmtAdpt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.on_goning_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecvAmtAdpt.ViewHolder holder, int position) {
        holder.prjnm.setText(list.get(position).getPrj());
        holder.date.setTextColor(Color.BLACK);
        holder.date.setText(list.get(position).getDate());
        holder.prjamt.setText(list.get(position).getAmt());

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
        TextView prjnm, prjamt, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.ongdate);
            prjnm = itemView.findViewById(R.id.ongprjnm);
            prjamt = itemView.findViewById(R.id.ongamt);
        }
    }
}
