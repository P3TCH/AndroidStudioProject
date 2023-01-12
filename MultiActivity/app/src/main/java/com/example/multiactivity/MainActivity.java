package com.example.multiactivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        try {
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            ImageButton btn1 = (ImageButton) findViewById(R.id.camera_btn);
                            btn1.setImageBitmap(imageBitmap);
                            TextView txt = (TextView) findViewById(R.id.textView);
                            txt.setText("Camera");
                        } catch (Exception e) {
                            Log.e("Log", "Error from Camera Activity");
                        }
                    }
                }
            });
    ActivityResultLauncher<Intent> activityResultLauncher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        try {
                            Uri videoUri = data.getData();
                            VideoView videoView = (VideoView) findViewById(R.id.videoView);
                            videoView.getLayoutParams().height = 400;
                            ImageView imageView = (ImageView) findViewById(R.id.imageView);
                            imageView.getLayoutParams().height = 0;
                            videoView.setVideoURI(videoUri);
                            videoView.setMediaController(new MediaController(MainActivity.this));
                            videoView.requestFocus();
                            TextView txt = (TextView) findViewById(R.id.textView);
                            txt.setText("Video : "+videoUri);
                        } catch (Exception e) {
                            Log.e("Log", "Error from Video Activity");
                        }
                    }
                }
            });
    ActivityResultLauncher<Intent> activityResultLauncher3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        try {
                            Uri uri = data.getData();
                            try {
                                VideoView videoView = (VideoView) findViewById(R.id.videoView);
                                videoView.getLayoutParams().height = 0;
                                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                imageView.getLayoutParams().height = 400;
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                imageView.setImageBitmap(bitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            TextView txt = (TextView) findViewById(R.id.textView);
                            txt.setText("Gallery : "+uri);
                        } catch (Exception e) {
                            Log.e("Log", "Error from Gallery Activity");
                        }
                    }
                }
            });
    ActivityResultLauncher<Intent> activityResultLauncher4 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        try {
                            Uri uri = data.getData();
                            try {
                                MediaPlayer mPlayer = new MediaPlayer();
                                mPlayer.setDataSource(getApplicationContext(), uri);
                                mPlayer.prepare();
                                mPlayer.start();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            TextView txt = (TextView) findViewById(R.id.textView);
                            txt.setText("Audio : "+uri);
                        } catch (Exception e) {
                            Log.e("Log", "Error from Audio Activity");
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton btn1 = (ImageButton) findViewById(R.id.camera_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncher1.launch(intent);

            }
        });
        final ImageButton btn2 = (ImageButton) findViewById(R.id.video_btn);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); //High Quality
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                activityResultLauncher2.launch(intent);
            }
        });
        final ImageButton btn3 = (ImageButton) findViewById(R.id.gallery_btn);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                Intent.createChooser(intent, "Select photo from");
                activityResultLauncher3.launch(intent);
            }
        });
        final ImageButton btn4 = (ImageButton) findViewById(R.id.audio_btn);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //To Do
                //Intent intent = new Intent(???);
                //activityResultLauncher4.launch(intent);
                //record audio
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                activityResultLauncher4.launch(intent);
            }  
        });
    }//end onCreate()

}//end Class