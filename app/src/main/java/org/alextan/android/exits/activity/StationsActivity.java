package org.alextan.android.exits.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.alextan.android.exits.R;
import org.alextan.android.exits.model.DreamFactoryJsonResponse;
import org.alextan.android.exits.model.Station;
import org.alextan.android.exits.service.GtfsService2;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;

public class StationsActivity extends AppCompatActivity {

    TextView tvStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        setupRest();
    }

    private void setupRest() {
        tvStations = (TextView) findViewById(R.id.tv_stations);

        new DownloadStations().execute();
    }

    private class DownloadStations extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> result = new LinkedList<>();

            GtfsService2 gtfsService = GtfsService2.retrofit.create(GtfsService2.class);
            Call<DreamFactoryJsonResponse<Station>> call = gtfsService.getAllStations();
            DreamFactoryJsonResponse<Station> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Station> stations = response != null ? response.getData() : null;

            for (Station station : stations) {
                result.add(station.getName());
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            tvStations.setText(result.toString());
        }
    }
}