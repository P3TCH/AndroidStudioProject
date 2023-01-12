package com.example.bodymassindex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class activity_history extends AppCompatActivity {

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
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeSize();
    }

    public void changeSize(){
        final TextView textHis = (TextView)findViewById(R.id.textHistory);
        Configuration config = getResources().getConfiguration();
        textHis.setTextSize(config.fontScale*20);
    }
}