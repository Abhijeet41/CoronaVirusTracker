package com.abhi41.coronavirustracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailedActivity extends AppCompatActivity {

    TextView tvCases,tvcountry,tvtodayCases,tvtodayDeaths,tvrecovered,tvdeaths,tvactive,tvcritical;
    int positionCountry;
    private Button btnState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        getSupportActionBar().setTitle("Detailed Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvCases = findViewById(R.id.tvCases);
        tvcountry = findViewById(R.id.tvcountry);
        tvtodayCases = findViewById(R.id.tvtodayCases);
        tvtodayDeaths = findViewById(R.id.tvtodayDeaths);
        tvrecovered = findViewById(R.id.tvrecovered);
        tvdeaths = findViewById(R.id.tvdeaths);
        tvactive = findViewById(R.id.tvactive);
        tvcritical = findViewById(R.id.tvcritical);

        btnState = findViewById(R.id.btnState);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);

        tvCases.setText(AffectedCountries.countryModelList.get(positionCountry).getCases());
        tvcountry.setText(AffectedCountries.countryModelList.get(positionCountry).getCountry());
        tvtodayCases.setText(AffectedCountries.countryModelList.get(positionCountry).getTodayCases());
        tvtodayDeaths.setText(AffectedCountries.countryModelList.get(positionCountry).getTodayDeaths());
        tvrecovered.setText(AffectedCountries.countryModelList.get(positionCountry).getRecovered());
        tvdeaths.setText(AffectedCountries.countryModelList.get(positionCountry).getDeaths());
        tvactive.setText(AffectedCountries.countryModelList.get(positionCountry).getActive());
        tvcritical.setText(AffectedCountries.countryModelList.get(positionCountry).getCritical());

        if (AffectedCountries.countryModelList.get(positionCountry).getCountry().equals("India"))
        {
            btnState.setVisibility(View.VISIBLE);
            btnState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DetailedActivity.this,StateActivity.class);
                    startActivity(i);
                }
            });
        }




    }
}
