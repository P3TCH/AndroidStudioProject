package com.example.ass2_siamsi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorManager;
    private float acc_x = 0;
    private float acc_y = 0;
    private float acc_z = 0;
    private boolean showing = false;
    private Handler handler = new Handler();

    private Runnable pollTask = new Runnable() {
        @Override
        public void run() {
            showDialog();
            handler.postDelayed(pollTask, 500);
        }
    };

    private Runnable Shake1 = new Runnable() {
        @Override
        public void run() {
            simsi1();
            handler.postDelayed(Shake2, 500);
        }
    };

    private Runnable Shake2 = new Runnable() {
        @Override
        public void run() {
            simsi2();
            handler.postDelayed(Shake1, 500);
        }
    };

    private void simsi1(){
        if ((Math.abs(acc_x) > 10) || (Math.abs(acc_y) > 10) || (Math.abs(acc_z) > 10)) {
            ImageView simsi = (ImageView) findViewById(R.id.imageView);
            simsi.setImageResource(R.drawable.simsi2);
        }

    }

    private void simsi2(){
        if ((Math.abs(acc_x) > 10) || (Math.abs(acc_y) > 10) || (Math.abs(acc_z) > 10)) {
            ImageView simsi = (ImageView) findViewById(R.id.imageView);
            simsi.setImageResource(R.drawable.simsi3);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //read sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null){
            //no accelerometer on this device
            finish();
        }
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        //set image
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.simsi);
    }

    public String randomSamsi(int random){
        if (random == 1){
            return ("คำทำนายที่หนึ่ง เซียมซีใบนี้ว่าด้วย ช่วงนี้ชะตาชีวิตเปรียบเหมือนแสงพระอาทิตย์อันแรงกล้า ทำการสิ่งใดไม่มีอุปสรรค ถ้าเจ็บป่วยจะมีหมอดีมารักษา เงินทองไม่ขัดสนการงานก้าวหน้าสรรพสิ่งดีนักแล");
        } else if (random == 2){
            return ("คำทำนายที่สอง เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้ไม่มีอุปสรรค จะได้โชคลาภอันพึงประสงค์ การงานเจริญรุ่งเรือง ถ้าเจ็บป่วยจะมีหมอดีมารักษา เนื้อคู่ยังอยู่ไกลทุกอย่างดีนักแล");
        } else if (random == 3){
            return ("คำทำนายที่สาม เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้ อาจมีอุปสรรคอยู่บ้างเล็กน้อย จงไหว้พระขอพรสิ่งร้ายจะกลับกลายเป็นดี ถามเรื่องการงานแลเชื่อเสียงช่วงนี้ยังดีอยู่ โรคภัยไม่มี โชคลาภมีน้อย เนื้อคู่จะช่วยท่านสร้างชื่อเสียงได้");
        } else if (random == 4){
            return ("คำทำนายที่สี่ เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้จะมีโชคลาภ การงานรุ่งเรือง ถ้าเจ็บป่วยจะมีหมอดีมารักษา ครอบครัวและตัวท่านจะประสบสุขตลอดกาลดีนักแล");
        } else if (random == 5){
            return ("คำทำนายที่ห้า เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้ยังไม่ราบรื่น การงานมีอุปสรรค ท่านจะเจ็บไข้ได้ป่วยความรักไม่สดชื่น โชคลาภยังไม่มี จงหมั่นไหว้พระ และขอพร วสิ่งร้ายก็จะทุเลาลงบ้าง");
        } else if (random == 6){
            return ("คำทำนายที่หก เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้จะพบคนที่มีบุญมาช่วยเหลือ การงานก้าวหน้า เงินทองคล่องตัว ความรักจะพบเนื้อคู่ เจ็บป่วยไม่มี ทุกสิ่งทุกอย่างสมประสงค์ดีนักแล");
        } else if (random == 7){
            return ("คำทำนายที่เจ็ด เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้จะได้ลาภ ผ้าผ่อน เงินทอง การงานไม่มีอุปสรรค แลจะได้ที่อยู่กันอันพึงใจ ความรักสดชื่น เจ็บป่วยจะมีหมอดีมารักษา มิฉะนั้นจะได้รับความสุขอย่างประเสริฐยิ่งแล");
        } else if (random == 8){
            return ("คำทำนายที่แปด เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้จะมีลาภ การงานจะรุ่งเรือง เจ็บป่วยจะหมายเร็ว เงินทองไหลมาเทมา สรรพสิ่งสมดังปรารถนาทุกประการดีนักแล");
        } else if (random == 9){
            return ("คำทำนายที่เก้า เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้จะได้ยศฐาบรรดาศักดิ์ และสรรพสิ่งลาภจะบริบูรณ์ด้วยทรัพย์สมบัติ การงานเจริญรุ่งเรือง ไม่มีโรคภัยอันตราย ความรักสมหวัง สรรพสิ่งไม่มีอุปสรรคดีนักแล");
        } else if (random == 10){
            return ("คำทำนายที่สิบ เซียมซีใบนี้ว่าด้วย ชะตาชีวิตในช่วงนี้จะได้รับความเดือดร้อนด้วยถ้อยคำอันมิชอบจากผู้อื่น และควรระวังอันตรายจากการขับขี่ต่าง ๆ การงานมีอุปสรรคนิดหน่อย โชคลาภยังไม่มี ความรักมีอุปสรรคมาก จงหมั่นไหว้ขอพร สิ่งร้ายอาจกลายเป็นดี");
        } else if (random == 11){
            return ("คำทำนายที่สิบเอ็ด เซียมซีใบนี้ว่าด้วย ชะตาชีวิตในช่วงนี้จะเกิดถ้วยความให้เกรงสตรีเป็นศัตรูจงหนัก มิฉะนั้นจะถูกฟ้องหาเพราะชู้เมีย ไม่ควรเดินทางกลางคืน การงานไม่มีอุปสรรค อาจจะเจ็บป่วยเสียงเงินทอง จึงควรไหว้พระให้ท่านคุ้มครองสิ่งต่าง ๆ อาจกลายเป็นดี");
        } else if (random == 12) {
            return ("คำทำนายที่สิบสอง เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้ จะได้ลาภยศบริบูรณ์สมประสงค์ ทั้งจะอายุยืนยาว การค้าขายเจริญรุ่งเรือง จะมีเนื้อคู่อันประเสริฐ ทุกสิ่งที่ท่านขอจะสมประสงค์ดีนักแล");
        } else if (random == 13){
            return ("คำทำนายที่สิบสาม เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้ จะได้คนมีบุญมาอุ้มส่งและคุ้มครอง ถ้าเป็นความจะชนะความ การงานไม่มีอุปสรรค เจ็บป่วยจะมีหมอดีมารักษา ความรักเบิกบาน ถ้าเป็นนักเรียนควรตั้งใจศึกษา อนาคตจะรุ่งเรืองดีนักแล");
        } else if (random == 14){
            return ("คำทำนายที่สิบสี่ เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้ จะอันตรายถึงชีวิต ท่านควรจะไปไหว้ (แปะกง) และขอพรจากท่านแล้วทุกอย่างจะดีขึ้น การงานไม่มีอุปสรรค ครอบครัว อยู่เย็นเป็นสุข ตัวท่านอาจจะเจ็บป่วยได้ในช่วงนี้ จึงหมั่นขอพรไหว้พระให้มาก ๆ สิ่งร้ายอาจจะกลายเป็นดี");
        } else if (random == 15){
            return ("คำทำนายที่สิบห้า เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้ จะได้แก้วแหวนเงินทอง สรรพสิ่งสมดังใจหมาย การค้าขายรุ่งเรือง ถ้าเจ็บป่วยจะมีหมอดีมารักษา เนื้อคู่เป็นผู้มีบุญ ทุกสิ่งสมประสงค์ ดีนักแล");
        } else if (random == 16){
            return ("คำทำนายที่สิบหก เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้ จะได้ลาภ ผ้าผ่อน สรรพสิ่งทั้งศัตรูจะคิดร้ายมิได้ การค้าขายไม่มีอุปสรรค โรคภัยไม่มี เนื้อคู่นั้นอยู่ไกลเวลานี้ มิฉะนั้นจะมีผู้มีบุญมาคุ้มครองดีนักแล");
        } else if (random == 17){
            return ("คำทำนายที่สิบเจ็ด เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้ จะได้พึ่งผู้สูงอายุหรือผู้มียศฐาบรรดาศักดิ์ และลาภยศกาล สรรพสิ่งสมประสงค์ การงานก้าวหน้า อายุยืนยาว มิฉะนั้นจะได้คู่ที่มีทรัพย์ดีนักแล");
        } else if (random == 18){
            return ("คำทำนายที่สิบแปด เซียมซีใบนี้ว่าด้วย ชะตาชีวิตช่วงนี้ จะมีลาภน้อย การค้าขายหาผล ๆ บังเกิด จะได้คู่ที่พอใจ ถ้าเจ็บป่วยหายง่าย ถ้าคบผู้ใหญ่จะมีอำนาจ ควรหมั่นไหว้พระแล้วท่านจะได้สรรพสิ่งดังใจหมาย");
        } else {
            return ("");
        }
    }

    public void showDialog() {
        //check if the device is shaking show dialog
        if ((Math.abs(acc_x) > 15) || (Math.abs(acc_y) > 15) || (Math.abs(acc_z) > 15)) {
            //show dialog
            if(showing == false){
                showing = true;
                //show dialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageResource(R.drawable.simsi);

                //random number 1-18
                int random = (int)(Math.random() * 18) + 1;
                builder.setTitle("ใบเซียมซีที่ " + random);
                builder.setMessage(randomSamsi(random));
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            handler.post(Shake1);
                            showing = false;
                        }
                    });
                builder.show();
            }
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            acc_x = event.values[0];
            acc_y = event.values[1];
            acc_z = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        handler.post(pollTask);
        handler.post(Shake1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        handler.removeCallbacks(pollTask);
        handler.removeCallbacks(Shake1);
        handler.removeCallbacks(Shake2);
    }
}