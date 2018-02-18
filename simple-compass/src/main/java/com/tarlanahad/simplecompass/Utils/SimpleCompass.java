package com.tarlanahad.simplecompass.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;

import com.tarlanahad.simplecompass.Activities.CompassActivity;

/**
 * Created by tarlan on 2/16/18.
 */

public class SimpleCompass {

    private Activity activity;

    private int CircleColor = Color.parseColor("#ffffff");

    private int CurrentDegreeColor = Color.parseColor("#ffffff");

    private int infoTextColor = Color.parseColor("#ffffff");

    private int arrowColor = Color.parseColor("#FFFFFF");

    private int finishActivityButtonColor = Color.parseColor("#ffffff");

    private Location fromLocation, toLocation;

    private int ContainerBackgroundColor = Color.parseColor("#514F4F");

    private String infoText = " _ ";

    public SimpleCompass setInfoText(String infoText) {
        this.infoText = infoText;
        return this;
    }
    // private int statusBarColor = Color.parseColor("#000000");

    public SimpleCompass Builder(Activity activity) {
        this.activity = activity;
        return this;
    }

    public SimpleCompass setCircleColor(int circleColor) {
        CircleColor = circleColor;
        return this;
    }

    public SimpleCompass setContainerBackgroundColor(int containerBackgroundColor) {
        ContainerBackgroundColor = containerBackgroundColor;
        return this;
    }

    /*
    public SimpleCompass setStatusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
        return this;
    }
    */

    public SimpleCompass setCurrentDegreeColor(int currentDegreeColor) {
        CurrentDegreeColor = currentDegreeColor;
        return this;
    }


    public SimpleCompass setFinishActivityButtonColor(int finishActivityButtonColor) {
        this.finishActivityButtonColor = finishActivityButtonColor;
        return this;
    }


    public void build() {
        Intent toCompass = new Intent(activity, CompassActivity.class);

        toCompass.putExtra("CircleColor", CircleColor);
        toCompass.putExtra("CurrentDegreeColor", CurrentDegreeColor);
        toCompass.putExtra("finishActivityButtonColor", finishActivityButtonColor);
        toCompass.putExtra("ContainerBackgroundColor", ContainerBackgroundColor);
        // toCompass.putExtra("statusBarColor", statusBarColor);

        if (fromLocation != null) {
            toCompass.putExtra("infoTextColor", infoTextColor);
            toCompass.putExtra("infoText", infoText);
            toCompass.putExtra("arrowColor", arrowColor);
            toCompass.putExtra("fromLocationLat", fromLocation.getLatitude());
            toCompass.putExtra("fromLocationLng", fromLocation.getLongitude());
            toCompass.putExtra("toLocationLat", toLocation.getLatitude());
            toCompass.putExtra("toLocationLng", toLocation.getLongitude());
        }
        activity.startActivity(toCompass);

    }


    public SimpleCompass withDestination(Location fromLocation, Location toLocation) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        return this;
    }


    public SimpleCompass setInfoTextColor(int infoTextColor) {
        this.infoTextColor = infoTextColor;
        return this;
    }

    //when there is to destination
    public SimpleCompass setArrowColor(int arrowColor) {
        this.arrowColor = arrowColor;
        return this;
    }
}
