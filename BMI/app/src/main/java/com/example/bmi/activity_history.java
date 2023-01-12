package com.example.bmi;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_history extends AppCompatActivity {

    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final ImageView imgView = findViewById(R.id.imageView2);
        imgView.setImageResource(R.drawable.back);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeSize();
        loadData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeSize();
        showList();
    }

    public void showList(){
        final ListView listView = (ListView)findViewById(R.id.listView);
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.activity_column,
                new String[]{"date", "weight", "bmi", "categories"},
                new int[]{R.id.col_date, R.id.col_weight, R.id.col_bmi, R.id.col_cate});
        listView.setAdapter(adapter);
    }

    public void loadData(){
        Configuration config = getResources().getConfiguration();
        String filename = "cs361_6309650395.txt";
        String inputString;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                StringBuffer stringBuffer = new StringBuffer();
                while ((inputString = inputReader.readLine()) != null) {
                    stringBuffer.append(inputString + "\n");
                }

                Map<String, Object> map_main = new HashMap<String, Object>();
                map_main.put("date", getResources().getString(R.string.col_date));
                map_main.put("weight", getResources().getString(R.string.col_weight));
                map_main.put("bmi", getResources().getString(R.string.col_bmi));
                map_main.put("categories", getResources().getString(R.string.col_categories));
                list.add(map_main);

                JSONArray infile_array = new JSONArray(stringBuffer.toString());
                for (int i = infile_array.length()-1 ; i >= 0 ; i--) {
                    JSONObject obj = infile_array.getJSONObject(i);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("date", obj.getString("date"));
                    map.put("weight", obj.getString("weight"));
                    map.put("bmi", obj.getString("bmi"));

                    if (config.locale.getLanguage().equals("th")) {
                        if (obj.getString("categories").equals("Underweight")) {
                            map.put("categories", "ผอมเกินไป");
                        } else if (obj.getString("categories").equals("Normal")) {
                            map.put("categories", "น้ำหนักปกติ");
                        } else if (obj.getString("categories").equals("Overweight")) {
                            map.put("categories", "น้ำหนักเกิน");
                        } else if (obj.getString("categories").equals("Obesity Class 1")) {
                            map.put("categories", "โรคอ้วนระดับ 1");
                        } else if (obj.getString("categories").equals("Obesity Class 2")) {
                            map.put("categories", "โรคอ้วนระดับ 2");
                        }
                    } else {
                        map.put("categories", obj.getString("categories"));
                    }
                    list.add(map);
                }
                showList();

                inputReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//end if
    }

    public void changeSize(){
        final TextView textHis = (TextView)findViewById(R.id.textHistory);

        Configuration config = getResources().getConfiguration();
        textHis.setTextSize(config.fontScale*25);
    }
}