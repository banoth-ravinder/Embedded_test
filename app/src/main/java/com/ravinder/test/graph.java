package com.ravinder.test;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class graph extends AppCompatActivity implements SensorEventListener {

    private GraphView graphTemp, graphProx, graphAccelor, graphLight;
    private LineGraphSeries<DataPoint> tempSeries, proxSeries , accelorSeries, lightSeries;
    private int i,j,k,l;
    private Button previous;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(graph.this, MainActivity.class);
                startActivity(intent);
            }
        });

        graphTemp = (GraphView) findViewById(R.id.temp);
        graphProx = (GraphView) findViewById(R.id.proximity);
        graphAccelor = (GraphView) findViewById(R.id.accelormeter);
        graphLight = (GraphView) findViewById(R.id.light);


        graphTemp.getViewport().setScrollable(true);
        graphTemp.getViewport().setScalable(true);
        graphTemp.getViewport().setMaxX(100);
        graphTemp.setTitle("Temperatue");

        graphProx.getViewport().setScrollable(true);
        graphProx.getViewport().setScalable(true);
        graphProx.getViewport().setMaxX(100);
        graphProx.setTitle("proximity");

        graphAccelor.getViewport().setScrollable(true);
        graphAccelor.getViewport().setScalable(true);
        graphAccelor.getViewport().setMaxX(100);
        graphAccelor.setTitle("accelerometer");


        graphLight.getViewport().setScrollable(true);
        graphLight.getViewport().setScalable(true);
        graphLight.getViewport().setMaxX(100);
        graphLight.setTitle("Temperatue");

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Sensor proxitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Sensor acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (acceleroSensor != null) {
            sensorManager.registerListener(this, acceleroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Accelerometer not available", Toast.LENGTH_SHORT).show();
        }

        if (proxitySensor != null) {
            sensorManager.registerListener(this, proxitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "proximity not available", Toast.LENGTH_SHORT).show();
        }

        if (tempSensor != null) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "temperature not available", Toast.LENGTH_SHORT).show();
        }


        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "lightSensor not available", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                double rms = Math.sqrt(event.values[0]*event.values[0]+event.values[1]*event.values[1]+event.values[2]*event.values[2]);
                accelorSeries.appendData(new DataPoint(i++,rms),true,1000);
                break;
            case Sensor.TYPE_PROXIMITY:
                proxSeries.appendData(new DataPoint(j++,event.values[0]),true,10);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                tempSeries.appendData(new DataPoint(k++,event.values[0]),true,1000);
                break;
            case Sensor.TYPE_LIGHT:
                lightSeries.appendData(new DataPoint(l++,event.values[0]),true,100);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected  void onResume() {
        super.onResume();
        tempSeries = new LineGraphSeries<>();
        proxSeries = new LineGraphSeries<>();
        accelorSeries = new LineGraphSeries<>();
        lightSeries = new LineGraphSeries<>();

        graphTemp.addSeries(tempSeries);
        graphProx.addSeries(proxSeries);
        graphAccelor.addSeries(accelorSeries);
        graphLight.addSeries(lightSeries);

        i=j=k=l=0;
    }
}
