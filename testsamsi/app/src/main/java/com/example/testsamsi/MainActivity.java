package com.example.testsamsi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    private static final int POLL_INTERVAL = 500;
    private Handler hdr = new Handler();
    private PowerManager.WakeLock wl;
    SensorInfo sensor_info = new SensorInfo();
    Boolean shown_dialog = false;
    private static final int shake_threshold = 15;

    private Runnable pollTask = new Runnable() {
        public void run() {
            showDialog();
            hdr.postDelayed(pollTask, POLL_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null){
            finish();
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){
        // TO DO
    }//end onAccuracyChanged

    public void onSensorChanged(SensorEvent event){
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            sensor_info.accX=event.values[0];
            sensor_info.accY=event.values[1];
            sensor_info.accZ=event.values[2];
        }
    }//end onSensorChanged

    public void showDialog() {
        if( (Math.abs(sensor_info.accX)>shake_threshold) || (Math.abs(sensor_info.accY)>shake_threshold) || (Math.abs(sensor_info.accZ)>shake_threshold) ) {
            if(!shown_dialog) {
                shown_dialog = true;
                final AlertDialog.Builder viewDialog = new AlertDialog.Builder(this);
                //random number 1-20
                int random = (int)(Math.random() * 20 + 1);
                String textss;
                if (random == 1){
                    textss = "1 asdasdasd";
                } else if (random == 2){
                    textss = "2 asdasdasd";
                } else{
                    textss = "asdasd";
                }
                viewDialog.setTitle("เซียมซี");
                viewDialog.setMessage(textss);
                viewDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                shown_dialog = false;
                            }
                        });
                viewDialog.show();
            }//end if
        }//end if
    }//end showDialog

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        hdr.postDelayed(pollTask, POLL_INTERVAL);
    }//end onResume

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

        hdr.removeCallbacks(pollTask);
    }//end onPause

    static class SensorInfo{
        float accX, accY, accZ;
    }//end class SensorInfo

}