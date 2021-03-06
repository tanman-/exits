package org.alextan.android.exits.util;

import android.content.Context;

import org.alextan.android.exits.R;
import org.alextan.android.exits.common.Constants;

/**
 * Utility methods related to Trip objects
 */
public class TripUtil {

    /**
     * Cleanse station name strings retrieved from the Directions API.
     * Original strings have a 'station' suffix or are too long for displaying on phone screens.
     * Note: strings are stored in constants rather than string resources because context is not
     * available in the calling object.
     */
    public static String cleanseStationName(String station) {
        return station
                .replace(Constants.SEARCH_STATION_1, Constants.NULL)
                .replace(Constants.SEARCH_STATION_2, Constants.NULL)
                .replace(Constants.SEARCH_TRAIN_1, Constants.NULL)
                .replace(Constants.SEARCH_TRAIN_2, Constants.NULL)
                .replace(Constants.SEARCH_SHELLHARBOUR, Constants.CLEANSED_SHELLHARBOUR)
                .replace(Constants.SEARCH_MQU, Constants.CLEANSED_MQU)
                .replace(Constants.SEARCH_INTL_AIRPORT,  Constants.CLEANSED_INTL_AIRPORT)
                .trim();
    }

    /**
     * Cleanse line name strings retrieved from the Directions API.
     * Original line strings have a are too long for displaying on phone screens or are
     * inconsistent.
     * Note: line names are stored in constants rather than string resources because context is not
     * available in the calling object.
     */
    public static String cleanseLineName(String line) {
        if (line.contains(Constants.SUBSTRING_T1)) {
            return Constants.T1_LINE;
        } else if (line.contains(Constants.SUBSTRING_T2)) {
            return Constants.T2_LINE;
        } else if (line.contains(Constants.SUBSTRING_T3)) {
            return Constants.T3_LINE;
        } else if (line.contains(Constants.SUBSTRING_T4)) {
            return Constants.T4_LINE;
        } else if (line.contains(Constants.SUBSTRING_T5)) {
            return Constants.T5_LINE;
        } else if (line.contains(Constants.SUBSTRING_T6)) {
            return Constants.T6_LINE;
        } else if (line.contains(Constants.SUBSTRING_T7)) {
            return Constants.T7_LINE;
        } else if (line.contains(Constants.SUBSTRING_NORTH_COAST)) {
            return Constants.NORTH_COAST_NSW_LINE;
        } else if (line.contains(Constants.SUBSTRING_SOUTHERN_NSW)
                || line.contains(Constants.SUBSTRING_SYD_MEL_1)
                || line.contains(Constants.SUBSTRING_SYD_MEL_2)) {
            return Constants.SOUTHERN_NSW_LINE;
        } else if (line.contains(Constants.SUBSTRING_WESTERN_NSW)) {
            return Constants.WESTERN_NSW_LINE;
        } else if (line.contains(Constants.SUBSTRING_NORTH_WESTERN)) {
            return Constants.NORTH_WESTERN_NSW_LINE;
        } else {
            return line;
        }
    }

    /**
     * Fetch the resource ID of a line, given the line name
     */
    public static int chooseLineColour(String line, Context context) {
        if (line.equals(context.getResources().getString(R.string.line_t1))) {
            return R.color.colorT1Line;
        } else if (line.equals(context.getResources().getString(R.string.line_t2))) {
            return R.color.colorT2Line;
        } else if (line.equals(context.getResources().getString(R.string.line_t3))) {
            return R.color.colorT3Line;
        } else if (line.equals(context.getResources().getString(R.string.line_t4))) {
            return R.color.colorT4Line;
        } else if (line.equals(context.getResources().getString(R.string.line_t5))) {
            return R.color.colorT5Line;
        } else if (line.equals(context.getResources().getString(R.string.line_t6))) {
            return R.color.colorT6Line;
        } else if (line.equals(context.getResources().getString(R.string.line_t7))) {
            return R.color.colorT7Line;
        } else {
            return R.color.colorOtherLine;
        }
    }

    /**
     * Fetch drawable shape of a line, given the line name
     */
    public static int fetchLineDrawable(String line, Context context) {
        if (line.equals(context.getResources().getString(R.string.line_t1))) {
            return R.drawable.round_corner_t1;
        } else if (line.equals(context.getResources().getString(R.string.line_t2))) {
            return R.drawable.round_corner_t2;
        } else if (line.equals(context.getResources().getString(R.string.line_t3))) {
            return R.drawable.round_corner_t3;
        } else if (line.equals(context.getResources().getString(R.string.line_t4))) {
            return R.drawable.round_corner_t4;
        } else if (line.equals(context.getResources().getString(R.string.line_t5))) {
            return R.drawable.round_corner_t5;
        } else if (line.equals(context.getResources().getString(R.string.line_t6))) {
            return R.drawable.round_corner_t6;
        } else if (line.equals(context.getResources().getString(R.string.line_t7))) {
            return R.drawable.round_corner_t7;
        } else {
            return R.drawable.round_corner_other;
        }
    }
}
