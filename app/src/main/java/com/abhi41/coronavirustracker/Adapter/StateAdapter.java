package com.abhi41.coronavirustracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi41.coronavirustracker.DistrictReport;
import com.abhi41.coronavirustracker.R;
import com.abhi41.coronavirustracker.StateActivity;
import com.abhi41.coronavirustracker.model.DistrictModel;
import com.abhi41.coronavirustracker.model.StateModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder>  {

    private Context context;
    private List<StateModel> stateModelList;
    private List<DistrictModel> districtModels;

    public StateAdapter(StateActivity stateActivity, List<StateModel> stateModelList, List<DistrictModel> districtModelList) {
        this.stateModelList =stateModelList;
        this.districtModels =districtModelList;
        this.context = stateActivity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layout_state,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final StateModel  stateModel = stateModelList.get(position);
        
        holder.tvStatename.setText(stateModel.getState());
        holder.tvConfirmed.setText(stateModel.getConfirmed());
        holder.tvRecovered.setText(stateModel.getRecovered());
        holder.tvDeceased.setText(stateModel.getDeaths());

        holder.Ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DistrictReport.class);
                intent.putExtra("districtArrayList",stateModel.getDistrictModelArrayList());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stateModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvStatename,tvConfirmed,tvRecovered,tvDeceased;
        LinearLayout Ll1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStatename = itemView.findViewById(R.id.tvStatename);
            tvConfirmed = itemView.findViewById(R.id.tvConfirmed);
            tvRecovered = itemView.findViewById(R.id.tvRecovered);
            tvDeceased = itemView.findViewById(R.id.tvDeceased);

            Ll1 = itemView.findViewById(R.id.Ll1);

        }
    }
}
