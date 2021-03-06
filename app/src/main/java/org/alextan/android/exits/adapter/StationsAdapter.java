package org.alextan.android.exits.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import org.alextan.android.exits.common.Constants;
import org.alextan.android.exits.R;
import org.alextan.android.exits.model.StationLocation;

import java.util.List;

/**
 * RecyclerView Adapter for StationsActivity
 * View of a list of destination stations
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {

    private Context mContext;
    private List<StationLocation> mStations;

    public StationsAdapter(Context context, List<StationLocation> stations) {
        mContext = context;
        mStations = stations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_item,
                parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(mStations.get(position));
    }

    @Override
    public int getItemCount() {
        return mStations.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return mStations.get(position).getStopName().substring(0, 1);
    }

    /**
     * View holder for managing items of the recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTvStationName;
        private RelativeLayout mLayoutRoot;
        private StationLocation mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayoutRoot = (RelativeLayout) itemView.findViewById(R.id.trip_item_root_layout);
            mTvStationName = (TextView) itemView.findViewById(R.id.station_item_tv_station_name);

            mLayoutRoot.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(Constants.EXTRA_STATION_INDEX, mItem.getStopIndex());
            ((Activity) mContext).setResult(Activity.RESULT_OK, returnIntent);
            ((Activity) mContext).finish();
        }

        public void setItem(StationLocation item) {
            mItem = item;
            mTvStationName.setText(mItem.getStopName());
        }
    }
}
