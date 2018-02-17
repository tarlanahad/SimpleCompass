package com.tarlanahad.sample;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tarlanahad.sample.R;
import com.tarlanahad.simplecompass.Utils.SimpleCompass;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Location fromLocation = new Location("");
        Location toLocation = new Location("");

        fromLocation.setLongitude(47);
        fromLocation.setLatitude(40);

        toLocation.setLongitude(49);
        toLocation.setLatitude(40);


        new SimpleCompass().Builder(this)
                .withDestination(fromLocation, toLocation)//TO show views related to destination
                .setContainerBackgroundColor(Color.WHITE)
                .setInfoText("Bearing from location A is ")
                .setCircleColor(Color.BLACK)
                .setCurrentDegreeColor(Color.BLACK)
                .setInfoTextColor(Color.BLACK)
                .setFinishActivityButtonColor(Color.BLACK)
                .setArrowColor(Color.DKGRAY)
                .build();

    }
}
