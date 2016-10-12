package org.alextan.android.exits.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.alextan.android.exits.model.DreamFactoryResource;
import org.alextan.android.exits.model.Station;
import org.alextan.android.exits.model.StationLocation;
import org.alextan.android.exits.util.DreamFactoryJsonDeserializer;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GtfsService {
    String GTFS_URL = "***REMOVED***";
    String API_KEY_KEY = "X-DreamFactory-Api-Key";
    String API_KEY_VALUE = "***REMOVED***";
    String API_KEY_HEADER = API_KEY_KEY + ": " + API_KEY_VALUE;

    @Headers(API_KEY_HEADER)
    @GET("/api/v2/gtfs/_table/stations")
    Call<DreamFactoryResource<Station>> getAllStations();

    @Headers(API_KEY_HEADER)
    @GET("/api/v2/gtfs/_table/train_parent_stops")
    Call<DreamFactoryResource<StationLocation>> getAllStationLocations();

    Type stationType = new TypeToken<DreamFactoryResource<Station>>(){}.getType();
    Type stationLocationType = new TypeToken<DreamFactoryResource<StationLocation>>(){}.getType();
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(stationType, new DreamFactoryJsonDeserializer<>(Station.class))
            .registerTypeAdapter(stationLocationType, new DreamFactoryJsonDeserializer<>(StationLocation.class))
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GTFS_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

}