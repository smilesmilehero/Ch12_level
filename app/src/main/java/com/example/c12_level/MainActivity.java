package com.example.c12_level;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
implements SensorEventListener {
    SensorManager sm;
    Sensor sr;
    TextView txv;
    ImageView igv;
    RelativeLayout layout;
    double mx = 0, my = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        txv = findViewById(R.id.txvIno);
        igv=findViewById(R.id.igvMove);
        layout = findViewById(R.id.layout);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(mx==0){
            mx=(layout.getWidth()-igv.getWidth())/20.0;
            my=(layout.getHeight()-igv.getHeight())/20.0;
        }

        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams) igv.getLayoutParams();

        params.leftMargin=(int)((5-event.values[0])*2*mx);
        params.topMargin=(int)((5+event.values[1])*2*my);

        igv.setLayoutParams(params);

        txv.setText(String.format("X軸: %1.2f, Y軸: %1.2f, Z軸: %1.2f",
                event.values[0],event.values[1],event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onResume(){
        super.onResume();
        sm.registerListener(this,sr,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause(){
        super.onPause();
        sm.unregisterListener(this);
    }

   /*@Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main,menu);
        return true;
    }*/
}