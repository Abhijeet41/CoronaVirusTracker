package com.abhi41.coronavirustracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.abhi41.coronavirustracker.Adapter.DistrictAdapter;
import com.abhi41.coronavirustracker.Adapter.StateAdapter;
import com.abhi41.coronavirustracker.model.DistrictModel;
import com.abhi41.coronavirustracker.model.StateModel;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistrictReport extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<DistrictModel> districtModelList;
    private StateModel stateModel;
    private DistrictAdapter districtAdapter;
    private SwipeRefreshLayout swipeRefresh;
    int[] colors = {Color.parseColor("#757575"), Color.parseColor("#757575")};
    ArcConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_report);

        getSupportActionBar().setTitle("District Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);

        districtModelList = new ArrayList<>();



        Intent intent = getIntent();
        districtModelList = intent.getParcelableArrayListExtra("districtArrayList");
        districtAdapter = new DistrictAdapter(DistrictReport.this,districtModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(districtAdapter);

    }
}
