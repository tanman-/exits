package org.alextan.android.exits.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.alextan.android.exits.R;
import org.alextan.android.exits.adapter.StationsAdapter;
import org.alextan.android.exits.api.GtfsApi;
import org.alextan.android.exits.common.Constants;
import org.alextan.android.exits.model.DreamFactoryResource;
import org.alextan.android.exits.model.StationLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;

/**
 * Shows list of destination stations
 */
public class StationsActivity extends AppCompatActivity {

    private RecyclerView mStationRecyclerView;
    private StationsAdapter mStationsAdapter;
    private int mOriginStationIndex;
    private ProgressDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        mOriginStationIndex = getIntent()
                .getIntExtra(Constants.EXTRA_STATION_INDEX, Constants.STATION_INDEX_DEFAULT_VALUE);
        if (mOriginStationIndex > Constants.ZERO) {
            new FetchStationListTask().execute();
        }
    }

    /**
     * Fetch list of stations from API
     */
    private class FetchStationListTask extends AsyncTask<Void, Void, ArrayList<StationLocation>> {

        @Override
        protected void onPreExecute() {
            mLoadingDialog = ProgressDialog.show(StationsActivity.this, null,
                    getString(R.string.msg_loading), true, false);
        }

        @Override
        protected ArrayList<StationLocation> doInBackground(Void... params) {
            GtfsApi gtfsApi = GtfsApi.retrofit.create(GtfsApi.class);
            Call<DreamFactoryResource<StationLocation>> call = gtfsApi.getAllStationLocations();
            DreamFactoryResource<StationLocation> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                call.cancel();
                cancel(true);
            }

            ArrayList<StationLocation> result;
            if (response != null) {
                result = (ArrayList<StationLocation>) response.getData();
                StationLocation originStation = null;
                for (StationLocation station : result) {
                    if (station.getStopIndex() == mOriginStationIndex) {
                        originStation = station;
                    }
                }
                if (originStation != null) {
                    result.remove(originStation);
                }
            } else {
                result = null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<StationLocation> result) {
            Collections.sort(result, new Comparator<StationLocation>() {
                @Override
                public int compare(StationLocation o1, StationLocation o2) {
                    return o1.getStopName().compareTo(o2.getStopName());
                }
            });
            mStationRecyclerView = (RecyclerView) findViewById(R.id.act_stations_rv);
            mStationsAdapter = new StationsAdapter(StationsActivity.this, result);
            mStationRecyclerView.setHasFixedSize(true);
            mStationRecyclerView.setLayoutManager(new LinearLayoutManager(StationsActivity.this));
            mStationRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mStationRecyclerView.setAdapter(mStationsAdapter);

            mLoadingDialog.dismiss();
        }
    }
}
