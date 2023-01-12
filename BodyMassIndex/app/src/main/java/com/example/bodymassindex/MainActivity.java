package com.example.bodymassindex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set max the input of height and weight
        final EditText edit_text1 = (EditText)findViewById(R.id.editTextWeight);
        edit_text1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        final EditText edit_text2 = (EditText)findViewById(R.id.editTextHeight);
        edit_text2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        final TextView bmi = (TextView)findViewById(R.id.textView8);
        final TextView categories = (TextView)findViewById(R.id.textView7);
        bmi.setText("-");
        categories.setText("-");

        //default text
        changeSize();

        //open history
        final ImageView imgView = findViewById(R.id.imageView);
        imgView.setImageResource(R.drawable.img);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHistory();
            }
        });

        //calculate BMI
        final Button btn = findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal();
                saveFile();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeSize();
    }

    public void changeSize(){
        final TextView bmi = (TextView)findViewById(R.id.textView8);
        final TextView txt1 = (TextView)findViewById(R.id.textView4);
        final TextView txt2 = (TextView)findViewById(R.id.textView5);
        final TextView txt3 = (TextView)findViewById(R.id.textView6);
        final TextView txt4 = (TextView)findViewById(R.id.textView2);
        final TextView txt5 = (TextView)findViewById(R.id.textView7);
        final EditText weight = (EditText)findViewById(R.id.editTextWeight);
        final EditText height = (EditText)findViewById(R.id.editTextHeight);
        final TextView main = (TextView)findViewById(R.id.textView);
        final Button btn = (Button)findViewById(R.id.button3);

        Configuration config = getResources().getConfiguration();
        bmi.setTextSize(config.fontScale*20);
        txt1.setTextSize(config.fontScale*20);
        txt2.setTextSize(config.fontScale*20);
        txt3.setTextSize(config.fontScale*20);
        txt4.setTextSize(config.fontScale*20);
        txt5.setTextSize(config.fontScale*20);
        weight.setTextSize(config.fontScale*20);
        height.setTextSize(config.fontScale*20);
        main.setTextSize(config.fontScale*25);
        btn.setTextSize(config.fontScale*20);
    }

    public void saveFile(){
        String filename = "cs361.txt";
        String string = "Internal Storage";
        FileOutputStream outputStream;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                outputStream = new FileOutputStream(file);
                outputStream.write(string.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    DecimalFormat formatter = new DecimalFormat("#,###.##");

    public void openHistory() {
        Intent intent = new Intent(this, activity_history.class);
        startActivity(intent);
    }

    public void cal(){
        final TextView bmi = findViewById(R.id.textView8);
        final TextView status = findViewById(R.id.textView7);

        final EditText weight = findViewById(R.id.editTextWeight);
        final EditText height = findViewById(R.id.editTextHeight);

        if (weight.getText().toString().equals("") || height.getText().toString().equals("")) {
            bmi.setText("-");
            status.setText("-");
        } else {
            double w = Double.parseDouble(weight.getText().toString());
            double h = Double.parseDouble(height.getText().toString());
            double h_m = h/100;
            double bmiValue = w/(h_m * h_m);
            bmi.setText(formatter.format(bmiValue));

            if (bmiValue < 18.5) {
                status.setText(R.string.underweight);
            } else if (bmiValue >= 18.5 && bmiValue < 23) {
                status.setText(R.string.normal);
            } else if (bmiValue >= 23 && bmiValue < 25) {
                status.setText(R.string.overweight);
            } else if (bmiValue >= 25 && bmiValue < 30) {
                status.setText(R.string.obese1);
            } else if (bmiValue >= 30) {
                status.setText(R.string.obese2);
            }
        }
    }
}

class DecimalDigitsInputFilter implements InputFilter {
    private Pattern mPattern;
    DecimalDigitsInputFilter(int digits, int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digits - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches())
            return "";
        return null;
    }
}