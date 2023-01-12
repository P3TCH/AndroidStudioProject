package com.example.appicationresource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        final String[] tempArray = res.getStringArray(R.array.heros);
        final TextView txt1 = findViewById(R.id.textView);
        txt1.setText(tempArray[1]);

        final ImageView imgView = findViewById(R.id.imageView);
        imgView.setImageResource(R.drawable.pipe);

        final VideoView mVideoView = findViewById(R.id.videoView);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sec));
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Item 1", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_settings2) {
            Toast.makeText(this, "Item 2", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

