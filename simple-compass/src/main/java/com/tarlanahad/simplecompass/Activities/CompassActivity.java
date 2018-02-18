package com.tarlanahad.simplecompass.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tarlanahad.simplecompass.Interfaces.CompassDegreeListener;
import com.tarlanahad.simplecompass.R;
import com.tarlanahad.simplecompass.Utils.Compass;
import com.tarlanahad.simplecompass.Views.CustomTypefaceSpan;
import com.tarlanahad.simplecompass.Views.FontableTextView;

import java.util.Locale;


public class CompassActivity extends AppCompatActivity {

    private static final String TAG = "CompassActivity";
    float BearingToLocation;
    FontableTextView DegreesTV;
    private Compass compass;
    TextView mTV_BearingToLocation;
    Boolean showDestination = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //  updateStatusBarColor(getIntent().getIntExtra("statusBarColor", 0));
        } catch (Exception e) {
        }

        setContentView((int) R.layout.activity_qible_compass);

        DegreesTV = findViewById(R.id.degreeTV);
        mTV_BearingToLocation = findViewById(R.id.bearingToLocation);
        this.compass = new Compass(this);

        if (getIntent().getDoubleExtra("fromLocationLat", 0) != 0) {

            Location toLocation = new Location("");

            toLocation.setLatitude(getIntent().getDoubleExtra("toLocationLat", 0));

            toLocation.setLongitude(getIntent().getDoubleExtra("toLocationLng", 0));

            Location fromLocation = new Location("");

            fromLocation.setLatitude(getIntent().getDoubleExtra("fromLocationLat", 0));

            fromLocation.setLongitude(getIntent().getDoubleExtra("fromLocationLng", 0));

            this.BearingToLocation = fromLocation.bearingTo(toLocation);

            findViewById(R.id.arrow).setRotation(this.BearingToLocation);

            this.compass.setBearingToMakkah(this.BearingToLocation);

            showDestination = true;

        }


        this.compass.setCompassDegreeListener(new CompassDegreeListener() {
            @Override
            public void compassDegreeListener(float degrees) {

                CompassActivity.this.DegreesTV.setText(String.valueOf(String.format(Locale.getDefault(), "%.1f", new Object[]{Float.valueOf(degrees)}) + "°"));


                if (showDestination) {
                    CompassActivity compassActivity = CompassActivity.this;
                    Locale locale = Locale.getDefault();
                    String str = "%.1f";
                    Object[] objArr = new Object[1];
                    objArr[0] = Float.valueOf((CompassActivity.this.BearingToLocation < 0.0f ? CompassActivity.this.BearingToLocation + 360.0f : CompassActivity.this.BearingToLocation) - degrees);
                    try {
                        compassActivity.setSpannableText(String.valueOf(String.format(locale, str, objArr)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        this.compass.arrowView = findViewById(R.id.arrowView);

        findViewById(R.id.finishActivity).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CompassActivity.this.finish();
            }
        });

        InitColors();
    }

    public void updateStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(color);
        }
    }

    private void InitColors() {

        Intent i = getIntent();

        GradientDrawable circleBack = (GradientDrawable) ((RelativeLayout) findViewById(R.id.arrowView)).getBackground();
        circleBack.setStroke((int) convertDpToPixel(2), i.getIntExtra("CircleColor", Color.parseColor("#ffffff")));
        circleBack.setColor(i.getIntExtra("ContainerBackgroundColor", Color.parseColor("#ffffff")));

        //N
        ((FontableTextView) findViewById(R.id.fontableTextView)).setTextColor(i.getIntExtra("CircleColor", Color.parseColor("#ffffff")));

        ((FontableTextView) findViewById(R.id.degreeTV)).setTextColor(i.getIntExtra("CurrentDegreeColor", Color.parseColor("#ffffff")));


        ((ImageView) findViewById(R.id.finishActivity)).setColorFilter(i.getIntExtra("finishActivityButtonColor", Color.parseColor("#ffffff")), android.graphics.PorterDuff.Mode.MULTIPLY);

        findViewById(R.id.container).setBackgroundColor(i.getIntExtra("ContainerBackgroundColor", Color.parseColor("#ffffff")));


        if (showDestination) {

            ((TextView) findViewById(R.id.bearingToLocation)).setTextColor(i.getIntExtra("infoTextColor", Color.parseColor("#ffffff")));

            ((TextView) findViewById(R.id.bearingToLocation)).setVisibility(View.VISIBLE);

            ((ImageView) findViewById(R.id.compassPin)).setColorFilter(i.getIntExtra("arrowColor", Color.parseColor("#ffffff")), android.graphics.PorterDuff.Mode.MULTIPLY);

            (findViewById(R.id.arrow)).setVisibility(View.VISIBLE);

        }
    }

    public float convertDpToPixel(float dp) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    private void setSpannableText(String degree) throws Exception {
        String infoText = getIntent().getStringExtra("infoText");
        Typeface Lato_Light = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        Typeface Lato_Bold = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf");

        SpannableStringBuilder SS = new SpannableStringBuilder(infoText + degree + "°");

        SS.setSpan(new CustomTypefaceSpan("", Lato_Light), 0, infoText.length(), 34);

        SS.setSpan(new CustomTypefaceSpan("", Lato_Bold), infoText.length(), SS.toString().length(), 34);

        //SS.setSpan(new CustomTypefaceSpan("", Lato_Light), SS.length() - lengthOfSecondPart, SS.toString().length(), 34);

        this.mTV_BearingToLocation.setText(SS);
    }

    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        this.compass.start();
    }

    protected void onPause() {
        super.onPause();
        this.compass.stop();
    }

    protected void onResume() {
        super.onResume();
        this.compass.start();
    }

    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        this.compass.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compass.stop();
    }
}
