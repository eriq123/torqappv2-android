package com.tupt.torqapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tupt.torqapp.Model.Ppmp;
import com.tupt.torqapp.R;

import java.util.List;

public class PpmpAdapter extends RecyclerView.Adapter<PpmpAdapter.ViewHolder> {
    private Context context;
    private List<Ppmp> list;

    public PpmpAdapter(Context context, List<Ppmp> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ppmp_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ppmp ppmp = list.get(position);

        holder.textCourse.setText(String.valueOf(ppmp.getCourse()));
        holder.textFYear.setText(String.valueOf(ppmp.getFiscal_year()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textCourse, textFYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textCourse = itemView.findViewById(R.id.course);
            textFYear = itemView.findViewById(R.id.fiscal_year);

        }
    }
}
