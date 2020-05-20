package com.abhi41.coronavirustracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abhi41.coronavirustracker.Adapter.StateAdapter;
import com.abhi41.coronavirustracker.Common.DIalog_Internet;
import com.abhi41.coronavirustracker.Common.SingletonRequestQueue;
import com.abhi41.coronavirustracker.model.DistrictModel;
import com.abhi41.coronavirustracker.model.StateModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcLoader;
import com.wessam.library.NetworkChecker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StateActivity extends AppCompatActivity implements Utils{

    private RecyclerView recyclerView;
    public static List<StateModel> stateModelList;
    public static ArrayList<DistrictModel> districtModelList;
    private StateModel stateModel;
    private StateAdapter stateAdapter;
    private SimpleArcLoader loader;
    private SwipeRefreshLayout swipeRefresh;

    int[] colors = {Color.parseColor("#757575"), Color.parseColor("#757575")};
    ArcConfiguration configuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        getSupportActionBar().setTitle("State Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        recyclerView = findViewById(R.id.recyclerView);
        loader = findViewById(R.id.loader);
        swipeRefresh = findViewById(R.id.swipeRefresh);

        configuration = new ArcConfiguration(StateActivity.this);
        configuration.setColors(colors);
        loader.refreshArcLoaderDrawable(configuration);

        stateModelList = new ArrayList<>();


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkChecker.isNetworkConnected(StateActivity.this))
                {
                    fetchState();
                    swipeRefresh.setRefreshing(false);
                }else {
                    DIalog_Internet.dialog_internet(StateActivity.this);
                }
            }
        });

        if (NetworkChecker.isNetworkConnected(StateActivity.this))
        {
            swipeRefresh.setRefreshing(false);
            fetchState();

        }else {
            DIalog_Internet.dialog_internet(StateActivity.this);
        }
        if (!NetworkChecker.isNetworkConnected(StateActivity.this)) {

            DIalog_Internet.dialog_internet(this);

        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            boolean fetchEnableTrue = intent.getBooleanExtra("fetchEnableTrue",false);
            if (fetchEnableTrue = true)
            {
                fetchState();
            }
        }
    };
    private void fetchState() {
        loader.setVisibility(View.VISIBLE);
        loader.start();
        stateModelList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.url_getState, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    for (int i = 0; i<jsonArray.length();i++)
                    {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String state = jsonObject.getString("state");
                        String active = formatter.format(Integer.parseInt(jsonObject.getString("active")));
                        String confirmed = formatter.format(Integer.parseInt(jsonObject.getString("confirmed")));
                        String recovered = formatter.format(Integer.parseInt(jsonObject.getString("recovered")));
                        String deaths = formatter.format(Integer.parseInt(jsonObject.getString("deaths")));

                        JSONArray jsonArray1 = jsonObject.getJSONArray("districtData");
                        districtModelList = new ArrayList<>();
                            for (int j = 0; j<jsonArray1.length();j++)
                            {
                                JSONObject object = jsonArray1.getJSONObject(j);

                                String Districtname = object.getString("name");
                                String zone = object.getString("zone");
                                String district_confirmed = object.getString("confirmed");

                                Log.d("Districtname",Districtname);
                                Log.d("zone",zone);

                                DistrictModel districtModel = new DistrictModel();
                                districtModel.setDistrictName(Districtname);
                                districtModel.setZone(zone);
                                districtModel.setConfirmed(district_confirmed);

                                districtModelList.add(districtModel);

                            }



                        stateModel = new StateModel();
                        stateModel.setState(state);
                        stateModel.setDeaths(deaths);
                        stateModel.setConfirmed(confirmed);
                        stateModel.setRecovered(recovered);
                        stateModel.setActive(active);
                        stateModel.setDistrictModelArrayList(districtModelList);


                        stateModelList.add(stateModel);

                    }

                    stateAdapter = new StateAdapter(StateActivity.this,stateModelList,districtModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(stateAdapter);


                    loader.stop();
                    loader.setVisibility(View.GONE);
                    swipeRefresh.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StateActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                loader.stop();
                loader.setVisibility(View.GONE);
            }
        });
        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        queue.add(stringRequest);
    }
}
