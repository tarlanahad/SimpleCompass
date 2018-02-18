package com.tarlanahad.simplecompass.Utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.tarlanahad.simplecompass.Interfaces.CompassDegreeListener;


public class Compass implements SensorEventListener {
    private static final String TAG = "Compass";
    private float BearingToMakkah;
    public View arrowView = null;
    private float azimuth = 0.0f;
    CompassDegreeListener compassDegreeListener;
    private float currectAzimuth = 0.0f;
    private Sensor gsensor;
    private float[] mGeomagnetic = new float[3];
    private float[] mGravity = new float[3];
    private Sensor msensor;
    private SensorManager sensorManager;

    public void setCompassDegreeListener(CompassDegreeListener compassDegreeListener) {
        this.compassDegreeListener = compassDegreeListener;
    }

    public void setBearingToMakkah(float bearingToMakkah) {
        this.BearingToMakkah = bearingToMakkah;
    }

    public Compass(Context context) {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.gsensor = this.sensorManager.getDefaultSensor(1);
        this.msensor = this.sensorManager.getDefaultSensor(2);
    }

    public void start() {
        this.sensorManager.registerListener(this, this.gsensor, 1);
        this.sensorManager.registerListener(this, this.msensor, 1);
    }

    public void stop() {
        this.sensorManager.unregisterListener(this);
    }

    private void adjustArrow() {
        if (this.arrowView == null) {
            Log.i(TAG, "arrow view is not set");
            return;
        }
        Log.i(TAG, "will set rotation from " + this.currectAzimuth + " to " + this.azimuth);
        Animation an = new RotateAnimation(-this.currectAzimuth, -this.azimuth, 1, 0.5f, 1, 0.5f);
        this.currectAzimuth = this.azimuth;
        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        this.arrowView.startAnimation(an);
    }

    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            if (event.sensor.getType() == 1) {
                this.mGravity[0] = (this.mGravity[0] * 0.97f) + (event.values[0] * 0.029999971f);
                this.mGravity[1] = (this.mGravity[1] * 0.97f) + (event.values[1] * 0.029999971f);
                this.mGravity[2] = (this.mGravity[2] * 0.97f) + (event.values[2] * 0.029999971f);
            }
            if (event.sensor.getType() == 2) {
                this.mGeomagnetic[0] = (this.mGeomagnetic[0] * 0.97f) + (event.values[0] * 0.029999971f);
                this.mGeomagnetic[1] = (this.mGeomagnetic[1] * 0.97f) + (event.values[1] * 0.029999971f);
                this.mGeomagnetic[2] = (this.mGeomagnetic[2] * 0.97f) + (event.values[2] * 0.029999971f);
            }
            float[] R = new float[9];
            if (SensorManager.getRotationMatrix(R, new float[9], this.mGravity, this.mGeomagnetic)) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                this.azimuth = (float) Math.toDegrees((double) orientation[0]);
                this.azimuth = (this.azimuth + 360.0f) % 360.0f;
                try {
                    this.compassDegreeListener.compassDegreeListener(this.azimuth);
                }catch (Exception e){}
                adjustArrow();
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}