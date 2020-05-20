package com.abhi41.coronavirustracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhi41.coronavirustracker.Common.DIalog_Internet;
import com.abhi41.coronavirustracker.Common.SingletonRequestQueue;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcLoader;
import com.wessam.library.LayoutImage;
import com.wessam.library.NetworkChecker;
import com.wessam.library.NoInternetLayout;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Utils {

    private PieChart pie;
    private TextView tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvDeath, tvAffectedCountry,txtDateTime;
    private SimpleArcLoader loader;
    private ScrollView scrollstate;
    private Button btnTrack;
    private SwipeRefreshLayout swipeRefresh;
    int[] colors = {Color.parseColor("#757575"), Color.parseColor("#757575")};
    ArcConfiguration configuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pie = findViewById(R.id.pie);

        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvDeath = findViewById(R.id.tvDeath);
        tvAffectedCountry = findViewById(R.id.tvAffectedCountry);
        txtDateTime = findViewById(R.id.txtDateTime);

        loader = findViewById(R.id.loader);
        scrollstate = findViewById(R.id.scrollstate);
        btnTrack = findViewById(R.id.btnTrack);

        configuration = new ArcConfiguration(MainActivity.this);
        configuration.setColors(colors);
        loader.refreshArcLoaderDrawable(configuration);

        swipeRefresh = findViewById(R.id.swipeRefresh);

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (NetworkChecker.isNetworkConnected(MainActivity.this))
                {
                    swipeRefresh.setRefreshing(false);
                    Intent intent = new Intent(MainActivity.this, AffectedCountries.class);
                    startActivity(intent);

                }else {
                    DIalog_Internet.dialog_internet(MainActivity.this);
                }

            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkChecker.isNetworkConnected(MainActivity.this))
                {
                    fetchData();
                    swipeRefresh.setRefreshing(false);
                }else {
                    DIalog_Internet.dialog_internet(MainActivity.this);
                }

            }
        });

        if (!NetworkChecker.isNetworkConnected(MainActivity.this)) {

            DIalog_Internet.dialog_internet(this);

        }else {
            fetchData();
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
                fetchData();
            }
        }
    };

    private void fetchData() {
        loader.setVisibility(View.VISIBLE);
        loader.start();


        final StringRequest request = new StringRequest(Request.Method.GET, Utils.BaseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    DecimalFormat formatter = new DecimalFormat("#,###,###");

                    tvCases.setText(formatter.format(Integer.parseInt(jsonObject.getString("cases"))));
                    tvRecovered.setText(formatter.format(Integer.parseInt(jsonObject.getString("recovered"))));
                    tvActive.setText(formatter.format(Integer.parseInt(jsonObject.getString("active"))));
                    tvCritical.setText(formatter.format(Integer.parseInt(jsonObject.getString("critical"))));
                    tvAffectedCountry.setText(formatter.format(Integer.parseInt(jsonObject.getString("affectedCountries"))));
                    tvDeath.setText(formatter.format(Integer.parseInt(jsonObject.getString("deaths"))));
                    tvTodayCases.setText(formatter.format(Integer.parseInt(jsonObject.getString("todayCases"))));




                    pie.setRotationEnabled(true);
                    //pieChart.setUsePercentValues(true);
                    //pieChart.setHoleColor(Color.BLUE);
                    //pieChart.setCenterTextColor(Color.BLACK);
                    pie.setHoleRadius(25f);
                    pie.setTransparentCircleAlpha(0);
                    pie.setCenterText("Total Cases\n" + NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(jsonObject.getString("cases"))));
                    pie.setCenterTextSize(6);

                    addDataSet(Integer.parseInt(jsonObject.getString("recovered")), Integer.parseInt(jsonObject.getString("active")), Integer.parseInt(jsonObject.getString("deaths")));

                    loader.stop();
                    loader.setVisibility(View.GONE);
                    swipeRefresh.setRefreshing(false);
                    dateTimestamp();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefresh.setRefreshing(false);
                loader.stop();
                loader.setVisibility(View.GONE);
            }
        });
        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        queue.add(request);

    }

    private void addDataSet(int recover, int active, int death) {


        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        float[] yData = {active, recover, death};


        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(1);
        pieDataSet.setValueTextSize(6);


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#0098D5"));
        colors.add(Color.parseColor("#4CAF50"));
        colors.add(Color.parseColor("#DB0F00"));
        pieDataSet.setColors(colors);

//
        Legend l = pie.getLegend();
        l.setEnabled(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pie.setData(pieData);
        pie.invalidate();

    }

    private void dateTimestamp()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy h:mm a");
        String dateString = formatter.format(new Date());
        txtDateTime.setText(dateString);
    }

}
