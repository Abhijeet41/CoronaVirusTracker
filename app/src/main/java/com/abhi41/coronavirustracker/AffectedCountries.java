package com.abhi41.coronavirustracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abhi41.coronavirustracker.Adapter.CountryAdapter;
import com.abhi41.coronavirustracker.Common.SingletonRequestQueue;
import com.abhi41.coronavirustracker.model.CountryModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity {

    public static List<CountryModel> countryModelList;
    private CountryModel countryModel;
    private CountryAdapter countryAdapter;
    private EditText edtSearch;
    private RecyclerView recy;
    private SimpleArcLoader arcLoader;
    int[] colors = {Color.parseColor("#757575"), Color.parseColor("#757575")};
    ArcConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);

        edtSearch = findViewById(R.id.edtSearch);
        recy = findViewById(R.id.recy);
        arcLoader = findViewById(R.id.arcLoader);
        countryModelList = new ArrayList<>();

        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        configuration = new ArcConfiguration(AffectedCountries.this);
        configuration.setColors(colors);
        arcLoader.refreshArcLoaderDrawable(configuration);


        fetchCountryname();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                countryAdapter.getFilter().filter(s);
                countryAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void fetchCountryname() {

        arcLoader.setVisibility(View.VISIBLE);
        arcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, Utils.url_countryName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arcLoader.setVisibility(View.GONE);
                arcLoader.stop();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        DecimalFormat formatter = new DecimalFormat("#,###,###");

                        String countryName = jsonObject.getString("country");
                        String cases = formatter.format(Integer.parseInt(jsonObject.getString("cases")));
                        String todayCases = formatter.format(Integer.parseInt(jsonObject.getString("todayCases")));
                        String deaths = formatter.format(Integer.parseInt(jsonObject.getString("deaths")));
                        String todayDeaths = formatter.format(Integer.parseInt(jsonObject.getString("todayDeaths")));
                        String recovered = formatter.format(Integer.parseInt(jsonObject.getString("recovered")));
                        String active = formatter.format(Integer.parseInt(jsonObject.getString("active")));
                        String critical = formatter.format(Integer.parseInt(jsonObject.getString("critical")));

                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flag = object.getString("flag");


                        countryModel = new CountryModel();
                        countryModel.setCountry(countryName);
                        countryModel.setCases(cases);
                        countryModel.setTodayCases(todayCases);
                        countryModel.setDeaths(deaths);
                        countryModel.setTodayDeaths(todayDeaths);
                        countryModel.setRecovered(recovered);
                        countryModel.setActive(active);
                        countryModel.setCritical(critical);
                        countryModel.setFlag(flag);

                        countryModelList.add(countryModel);

                    }

                    countryAdapter = new CountryAdapter(AffectedCountries.this, countryModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recy.setLayoutManager(mLayoutManager);
                    recy.setItemAnimator(new DefaultItemAnimator());
                    recy.setAdapter(countryAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                arcLoader.setVisibility(View.GONE);
                arcLoader.stop();
                Toast.makeText(AffectedCountries.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        queue.add(request);
    }
}
