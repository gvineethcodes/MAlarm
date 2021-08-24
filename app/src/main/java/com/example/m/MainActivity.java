package com.example.m;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Button b;
    TextView textView;
    String text="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=findViewById(R.id.button);

        sharedpreferences = getSharedPreferences("MyPREFERECES", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        if(sharedpreferences.getBoolean("one",true)){

            Intent notifyIntent = new Intent(this, AlarmReceiver.class);

            final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this, 1111, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

            long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;

            /*if (alarmManager != null) {
                alarmManager.cancel(notifyPendingIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, repeatInterval, notifyPendingIntent);
            }*/

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, repeatInterval, notifyPendingIntent);
            editor.putBoolean("one", false);
            editor.commit();
        }
        Toast.makeText(getApplicationContext(), "ARUN", Toast.LENGTH_SHORT).show();



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying())
                    b.setVisibility(View.VISIBLE);
                else b.setVisibility(View.INVISIBLE);

            }
        } ,1000);*/

        if(sharedpreferences.getBoolean("play",false)){
            Toast.makeText(getApplicationContext(), "G", Toast.LENGTH_SHORT).show();
            
            /*
            textView = findViewById(R.id.textView);
        final StorageReference[] mStorageRef = new StorageReference[1];
        mStorageRef[0] = FirebaseStorage.getInstance().getReference();

        mStorageRef[0].listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        text="";
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.
                            //text=text+prefix.toString()+"\n";
                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(getApplicationContext(), "R", Toast.LENGTH_SHORT).show();

                                    try {
                                        mediaPlayer = new MediaPlayer();
                                        mediaPlayer.setDataSource(getApplicationContext(), uri);
                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                        mediaPlayer.prepare();
                                        mediaPlayer.start();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    textView.setText(""+listResult.getItems().size());

                                }
                            });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });
            */

            StorageReference mStorageRef;
            mStorageRef = FirebaseStorage.getInstance().getReference("/APATKALAMANDU YEHOVA NEEKU UTTARAMICHUNU Song.mp3");
            mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Toast.makeText(getApplicationContext(), "R", Toast.LENGTH_SHORT).show();

                        try {
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setDataSource(getApplicationContext(), uri);
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            });
            editor.putBoolean("play",false);
            editor.commit();
        }
    }
}
