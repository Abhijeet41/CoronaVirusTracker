package com.abhi41.coronavirustracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi41.coronavirustracker.DistrictReport;
import com.abhi41.coronavirustracker.R;
import com.abhi41.coronavirustracker.model.DistrictModel;

import java.util.List;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.MyViewHolder> {
    int position;
    DistrictReport districtReport;
    List<DistrictModel> districtModelList;
    public DistrictAdapter(DistrictReport districtReport, List<DistrictModel> districtModelList) {
        this.districtReport = districtReport;
        this.districtModelList = districtModelList;

    }

    @NonNull
    @Override
    public DistrictAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_district,parent,false);
        return new DistrictAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictAdapter.MyViewHolder holder, int position) {

        holder.tvConfirmed.setText(districtModelList.get(position).getConfirmed());
        holder.tvDistrictname.setText(districtModelList.get(position).getDistrictName());
     //   holder.tvZone.setText(districtModelList.get(position).getZone());

        if (districtModelList.get(position).getZone().equals("RED"))
        {
            holder.tvZone.setBackgroundColor(ContextCompat.getColor(districtReport,R.color.red));
        }
        else if (districtModelList.get(position).getZone().equals("ORANGE"))
        {
            holder.tvZone.setBackgroundColor(ContextCompat.getColor(districtReport,R.color.orange));
        } else if (districtModelList.get(position).getZone().equals("GREEN"))
        {
            holder.tvZone.setBackgroundColor(ContextCompat.getColor(districtReport,R.color.green));
        }else {
            holder.tvZone.setBackgroundColor(ContextCompat.getColor(districtReport,R.color.darkGray));
        }
    }

    @Override
    public int getItemCount() {
        return districtModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDistrictname,tvConfirmed;
        View tvZone;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvConfirmed = itemView.findViewById(R.id.tvConfirmed);
            tvDistrictname = itemView.findViewById(R.id.tvDistrictname);
            tvZone = itemView.findViewById(R.id.tvZone);
        }
    }
}
