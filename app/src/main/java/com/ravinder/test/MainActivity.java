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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,SensorEventListener{

    private TextView proxity,lightIntensity,accelerometer,Temperature;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        next= (Button) findViewById(R.id.next);
//        next.setOnClickListener((v)-> {
//            Intent intent = new Intent(MainActivity.this, graph.class);
//            startActivity(intent);
//        });

        proxity = (TextView) findViewById(R.id.humidity);
        accelerometer = (TextView) findViewById(R.id.pressure);
        Temperature = (TextView) findViewById(R.id.temperature);
        lightIntensity = (TextView) findViewById(R.id.lightIntenstiy);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Sensor proxitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Sensor acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(acceleroSensor!=null){
            sensorManager.registerListener(this,acceleroSensor,SensorManager.SENSOR_DELAY_NORMAL );
        }
        else {
            Toast.makeText(this, "Accelerometer not available", Toast.LENGTH_SHORT).show();
        }

        if(proxitySensor!=null){
            sensorManager.registerListener(this,proxitySensor,SensorManager.SENSOR_DELAY_NORMAL );
        }
        else {
            Toast.makeText(this, "proximity not available", Toast.LENGTH_SHORT).show();
        }

        if(tempSensor!=null){
            sensorManager.registerListener(this,tempSensor,SensorManager.SENSOR_DELAY_NORMAL );
        }
        else {
            Toast.makeText(this, "temperature not available", Toast.LENGTH_SHORT).show();
        }


        if(lightSensor!=null){
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL );
        }
        else {
            Toast.makeText(this, "lightSensor not available", Toast.LENGTH_SHORT).show();
        }


        initViews();
        initListerners();

    }

    private void initViews(){
        next= (Button) findViewById(R.id.next);
    }

    private void initListerners(){
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.next:
                Intent intent = new Intent(MainActivity.this, graph.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                accelerometer.setText("Accelerometer"+Math.sqrt(event.values[0]*event.values[0]+event.values[1]*event.values[1]+event.values[2]*event.values[2]));

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                Temperature.setText("Temperature"+event.values[0]);

            case Sensor.TYPE_PROXIMITY:
                proxity.setText("Proximity"+event.values[0]);

            case Sensor.TYPE_LIGHT:
                lightIntensity.setText("Light"+event.values[0]);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
