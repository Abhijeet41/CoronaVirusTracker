package com.abhi41.coronavirustracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi41.coronavirustracker.AffectedCountries;
import com.abhi41.coronavirustracker.DetailedActivity;
import com.abhi41.coronavirustracker.R;
import com.abhi41.coronavirustracker.model.CountryModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder>implements Filterable {

    private Context context;
    private List<CountryModel> countryModels;
    private List<CountryModel> countrylistForFilter;

    public CountryAdapter(AffectedCountries affectedCountries, List<CountryModel> countryModelList) {
        this.countryModels =countryModelList;
        this.countrylistForFilter =countryModelList;
        this.context = affectedCountries;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layout_country,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        RequestOptions reqOpt = RequestOptions
                .fitCenterTransform()
                .transform(new RoundedCorners(5))
                .diskCacheStrategy(DiskCacheStrategy.ALL) // It will cache your image after loaded for first time
                .override(holder.imageflag.getWidth(),holder.imageflag.getHeight());

        holder.tvCountryname.setText(countrylistForFilter.get(position).getCountry());
        Glide.with(context).load(countrylistForFilter.get(position).getFlag()).apply(reqOpt).thumbnail(/*sizeMultiplier=*/ 0.25f).into(holder.imageflag);
        holder.root_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("position", holder.getAdapterPosition());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return countrylistForFilter.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageflag;
        TextView tvCountryname;
        LinearLayout root_ll;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

             imageflag = itemView.findViewById(R.id.imageflag);
             tvCountryname = itemView.findViewById(R.id.tvCountryname);
            root_ll = itemView.findViewById(R.id.root_ll);
        }
    }
    @Override
    public Filter getFilter() {
        return filter;
    }

       final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0)
                {
                    results.count = countryModels.size();
                    results.values = countryModels;
                }else {
                    List<CountryModel> resultModel = new ArrayList<>();
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (CountryModel item : countryModels) {
                        if (item.getCountry().toLowerCase().contains(filterPattern)) {
                            resultModel.add(item);
                        }
                    }

                    results.count = resultModel.size();
                    results.values = resultModel;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countrylistForFilter = (List<CountryModel>)results.values;
                AffectedCountries.countryModelList =(List<CountryModel>)results.values;

                notifyDataSetChanged();
            }

    };
}
